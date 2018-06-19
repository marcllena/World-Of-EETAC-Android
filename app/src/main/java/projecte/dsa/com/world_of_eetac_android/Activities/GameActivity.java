package projecte.dsa.com.world_of_eetac_android.Activities;

import projecte.dsa.com.world_of_eetac_android.Game.*;
import projecte.dsa.com.world_of_eetac_android.Mon.Escena;
import projecte.dsa.com.world_of_eetac_android.Mon.Globals;
import projecte.dsa.com.world_of_eetac_android.Mon.Partida;
import projecte.dsa.com.world_of_eetac_android.Mon.Usuario;
import projecte.dsa.com.world_of_eetac_android.Objectes.Objeto;
import projecte.dsa.com.world_of_eetac_android.R;
import projecte.dsa.com.world_of_eetac_android.Services.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.logging.Handler;

public class GameActivity extends AppCompatActivity implements JoystickView.JoystickListener,InventarioView.InventarioListener{

    GameView gameView;
    JoystickView joystickView;
    static ProgressBar progressBarSalut;
    private InventarioView inventarioView;
    static TextView textViewRonda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Globals.getInstance().setGameActivity(this);
        setContentView(R.layout.activity_game);

        gameView=(GameView) findViewById(R.id.gameView);
        gameView.setZOrderOnTop(false);
        gameView.getHolder().setFormat(PixelFormat.TRANSPARENT);

        joystickView=(JoystickView) findViewById(R.id.joystickView);
        int size=Math.min(this.getWindow().getDecorView().getWidth(),this.getWindow().getDecorView().getHeight());
        joystickView.getHolder().setFixedSize((size/4), (size/4));
        joystickView.setZOrderMediaOverlay(true);
        SurfaceHolder jvHolder=joystickView.getHolder();
        jvHolder.setFormat(PixelFormat.TRANSPARENT);

        inventarioView=(InventarioView) findViewById(R.id.inventarioView);
        inventarioView.setVisibility(View.VISIBLE);


        //inventarioView.getHolder().setFixedSize((int)(this.getWindow().getDecorView().getWidth()*3/4),(int)(this.getWindow().getDecorView().getHeight()*3/4));
        inventarioView.setZOrderOnTop(true);
        //inventarioView.getHolder().setFormat(PixelFormat.TRANSPARENT);

        //si comentais la linea de abajo va, pero mirad si veis donde peta que la linea esta es para establecer el tamaño del inventario
        //inventarioView.startInventario();

        progressBarSalut=(ProgressBar) findViewById(R.id.progressBarSalut);
        textViewRonda =(TextView) findViewById(R.id.textViewRonda);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               gameView.atacar();
            }
        });
        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongCall")
            @Override
            public void onClick(View view) {
                if(inventarioView.getVisibility()==View.VISIBLE) {
                    //gameView.setVisibility(View.VISIBLE);
                    inventarioView.setVisibility(View.INVISIBLE);


                }
                else{
                    //gameView.setVisibility(View.INVISIBLE);
                    inventarioView.setVisibility(View.VISIBLE);
                    inventarioView.setupDimensions();
                    inventarioView.cargarCeldas();
                    inventarioView.onDraw(-1,0,0);

                }
            }
        });

        //gameView.setAnchoSurface(this.getWindow().getDecorView().getBottom());
        //gameView.setAltoSurface((int)(this.getWindow().getDecorView().getHeight()*4/6));

        //gameView=new GameView(this);
        //joystickView= new JoystickView(this);

        //setContentView(new GameView(this));
    }
    public void nextRoundGame(List<Escena> escenaList) {
        Partida game = Globals.getInstance().getGame();
        RetrofitAPI servei = Globals.getInstance().getServeiRetrofit();
        Call<Partida> callPartida = servei.nextRoundGame(game);
        callPartida.enqueue(new Callback<Partida>() {
            int resultat = -1;

            @Override
            public void onResponse(Call<Partida> call, Response<Partida> resposta) {
                int codi = resposta.code();
                Log.d("Proba ", "Codi agafat" + codi);
                if (codi == 200) {
                    Partida game = (Partida) resposta.body();
                    //CONTINUAR PARTIDA
                } else if (codi == 204) {
                    resultat = -1;
                }
                if (resultat == -1) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(GameActivity.this);
                    dlgAlert.setMessage("Error al continuar la partida");
                    dlgAlert.setTitle("Error en les dades");
                    dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
            }

            @Override
            public void onFailure(Call<Partida> call, Throwable t) {
                // log error here since request failed
                Log.d("Request: ", "error loading API" + t.toString());

            }
        });
    }
    public void endGame(List<Escena> escenaList) {
        Partida game = Globals.getInstance().getGame();

        RetrofitAPI servei = Globals.getInstance().getServeiRetrofit();
        Call<Partida> callPartida = servei.nextRoundGame(game);
        callPartida.enqueue(new Callback<Partida>() {
            int resultat = -1;

            @Override
            public void onResponse(Call<Partida> call, Response<Partida> resposta) {
                int codi = resposta.code();
                Log.d("Proba ", "Codi agafat" + codi);
                if (codi == 200) {
                    Partida game = (Partida) resposta.body();
                    Globals.getInstance().setGame(new Partida());
                } else if (codi == 204) {
                    resultat = -1;
                }
                if (resultat == -1) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(GameActivity.this);
                    dlgAlert.setMessage("Error al finalitzar la partida");
                    dlgAlert.setTitle("Error en les dades");
                    dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
            }

            @Override
            public void onFailure(Call<Partida> call, Throwable t) {
                // log error here since request failed
                Log.d("Request: ", "error loading API" + t.toString());

            }
        });
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int source) {
        gameView.getJugador().setMoviment(xPercent,yPercent);
    }

    public static void setSalutMax(double max)
    {
        progressBarSalut.setMinimumHeight(20);
        progressBarSalut.setVisibility(View.VISIBLE);
        progressBarSalut.setMax((int)max);
    }

    public  void setRonda(int ronda)
    {
        this.runOnUiThread(new Runnable() {
            public void run() {
                textViewRonda.setText(String.valueOf(ronda));
            }
        });

    }

    public static void setSalut(double val)
    {
        progressBarSalut.setProgress((int)val);
    }

    @Override
    public void onItemMoved(int posX, int posY, boolean moving, int source) {
        Toast.makeText(this,"Me tocas",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemReleased(int posX, int posY,int itemPos, int source) {
        if(Globals.getInstance().getObjetos()[posX+posY]==null)//comparar si posX+posY esta ocupado en el inventario
        {
            Globals.getInstance().getObjetos()[posX+posY]=Globals.getInstance().getObjetos()[itemPos];
        }
        else//si lo esta intercambiar los dos objetos
        {
            Objeto o=Globals.getInstance().getObjetos()[posX+posY];
            Globals.getInstance().getObjetos()[posX+posY]=Globals.getInstance().getObjetos()[itemPos];
            Globals.getInstance().getObjetos()[itemPos]=o;
        }
        //si es negativo posX, es que lo intenta equipar
        //comprobar que se puede equipar en ese hueco
        //intercambiar los items

    }

    @Override
    public void exitInventario(int source) {
        inventarioView.setVisibility(View.INVISIBLE);
        gameView.setVisibility(View.VISIBLE);
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public JoystickView getJoystickView() {
        return joystickView;
    }

    public void setJoystickView(JoystickView joystickView) {
        this.joystickView = joystickView;
    }

    public InventarioView getInventarioView() {
        return inventarioView;
    }

    public void setInventarioView(InventarioView inventarioView) {
        this.inventarioView = inventarioView;
    }
}
