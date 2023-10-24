package greencarson.videojuego;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

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

    //Para ir a dialog_warningquit.xml
    public void dialogWarningQuit(View v) {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(LoginActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_warningquit, viewGroup, false);

        Button buttonBack = dialogView.findViewById(R.id.buttonBack);
        Button buttonQuit = dialogView.findViewById(R.id.buttonQuit);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        buttonBack.setOnClickListener(v1 -> alertDialog.dismiss());
        buttonQuit.setOnClickListener(v1 -> {
            alertDialog.dismiss();
            //Cerrar app
            finishAffinity();
        });

        alertDialog.show();
    }
}