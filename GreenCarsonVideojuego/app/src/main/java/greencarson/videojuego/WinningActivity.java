package greencarson.videojuego;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        if (winningState==1 && !second)
            setContentView(R.layout.activity_winning);
        else if (winningState==0 && !second)
            setContentView(R.layout.activity_losing);

        //else if (winningState==1 && second)
        else if (winningState==1)
            setContentView(R.layout.activity_winning2);
        //else if (winningState==0 && second)
        else if (winningState==0)
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
        startActivity(intent);
    }

    //FALTA AQUI ENVIAR A RESPECTIVO TUTORIAL
    public void goToTutorial(View v){
        Intent intent = new Intent(this, TutorialActivity.class);
        intent.putExtra("levelNumber", levelNumber);
        startActivity(intent);
    }

/*    public void startTutorial(View v){
        Intent intent;
        //Mandar a java de tutoriales
        if (levelNumber>0 && levelNumber<5) {
            Log.d("100", "Se manda a tutorial");
            intent = new Intent(this, TutorialActivity.class);
            intent.putExtra("levelNumber", levelNumber);
            startActivity(intent);
        } else {
            dialogWarningAlert(v);
            Log.d("--1", "Nunca de los nuncas debería pasar este error");
        }
    }*/

}