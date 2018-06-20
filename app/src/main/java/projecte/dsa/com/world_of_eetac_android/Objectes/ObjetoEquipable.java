package projecte.dsa.com.world_of_eetac_android.Objectes;

public class ObjetoEquipable extends Objeto {
    public int parteCuerpo;

    public ObjetoEquipable(int identificador, String nombre, String descripcion, int tipo,int stat) {
        super(identificador, nombre, descripcion, tipo,stat);
    }

    public ObjetoEquipable() {
    }
    @Override
    public int getTipo() {
        return 0;
    }
}