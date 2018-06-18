package projecte.dsa.com.world_of_eetac_android.Game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import projecte.dsa.com.world_of_eetac_android.Mon.Globals;
import projecte.dsa.com.world_of_eetac_android.Objectes.Objeto;
import projecte.dsa.com.world_of_eetac_android.R;

/**
 * Created by GABRI on 15/06/2018.
 */

public class InventarioView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    private SurfaceHolder holder;
    private float anchoCelda;
    private float altoCelda;
    private float anchoObjeto;
    private float altoObjeto;
    private float tamañoBordes;
    private float tamañoBottom;
    private float tamañoTop;
    private float tamañoSeparador;
    private float altoBordes;
    private int width;
    private int height;
    private int initHeight;
    private int initwidth;
    private int startInventarioWidht;
    private int startInventarioHeight;
    private Bitmap top;
    private Bitmap left;
    private Bitmap right;
    private Bitmap bottom;
    private Bitmap separador;
    private Bitmap[] celdas=new Bitmap[7];
    private InventarioListener InventarioCallback;
    private boolean moving;
    private int movingItem;

    /*//public InventarioView(Context context) {
        super(context);
    }*/

    public InventarioView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof InventarioListener)
            InventarioCallback=(InventarioListener) context;

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                setupDimensions();
                cargarCeldas();
                Canvas canvas = holder.lockCanvas();
                drawFondo(canvas);
                drawInventario(canvas,-1,3,4,null);
                getHolder().unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
    }

    public InventarioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        holder = getHolder();
        holder.addCallback(this);
        setOnTouchListener(this);
        if(context instanceof InventarioListener)
            InventarioCallback=(InventarioListener) context;

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                startInventario();
                cargarCeldas();
                Canvas canvas = holder.lockCanvas();
                drawFondo(canvas);
                drawInventario(canvas,-1,0,0,null);
                getHolder().unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
    }
    public InventarioView(Context context) {
        super(context);
        // this.context = context;
        //Fixem el Bitmap
        holder = getHolder();
        holder.addCallback(this);
        setOnTouchListener(this);
        if(context instanceof InventarioListener)
            InventarioCallback=(InventarioListener) context;

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                setupDimensions();
                cargarCeldas();
                Canvas canvas = holder.lockCanvas();
                drawFondo(canvas);
                drawInventario(canvas,-1,0,0,null);
                getHolder().unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
    }

    public void startInventario(){
        setupDimensions();
        Canvas canvas=holder.lockCanvas();
        drawFondo(canvas);
        drawInventario(canvas,-1,0,0,null);
        holder.unlockCanvasAndPost(canvas);
    }

    private void setupDimensions()
    {
        width=getWidth();
        height=getHeight();
        initwidth=0;
        initHeight=0;
        
        
        tamañoBordes=11*width/227;
        tamañoTop=height*21/113;
        tamañoBottom=tamañoTop;
        //int min=Math.min(width,height);
        altoCelda = (float) (1.01*(height-tamañoBottom-tamañoTop)/3);
        anchoCelda=altoCelda;
        tamañoSeparador= (float) (width-2*tamañoBordes-5*anchoCelda-2*anchoCelda);
        altoBordes=(float)(height-tamañoTop-tamañoBottom);

        anchoObjeto=anchoCelda*4/5;
        altoObjeto=anchoObjeto;
        startInventarioHeight=(int)(initHeight+tamañoTop);
        startInventarioWidht=(int)(initwidth+tamañoBordes);



    }

    private void cargarCeldas(){
        celdas[0]= BitmapFactory.decodeResource(getResources(), R.drawable.celda_inventario);
        celdas[1]= BitmapFactory.decodeResource(getResources(), R.drawable.celda_inventario_cabeza);
        celdas[2]= BitmapFactory.decodeResource(getResources(), R.drawable.celda_inventario_pecho);
        celdas[3]= BitmapFactory.decodeResource(getResources(), R.drawable.celda_inventario_piernas);
        celdas[4]= BitmapFactory.decodeResource(getResources(), R.drawable.celda_inventario_pies);
        celdas[5]= BitmapFactory.decodeResource(getResources(), R.drawable.celda_inventario_fuego);
        celdas[6]= BitmapFactory.decodeResource(getResources(), R.drawable.celda_inventario_melee);
        top= BitmapFactory.decodeResource(getResources(), R.drawable.inventario_title);
        bottom= BitmapFactory.decodeResource(getResources(), R.drawable.borde_bot);
        left= BitmapFactory.decodeResource(getResources(), R.drawable.borde_izq);
        right= BitmapFactory.decodeResource(getResources(), R.drawable.borde_der);
        separador= BitmapFactory.decodeResource(getResources(),R.drawable.separador);
    }



    private void drawInventario(Canvas canvas, int posItem, int posX, int posY, Objeto[] objetos){
        if(objetos!=null) {
            if (getHolder().getSurface().isValid()) {
                //Canvas canvas = this.getHolder().lockCanvas();
                Bitmap simbol;
                String nombreResource;
                int idObjeto;
                Bitmap objeto;

                for (int j = 0; j < 3; j++) {
                    for (int i = 0; i < 5; i++) {
                        nombreResource = "hacha_bombero";
                        idObjeto = getResources().getIdentifier(nombreResource, "drawable", "projecte.dsa.com.world_of_eetac_android");
                        objeto = BitmapFactory.decodeResource(getResources(), idObjeto);
                        simbol = Bitmap.createScaledBitmap(objeto, (int) anchoObjeto, (int) altoObjeto, true);
                        if ((i + j * 5) == posItem) {
                            canvas.drawBitmap(simbol, posX-anchoObjeto/2, posY-altoObjeto/2, null);
                        } else {
                            canvas.drawBitmap(simbol, startInventarioWidht + i * anchoCelda + anchoCelda / 10, startInventarioHeight + j * altoCelda + altoCelda / 10, null);
                        }
                    }
                }

            }
        }

    }

    private void onDraw(int posItem,int posX,int posY, Objeto[] objetos){
        Canvas canvas=holder.lockCanvas();
        drawFondo(canvas);
        drawInventario(canvas,posItem,posX,posY,objetos);
        holder.unlockCanvasAndPost(canvas);
    }


    private void drawFondo(Canvas canvas){
        Bitmap simbol;
        simbol= Bitmap.createScaledBitmap(top, width, (int) tamañoTop, true);
        canvas.drawBitmap(simbol,initwidth,initHeight,null);

        simbol= Bitmap.createScaledBitmap(left, (int) tamañoBordes,(int)altoBordes,true);
        canvas.drawBitmap(simbol,initwidth,initHeight+(int)tamañoTop,null);
        simbol= Bitmap.createScaledBitmap(bottom, width,(int)(1.1*tamañoBottom),true);
        canvas.drawBitmap(simbol,initwidth,(int)(tamañoTop+altoBordes)+initHeight,null);
        for(int j=0;j<3;j++){
            for(int i=0;i<5;i++){
                simbol= Bitmap.createScaledBitmap(celdas[0], (int)anchoCelda, (int)altoCelda, true);
                canvas.drawBitmap(simbol,i*(int)anchoCelda+tamañoBordes+initwidth,j*(int)altoCelda+tamañoTop+initHeight,null);
            }
        }
        simbol= Bitmap.createScaledBitmap(separador, (int)(tamañoSeparador*1.1+(int)(2.1*anchoCelda)), (int)altoBordes, true);
        canvas.drawBitmap(simbol,5*(int)anchoCelda+tamañoBordes+initwidth,tamañoTop+initHeight,null);

        simbol= Bitmap.createScaledBitmap(right, (int)tamañoBordes, (int)altoBordes, true);
        canvas.drawBitmap(simbol,width-tamañoBordes+initwidth,tamañoTop+initHeight,null);

        simbol= Bitmap.createScaledBitmap(celdas[1], (int)anchoCelda, (int)altoCelda, true);
        canvas.drawBitmap(simbol,(int)(5*anchoCelda+tamañoBordes+tamañoSeparador)+initwidth,tamañoTop+initHeight,null);
        simbol= Bitmap.createScaledBitmap(celdas[2], (int)anchoCelda, (int)altoCelda, true);
        canvas.drawBitmap(simbol,(int)(6*anchoCelda+tamañoBordes+tamañoSeparador)+initwidth,tamañoTop+initHeight,null);

        simbol= Bitmap.createScaledBitmap(celdas[3], (int)anchoCelda, (int)altoCelda, true);
        canvas.drawBitmap(simbol,(int)(5*anchoCelda+tamañoBordes+tamañoSeparador)+initwidth,tamañoTop+altoCelda+initHeight,null);
        simbol= Bitmap.createScaledBitmap(celdas[4], (int)anchoCelda, (int)altoCelda, true);
        canvas.drawBitmap(simbol,(int)(6*anchoCelda+tamañoBordes+tamañoSeparador)+initwidth,tamañoTop+altoCelda+initHeight,null);

        simbol= Bitmap.createScaledBitmap(celdas[5], (int)anchoCelda, (int)altoCelda, true);
        canvas.drawBitmap(simbol,(int)(5*anchoCelda+tamañoBordes+tamañoSeparador)+initwidth,tamañoTop+2*altoCelda+initHeight,null);
        simbol= Bitmap.createScaledBitmap(celdas[6], (int)anchoCelda, (int)altoCelda, true);
        canvas.drawBitmap(simbol,(int)(6*anchoCelda+tamañoBordes+tamañoSeparador)+initwidth,tamañoTop+2*altoCelda+initHeight,null);


        //this.getHolder().unlockCanvasAndPost(canvas);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setupDimensions();
        cargarCeldas();
        Canvas canvas = holder.lockCanvas();
        drawFondo(canvas);
        drawInventario(canvas,-1,0,0,null);
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @SuppressLint("WrongCall")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.equals(this)){
            if(event.getAction() != event.ACTION_UP){
                int posX = 0;
                int posY=0;
                //InventarioCallback.onItemMoved(posX,posY,moving,getId());
                Canvas canvas=holder.lockCanvas();
                if (!moving) {
                    posX= (int)((event.getX()-startInventarioWidht)/anchoCelda);
                    posY= (int)((event.getY()-startInventarioHeight)/altoCelda);
                    movingItem = (posX + 5 * posY);


                }
                moving = true;
                if (canvas!=null) {
                    //if(posX<=4) {
                        drawFondo(canvas);


                        drawInventario(canvas, movingItem, (int) event.getX(), (int) event.getY(), Globals.getInstance().getObjetos());
                        holder.unlockCanvasAndPost(canvas);
                    //}
                }

            }
            else {
                int posX= (int)((event.getX()-startInventarioWidht)/anchoCelda);
                int posY= (int)((event.getY()-startInventarioHeight)/altoCelda);
                moving=false;
                movingItem=-1;
                if (posX>0||posY<0||posY>2){
                if (posX>4){
                //InventarioCallback.onItemReleased(posX,posY,movingItem,getId());

                    posX=-((int)((event.getX()-(startInventarioWidht+5*anchoCelda+tamañoSeparador))/anchoCelda)-1);
                }
                //InventarioCallback.onItemReleased(posX);
                }
                else{
                    onDraw(-1,0,0,Globals.getInstance().getObjetos());
                }
            }
        }
        return true;
    }

    public interface InventarioListener
    {

        void onItemMoved(int posX, int posY, boolean moving, int source);

        void onItemReleased(int posX, int posY,int itemPos, int source);
    }
}
