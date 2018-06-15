package projecte.dsa.com.world_of_eetac_android.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ListIterator;
import java.util.Random;

import projecte.dsa.com.world_of_eetac_android.Activities.GameActivity;
import projecte.dsa.com.world_of_eetac_android.R;


public class Zombie {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_COLUMNS = 3;
    private static final int BMP_ROWS = 4;
    private int width;
    private int height;
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private GameView gameView;
    private Bitmap bmp;
    private int currentFrame;

    private static final int SPEED_MULTIPLIER= 3;
    private static final int SALUT_MULTIPLIER= 5;
    private static final int LAG_MULTIPLIER= 4;
    private static final double LAG_MAXIM= 0.5;
    private static final int LAG_DIREC= 0;
    private static final int MIN_SPRITES_SEPARACIO=3;
    private static final int DETECCTIO_OBS=20;//FPS
    private static final double DIVERGENCIA_OBS=0.9;//FPS
    private static final int SPRITES_SEPARACIO_DANY=2;
    private static final double DANY_MINIM=1.5;
    private static final double DANY_MULTIPLIER=0.5;
    private static final double SALUT_MINIMA=20;
    private int nivell;
    private double salut;
    int obstacle=0;
    private Bitmap bmpBlood=null;

    public Zombie(GameView gameView,int nivell, Context context) {
        this.gameView=gameView;
        this.nivell=nivell;
        int id = context.getResources().getIdentifier("zombie"+nivell, "drawable", context.getPackageName());
        bmp = BitmapFactory.decodeResource(context.getResources(),id);
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        //Fixem la posició aleatoria
        Random rnd = new Random();
        for (int k = 0; k < 10; k++) {
            x = rnd.nextInt(gameView.getAnchoSurface() - width);
            y = rnd.nextInt(gameView.getAltoSurface()- height);
            int i = y/gameView.getAltoSprite();
            int j = x/gameView.getAnchoSprite();
            if(gameView.actual.getDatos()[i][j].getPisable()==1&&MIN_SPRITES_SEPARACIO*gameView.getAnchoSprite()<Math.abs(x-gameView.getJugador().getX())&&MIN_SPRITES_SEPARACIO*gameView.getAltoSprite()<Math.abs(y-gameView.getJugador().getY()))
                break;
        }

        double lagSpeed =  LAG_MAXIM + (1 - LAG_MAXIM)*rnd.nextDouble();

        //Fixem que vagi cap al usuari amb velocitat en funció del nivell
        double theta = Math.atan2(gameView.getJugador().getY() - y,gameView.getJugador().getX() - x);
        xSpeed = (int) ((int) (lagSpeed*10+SPEED_MULTIPLIER*nivell)* Math.cos(theta));
        ySpeed = (int) ((int) (lagSpeed*10+SPEED_MULTIPLIER*nivell) * Math.sin(theta));

        //Fixem Salut en funció del nivell
        salut=SALUT_MINIMA+SALUT_MULTIPLIER*nivell;
         bmpBlood = BitmapFactory.decodeResource(context.getResources(), R.drawable.blood1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return bmp.getWidth();
    }

    public int getHeight() {
        return bmp.getHeight();
    }

    public double getSalut() {
        return salut;
    }

    private void update() {
        //Detectem si la celda a la que va es pisable
        int a = ((y + (ySpeed / 2)+(height) )/ gameView.getAltoSprite());
        int b;
        if(xSpeed>0){
            b = ((x + (xSpeed / 2)+(width) )/ gameView.getAnchoSprite());
        }
       else{
            b = (x + (xSpeed / 2)/ gameView.getAnchoSprite());
        }
        boolean perseguir=false;
        if (x > gameView.getAnchoSurface() - width - xSpeed || x + xSpeed < 0) {
            xSpeed = -xSpeed;
            perseguir=false;
            obstacle=0;
        } else if (y > gameView.getAltoSurface() - height - ySpeed || y + ySpeed < 0) {
            ySpeed = -ySpeed;
            perseguir=false;
            obstacle=0;
        }
        else if (a>-1&&b>-1&&a<gameView.actual.getAlto()&&b<gameView.actual.getAncho()&&gameView.actual.getDatos()[a][b].getPisable() == 0) {
            perseguir=false;
           obstacle=DETECCTIO_OBS;
        } else {
            if(obstacle>0)
                obstacle--;
            perseguir=true;
        }
        if(obstacle==DETECCTIO_OBS)
        {
            Random rnd = new Random();
            xSpeed = (int) (-(DIVERGENCIA_OBS + (1 - DIVERGENCIA_OBS)* rnd.nextDouble())*xSpeed);
            ySpeed = (int) (-(DIVERGENCIA_OBS + (1 - DIVERGENCIA_OBS)* rnd.nextDouble())*xSpeed);
            obstacle--;
        }
        else if(perseguir&&obstacle==0){
            Random rnd = new Random();
            double lagDirec = LAG_MAXIM + (LAG_DIREC - LAG_MAXIM) * LAG_MULTIPLIER * rnd.nextDouble();
            double theta = Math.atan2((gameView.getJugador().getY() - lagDirec * gameView.getJugador().getySpeed()) - y, (gameView.getJugador().getX() - lagDirec * gameView.getJugador().getxSpeed()) - x);
            double lagSpeed = LAG_MAXIM + (1 - LAG_MAXIM) * LAG_MULTIPLIER * rnd.nextDouble();
            xSpeed = (int) ((2 + lagSpeed * SPEED_MULTIPLIER * nivell) * Math.cos(theta));
            ySpeed = (int) ((2 + lagSpeed * SPEED_MULTIPLIER * nivell) * Math.sin(theta));
        }
        x = x + xSpeed;
        y = y + ySpeed;
        currentFrame = ++currentFrame % BMP_COLUMNS;
        //Calculem si fa dany al jugador
        int disX=((gameView.getJugador().getX()+(gameView.getJugador().getWidth()/2)) - (x+(bmp.getWidth()/2)));
        int disY=((gameView.getJugador().getY()+(gameView.getJugador().getHeight()/2)) - (y+bmp.getHeight()/2));
        double dis= Math.sqrt(Math.pow(disX,2)+Math.pow(disY,2));
        if(((dis)/gameView.getAltoSprite())<SPRITES_SEPARACIO_DANY)
        {
            double dany=DANY_MINIM+DANY_MULTIPLIER*nivell;
            gameView.getJugador().restarSalut(dany);
        }
    }



    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp,src,dst,null);
    }

    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public boolean isCollision(float x2, float y2) {
        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }
    public void restarSalut(double daño) {
        this.salut = this.salut-daño;
        /*if(salut<0) {
            for (ListIterator<Zombie> listIterator= gameView.getZombies().listIterator(); listIterator.hasNext(); ) {
                Zombie zombie = listIterator.next();
                if (zombie.getX()==x&&zombie.getY()==y) {
                    listIterator.remove();
                    ListIterator<ZombieMort> iterator= gameView.getMorts().listIterator();
                    iterator.add(new ZombieMort(gameView.getMorts(), gameView, x, y, bmpBlood));
                    break;
                }
            }
        }*/
    }
}


