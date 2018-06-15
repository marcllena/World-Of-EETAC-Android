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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;

import java.util.List;

public class GameActivity extends AppCompatActivity implements JoystickView.JoystickListener{

    GameView gameView;
    JoystickView joystickView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView=(GameView) findViewById(R.id.gameView);
        joystickView=(JoystickView) findViewById(R.id.joystickView);
        gameView.setAnchoSurface(this.getWindow().getDecorView().getBottom());
        gameView.setAltoSurface((int)(this.getWindow().getDecorView().getHeight()*4/6));

        gameView=new GameView(this);
        joystickView= new JoystickView(this);

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

    }
}
