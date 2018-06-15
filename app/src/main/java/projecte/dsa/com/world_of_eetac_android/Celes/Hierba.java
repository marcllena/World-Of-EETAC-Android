package projecte.dsa.com.world_of_eetac_android.Celes;

/**
 * Created by jordi on 05/03/2018.
 */
public class Hierba extends Celda {

    public String getSimbolo() {
        return "0";
    }

    public int getPisablePersonaje() {
        return 1;
    }
    public int getPisableZombie() {
        return 1;
    }
    public int getInteractuable() {
        return 0;
    }

}
