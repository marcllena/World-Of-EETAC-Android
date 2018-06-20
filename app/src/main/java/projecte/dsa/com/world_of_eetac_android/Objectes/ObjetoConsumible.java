package projecte.dsa.com.world_of_eetac_android.Objectes;

public class ObjetoConsumible extends Objeto {
    public ObjetoConsumible(int identificador,String nombre, String descripcion, int tipo, int stat)
    {
        super(identificador,nombre,descripcion,tipo,stat);
    }
    public ObjetoConsumible(){}
    @Override
    public int getTipo() {
        return 0;
    }
}
