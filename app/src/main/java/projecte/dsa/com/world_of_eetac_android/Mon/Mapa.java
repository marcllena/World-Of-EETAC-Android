package projecte.dsa.com.world_of_eetac_android.Mon;

import java.util.List;

public class Mapa {
    public List<Escena> pantalles;

    public Mapa(List<Escena> pantalles) {
        this.pantalles = pantalles;
    }

    public Mapa() {
    }

    public List<Escena> getPantalles() {
        return pantalles;
    }

    public void setPantalles(List<Escena> pantalles) {
        this.pantalles = pantalles;
    }

}
