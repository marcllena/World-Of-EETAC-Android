package projecte.dsa.com.world_of_eetac_android.Game;

import projecte.dsa.com.world_of_eetac_android.Mon.Globals;
import projecte.dsa.com.world_of_eetac_android.Mon.Escena;
import projecte.dsa.com.world_of_eetac_android.Services.RetrofitAPI;
import projecte.dsa.com.world_of_eetac_android.R;
import retrofit2.Call;
import retrofit2.Response;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView {
    private final Bitmap bmpBlood;
    private List<TempSprite> temps = new ArrayList<TempSprite>();
    private SurfaceHolder holder;
    Context context;
    private GameLoopThread gameLoopThread;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private long lastClick;
    private Bitmap[] celdas=new Bitmap[4];


    public GameView(Context context) {
        super(context);
        this.context = context;
        gameLoopThread = new GameLoopThread(this);

        //Fixem el Bitmap
        holder = getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createSprites();
                createCeldas();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {

                    }

                }
            }
        });
        bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);
    }

    private void createSprites() {
        sprites.add(createSprite(R.drawable.bad1));
        sprites.add(createSprite(R.drawable.bad2));
        sprites.add(createSprite(R.drawable.bad3));
        sprites.add(createSprite(R.drawable.bad4));
        sprites.add(createSprite(R.drawable.bad5));
        sprites.add(createSprite(R.drawable.bad6));
        sprites.add(createSprite(R.drawable.good1));
        sprites.add(createSprite(R.drawable.good2));
        sprites.add(createSprite(R.drawable.good3));
        sprites.add(createSprite(R.drawable.good4));
        sprites.add(createSprite(R.drawable.good5));
        sprites.add(createSprite(R.drawable.good6));
    }

    private Sprite createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(this, bmp);
    }

    private void createCeldas(){
        celdas[0]= BitmapFactory.decodeResource(getResources(), R.drawable.bad1);
        celdas[1]=BitmapFactory.decodeResource(getResources(), R.drawable.bad1);
        celdas[2]=BitmapFactory.decodeResource(getResources(), R.drawable.bad1);
        celdas[3]=BitmapFactory.decodeResource(getResources(), R.drawable.bad1);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        RetrofitAPI servei = Globals.getInstance().getServeiRetrofit();
        Call<Escena> callUser = servei.escenas("2");
        Response resposta=null;
        try
        {
            resposta = callUser.execute();
        }
        catch (IOException excepcio)
        {

        }
        int resultat = resposta.code();
        Log.d("Proba ", "Codi agafat" + resultat);
        //Escena escena= FuncionsRetrofit.obtindreEscena(2);
        //escena.setEscenas(celdas);
        //int altoSprite= getHeight()/escena.getAlto();
        //int anchoSprite=getWidth()/escena.getAncho();
        //escena.onDraw(canvas,altoSprite,anchoSprite);*/
        //Fixem el fons
        canvas.drawColor(Color.BLACK);
        for (int i = temps.size() - 1; i >= 0; i--) {
            temps.get(i).onDraw(canvas);
        }
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - lastClick > 300) {
            lastClick = System.currentTimeMillis();
            synchronized (getHolder()) {
                float x=event.getX();
                float y=event.getY();
                for (int i = sprites.size() - 1; i >= 0; i--) {
                    Sprite sprite = sprites.get(i);
                    if (sprite.isCollision(x,y)) {
                        sprites.remove(sprite);
                        temps.add(new TempSprite(temps, this, x, y, bmpBlood));
                        break;
                    }
                }
            }
        }
        return true;
    }
}
