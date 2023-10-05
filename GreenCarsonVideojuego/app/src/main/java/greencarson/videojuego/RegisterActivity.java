package greencarson.videojuego;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    //Declarar variables
    EditText aTxt;
    EditText bTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ocultar barra de status
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        //Ligar elementos a la pantalla
        aTxt=findViewById(R.id.textEmail);
        bTxt=findViewById(R.id.textPassword);
    }

    //Para hacer login
    public void goToNextScreen(View v){

        //Recuperar strings de login
        String stringEmail = aTxt.getText().toString();
        String stringPassword = bTxt.getText().toString();

        //Falta hacer login antes de hacer intento
        Intent intent = new Intent(this, SelectLevelActivity.class);
        startActivity(intent);
    }


    //Tras haber creadolo, lo unimos con el onClick, seleccionando la función correspondiente

}