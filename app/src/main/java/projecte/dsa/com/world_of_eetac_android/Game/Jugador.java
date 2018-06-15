package projecte.dsa.com.world_of_eetac_android.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

import projecte.dsa.com.world_of_eetac_android.Activities.GameActivity;


public class Jugador {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_COLUMNS = 3;
    private static final int BMP_ROWS = 4;
    private final int width;
    private final int height;
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private GameView gameView;
    private Bitmap bmp;
    private int currentFrame;

    private int nivell;
    private int salut;
    Context context;

    public Jugador(GameView gameView,int nivell, Context context) {
        this.gameView=gameView;
        this.nivell=nivell;
        int id = context.getResources().getIdentifier("bad"+nivell, "drawable", context.getPackageName());
        bmp = BitmapFactory.decodeResource(context.getResources(),id);
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        salut=100;
        Random rnd = new Random();
        x = rnd.nextInt(gameView.getAnchoSurface() - width);
        y = rnd.nextInt(gameView.getAltoSurface()- height);
        xSpeed = rnd.nextInt(10 * 2) - 10;
        ySpeed = rnd.nextInt(10 * 2) - 10;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public int getSalut() {
        return salut;
    }

    public void setSalut(int salut) {
        this.salut = salut;
    }

    private void update() {
        if(xSpeed!=0||ySpeed!=0) {
            if (x > gameView.getAnchoSurface() - width - xSpeed || x + xSpeed < 0) {
                xSpeed = -xSpeed;
            }
            x = x + xSpeed;
            if (y > gameView.getAltoSurface() - height - ySpeed || y + ySpeed < 0) {
                ySpeed = -ySpeed;
            }
            y = y + ySpeed;
            currentFrame = ++currentFrame % BMP_COLUMNS;
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
}



