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

import java.util.Objects;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Variables pasadas
        int levelNumber = getIntent().getIntExtra("levelNumber", 0);
/*        if (levelNumber == 1){
            Log.d("1", "SI ha pasado la variable");
        }
        else if (levelNumber == 2){
            Log.d("2", "SI ha pasado la variable");
        }
        else if (levelNumber == 3){
            Log.d("3", "SI ha pasado la variable");
        }
        else{
            Log.d("0", "NO SE HA PASADO LA VARIABLE");
        }*/

        //Ocultar barra de status
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Dendiendo del valor de levelNumber se dibujen distintos tutoriales
        if (levelNumber==1 || levelNumber==2){
            setContentView(R.layout.activity_tutorialbasic);
        }
        else if (levelNumber==3){
            setContentView(R.layout.activity_tutorialadvanced);
        }
        else{
            Log.d("Error TutorialBasicActivity l46", "Jamás de los jamases debería aparecer este caso");
        }

    }

    //Mandar a respectivos niveles
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

    //Para ir a dialog_selectlevel.xml
    public void dialogSelectLevel(View v) {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(TutorialActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_selectlevel, viewGroup, false);
        Button buttonBack = dialogView.findViewById(R.id.buttonBack);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        buttonBack.setOnClickListener(v1 -> alertDialog.dismiss());
        alertDialog.show();
    }

    //Para ir a dialog_trashcan1.xml
    public void dialog_trashcan1(View v) {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(TutorialActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_trashcan1, viewGroup, false);
        Button buttonBack = dialogView.findViewById(R.id.buttonBack);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        buttonBack.setOnClickListener(v1 -> alertDialog.dismiss());
        alertDialog.show();
    }

    public void dialog_trashcan2(View v) {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(TutorialActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_trashcan2, viewGroup, false);
        Button buttonBack = dialogView.findViewById(R.id.buttonBack);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        buttonBack.setOnClickListener(v1 -> alertDialog.dismiss());
        alertDialog.show();
    }

    public void dialog_trashcan3(View v) {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(TutorialActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_trashcan3, viewGroup, false);
        Button buttonBack = dialogView.findViewById(R.id.buttonBack);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        buttonBack.setOnClickListener(v1 -> alertDialog.dismiss());
        alertDialog.show();
    }

    public void dialog_trashcan4(View v) {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(TutorialActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_trashcan4, viewGroup, false);
        Button buttonBack = dialogView.findViewById(R.id.buttonBack);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        buttonBack.setOnClickListener(v1 -> alertDialog.dismiss());
        alertDialog.show();
    }
}