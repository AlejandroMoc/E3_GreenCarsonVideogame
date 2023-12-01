/*
    Nombre del archivo: WinningActivity.java
    Nombre del proyecto: Green Carson Reecicla! Game

    Creado y Desarrollado por:

    César Guerra Martínez
    Alejandro Daniel Moctezuma Cruz

    En colaboración con el Instituto Tecnológico y
    de Estudios Superiores de Monterrey y la empresa Green Carson.
*/

package greencarson.videojuego;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import java.util.Objects;
import java.util.Random;

public class WinningActivity extends Activity {

    int winningState, levelNumber;
    boolean second = false;
    MediaPlayer loseWahWah, winWoo;

    //Crear pantalla de estado (ganado/perdido)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Recuperar datos
        winningState = Objects.requireNonNull(getIntent().getExtras()).getInt("winningState");
        levelNumber = Objects.requireNonNull(getIntent().getExtras()).getInt("levelNumber");
        second = getIntent().getExtras().getBoolean("second");

        loseWahWah = MediaPlayer.create(this, R.raw.losing_wah);
        winWoo = MediaPlayer.create(this, R.raw.winning_woo);


        //Decidir a qué pantalla enviar
        //Si se ganó
        if (winningState==1 && !second) {
            winWoo.start();
            setContentView(R.layout.activity_winning);
        }
        else if (winningState == 1){
            setContentView(R.layout.activity_winning2);

            //Seleccionar cadena aleatoria y poner tip aleatorio
            AppCompatButton buttonTip = findViewById(R.id.stateDescription);
            Random random = new Random();
            int randomNumber = random.nextInt(10) + 1;

            //int stringResource = getResources().getIdentifier("winning_tip" + randomNumber, "string", getPackageName());
            //buttonTip.setText(stringResource);

            //Usar un .xml para sortear los tips
            String[] winningTips = getResources().getStringArray(R.array.winning_tips);
            int index = randomNumber - 1;
            String tip = winningTips[index];
            buttonTip.setText(tip);
        }

        //Si se perdió
        else if (winningState==0 && !second) {
            loseWahWah.start();
            setContentView(R.layout.activity_losing);
        }
        else if (winningState == 0) {
            setContentView(R.layout.activity_losing2);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loseWahWah != null) {
            loseWahWah.stop();
            loseWahWah.release();
            loseWahWah = null;
        }
        if (winWoo != null) {
            winWoo.stop();
            winWoo.release();
            winWoo = null;
        }
    }


    //Quitar capacidad de regresar
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
    }
}