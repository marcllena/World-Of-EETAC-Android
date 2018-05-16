package projecte.dsa.com.world_of_eetac_android.ListAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import projecte.dsa.com.world_of_eetac_android.Mon.Usuario;
import projecte.dsa.com.world_of_eetac_android.R;

public class UsuarisListArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final List<Usuario> llista;

    public UsuarisListArrayAdapter(Activity context, List<Usuario> llista, String[] dades) {
        super(context, R.layout.llista_usuaris, dades);
        this.context = context;
        this.llista = llista;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.llista_usuaris, null, true);
        //Operem cada fila
        TextView textViewClas = (TextView) rowView.findViewById(R.id.clas);
        TextView textViewNom = (TextView) rowView.findViewById(R.id.nom);
        TextView textViewProf = (TextView) rowView.findViewById(R.id.prof);
        TextView textViewNivell = (TextView) rowView.findViewById(R.id.nivell);
        TextView textViewAtac = (TextView) rowView.findViewById(R.id.atac);
        TextView textViewDef = (TextView) rowView.findViewById(R.id.def);
        Usuario usuari = llista.get(position);
        textViewClas.setText(String.valueOf(position+1)+".");
        textViewNom.setText(usuari.getNickname());
        textViewProf.setText(String.valueOf(usuari.getProfession()));
        textViewNivell.setText(String.valueOf(usuari.getLevel()));
        textViewAtac.setText(String.valueOf(usuari.getAttack()));
        textViewDef.setText(String.valueOf(usuari.getDefense()));
        return rowView;
    }
}
