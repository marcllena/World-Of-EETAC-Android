package projecte.dsa.com.world_of_eetac_android.Celes;

public class Ventana extends Celda {

    @Override
    public int getPisablePersonaje() {
        return 0;
    }

    @Override
    public int getInteractuable() {
        return 0;
    }

    @Override
    public int getPisableZombie() {
        return 1;
    }

    @Override
    public String getSimbolo() {
        return "W";
    }
}
