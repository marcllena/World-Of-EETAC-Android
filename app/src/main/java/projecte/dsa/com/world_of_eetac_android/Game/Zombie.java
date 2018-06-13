package projecte.dsa.com.world_of_eetac_android.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

import projecte.dsa.com.world_of_eetac_android.Activities.GameActivity;


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
    private static final int SALUT_MULTIPLIER= 3;
    private int nivell;
    private int salut;
    Context context;

    public Zombie(GameView gameView,int nivell, Context context) {
        this.gameView=gameView;
        this.nivell=nivell;
        int id = context.getResources().getIdentifier("zombie"+nivell, "drawable", context.getPackageName());
        bmp = BitmapFactory.decodeResource(context.getResources(),id);
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        //Fixem la posició aleatoria
        Random rnd = new Random();
        x = rnd.nextInt(gameView.getAnchoSurface() - width);
        y = rnd.nextInt(gameView.getAltoSurface()- height);
        //Fixem que vagi cap al usuari amb velocitat en funció del nivell
        double theta = Math.atan2(gameView.getJugador().getY() - y,gameView.getJugador().getX() - x);
        xSpeed = (int) ((10+SPEED_MULTIPLIER*nivell)* Math.cos(theta));
        ySpeed = (int) ((10+SPEED_MULTIPLIER*nivell) * Math.sin(theta));
        //Fixem que tingui salut aleatoria
    }

    private void update() {
            Random rnd = new Random();
            double lag = rnd.nextDouble();
            double theta = Math.atan2((gameView.getJugador().getY() - lag * gameView.getJugador().getySpeed()) - y, (gameView.getJugador().getX() - lag * gameView.getJugador().getxSpeed()) - x);
            xSpeed = (int) ((2 + SPEED_MULTIPLIER * nivell) * Math.cos(theta));
            ySpeed = (int) ((2 + SPEED_MULTIPLIER * nivell) * Math.sin(theta));
        x = x + xSpeed;
        y = y + ySpeed;
        currentFrame = ++currentFrame % BMP_COLUMNS;
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
}


