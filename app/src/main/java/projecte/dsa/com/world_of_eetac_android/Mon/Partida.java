package projecte.dsa.com.world_of_eetac_android.Mon;

import java.util.List;

public class Partida {
    Mapa map;
    int mapSelection;
    Usuario jugador;
    String nom;
    List<Escena> mapaFull;
    int ronda=0;
    int enemics;
    long score=0;
    public Partida(Usuario jug, List<Escena> mapa){

        jugador=jug;
        mapaFull=mapa;
    }

    public Partida() {
    }
}
