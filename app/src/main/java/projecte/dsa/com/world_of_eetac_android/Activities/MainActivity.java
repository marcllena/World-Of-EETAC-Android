package projecte.dsa.com.world_of_eetac_android.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;


import projecte.dsa.com.world_of_eetac_android.ListAdapters.UsuarisListArrayAdapter;
import projecte.dsa.com.world_of_eetac_android.Mon.Usuario;
import projecte.dsa.com.world_of_eetac_android.R;

public class MainActivity extends AppCompatActivity {
    List<Usuario> usuaris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuaris=new LinkedList<Usuario>();
        usuaris.add(new Usuario("Marc","Marcp",2));
        usuaris.add(new Usuario("Juan","Juanp",1));


        //Mostrem la llista
        String[] noms = new String[usuaris.size()];
        for (int i = 0; i < usuaris.size(); i++) {
            noms[i]=usuaris.get(i).getNickname();
        }

        ArrayAdapter<String> adapter;
        adapter = new UsuarisListArrayAdapter(MainActivity.this, usuaris,noms);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
    public void iniciarGameActivity(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

}
