package greencarson.videojuego;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TutorialActivity extends AppCompatActivity {

    //Variables
    private Button buttonBasic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ocultar barra de status
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_selectlevel);
    }

    //Jugar nivel sencillo
    public void playLevel_basic(View v){
        Log.d("1", "Se envia a nivel 1");
        //Intent intent = new Intent(this, SelectLevelActivity.class);
        //startActivity(intent);
    }

    //Para regresar
    public void goToSelectScreen(View v){
        Intent intent = new Intent(this, SelectLevelActivity.class);
        startActivity(intent);
    }

    //Tras haber creadolo, lo unimos con el onClick, seleccionando la función correspondiente

}