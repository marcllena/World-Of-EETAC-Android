package projecte.dsa.com.world_of_eetac_android.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



import java.util.List;

import projecte.dsa.com.world_of_eetac_android.ListAdapters.ObjectesListArrayAdapter;
import projecte.dsa.com.world_of_eetac_android.Mon.Usuario;
import projecte.dsa.com.world_of_eetac_android.R;
import projecte.dsa.com.world_of_eetac_android.Mon.Globals;


//Activity que mostra els jugadors de un equip
public class UsuariActivity extends AppCompatActivity {
    Globals dades= Globals.getInstance();
    int usuariSele=dades.getUsuariSeleccionat();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //En comptes de tornar a realitzar la petició, agafo els jugadors del Singleton Globals
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuari);
        Usuario usuario= dades.getUsuaris().get(usuariSele);
        TextView textView = (TextView) findViewById(R.id.textViewUsuari);
        textView.setText(dades.getUsuaris().get(usuariSele).getNickname());
        ImageView image = (ImageView) findViewById(R.id.imageViewProf);
        int id =this.getResources().getIdentifier("p"+usuario.getProfession(), "drawable", this.getPackageName());
        image.setImageResource(id);

        //Mostrem el nivell, atac i defensa
        TextView textViewNivell = (TextView) findViewById(R.id.textNivell);
        textViewNivell.setText("Nivell: "+String.valueOf(usuario.getLevel()));
        TextView textViewAtac = (TextView) findViewById(R.id.textAtac);
        textViewAtac.setText("Atac: "+String.valueOf(usuario.getAttack()));
        TextView textViewDef = (TextView) findViewById(R.id.textDef);
        textViewDef.setText("Defensa: "+String.valueOf(usuario.getDefense()));



    }

}

