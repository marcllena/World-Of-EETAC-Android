package projecte.dsa.com.world_of_eetac_android.Game;

import java.util.List;
import java.util.ListIterator;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ZombieMort {
    private float x;
    private float y;
    private Bitmap bmp;
    private int life = 15;//En ticks de vida
    List<ZombieMort> morts;
    ListIterator<ZombieMort> listIteratorSang;

    public ZombieMort(List<ZombieMort> morts,ListIterator<ZombieMort> listIteratorSang, GameView gameView, float x,
                      float y, Bitmap bmp) {
        this.x = Math.min(Math.max(x - bmp.getWidth() / 2, 0),
                gameView.getWidth() - bmp.getWidth());
        this.y = Math.min(Math.max(y - bmp.getHeight() / 2, 0),
                gameView.getHeight() - bmp.getHeight());
        this.bmp = bmp;
        this.morts = morts;
        this.listIteratorSang=listIteratorSang;

    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }

    private void update() {
        if (--life < 1) {
            for (listIteratorSang = morts.listIterator(); listIteratorSang.hasNext(); ) {
                ZombieMort mort = listIteratorSang.next();
                if (mort.x==x&&mort.y==y) {
                    listIteratorSang.remove();
                }
            }
        }
    }

}


