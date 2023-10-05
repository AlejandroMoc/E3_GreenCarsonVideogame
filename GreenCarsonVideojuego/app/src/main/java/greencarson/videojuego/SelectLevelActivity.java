package greencarson.videojuego;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class SelectLevelActivity extends AppCompatActivity {

    //Declarar variables
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
        switch(v.getId()) {
            case R.id.buttonBasic:
                levelNumber = 1;
                break;
            case R.id.buttonInter:
                levelNumber = 2;
                break;
            case R.id.buttonAdv:
                levelNumber = 3;
                break;
        }
    }

    //Para empezar nivel
    public void startLevel(View v){

        //Seleccionar nivel
        switch (levelNumber) {
            case 1:
                Log.d("1", "Se envia a nivel 1");
                break;
            case 2:
                Log.d("2", "Se envia a nivel 2");
                break;
            case 3:
                Log.d("3", "Se envia a nivel 3");
                break;
            default:
                Log.d("0", "Se envia a nivel 1");
                break;
        }
    }

    //Para regresar
    public void goToBackScreen(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //Tras haber creadolo, lo unimos con el onClick, seleccionando la función correspondiente

}