package projecte.dsa.com.world_of_eetac_android.ListAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import projecte.dsa.com.world_of_eetac_android.Objectes.Objeto;
import projecte.dsa.com.world_of_eetac_android.R;

public class ObjectesListArrayAdapter extends BaseAdapter {
    private final Context context;
    private final List<Objeto> llista;

    public ObjectesListArrayAdapter(Context context, List<Objeto> llista) {
        this.context = context;
        this.llista = llista;
    }

    @Override
    public int getCount() {
        return llista.size();
    }

    @Override
    public Objeto getItem(int position) {
        return llista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.llista_usuaris, null, true);
        //Operem cada fila
        TextView textViewId = (TextView) rowView.findViewById(R.id.id);
        TextView textViewNom = (TextView) rowView.findViewById(R.id.nom);
        TextView textViewTipo = (TextView) rowView.findViewById(R.id.tipo);
        TextView textViewDes = (TextView) rowView.findViewById(R.id.des);
        Objeto objeto = llista.get(position);
        textViewId.setText(Integer.toString(objeto.getID()));
        textViewNom.setText(objeto.getNombre());
        textViewTipo.setText(Integer.toString(objeto.getTipo()));
        textViewDes.setText(objeto.getDescripcion());
        return rowView;
    }

}

