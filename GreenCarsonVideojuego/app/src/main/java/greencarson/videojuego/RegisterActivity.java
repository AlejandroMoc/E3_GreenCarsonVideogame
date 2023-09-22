package greencarson.videojuego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    //Para ligar al boton
    /*
    public void goToNextScreen(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    */
    //Tras haber creadolo, lo unimos con el onClick, seleccionando goToNextScreen

}