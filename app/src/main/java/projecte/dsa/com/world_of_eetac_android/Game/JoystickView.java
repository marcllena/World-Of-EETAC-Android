package projecte.dsa.com.world_of_eetac_android.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by GABRI on 12/06/2018.
 */

public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener{
    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private JoystickListener joystickCallback;

    private void setupDimensions()
    {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        baseRadius = 2* Math.min(getWidth(), getHeight()) / 7;
        hatRadius = 2* Math.min(getWidth(), getHeight()) / 13;

    }

    private void drawJoystick(float newX, float newY){
        if(getHolder().getSurface().isValid()) {
            Canvas myCanvas = this.getHolder().lockCanvas();
            Paint color = new Paint();
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            color.setARGB(255, 57, 34, 34);
            myCanvas.drawCircle(centerX, centerY, baseRadius, color);
            color.setARGB(255, 226, 20, 20);
            myCanvas.drawCircle(newX, newY, hatRadius, color);
            getHolder().unlockCanvasAndPost(myCanvas);
        }
    }



    public JoystickView(Context context){
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener)
            joystickCallback=(JoystickListener) context;
    }
    public JoystickView(Context context, AttributeSet attributes, int style){
        super(context,attributes,style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener)
            joystickCallback=(JoystickListener) context;
    }

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener)
            joystickCallback=(JoystickListener) context;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setupDimensions();
        drawJoystick(centerX,centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.equals(this)){
            if(event.getAction() != event.ACTION_UP){
                float displacement = (float) Math.sqrt(Math.pow(event.getX() - centerX, 2) + Math.pow(event.getY() - centerY, 2));
                if(displacement < baseRadius) {
                    drawJoystick(event.getX(), event.getY());
                    joystickCallback.onJoystickMoved((event.getX() - centerX) / baseRadius, (event.getY() - centerY) / baseRadius, getId());
                }
                else{
                    float ratio = baseRadius / displacement;

                    float constrainedX = centerX + (event.getX() - centerX) * ratio;
                    float constrainedY = centerY + (event.getY() - centerY) * ratio;
                    drawJoystick(constrainedX, constrainedY);

                    joystickCallback.onJoystickMoved((constrainedX-centerX)/baseRadius,(constrainedY-centerY)/baseRadius,getId());
                }
            }
            else {
                drawJoystick(centerX, centerY);
                joystickCallback.onJoystickMoved(0, 0, getId());
            }
        }
        return true;
    }

    public float getBaseRadius() {
        return baseRadius;
    }

    public interface JoystickListener
    {

        void onJoystickMoved(float xPercent, float yPercent, int source);

    }
}

