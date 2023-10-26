package greencarson.videojuego;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class WinningActivity extends Activity {

    int winningState;
    boolean second = false;

    //Crear pantalla de estado (ganado/perdido)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Recuperar datos
        winningState = Objects.requireNonNull(getIntent().getExtras()).getInt("winningState");
        second = getIntent().getExtras().getBoolean("second");

        //Decidir a qué pantalla enviar
        if (winningState==1 && !second)
            setContentView(R.layout.activity_winning);
        else if (winningState==0 && !second)
            setContentView(R.layout.activity_losing);

        else if (winningState==1 && second)
            setContentView(R.layout.activity_winning2);
        else if (winningState==0 && second)
            setContentView(R.layout.activity_losing2);
    }

    //Función para ir a pantallas
    public void goToLevels(View v){
        Intent intent = new Intent(this, TutorialActivity.class);
        //intent.putExtra("points", points);
        //AQUI AHORA ADD EXTRA
        startActivity(intent);
    }

    //FALTA AQUI AHORA CAMBIAR PARA ENVIAR A PANTALLA DE SELECCION DE NIVELES
    public void goToSecondPart(View v){
        Intent intent = new Intent(this, WinningActivity.class);
        intent.putExtra("second", true);
        startActivity(intent);
    }

}