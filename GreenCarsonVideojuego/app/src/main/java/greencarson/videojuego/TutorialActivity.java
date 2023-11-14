package greencarson.videojuego;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class TutorialActivity extends AppCompatActivity {

    int levelNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Variables pasadas
        levelNumber = getIntent().getIntExtra("levelNumber", 0);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Enviar a tutorial correspondiente
        if (levelNumber== 4){
            setContentView(R.layout.activity_tutorialadvanced);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            setContentView(R.layout.activity_tutorialbasic);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    //Para regresar
    public void goToSelectScreen(View v){
        Intent intent = new Intent(this, SelectLevelActivity.class);
        startActivity(intent);
        finish();
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

    public void dialog_trashcans(View v){

        int buttonId = v.getId();
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(TutorialActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView;

        //FALTA CAMBIAR == A EQUALS
        if (buttonId == R.id.trashCan4 || buttonId == R.id.trashCan4_help){
            dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_trashcan4, viewGroup, false);
        } else if (buttonId == R.id.trashCan3 || buttonId == R.id.trashCan3_help){
            dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_trashcan3, viewGroup, false);
        } else if (buttonId == R.id.trashCan2 || buttonId == R.id.trashCan2_help){
            dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_trashcan2, viewGroup, false);
        } else {
            dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_trashcan1, viewGroup, false);
        }

        Button buttonBack = dialogView.findViewById(R.id.buttonBack);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        buttonBack.setOnClickListener(v1 -> alertDialog.dismiss());
        alertDialog.show();
    }

    //Enviar a nivel respectivo
    public void playLevel(View v){
        GameView gameView = new GameView(this, levelNumber);
        setContentView(gameView);
    }

}