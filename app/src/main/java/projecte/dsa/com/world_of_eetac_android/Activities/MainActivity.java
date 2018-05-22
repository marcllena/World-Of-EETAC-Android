package projecte.dsa.com.world_of_eetac_android.Activities;

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
import projecte.dsa.com.world_of_eetac_android.Mon.Usuario;
import projecte.dsa.com.world_of_eetac_android.R;
import projecte.dsa.com.world_of_eetac_android.Services.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    List<Usuario> usuaris;
    Globals dades;

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

    public void iniciarGameActivity(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
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
