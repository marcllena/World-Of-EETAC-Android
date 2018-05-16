package projecte.dsa.com.world_of_eetac_android.Activities;

import projecte.dsa.com.world_of_eetac_android.Game.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(new GameView(this));
    }
}
