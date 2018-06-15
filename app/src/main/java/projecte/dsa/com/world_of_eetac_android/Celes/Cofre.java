package projecte.dsa.com.world_of_eetac_android.Celes;
import com.fasterxml.jackson.annotation.JsonIgnore;

import projecte.*;
import projecte.dsa.com.world_of_eetac_android.Objectes.Objeto;

import java.util.List;

/**
 * Created by jordi on 05/03/2018.
 */
public class Cofre extends Celda {
    public String getSimbolo() {
        return "X";
    }

    private List<Objeto> contenido;
    @JsonIgnore
    private boolean abierto;

    public Cofre() {
        abierto=false;
    }

    public int getPisablePersonaje() {
        return 0;
    }
    public int getPisableZombie() {
        return 0;
    }
    public int getInteractuable() {
        return 1;
    }
    public List<Objeto> getContenido(){
        return this.contenido;
    }
    public void abrir(){getContenido();}

    public void setContenido(List<Objeto> contenido) {
        this.contenido = contenido;
    }
    public boolean getAbierto() {
        return abierto;
    }
    public void setAbierto(boolean abierto) {
        this.abierto = abierto;
    }
}
