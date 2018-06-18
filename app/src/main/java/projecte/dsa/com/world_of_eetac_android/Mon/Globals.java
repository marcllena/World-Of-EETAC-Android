package projecte.dsa.com.world_of_eetac_android.Mon;


import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import projecte.dsa.com.world_of_eetac_android.Activities.GameActivity;
import projecte.dsa.com.world_of_eetac_android.Objectes.Objeto;
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



    //public static String API_URL = "http://192.168.43.9:8080";
    Usuario user;
    Objeto[] objetos;
    RetrofitAPI serveiRetrofit;
    List<Usuario> usuaris;
    Partida game;
    GameActivity gameActivity;
    int usuariSeleccionat=-1;
    public Partida getGame() {
        return game;
    }

    public void setGame(Partida game) {
        this.game = game;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    // Restrict the constructor from being instantiated
    private Globals(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        //Creem el Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
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

    public Objeto[] getObjetos() {
        return objetos;
    }

    public void setObjetos(Objeto[] objetos) {
        this.objetos = objetos;
    }

    public GameActivity getGameActivity() {
        return gameActivity;
    }

    public void setGameActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }
}

