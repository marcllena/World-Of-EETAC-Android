package projecte.dsa.com.world_of_eetac_android.Game;

import projecte.dsa.com.world_of_eetac_android.Activities.GameActivity;
import projecte.dsa.com.world_of_eetac_android.Activities.LoginActivity;
import projecte.dsa.com.world_of_eetac_android.Celes.Cofre;
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
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView {
    private final Bitmap bmpBlood;
    //private List<TempSprite> temps = new ArrayList<TempSprite>();
    private List<ZombieMort> morts = new ArrayList<ZombieMort>();
    private SurfaceHolder holder;
    Context context;
    private GameLoopThread gameLoopThread;
    //private List<Sprite> sprites = new ArrayList<Sprite>();
    private List<Zombie> zombies = new ArrayList<Zombie>();
    private long lastClick;
    private Bitmap[] celdas=new Bitmap[5];
    Escena actual;
    private int anchoSurface;
    private int altoSurface;
    private int anchoSprite;
    private int altoSprite;

    private static final int ZOMBIESINICIALS_MULTIPLIER= 1;
    private static final int ZOMBIESMINIMS= 3;
    private static final int ZOMBIESRESPAWN_MULTIPLIER= 2;
    private static final int ZOMBIESRESPAWN_DELAY= 20000;//ms
    private static final int ZOMBIESRESPAWN_RATE=5000;
    private Jugador jugador;
    private Iterator<Zombie> iterator;
    private ListIterator<Zombie> listIterator=zombies.listIterator();
    //private ListIterator<TempSprite> listIteratorSang=temps.listIterator();
    private ListIterator<ZombieMort> listIteratorMorts=morts.listIterator();


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
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
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

    public void setAnchoSurface(int anchoSurface) {
        this.anchoSurface = anchoSurface;
    }

    public void setAltoSurface(int altoSurface) {
        this.altoSurface = altoSurface;
    }

    public int getAnchoSprite() {
        return anchoSprite;
    }

    public void setAnchoSprite(int anchoSprite) {
        this.anchoSprite = anchoSprite;
    }

    public int getAltoSprite() {
        return altoSprite;
    }

    public void setAltoSprite(int altoSprite) {
        this.altoSprite = altoSprite;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    /* private void createSprites() {
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
    }*/

    private Sprite createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(this, bmp);
    }

    private void createCeldas(){
        celdas[0]= BitmapFactory.decodeResource(getResources(), R.drawable.hierba);
        celdas[1]=BitmapFactory.decodeResource(getResources(), R.drawable.rio);
        celdas[2]=BitmapFactory.decodeResource(getResources(), R.drawable.cofre);
        celdas[3]=BitmapFactory.decodeResource(getResources(), R.drawable.puerta);
        celdas[4]=BitmapFactory.decodeResource(getResources(), R.drawable.cofre2);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        //Mirem si ja hem rebut la escena
        if(actual!=null) {
            actual.onDraw(canvas, altoSprite, anchoSprite);
            jugador.onDraw(canvas);
            //Fixem el fons*/
            //canvas.drawColor(Color.BLACK);

            for (listIteratorMorts=morts.listIterator();listIteratorMorts.hasNext();) {
                listIteratorMorts.next().onDraw(canvas);
            }
            for (int i = zombies.size() - 1; i >= 0; i--) {
                zombies.get(i).onDraw(canvas);
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
                for(int i=0;i<actual.getAlto();i++) {
                    for (int j = 0; j < actual.getAncho(); j++) {
                        if(actual.getDatos()[i][j].getInteractuable()==1)
                        {
                            int res =isCollision(i,j,x,y);
                            if(res==1)
                            {
                                Cofre cofre= (Cofre) actual.getDatos()[i][j];
                                cofre.setAbierto(true);
                                break;
                            }
                        }
                    }
                }
                for (listIterator= zombies.listIterator(); listIterator.hasNext(); ) {
                    Zombie sprite = listIterator.next();
                    if (sprite.isCollision(x,y)) {
                        listIterator.remove();
                        listIteratorMorts.add(new ZombieMort(morts,listIteratorMorts, this, x, y, bmpBlood));
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
                    anchoSurface= (int) (getHeight()*1.5);
                    altoSprite = altoSurface/ actual.getAlto();
                    anchoSprite = anchoSurface / actual.getAncho();
                    actual.setEscenas(celdas);
                    //createSprites();
                    jugador=new Jugador(GameView.this,1,context);
                    startRonda(1);

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

    public int isCollision(int i, int j,float x2, float y2) {
        if (y2 < (i+1) * altoSprite && y2 > (i * altoSprite) && x2 < (j+1) * anchoSprite && x2 > (j * anchoSprite)) {
            if (actual.getDatos()[i][j].getSimbolo().equals("X")) return 1;
            else if (actual.getDatos()[i][j].getSimbolo().equals("G")) return 2;
            else return 0;
        }
        return -1;
    }

    public void startRonda(final int numRonda){
        //Creem els primes zombies
        for (int i = 0; i < ZOMBIESMINIMS+ZOMBIESINICIALS_MULTIPLIER*numRonda; i++) {
                listIterator.add(new Zombie(this,(int)numRonda/5+1,context));
        }

        //Creem els de respawn
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < ZOMBIESRESPAWN_MULTIPLIER*numRonda; i++) {
                    listIterator.add(new Zombie(GameView.this,(int)numRonda/5+1,context));
                }
            }
        };
        timer.schedule(timerTask,ZOMBIESRESPAWN_DELAY,ZOMBIESRESPAWN_RATE);
    }

}
