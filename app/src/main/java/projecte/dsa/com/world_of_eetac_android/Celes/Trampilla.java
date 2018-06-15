package projecte.dsa.com.world_of_eetac_android.Celes;

public class Trampilla extends Celda {

    @Override
    public int getPisablePersonaje() {
        return 1;
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
        return "T";
    }
}
