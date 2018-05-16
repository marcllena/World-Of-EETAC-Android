package projecte.dsa.com.world_of_eetac_android.Mon;

public class Transicion {
    private String escenario1;
    private int X1;
    private int Y1;
    private String escenario2;
    private int X2;
    private int Y2;

    public void setX1(int x1) {
        X1 = x1;
    }

    public void setY1(int y1) {
        Y1 = y1;
    }

    public void setEscenario1(String escenario1) {
        this.escenario1 = escenario1;
    }

    public void setX2(int x2) {
        X2 = x2;
    }

    public void setY2(int y2) {
        Y2 = y2;
    }

    public void setEscenario2(String escenario2) {
        this.escenario2 = escenario2;
    }

    public int getX1() {
        return X1;
    }

    public int getY1() {
        return Y1;
    }

    public String getEscenario1() {
        return escenario1;
    }

    public int getX2() {
        return X2;
    }

    public int getY2() {
        return Y2;
    }

    public String getEscenario2() {
        return escenario2;
    }

    public void Transicion(){}

    public void Transicion(String escenario1, int x1,int y1,String escenario2,int x2,int y2){
        this.setEscenario1(escenario1);
        this.setX1(x1);
        this.setY1(y1);
        this.setEscenario2(escenario2);
        this.setX2(x2);
        this.setY2(y2);
    }
}
