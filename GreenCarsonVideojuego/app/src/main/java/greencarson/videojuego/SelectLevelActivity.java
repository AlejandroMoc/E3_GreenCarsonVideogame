package greencarson.videojuego;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Objects;

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
                buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
                buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));

                //Falta poner para desactivar botones
                break;

            case R.id.buttonInter:
                levelNumber = 2;
                buttonBasic.setBackgroundResource(R.drawable.gradient_button_d);
                buttonInter.setBackgroundResource(R.drawable.gradient_button2);
                buttonAdvan.setBackgroundResource(R.drawable.gradient_button_d);
                buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
                buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
                break;
            case R.id.buttonAdvan:
                levelNumber = 3;
                buttonBasic.setBackgroundResource(R.drawable.gradient_button_d);
                buttonInter.setBackgroundResource(R.drawable.gradient_button2_d);
                buttonAdvan.setBackgroundResource(R.drawable.gradient_button);
                buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
                buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
                buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                break;
        }
    }

    //Para empezar nivel
    public void startLevel(View v){

        //Seleccionar nivel
        switch (levelNumber) {
            case 1:
                //Ir al tutorial 1
                Intent intent = new Intent(this, TutorialBasicActivity.class);
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

    //Para ir a dialog_warningexit.xml y para regresar
    public void dialogWarningExit(View v) {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SelectLevelActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_warningexit, viewGroup, false);

        Button buttonBack = dialogView.findViewById(R.id.buttonBack);
        Button buttonQuit = dialogView.findViewById(R.id.buttonQuit);

        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        buttonBack.setOnClickListener(v1 -> alertDialog.dismiss());
        buttonQuit.setOnClickListener(v1 -> {
            //Regresar a pantalla de login
            alertDialog.dismiss();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        alertDialog.show();
    }

    //Para ir a dialog_selectlevel.xml
    public void dialogSelectLevel(View v) {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SelectLevelActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_selectlevel, viewGroup, false);
        Button buttonBack = dialogView.findViewById(R.id.buttonBack);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        buttonBack.setOnClickListener(v1 -> alertDialog.dismiss());
        alertDialog.show();
    }

    //Tras haber creadolo, lo unimos con el onClick, seleccionando la función correspondiente

}