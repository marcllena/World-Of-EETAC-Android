package projecte.dsa.com.world_of_eetac_android.Mon;

import java.util.List;

public class Mapa {
    List<Escena> pantalles;
    List<Transicion> transicions;

    public Mapa(List<Escena> pantalles, List<Transicion> transicions) {
        this.pantalles = pantalles;
        this.transicions = transicions;
    }

    public List<Escena> getPantalles() {
        return pantalles;
    }

    public void setPantalles(List<Escena> pantalles) {
        this.pantalles = pantalles;
    }

    public List<Transicion> getTransicions() {
        return transicions;
    }

    public void setTransicions(List<Transicion> transicions) {
        this.transicions = transicions;
    }
}
