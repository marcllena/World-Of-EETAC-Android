package projecte.dsa.com.world_of_eetac_android.Mon;

import java.util.List;


//Server s'encarrega de gestionar l'inici i el final de la partida.
public class Partida {
    public Mapa map;
    public int mapSelection;
    public String jugador;
    public Jugador player;
    public int proffSelection;
    public String nom;
    public List<Escena> mapaFull;
    public int ronda=0;
    public int enemics;
    public long score=0;
    public Partida(String jug, List<Escena> mapa, int eleccio){
        proffSelection=eleccio;
        jugador=jug;
        mapaFull=mapa;
    }
    public Partida(String jug,int mapSelection,int proffSelection){

        jugador=jug;
        this.mapSelection=mapSelection;
        this.proffSelection = proffSelection;
    }
    public Partida(){}
    public Partida(int mapSelection,int eleccio, String jugador,int ronda, int enemics, long score)
    {
        this.proffSelection = eleccio;
        this.mapSelection = mapSelection;
        this.jugador = jugador;
        this.ronda = ronda;
        this.enemics = enemics;
        this.score = score;
    }
}

