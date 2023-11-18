package greencarson.videojuego;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import java.util.Objects;
import java.util.Random;

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
        else if (winningState==1 && second){
            setContentView(R.layout.activity_winning2);

            //Seleccionar cadena aleatoria y poner tip aleatorio
            AppCompatButton buttonTip = findViewById(R.id.stateDescription);
            Random random = new Random();
            int randomNumber = random.nextInt(10) + 1;
            int stringResource = getResources().getIdentifier("winning_tip" + randomNumber, "string", getPackageName());
            buttonTip.setText(stringResource);
        }

        //Si se perdió
        else if (winningState==0 && !second)
            setContentView(R.layout.activity_losing);
        else if (winningState==0 && second)
            setContentView(R.layout.activity_losing2);
    }

    //Para ir a niveles
    public void goToLevels(View v){
        Intent intent = new Intent(this, SelectLevelActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToSecondPart(View v){
        Intent intent = new Intent(this, WinningActivity.class);
        intent.putExtra("second", true);
        intent.putExtra("levelNumber", levelNumber);
        intent.putExtra("winningState", winningState);
        startActivity(intent);
        finish();
    }

    public void playLevelAgain(View v){
        GameView gameView = new GameView(this, levelNumber);
        setContentView(gameView);
        //finish();
    }

}