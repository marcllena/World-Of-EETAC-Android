package projecte.dsa.com.world_of_eetac_android.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;


import projecte.dsa.com.world_of_eetac_android.ListAdapters.UsuarisListArrayAdapter;
import projecte.dsa.com.world_of_eetac_android.Mon.Globals;
import projecte.dsa.com.world_of_eetac_android.Mon.Jugador;
import projecte.dsa.com.world_of_eetac_android.Mon.Partida;
import projecte.dsa.com.world_of_eetac_android.Mon.Usuario;
import projecte.dsa.com.world_of_eetac_android.R;
import projecte.dsa.com.world_of_eetac_android.Services.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    List<Usuario> usuaris;
    Globals dades;
    int eleccio;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //Falta fer el override bo
    @Override
    protected void onStart() {
        super.onStart();
        //Mostrem la Llista
        usuarisLlista();
        dades=Globals.getInstance();

    }
    public void startGame(int eleccio) {
        Partida game = new Partida(Globals.getInstance().getUser().getNickname(),eleccio,1);
        RetrofitAPI servei = Globals.getInstance().getServeiRetrofit();
        Call<Partida> callPartida = servei.startGame(game);
        callPartida.enqueue(new Callback<Partida>() {
            int resultat = -1;

            @Override
            public void onResponse(Call<Partida> call, Response<Partida> resposta) {
                int codi = resposta.code();
                //Tot aixo no funciona perque no tenim la partida
                Log.d("Proba ", "Codi agafat" + codi);
                if (codi == 200) {
                    resultat=0;
                    Partida game = (Partida) resposta.body();
                    Globals.getInstance().setGame(game);
                    Globals.getInstance().setPlayer(new Jugador(Globals.getInstance().getUser().getNickname(),Globals.getInstance().getUser().getProfession()));
                    Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                    startActivity(intent);
                } else if (codi == 204) {
                    resultat = -1;
                }
                if (resultat == -1) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setMessage("Error al començar la partida");
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
    public void eleccio(int i)
    {
        this.eleccio = i;
    }
    public void iniciarGameActivity(View view){
        intent = new Intent(this, GameActivity.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecció de mapa")
                .setMessage("Mazmorra o laberint?")
                .setPositiveButton("Mazmorra", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        eleccio(0);
                        startGame(eleccio);
                    }
                })
                /*.setNegativeButton("Laberint", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        eleccio(1);
                        startGame(eleccio);
                    }
                })*/
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public void usuarisLlista(){
        RetrofitAPI servei = Globals.getInstance().getServeiRetrofit();
        Call<List<Usuario>> callUser = servei.obtindreUsuaris();
        // Fetch and print a list of the contributors to the library.
        callUser.enqueue(new Callback<List<Usuario>>() {
            int resultat = -1;

            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> resposta) {
                int codi = resposta.code();
                if (codi == 200) {
                    usuaris=resposta.body();
                    dades.setUsuaris(usuaris);
                    String[] noms = new String[usuaris.size()];
                    for (int i = 0; i < usuaris.size(); i++) {
                        noms[i]=usuaris.get(i).getNickname();
                    }
                    ArrayAdapter<String> adapter;
                    adapter = new UsuarisListArrayAdapter(MainActivity.this, usuaris,noms);
                    ListView listView = (ListView) findViewById(R.id.list);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            dades.setUsuariSeleccionat(position);
                            Intent intent = new Intent(MainActivity.this, UsuariActivity.class);
                            startActivity(intent);
                        }
                    });
                } else if (codi == 204) {
                    Log.d("onResponse", "onResponse. Code" + Integer.toString(204));
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                // log error here since request failed
                Log.d("Request: ", "error loading API" + t.toString());

            }
        });
    }

}
