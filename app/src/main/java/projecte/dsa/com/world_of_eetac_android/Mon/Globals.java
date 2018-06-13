package projecte.dsa.com.world_of_eetac_android.Mon;


import java.util.List;

import projecte.dsa.com.world_of_eetac_android.Services.RetrofitAPI;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//Singleton que guarda les dades a les que accedexixen totes les activities
public class Globals {
    private static Globals instance;

    // Global variables
    public static String API_URL = "http://10.0.2.2:8080";
    //public static String API_URL = "http://192.168.43.9:8080";
    RetrofitAPI serveiRetrofit;
    List<Usuario> usuaris;
    int usuariSeleccionat=-1;
    Usuario usuari;


    // Restrict the constructor from being instantiated
    private Globals(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        serveiRetrofit = retrofit.create(RetrofitAPI.class);
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }

    public static void setInstance(Globals instance) {
        Globals.instance = instance;
    }

    public static String getApiUrl() {
        return API_URL;
    }

    public RetrofitAPI getServeiRetrofit() {
        return serveiRetrofit;
    }

    public void setServeiRetrofit(RetrofitAPI serveiRetrofit) {
        this.serveiRetrofit = serveiRetrofit;
    }

    public static void setApiUrl(String apiUrl) {
        API_URL = apiUrl;
    }

    public int getUsuariSeleccionat() {
        return usuariSeleccionat;
    }

    public void setUsuariSeleccionat(int usuariSeleccionat) {
        this.usuariSeleccionat = usuariSeleccionat;
    }

    public List<Usuario> getUsuaris() {
        return usuaris;
    }

    public void setUsuaris(List<Usuario> usuaris) {
        this.usuaris = usuaris;
    }
    public Usuario getUsuari() {
        return usuari;
    }
    public void setUsuari(Usuario usuari) {
        this.usuari = usuari;
    }
}

