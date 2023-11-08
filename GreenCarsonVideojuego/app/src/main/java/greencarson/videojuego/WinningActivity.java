package greencarson.videojuego;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class WinningActivity extends Activity {

    int winningState, levelNumber;
    boolean second = false;

    //Crear pantalla de estado (ganado/perdido)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Recuperar datos
        winningState = Objects.requireNonNull(getIntent().getExtras()).getInt("winningState");
        levelNumber = Objects.requireNonNull(getIntent().getExtras()).getInt("levelNumber");
        second = getIntent().getExtras().getBoolean("second");

        //Decidir a qué pantalla enviar

        //Si se ganó
        if (winningState==1 && !second)
            setContentView(R.layout.activity_winning);
        else if (winningState==1 && second)
            setContentView(R.layout.activity_winning2);

        //Si se perdió
        else if (winningState==0 && !second)
            setContentView(R.layout.activity_losing);
        else if (winningState==0 && second)
            setContentView(R.layout.activity_losing2);
    }

    //Función para ir a pantallas
    public void goToLevels(View v){
        Intent intent = new Intent(this, SelectLevelActivity.class);
        startActivity(intent);
    }

    public void goToSecondPart(View v){
        Intent intent = new Intent(this, WinningActivity.class);
        intent.putExtra("second", true);
        intent.putExtra("levelNumber", levelNumber);
        intent.putExtra("winningState", winningState);
        //Falta aquí pasar el winningState
        startActivity(intent);
    }

    public void playLevelAgain(View v){
        GameView gameView = new GameView(this, levelNumber);
        setContentView(gameView);
    }

}