package projecte.dsa.com.world_of_eetac_android.Objectes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by jordi on 23/02/2018.
 */
/*@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=ObjetoConsumible.class, name="consumible"),
        @JsonSubTypes.Type(value=ObjetoEquipable.class, name="equipable"),
})*/
public class Objeto {
    private int id;
    private String nombre;
    private String descripcion;
    private int tipo;

    public int getID(){
        return this.id;
    }
    public String getNombre(){
        return this.nombre;
    }
    public String getDescripcion(){
        return this.descripcion;
    }
    public int getTipo(){
        return this.tipo;
    } //Arma, pocion, etc

    public Objeto(int id,String nombre, String descripcion, int tipo)
    {
        this.id=id;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.tipo=tipo;
    }
    /*public Objeto(String id,String nombre, String descripcion, String tipo){
        this.id=Integer.parseInt(id);
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.tipo=Integer.parseInt(tipo);;
    }*/
    public Objeto(){
    }

    /*@Override
    public String toString() {
        return "Objeto [id=" + id + ", nombre=" + nombre +  ", descripcion=" + descripcion + ", tipo=" + tipo +  "]";
    }*/

}
