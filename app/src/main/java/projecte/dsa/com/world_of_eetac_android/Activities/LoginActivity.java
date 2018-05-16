package projecte.dsa.com.world_of_eetac_android.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import projecte.dsa.com.world_of_eetac_android.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    String IP = "192.168.1.43";//IP del server
    //String IP = "10.0.1.43";//IP del server
    //String IP = "10.0.2.2";//IP del server Android
    String username;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {//Funció que s'activa al fer click al login, i comprova el password i la contrasenya
        EditText nomText = (EditText) findViewById(R.id.nomEditText);
        EditText nomPassword = (EditText) findViewById(R.id.passwordEditText);
        username = nomText.getText().toString();
        password = nomPassword.getText().toString();
        if (username.equals("") || password.equals("")) {//Mirem que hagi emplenat els camps
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Emplena els camps de nom i contrasenya");
            dlgAlert.setTitle("Error");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            return;
        }
        //new login().execute("http://" + IP + ":9000/Application/loginUsuario"); //Cridem el AsyncTask login
        iniciarMainActivity();
    }

    public class login extends AsyncTask<String, Void, String> {//AsyncTask login
        InputStream stream = null;
        String result = null;

        @Override
        protected String doInBackground(String... urls) {//Fa la petició al servidor
            try {
                String query = String.format(urls[0]);
                URL url = new URL(query);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();

                String urlParameters = "username=" + username + "&password=" + password;
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                stream = conn.getInputStream();
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(stream));

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();

                //En un futur, passarem a una altra activity en funció del codi
                Log.i("Missatge del servidor", result);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {//Analitzar la resposta del servidor
            TextView n = (TextView) findViewById(R.id.debugText);
            int codi = Integer.parseInt(result);
            if (codi == 0) {//Login Correcte
                iniciarMainActivity();
            } else {
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
                dlgAlert.setMessage("Usuari/Password incorrecte");
                dlgAlert.setTitle("Error en les dades");
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss the dialog
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                n.setText("Login incorrecte");
            }
        }
    }

    public void registre_button(View view) {//Funció que s'executa per mostrar el camp adicional de repetir password
        EditText nomPassword2 = (EditText) findViewById(R.id.password2EditText);
        TextView password2 = (TextView) findViewById(R.id.textView3);
        Button login = (Button) findViewById(R.id.buttonLogin);
        Button cancelar = (Button) findViewById(R.id.buttonCancelar);
        Button registrar = (Button) findViewById(R.id.buttonRegistrar);
        Button registrar2 = (Button) findViewById(R.id.buttonRegister2);
        login.setVisibility(View.INVISIBLE);
        cancelar.setVisibility(View.VISIBLE);
        registrar.setVisibility(View.INVISIBLE);
        registrar2.setVisibility(View.VISIBLE);
        nomPassword2.setVisibility(View.VISIBLE);
        password2.setVisibility(View.VISIBLE);

    }

    public void cancelar_button(View view) {//Funció que s'executa quan volem cancela el registre, tornan al Login
        EditText nomPassword2 = (EditText) findViewById(R.id.password2EditText);
        TextView password2 = (TextView) findViewById(R.id.textView3);
        Button login = (Button) findViewById(R.id.buttonLogin);
        Button cancelar = (Button) findViewById(R.id.buttonCancelar);
        Button registrar = (Button) findViewById(R.id.buttonRegistrar);
        Button registrar2 = (Button) findViewById(R.id.buttonRegister2);
        login.setVisibility(View.VISIBLE);
        cancelar.setVisibility(View.INVISIBLE);
        registrar.setVisibility(View.VISIBLE);
        registrar2.setVisibility(View.INVISIBLE);
        nomPassword2.setText("");
        nomPassword2.setVisibility(View.INVISIBLE);
        password2.setVisibility(View.INVISIBLE);
    }

    public void registrar(View view) {//Funció que envia les dades del registre al server
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
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
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
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            return;
        }
        //Enviem els parametres amb un POST
        //new registre().execute("http://" + IP + ":9000/Application/registrarUsuario");//Cridem el AsyncTask registre
    }

    private class registre extends AsyncTask<String, Void, String> {//AsyncTask registre
        InputStream stream = null;
        String result = null;

        @Override
        protected String doInBackground(String... urls) {//Fa la petició al servidor
            try {
                String query = String.format(urls[0]);
                URL url = new URL(query);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();

                String urlParameters = "username=" + username + "&password=" + password;
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                stream = conn.getInputStream();

                BufferedReader reader;

                StringBuilder sb = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(stream));

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();

                //En un futur, passarem a una altra activity en funció del codi
                Log.i("Missatge del servidor", result);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {//Analitzar la resposta del servidor
            int codi = Integer.parseInt(result);
            if (codi == 0) {//Registre
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
                dlgAlert.setMessage("Usuari registrat correctamente");
                dlgAlert.setTitle("Registre");
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss the dialog
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                final Button cancelar = (Button) findViewById(R.id.buttonCancelar);
                cancelar.performClick();
            } else {
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
                dlgAlert.setMessage("No s'ha pogut registrar, ja que el nom d'usuari ja existeix");
                dlgAlert.setTitle("Registre");
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss the dialog
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
        }
    }

    public void iniciarMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}