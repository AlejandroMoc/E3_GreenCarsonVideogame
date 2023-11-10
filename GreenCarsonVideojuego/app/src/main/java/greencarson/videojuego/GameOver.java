package greencarson.videojuego;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class GameOver extends AppCompatActivity {

    TextView tvPoints;
    TextView tvHighest;
    SharedPreferences sharedPreferences;
    ImageView ivNewHighest;
    int winningState, points, levelNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        //Recuperar datos
        tvPoints = findViewById(R.id.tvPoints);
        tvHighest = findViewById(R.id.tvHighest);
        ivNewHighest = findViewById(R.id.ivNewHighest);
        points = Objects.requireNonNull(getIntent().getExtras()).getInt("points");
        winningState = Objects.requireNonNull(getIntent().getExtras()).getInt("winningState");
        levelNumber = Objects.requireNonNull(getIntent().getExtras()).getInt("levelNumber");

        tvPoints.setText(getString(R.string.points_placeholder, points));
        sharedPreferences=getSharedPreferences("my_pref",0);
        int highest1 = sharedPreferences.getInt("highest1",0);
        int highest2 = sharedPreferences.getInt("highest2",0);
        int highest3 = sharedPreferences.getInt("highest3",0);
        int highest4 = sharedPreferences.getInt("highest4",0);
        int rankpoints = sharedPreferences.getInt("rankpoints", 0);
        int progress = sharedPreferences.getInt("progress",0);

        //Comparar con mayor puntuación
        if (levelNumber == 1 && points > highest1) {
            ivNewHighest.setVisibility(View.VISIBLE);
            highest1=points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highest1",highest1);
            editor.apply();
        }
        if (levelNumber == 1) {
            tvHighest.setText(getString(R.string.highest_placeholder, highest1));
        }
        if (levelNumber == 2 && points > highest2) {
            ivNewHighest.setVisibility(View.VISIBLE);
            highest2=points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highest2",highest2);
            editor.apply();
        }
        if (levelNumber == 2) {
            tvHighest.setText(getString(R.string.highest_placeholder, highest2));
        }
        if (levelNumber == 3 && points > highest3) {
            ivNewHighest.setVisibility(View.VISIBLE);
            highest3=points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highest3",highest3);
            editor.apply();
        }
        if (levelNumber == 3) {
            tvHighest.setText(getString(R.string.highest_placeholder, highest3));
        }
        if (levelNumber == 4 && points > highest4) {
            ivNewHighest.setVisibility(View.VISIBLE);
            highest4=points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highest4",highest4);
            editor.apply();
        }
        if (levelNumber == 4) {
            tvHighest.setText(getString(R.string.highest_placeholder, highest4));
        }

        if (winningState == 1 && levelNumber == 1) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("progress", 1);
            editor.apply();
        } else if (winningState == 1 && levelNumber == 2) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("progress", 2);
            editor.apply();
        } else if (winningState == 1 && levelNumber == 3) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("progress", 3);
            editor.apply();
        }

    }

    public void goToWinningState(View v){
        Intent intent;
        intent = new Intent(this, WinningActivity.class);
        intent.putExtra("winningState", winningState);
        intent.putExtra("levelNumber", levelNumber);
        //Pasar aquí levelNumber (experimental)
        startActivity(intent);
    }

    //AQUI AHORA VERIFICAR SI ES NECESARIO PASAR EL CONTEXTO (YO CREO QUE YA NO)
/*
        Intent intent = new Intent(context, GameOver.class);
        intent.putExtra("points", points);
        intent.putExtra("winningState", winningState);
        //FALTA AQUI VER SI ESTA LINEA SE TIENE QUE PONER EN OTROS LAODS
        ((Activity)context).finish();
        context.startActivity(intent);
    }*/
}