package projecte.dsa.com.world_of_eetac_android.Celes;

import projecte.dsa.com.world_of_eetac_android.Mon.Transicion;

/**
 * Created by jordi on 05/03/2018.
 * Quan es carrega una porta, ha de definirse la transició que vol fer. Una porta és interactuable però no trepitjable
 */
public class Puerta extends Celda {
    public String getSimbolo() {
        return "G";
    }

    private Transicion teleport;
    public void setTeleport(Transicion door){
        this.teleport = door;
    }
    public int getInteractuable() {
        return 1;
    }
    public int getPisablePersonaje(){
        return 0;
    }
    public int getPisableZombie(){return 0;}
    public Puerta(Transicion door){
        setTeleport(door);
    }
    public Puerta(){}
    public Transicion getTeleport() {
        return this.teleport;
    }
    public void abrir(){ //N'he fet dos diferents. Un per possible gestió i l'altra per l'ingame.
        getTeleport();
    }
}
