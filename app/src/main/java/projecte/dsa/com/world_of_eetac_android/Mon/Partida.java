package projecte.dsa.com.world_of_eetac_android.Mon;

import java.util.List;

public class Partida {
    Mapa map=null;
    int mapSelection;
    String jugador;
    String nom;
    int ronda=0;
    public List<Escena> mapaFull;
    int enemics=0;
    long score=0;
    public Partida(String jug, int i){

        jugador=jug;
        this.mapSelection = i;
    }

    public Partida() {
    }
}
