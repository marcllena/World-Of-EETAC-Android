package projecte.dsa.com.world_of_eetac_android.Activities;

import projecte.dsa.com.world_of_eetac_android.Game.*;
import projecte.dsa.com.world_of_eetac_android.Mon.Escena;
import projecte.dsa.com.world_of_eetac_android.Mon.Globals;
import projecte.dsa.com.world_of_eetac_android.Mon.Partida;
import projecte.dsa.com.world_of_eetac_android.Mon.Usuario;
import projecte.dsa.com.world_of_eetac_android.R;
import projecte.dsa.com.world_of_eetac_android.Services.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import java.util.List;

public class GameActivity extends AppCompatActivity implements JoystickView.JoystickListener,InventarioView.InventarioListener{

    GameView gameView;
    JoystickView joystickView;
    static ProgressBar progressBarSalut;
    InventarioView inventarioView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView=(GameView) findViewById(R.id.gameView);
        gameView.setZOrderOnTop(false);
        gameView.getHolder().setFormat(PixelFormat.TRANSPARENT);

        joystickView=(JoystickView) findViewById(R.id.joystickView);
        int size=Math.min(this.getWindow().getDecorView().getWidth(),this.getWindow().getDecorView().getHeight());
        joystickView.getHolder().setFixedSize((size/4), (size/4));
        joystickView.setZOrderOnTop(true);
        SurfaceHolder jvHolder=joystickView.getHolder();
        jvHolder.setFormat(PixelFormat.TRANSPARENT);

        inventarioView=(InventarioView) findViewById(R.id.inventarioView);
        inventarioView.setVisibility(View.INVISIBLE);
        joystickView.getHolder().setFixedSize((int)(this.getWindow().getDecorView().getWidth()*2/3),(int)(this.getWindow().getDecorView().getHeight()*2/3));
        inventarioView.setZOrderOnTop(true);
        inventarioView.getHolder().setFormat(PixelFormat.TRANSPARENT);

        //si comentais la linea de abajo va, pero mirad si veis donde peta que la linea esta es para establecer el tamaño del inventario
        inventarioView.startInventario();

        progressBarSalut=(ProgressBar) findViewById(R.id.progressBarSalut);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               gameView.atacar();
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

    public static void setSalut(double val)
    {
        progressBarSalut.setProgress((int)val);
    }

    @Override
    public void onItemMoved(int posX, int posY, boolean moving, int source) {
        //Toast.makeText(this,"X: "+xPercent+" Y: "+yPercent,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemReleased(int posX, int posY,int itemPos, int source) {
        //comparar si posX+posY esta ocupado en el inventario
        //si lo esta intercambiar los dos objetos
        //si es negativo es que lo intenta equipar
        //comprobar que se puede equipar en ese hueco
        //intercambiar los items

    }
}
