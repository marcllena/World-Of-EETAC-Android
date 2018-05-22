package projecte.dsa.com.world_of_eetac_android.Mains;

import projecte.dsa.com.world_of_eetac_android.Mon.Escena;
import projecte.dsa.com.world_of_eetac_android.Mon.Globals;
import projecte.dsa.com.world_of_eetac_android.Mon.Usuario;
import projecte.dsa.com.world_of_eetac_android.Services.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main4 {
    public static void main(String[] args) {
        Globals.setApiUrl("http://localhost:8080");
        RetrofitAPI servei= Globals.getInstance().getServeiRetrofit();
        Call<Usuario> callesc = servei.consultarUsuarioJSON("Marc");
        callesc.enqueue( new Callback<Usuario>() {

            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                int codi= response.code();
                if(codi==200) {
                    Usuario usuario = (Usuario)response.body();
                    System.out.printf(usuario.getNickname()+"\n");
                }
                else if(codi==204){
                    System.out.printf("No hi ha cap usuari amb aquell identificador\n");
                }
                else{
                    System.out.printf("Error desconegut\n");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                // log error here since request failed
                //Log.d("onResponse ", "error on del API" + t.toString());
            }

        });
    }
}

