package projecte.dsa.com.world_of_eetac_android.Mon;

public class Transicion {
    public int idEscenario;
    public int X;
    public int Y;


    public Transicion(int escenario, int x, int y) {
        this.idEscenario = escenario;
        X = x;
        Y = y;
    }

    public Transicion() {
    }
}
