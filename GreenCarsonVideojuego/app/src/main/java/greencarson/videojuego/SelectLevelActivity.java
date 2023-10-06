package greencarson.videojuego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SelectLevelActivity extends AppCompatActivity {

    //Variables
    int levelNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ocultar barra de status
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_selectlevel);
    }

    //Para seleccionar nivel
    public void selectLevel(View v) {

        //Ligar elementos a la pantalla
        Button buttonBasic = findViewById(R.id.buttonBasic);
        Button buttonInter = findViewById(R.id.buttonInter);
        Button buttonAdvan = findViewById(R.id.buttonAdvan);

        switch(v.getId()) {
            case R.id.buttonBasic:
                levelNumber = 1;
                buttonBasic.setBackgroundResource(R.drawable.gradient_button);
                buttonInter.setBackgroundResource(R.drawable.gradient_button2_d);
                buttonAdvan.setBackgroundResource(R.drawable.gradient_button_d);
                //buttonBasic.setTextColor(getApplication().getResources().getColor(R.color.white));
                buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white_deactivated));
                buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white_deactivated));
                //Cambiar de pantalla
                break;

            case R.id.buttonInter:
                levelNumber = 2;
                buttonBasic.setBackgroundResource(R.drawable.gradient_button_d);
                buttonInter.setBackgroundResource(R.drawable.gradient_button2);
                buttonAdvan.setBackgroundResource(R.drawable.gradient_button_d);
                buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white_deactivated));
                buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white_deactivated));
                break;
            case R.id.buttonAdvan:
                levelNumber = 3;
                buttonBasic.setBackgroundResource(R.drawable.gradient_button_d);
                buttonInter.setBackgroundResource(R.drawable.gradient_button2_d);
                buttonAdvan.setBackgroundResource(R.drawable.gradient_button);
                buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white_deactivated));
                buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white_deactivated));
                buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                break;
        }
    }

    //Para empezar nivel
    public void startLevel(View v){

        //Seleccionar nivel
        switch (levelNumber) {
            case 1:
                //Log.d("1", "Se envia a tutorial 1");
                //Ir al tutorial
                //break;
                Intent intent = new Intent(this, TutorialActivity.class);
                startActivity(intent);
            case 2:
                Log.d("2", "Se envia a tutorial 2");
                break;
            case 3:
                Log.d("3", "Se envia a tutorial 3");
                break;
            default:
                Log.d("0", "Aun no eliges nivel");
                //Falta poner pedazo que diga, ¡Aun no eliges nivel!
                break;
        }
    }

    //Para regresar
    public void goToLoginScreen(View v){
        //Falta poner pedazo que diga, ¿Quieres salir del juego?

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //Para ir a tutorial
/*    public void goToTutorial(View v){
        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);
    }*/
    //Tras haber creadolo, lo unimos con el onClick, seleccionando la función correspondiente

}