package projecte.dsa.com.world_of_eetac_android.Services;

import android.util.Log;

import projecte.dsa.com.world_of_eetac_android.Mon.*;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class FuncionsRetrofit {
    //public static final String API_URL = "http://10.0.2.2:8080";
    public static final String API_URL = "http://localhost:8080";
    public static final String tag = "Retrofit:";
    private static RetrofitAPI servei;

    public static void pintar(Escena escena)
    {
        for(int i=0;i<escena.getAlto();i++) {
            String linea ="";
            for (int j = 0; j < escena.getAncho();j++) {
                linea=linea+" "+escena.getDatos()[i][j].getSimbolo();
            }
            System.out.println(linea);
        }
    }
    public static void init(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        servei = retrofit.create(RetrofitAPI.class);
    }
    public static Escena ObtindreEscena(int id){
        Call<Escena> call = servei.escenas("2");

        // Fetch and print a list of the contributors to the library.
        Response resposta=null;
        try
        {
            resposta = call.execute();
        }
        catch (IOException e)
        {
            Log.e("Error retrofit",e.getMessage());
        }
        int codi= resposta.code();
        if(codi==200) {
            Escena escena = (Escena)resposta.body();
            return escena;
        }
        else if(codi==204){
            System.out.printf("No hi ha cap escena amb aquell identificador\n");
            return null;
        }
        else{
            System.out.printf("Error desconegut\n");
            return null;
        }

    }
}
