package greencarson.videojuego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    //Variables
    EditText aTxt;
    EditText bTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Ocultar barras
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        //Imprimir pantalla
        setContentView(R.layout.activity_login);

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
        if (!stringEmail.isEmpty() && !stringPassword.isEmpty()){
            Intent intent = new Intent(this, SelectLevelActivity.class);
            startActivity(intent);
        }
    }

    //Tras haber creadolo, lo unimos con el onClick, seleccionando la función correspondiente

}