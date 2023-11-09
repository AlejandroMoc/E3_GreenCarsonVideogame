package greencarson.videojuego;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class SelectLevelActivity extends AppCompatActivity {

    //Variables
    SharedPreferences sharedPreferences;
    int levelNumber = 0, viewId, buttonSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_selectlevel);

        Button buttonBasic = findViewById(R.id.buttonLevel);
        Button buttonInter = findViewById(R.id.buttonInter);
        Button buttonAdvan = findViewById(R.id.buttonAdvan);
        Button buttonNightmare = findViewById(R.id.buttonNightmare);

        ImageView lock1 = findViewById(R.id.lock1);
        ImageView lock2 = findViewById(R.id.lock2);
        ImageView lock3 = findViewById(R.id.lock3);

        sharedPreferences=getSharedPreferences("my_pref",0);
        int points = sharedPreferences.getInt("highest", 0);
        int progress = sharedPreferences.getInt("progress",0);
        //int progress = 3;
        Log.d("0", progress + " " + points);

        if (progress == 0) {
            Log.d("10", "NINGUNO DESBLOQUEDADO");
            buttonInter.setClickable(false);
            buttonAdvan.setClickable(false);
            buttonNightmare.setClickable(false);
        } else if (progress == 1) {
            Log.d("10", "NIVEL 2 DESBLOQUEADO");
            buttonAdvan.setClickable(false);
            buttonNightmare.setClickable(false);
            lock1.setVisibility(View.INVISIBLE);
        }
        else if (progress == 2) {
            Log.d("10", "NIVEL 2 Y 3 DESBLOQUEADO");
            buttonNightmare.setClickable(false);
            lock1.setVisibility(View.INVISIBLE);
            lock2.setVisibility(View.INVISIBLE);
        }
        else {
            Log.d("10", "TODOS DESBLOQUEADOS");
            lock1.setVisibility(View.INVISIBLE);
            lock2.setVisibility(View.INVISIBLE);
            lock3.setVisibility(View.INVISIBLE);
        }
    }

    //Para seleccionar nivel
    public void selectLevel(View v) {

        //Ligar elementos
        Button buttonBasic = findViewById(R.id.buttonLevel);
        Button buttonInter = findViewById(R.id.buttonInter);
        Button buttonAdvan = findViewById(R.id.buttonAdvan);
        Button buttonNightmare = findViewById(R.id.buttonNightmare);

        viewId = v.getId();

        if (viewId == R.id.buttonLevel) {
            levelNumber = 1;
            buttonSelected = 1;
            Log.d("1", "Caso 1");
            buttonBasic.setBackgroundResource(R.drawable.gradient_button);
            buttonInter.setBackgroundResource(R.drawable.gradient_button2_d);
            buttonAdvan.setBackgroundResource(R.drawable.gradient_button_d);
            buttonNightmare.setBackgroundResource(R.drawable.gradient_button2_d);
            buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
            buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
            buttonNightmare.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
            //Falta poner para desactivar botones

        } else if (viewId == R.id.buttonInter) {
            levelNumber = 2;
            buttonSelected = 1;
            Log.d("2", "Caso 2");
            buttonBasic.setBackgroundResource(R.drawable.gradient_button_d);
            buttonInter.setBackgroundResource(R.drawable.gradient_button2);
            buttonAdvan.setBackgroundResource(R.drawable.gradient_button_d);
            buttonNightmare.setBackgroundResource(R.drawable.gradient_button2_d);
            buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
            buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
            buttonNightmare.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));

        } else if (viewId == R.id.buttonAdvan) {
            levelNumber = 3;
            buttonSelected = 1;
            Log.d("3", "Caso 3");
            buttonBasic.setBackgroundResource(R.drawable.gradient_button_d);
            buttonInter.setBackgroundResource(R.drawable.gradient_button2_d);
            buttonAdvan.setBackgroundResource(R.drawable.gradient_button);
            buttonNightmare.setBackgroundResource(R.drawable.gradient_button2_d);
            buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
            buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
            buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonNightmare.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
            
        } else if (viewId == R.id.buttonNightmare) {
            levelNumber = 4;
            buttonSelected = 1;
            Log.d("4", "Caso 4");
            buttonBasic.setBackgroundResource(R.drawable.gradient_button_d);
            buttonInter.setBackgroundResource(R.drawable.gradient_button2_d);
            buttonAdvan.setBackgroundResource(R.drawable.gradient_button_d);
            buttonNightmare.setBackgroundResource(R.drawable.gradient_button2);
            buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
            buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
            buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
            buttonNightmare.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        }

        //FALTA AQUI VER SI ES POSIBLE 'DESACTIVAR' BOTONES
/*        else {
            levelNumber=0;
            buttonSelected=0;
            Log.d("0", "Caso 0");
            buttonBasic.setBackgroundResource(R.drawable.gradient_button);
            buttonInter.setBackgroundResource(R.drawable.gradient_button2);
            buttonAdvan.setBackgroundResource(R.drawable.gradient_button);
            buttonNightmare.setBackgroundResource(R.drawable.gradient_button2);
            buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonNightmare.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        }*/
    }

    //Para enviar a tutoriales
    public void startTutorial(View v){
        Intent intent;
        //Mandar a java de tutoriales
        if (levelNumber>0 && levelNumber<5) {
            Log.d("100", "Se manda a tutorial");
            intent = new Intent(this, TutorialActivity.class);
            intent.putExtra("levelNumber", levelNumber);
            startActivity(intent);
        } else {
            dialogWarningAlert(v);
            Log.d("--1", "Nunca de los nuncas debería pasar este error");
        }
    }

    //Para ir a dialog_warningexit.xml y para regresar
    public void dialogWarningExit(View v) {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SelectLevelActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_warninglogout, viewGroup, false);

        Button buttonBack = dialogView.findViewById(R.id.buttonBack);
        Button buttonLogOut = dialogView.findViewById(R.id.buttonLogOut);

        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        buttonBack.setOnClickListener(v1 -> alertDialog.dismiss());
        buttonLogOut.setOnClickListener(v1 -> {
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

    //Para ir a dialog_warningalert.xml
    public void dialogWarningAlert(View v) {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SelectLevelActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_warningalert, viewGroup, false);
        Button buttonBack = dialogView.findViewById(R.id.buttonBack);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        buttonBack.setOnClickListener(v1 -> alertDialog.dismiss());
        alertDialog.show();
    }
}