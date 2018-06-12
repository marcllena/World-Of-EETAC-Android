package projecte.dsa.com.world_of_eetac_android.Mon;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import projecte.dsa.com.world_of_eetac_android.Celes.Celda;
import projecte.dsa.com.world_of_eetac_android.R;

public class Escena {
    private String nombre;
    private int ancho;//Ancho
    private int alto;//Alto
    private String descripcion;
    private Celda[][] Datos;//Ancho x alto CELDAS
    private Bitmap[] escenas;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAncho(int x) {
        ancho= x;
    }

    public void setAlto(int y) {
        alto = y;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDatos(Celda[][] datos) {
        Datos = datos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Celda[][] getDatos() {
        return Datos;
    }

    public Bitmap[] getEscenas() {
        return escenas;
    }

    public void setEscenas(Bitmap[] escenas) {
        this.escenas = escenas;
    }

    public void Escena(){

    }

    public void Escena(String nombre, int x,int y, String descripcion,Celda[][] datos){
        this.setNombre(nombre);
        this.setAncho(x);
        this.setAlto(y);
        this.setDescripcion(descripcion);
        this.setDatos(datos);
    }



    public void onDraw(Canvas canvas, int altoSprite,int anchoSprite) {
        for(int i=0;i<this.getAlto();i++) {
            for (int j = 0; j < this.getAncho();j++) {
                String simbolo=this.getDatos()[i][j].getSimbolo();
                Bitmap simbol;
                if ("0".equals(simbolo)) {
                    simbol=escenas[0];
                }
                else if ("-".equals(simbolo)) {
                   simbol=escenas[1];
                }
                else if ("X".equals(simbolo)) {
                    simbol=escenas[2];
                }
                else {
                    simbol=escenas[3];
                }
                //No funciona switch amb strings
                /*switch(simbolo){
                    case "0":simbol=escenas[0];
                    case "-":simbol=escenas[1];
                    case "X":simbol=escenas[2];
                    case "G":simbol=escenas[3];
                    default:simbol=escenas[0];
                }*/
                simbol= Bitmap.createScaledBitmap(simbol, altoSprite, anchoSprite, true);
                canvas.drawBitmap(simbol,i*altoSprite,j*anchoSprite,null);
            }
        }
    }


}
