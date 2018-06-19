package projecte.dsa.com.world_of_eetac_android.Game;

import projecte.dsa.com.world_of_eetac_android.Activities.GameActivity;
import projecte.dsa.com.world_of_eetac_android.Celes.Cofre;
import projecte.dsa.com.world_of_eetac_android.Celes.Puerta;
import projecte.dsa.com.world_of_eetac_android.Mon.Globals;
import projecte.dsa.com.world_of_eetac_android.Mon.Escena;
import projecte.dsa.com.world_of_eetac_android.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView{
    private final Bitmap bmpBlood;
    //private List<TempSprite> temps = new ArrayList<TempSprite>();
    private List<ZombieMort> morts = new ArrayList<ZombieMort>();
    private SurfaceHolder holder;
    Context context;
    private GameLoopThread gameLoopThread;
    //private List<Sprite> sprites = new ArrayList<Sprite>();
    private List<Zombie> zombies = new ArrayList<Zombie>();
    private long lastClick;
    private Bitmap[] celdas=new Bitmap[20];
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
    private static final int ZOMBIESRESPAWN_NUMERO=2;
    private static final int JUGADOR_DIRECCIOATAC=5;
    private static final int JUGADORMIN_SPRITES_SEPARACIO=2;
    private static final double JUGADOR_DANY_MINIM=5;
    private static final double JUGADOR_DANY_MULTIPLIER=1;
    private static final double JUGADOR_SEPARACIO_SPRITES_INTERACTUABLE=2;
    private Jugador jugador;
    private Iterator<Zombie> iterator;
    private ListIterator<Zombie> listIterator=zombies.listIterator();
    //private ListIterator<TempSprite> listIteratorSang=temps.listIterator();
    private ListIterator<ZombieMort> listIteratorMorts=morts.listIterator();
    private boolean fin=false;
    private int numRonda=1;
    private Canvas canvas;
    private GameActivity activity;

    public GameView(Context context) {
        super(context);
        this.context = context;
        gameLoopThread = new GameLoopThread(this);

        //Fixem el Bitmap
        holder = getHolder();

        //No s'executa aquesta funció
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //obtindreEscena("4"); //MAPA VELL
                actual = Globals.getInstance().getGame().map.getPantalles().get(0); //MAPA NOU
                altoSurface= getHeight();
                anchoSurface= (int) (getHeight()*1.5);
                altoSprite = altoSurface/ actual.getAlto();
                anchoSprite = anchoSurface / actual.getAncho();
                actual.setEscenas(celdas);
                //createSprites();
                jugador=new Jugador(GameView.this,1,context);
                //Posem el marcador de salut
                activity = Globals.getInstance().getGameActivity();
                activity.setSalutMax(jugador.getSalut());
                activity.setSalut(jugador.getSalut());
                activity.setRonda(numRonda);
                //startRonda(1);//Falta obtindre el numero de ronda
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
    public GameView(Context context,AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        gameLoopThread = new GameLoopThread(this);

        //Fixem el Bitmap
        holder = getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //obtindreEscena("4"); //MAPA VELL
                actual = Globals.getInstance().getGame().map.getPantalles().get(0); //MAPA NOU
                altoSurface= getHeight();
                anchoSurface= (int) (getHeight()*1.5);
                altoSprite = altoSurface/ actual.getAlto();
                anchoSprite = anchoSurface / actual.getAncho();
                actual.setEscenas(celdas);
                //createSprites();
                jugador=new Jugador(GameView.this,1,context);
                //Posem el marcador de salut
                activity = Globals.getInstance().getGameActivity();
                activity.setSalutMax(jugador.getSalut());
                activity.setSalut(jugador.getSalut());
                activity.setRonda(numRonda);
                startRonda(numRonda);
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
    public List<ZombieMort> getMorts() {
        return morts;
    }

    public List<Zombie> getZombies() {
        return zombies;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }


    private void createCeldas(){
        celdas[0]= BitmapFactory.decodeResource(getResources(), R.drawable.hierba);
        celdas[1]=BitmapFactory.decodeResource(getResources(), R.drawable.rio);
        celdas[2]=BitmapFactory.decodeResource(getResources(), R.drawable.cofre);
        celdas[3]=BitmapFactory.decodeResource(getResources(), R.drawable.puerta);
        celdas[4]=BitmapFactory.decodeResource(getResources(), R.drawable.cofre2);
        celdas[5]=BitmapFactory.decodeResource(getResources(), R.drawable.wall);
        celdas[6]=BitmapFactory.decodeResource(getResources(), R.drawable.forat);
        celdas[7]=BitmapFactory.decodeResource(getResources(), R.drawable.window);
        celdas[8]=BitmapFactory.decodeResource(getResources(), R.drawable.casco);
        celdas[9]=BitmapFactory.decodeResource(getResources(), R.drawable.armadura);
        celdas[10]=BitmapFactory.decodeResource(getResources(), R.drawable.malla);
        celdas[11]=BitmapFactory.decodeResource(getResources(), R.drawable.botas);
        celdas[12]=BitmapFactory.decodeResource(getResources(), R.drawable.arcodeflechas);
        celdas[13]=BitmapFactory.decodeResource(getResources(), R.drawable.espadalarga);
        celdas[14]=BitmapFactory.decodeResource(getResources(), R.drawable.bastonmagico);
        celdas[15]=BitmapFactory.decodeResource(getResources(), R.drawable.pocion);



    }



    @Override
    protected void onDraw(Canvas canvas) {
        //Mirem si ja hem rebut la escena
        if(actual!=null) {
            actual.onDraw(canvas, altoSprite, anchoSprite);
            for (int i = 0; i <morts.size(); i++) {
                morts.get(i).onDraw(canvas);
            }
            jugador.onDraw(canvas);
            for (int i = zombies.size() - 1; i >= 0; i--) {
                zombies.get(i).onDraw(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InventarioView inventarioView=Globals.getInstance().getGameActivity().getInventarioView();
        GameActivity gameActivity=Globals.getInstance().getGameActivity();
        int min=(gameActivity.getWindow().getDecorView().getWidth()-inventarioView.getWidth())/2;
        int max=(gameActivity.getWindow().getDecorView().getWidth()-min);
        if (inventarioView.getVisibility() == View.INVISIBLE) {
        if (System.currentTimeMillis() - lastClick > 300) {

                lastClick = System.currentTimeMillis();
                synchronized (getHolder()) {
                    float x = event.getX();
                    float y = event.getY();
                    for (int i = 0; i < actual.getAlto(); i++) {
                        for (int j = 0; j < actual.getAncho(); j++) {
                            if (actual.getDatos()[i][j].getInteractuable() == 1) {
                                int res = isCollision(i, j, x, y);
                                if (res == 1) {
                                    double dis = Math.sqrt(Math.pow(jugador.getX() - x, 2) + Math.pow(jugador.getY() - y, 2));
                                    if (((dis) / this.getAltoSprite()) < JUGADOR_SEPARACIO_SPRITES_INTERACTUABLE) {
                                        Cofre cofre = (Cofre) actual.getDatos()[i][j];
                                        cofre.setAbierto(true);
                                        InventarioView iv = Globals.getInstance().getGameActivity().getInventarioView();

                                        iv.setVisibility(View.VISIBLE);
                                        iv.setupDimensions();
                                        iv.cargarCeldas();
                                        iv.onDrawCofre(cofre.getContenido(), -2, 0, 0);

                                        break;
                                    }
                                } else if (res == 2) {
                                    double dis = Math.sqrt(Math.pow(jugador.getX() - x, 2) + Math.pow(jugador.getY() - y, 2));
                                    if (((dis) / this.getAltoSprite()) < JUGADOR_SEPARACIO_SPRITES_INTERACTUABLE) {
                                        Puerta porta = (Puerta) actual.getDatos()[i][j];
                                        actual = Globals.getInstance().getGame().map.getPantalles().get(porta.getTeleport().idEscenario); //MAPA NOU
                                        actual.setEscenas(celdas);
                                        resetZombies();
                                    }

                                }
                            }
                        }
                    }
                    for (listIterator = zombies.listIterator(); listIterator.hasNext(); ) {
                        Zombie sprite = listIterator.next();
                        if (sprite.isCollision(x, y)) {
                            listIterator.remove();
                            ListIterator<ZombieMort> iterator = morts.listIterator();
                            iterator.add(new ZombieMort(morts, this, x, y, bmpBlood));
                            break;
                        }
                    }
                }
            }
            return true;
        }
        else{
            Globals.getInstance().getGameActivity().getInventarioView().Touch(event,event.getX()-min,event.getY());
            return true;
        }
    }
    /*public void obtindreEscena(String id) {
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
                    //Posem el marcador de salut
                    GameActivity.setSalutMax(jugador.getSalut());
                    GameActivity.setSalut(jugador.getSalut());
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
    }*/

    public int isCollision(int i, int j,float x2, float y2) {
        if (y2 < (i+1) * altoSprite && y2 > (i * altoSprite) && x2 < (j+1) * anchoSprite && x2 > (j * anchoSprite)) {
            if (actual.getDatos()[i][j].getSimbolo().equals("X")) return 1;
            else if (actual.getDatos()[i][j].getSimbolo().equals("G")) return 2;
            else return 0;
        }
        return -1;
    }

    public boolean startRonda(final int numRonda){
        //GameActivity.setRonda(numRonda);
        final int[] zombiesRespawn = {ZOMBIESRESPAWN_NUMERO};
        fin = false;
        //Creem els primes zombies
        for (int i = 0; i < ZOMBIESMINIMS+ZOMBIESINICIALS_MULTIPLIER*numRonda; i++) {
            listIterator.add(new Zombie(this,(int)numRonda/3+1,context));
        }

        //Creem els de respawn
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                zombiesRespawn[0]--;
                if(zombiesRespawn[0]>0) {
                    for (int i = 0; i < ZOMBIESRESPAWN_MULTIPLIER * numRonda; i++) {
                        listIterator.add(new Zombie(GameView.this, (int) numRonda / 3 + 1, context));
                    }
                }
                else{
                    fin =true;
                    novaRonda();
                    timer.cancel();
                    timer.purge();
                }
            }
        };
        timer.schedule(timerTask,ZOMBIESRESPAWN_DELAY,ZOMBIESRESPAWN_RATE);
        return fin;
    }
    public void atacar(){
        //Mirem cap on es dirigeix el jugador
        int jugadorX=jugador.getX()+(jugador.getWidth()/2)+JUGADOR_DIRECCIOATAC*jugador.getxSpeed();
        int jugadorY=jugador.getY()+(jugador.getHeight()/2)+JUGADOR_DIRECCIOATAC*jugador.getySpeed();
        for (listIterator= zombies.listIterator(); listIterator.hasNext(); ) {
            Zombie objectiu = listIterator.next();
            int zombieX=objectiu.getX()+(objectiu.getWidth()/2);
            int zombieY=objectiu.getY()+(objectiu.getHeight()/2);
            double dis= Math.sqrt(Math.pow(jugadorX-zombieX,2)+Math.pow(jugadorY-zombieY,2));
            if(((dis)/this.getAltoSprite())<JUGADORMIN_SPRITES_SEPARACIO)
            {
                double dany=JUGADOR_DANY_MINIM+JUGADOR_DANY_MULTIPLIER;//Depenent de l'arma que utilitzi
                objectiu.restarSalut(dany);
                if(objectiu.getSalut()<0) {
                    listIterator.remove();
                    ListIterator<ZombieMort> iterator= morts.listIterator();
                    iterator.add(new ZombieMort(morts, this, objectiu.getX(), objectiu.getY(), bmpBlood));
                    break;
                }
            }
        }
    }
    public void resetZombies(){
        for (listIterator= zombies.listIterator(); listIterator.hasNext(); ) {
            Zombie zombie = listIterator.next();
            zombie.newRespawn();

        }
    }
    public void novaRonda(){
    numRonda++;
    activity.setRonda(numRonda);
    startRonda(numRonda);
    }

}
