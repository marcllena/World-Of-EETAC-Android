package projecte.dsa.com.world_of_eetac_android.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import projecte.dsa.com.world_of_eetac_android.Mon.Globals;
import projecte.dsa.com.world_of_eetac_android.Mon.Usuario;
import projecte.dsa.com.world_of_eetac_android.R;
import projecte.dsa.com.world_of_eetac_android.Services.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    String username;
    String password;
    Spinner spinnerProf;
    int spinnerPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Globals.setApiUrl("http://10.0.2.2:8080");
        spinnerProf = (Spinner) findViewById(R.id.spinnerProf);
        List<String> list = new ArrayList<String>();
        list.add("Guerrero");
        list.add("Arquero");
        list.add("Mago");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProf.setAdapter(dataAdapter);
        spinnerProf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                spinnerPos=pos;
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

    }

    public void loginClick(View view) {//Funció que s'activa al fer click al login, i comprova el password i la contrasenya
        EditText nomText = (EditText) findViewById(R.id.nomEditText);
        EditText nomPassword = (EditText) findViewById(R.id.passwordEditText);
        username = nomText.getText().toString();
        password = nomPassword.getText().toString();
        if (username.equals("") || password.equals("")) {//Mirem que hagi emplenat els camps
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Emplena els camps de nom i contrasenya");
            dlgAlert.setTitle("Error");
            dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            return;
        }
        //Fem la consulta
        login(username, password);

    }
    public void login(String username, final String password) {
        RetrofitAPI servei = Globals.getInstance().getServeiRetrofit();
        Call<Usuario> callResponse = servei.logUsuario(new Usuario(username, password));
        callResponse.enqueue(new Callback<Usuario>() {
            int resultat = -1;

            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> resposta) {
                int codi = resposta.code();
                if (codi == 200) {
                    Usuario usuario = (Usuario) resposta.body();
                    if (usuario.getPassword().equals(password)) {
                        resultat=0;
                        Globals.getInstance().setUser(usuario);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Globals.getInstance().setUser(usuario);
                        startActivity(intent);
                    } else resultat = -1;
                } else if (codi == 204) {
                    resultat = -1;
                }
                if (resultat == -1) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
                    dlgAlert.setMessage("Usuari/Password incorrecte");
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
            public void onFailure(Call<Usuario> call, Throwable t) {
                // log error here since request failed
                Log.d("Request: ", "error loading API" + t.toString());

            }
        });
    }

    public void registre_button(View view) {//Funció que s'executa per mostrar el camp adicional de repetir password
        EditText nomPassword2 = (EditText) findViewById(R.id.password2EditText);
        TextView password2 = (TextView) findViewById(R.id.textView3);
        Button login = (Button) findViewById(R.id.buttonLogin);
        Button cancelar = (Button) findViewById(R.id.buttonCancelar);
        Button registrar = (Button) findViewById(R.id.buttonRegistrar);
        Button registrar2 = (Button) findViewById(R.id.buttonRegister2);
        TextView profe = (TextView) findViewById(R.id.textView4);
        login.setVisibility(View.INVISIBLE);
        cancelar.setVisibility(View.VISIBLE);
        registrar.setVisibility(View.INVISIBLE);
        registrar2.setVisibility(View.VISIBLE);
        nomPassword2.setVisibility(View.VISIBLE);
        password2.setVisibility(View.VISIBLE);
        profe.setVisibility(View.VISIBLE);
        spinnerProf.setVisibility(View.VISIBLE);

    }

    public void cancelar_button(View view) {//Funció que s'executa quan volem cancela el registre, tornan al Login
        EditText nomPassword2 = (EditText) findViewById(R.id.password2EditText);
        TextView password2 = (TextView) findViewById(R.id.textView3);
        Button login = (Button) findViewById(R.id.buttonLogin);
        Button cancelar = (Button) findViewById(R.id.buttonCancelar);
        Button registrar = (Button) findViewById(R.id.buttonRegistrar);
        Button registrar2 = (Button) findViewById(R.id.buttonRegister2);
        TextView profe = (TextView) findViewById(R.id.textView4);
        login.setVisibility(View.VISIBLE);
        cancelar.setVisibility(View.INVISIBLE);
        registrar.setVisibility(View.VISIBLE);
        registrar2.setVisibility(View.INVISIBLE);
        nomPassword2.setText("");
        nomPassword2.setVisibility(View.INVISIBLE);
        password2.setVisibility(View.INVISIBLE);
        profe.setVisibility(View.INVISIBLE);
        spinnerProf.setVisibility(View.INVISIBLE);

    }

    public void registrarClick(View view) {//Funció que envia les dades del registre al server
        EditText nomText = (EditText) findViewById(R.id.nomEditText);
        EditText nomPassword = (EditText) findViewById(R.id.passwordEditText);
        EditText nomPassword2 = (EditText) findViewById(R.id.password2EditText);
        username = nomText.getText().toString();
        password = nomPassword.getText().toString();
        String password2 = nomPassword2.getText().toString();
        nomPassword.setText("");
        nomPassword2.setText("");
        if (username.equals("") || password.equals("") || password2.equals("")) {//Mirem que hagi emplenat els camps
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Emplena tots els camps");
            dlgAlert.setTitle("Error");
            dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            return;
        }
        if (!password.equals(password2)) {//Mirem que hagi els dos passwords coincideixen
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Les dos contrasenyes han de coincidir");
            dlgAlert.setTitle("Error");
            dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            return;
        }
        //Registre
        registre(username, password,spinnerPos+1);
    }

    public void registre(String username, final String password,int profession) {
        RetrofitAPI servei = Globals.getInstance().getServeiRetrofit();
        Call<Usuario> callUser = servei.regUsuario(new Usuario(username, password, profession));
        // Fetch and print a list of the contributors to the library.
        callUser.enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(Call<Usuario> user, Response<Usuario> resposta) {
                    int codi = resposta.code();
                    if (codi == 200) {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
                        dlgAlert.setMessage("Usuari registrat correctamente");
                        dlgAlert.setTitle("Registre");
                        dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss the dialog
                            }
                        });
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                        final Button cancelar = (Button) findViewById(R.id.buttonCancelar);
                        cancelar.performClick();

                    } else if (codi == 204) {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
                        dlgAlert.setMessage("Error en el registre");
                        dlgAlert.setTitle("El nom d'usuari ja existeix");
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
            public void onFailure(Call<Usuario> call, Throwable t) {
                // log error here since request failed
                Log.d("Request: ", "error loading API" + t.toString());

            }
        });
    }


}