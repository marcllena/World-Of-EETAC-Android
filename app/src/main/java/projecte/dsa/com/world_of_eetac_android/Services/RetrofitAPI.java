package projecte.dsa.com.world_of_eetac_android.Services;
import projecte.dsa.com.world_of_eetac_android.Mon.*;
import projecte.dsa.com.world_of_eetac_android.Objectes.Objeto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface RetrofitAPI {

    @GET("/myapp/service/escenaris/{id}")
    Call<Escena> escenas(
            @Path("id") String id);

    @POST("/myapp/service/usuari/register/")
    Call<Usuario> regUsuario(@Body Usuario user);

    @GET("/myapp/service/usuari/{nombre}")
    Call<Usuario> consultarUsuarioJSON(@Path("nombre") String nombre);

    @POST("/myapp/service/objecte/add/{nombre}")
    Call<Response> newObjeto(@Path("nombre") String nombre);

    @GET("/myapp/service/usuaris")
    Call<List<Usuario>> obtindreUsuaris();
}
