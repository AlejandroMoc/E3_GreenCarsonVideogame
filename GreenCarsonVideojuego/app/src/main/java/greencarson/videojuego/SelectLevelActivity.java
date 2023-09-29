package greencarson.videojuego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class SelectLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ocultar barra de status
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_selectlevel);
    }

    //Para cambiar pantalla
//    public void goToNextScreen(View v){
//        Intent intent = new Intent(this, SelectLevelActivity.class);
//        startActivity(intent);
//    }

    //Tras haber creadolo, lo unimos con el onClick, seleccionando la función correspondiente

}