package projecte.dsa.com.world_of_eetac_android.Game;

import projecte.dsa.com.world_of_eetac_android.Activities.LoginActivity;
import projecte.dsa.com.world_of_eetac_android.Mon.Globals;
import projecte.dsa.com.world_of_eetac_android.Mon.Escena;
import projecte.dsa.com.world_of_eetac_android.Mon.Usuario;
import projecte.dsa.com.world_of_eetac_android.Services.RetrofitAPI;
import projecte.dsa.com.world_of_eetac_android.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

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
    Escena actual;
    private int anchoSurface;
    private int altoSurface;
    private int anchoSprite;
    private int altoSprite;


    public GameView(Context context) {
        super(context);
        this.context = context;
        gameLoopThread = new GameLoopThread(this);

        //Fixem el Bitmap
        holder = getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                obtindreEscena("2");
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

    public int getAnchoSurface() {
        return anchoSurface;
    }

    public int getAltoSurface() {
        return altoSurface;
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
        celdas[0]= BitmapFactory.decodeResource(getResources(), R.drawable.hierba);
        celdas[1]=BitmapFactory.decodeResource(getResources(), R.drawable.rio);
        celdas[2]=BitmapFactory.decodeResource(getResources(), R.drawable.cofre);
        celdas[3]=BitmapFactory.decodeResource(getResources(), R.drawable.puerta);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        //Mirem si ja hem rebut la escena
        if(actual!=null) {
            actual.onDraw(canvas, altoSprite, anchoSprite);
            //Fixem el fons*/
            //canvas.drawColor(Color.BLACK);
            for (int i = temps.size() - 1; i >= 0; i--) {
                temps.get(i).onDraw(canvas);
            }
            for (Sprite sprite : sprites) {
                sprite.onDraw(canvas);
            }
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
    public void obtindreEscena(String id) {
        RetrofitAPI servei = Globals.getInstance().getServeiRetrofit();
        Call<Escena> callEscena = servei.escenas(id);
        // Fetch and print a list of the contributors to the library.
        callEscena.enqueue(new Callback<Escena>() {

            @Override
            public void onResponse(Call<Escena> Escena, Response<Escena> resposta) {
                int codi = resposta.code();
                if (codi == 200) {
                    actual = (Escena) resposta.body();
                    altoSurface= getHeight();
                    anchoSurface=getHeight();
                    altoSprite = altoSurface/ actual.getAlto();
                    anchoSprite = anchoSurface / actual.getAncho();
                    actual.setEscenas(celdas);
                    createSprites();

                } else if (codi == 204) {
                    Log.e("Escena", "Id no trobat");
                }
            }

            @Override
            public void onFailure(Call<Escena> call, Throwable t) {
                // log error here since request failed
                Log.d("Request: ", "error loading API" + t.toString());

            }
        });
    }
}
