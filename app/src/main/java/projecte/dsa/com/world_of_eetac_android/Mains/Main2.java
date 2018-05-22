package projecte.dsa.com.world_of_eetac_android.Mains;
import android.util.Log;

import java.util.List;

import projecte.dsa.com.world_of_eetac_android.Mon.Globals;
import projecte.dsa.com.world_of_eetac_android.Mon.Usuario;
import projecte.dsa.com.world_of_eetac_android.Services.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2 {
    public static void main(String[] args) {
        Globals.setApiUrl("http://localhost:8080");
        RetrofitAPI servei= Globals.getInstance().getServeiRetrofit();
        Call<List<Usuario>> callUser = servei.obtindreUsuaris();
        // Fetch and print a list of the contributors to the library.
        callUser.enqueue(new Callback<List<Usuario>>() {

            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> resposta) {
                int codi = resposta.code();
                if (codi == 200) {
                    List<Usuario> usuaris=resposta.body();
                    System.out.printf("Usuaris obtinguts: "+usuaris.toString());
                } else if (codi == 204) {
                    System.out.printf("No hi han usuaris");
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


