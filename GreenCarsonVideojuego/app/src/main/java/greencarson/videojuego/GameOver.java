package greencarson.videojuego;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        int highest = sharedPreferences.getInt("highest",0);

        //Comparar con mayor puntuación
        if (points > highest){
            ivNewHighest.setVisibility(View.VISIBLE);
            highest=points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highest",highest);
            editor.apply();
        }
        tvHighest.setText(getString(R.string.highest_placeholder, highest));
    }

    public void goToWinningState(View v){
        Intent intent;
        intent = new Intent(this, WinningActivity.class);
        intent.putExtra("winningState", winningState);
        intent.putExtra("levelNumber", levelNumber);
        //Pasar aquí levelNumber
        startActivity(intent);
    }

    //AQUI AHORA VERIFICAR SI ES NECESARIO PASAR EL CONTEXTO (YO CREO QUE YA NO)
/*
        Intent intent = new Intent(context, GameOver.class);
        intent.putExtra("points", points);
        intent.putExtra("winningState", winningState);
        ((Activity)context).finish();
        context.startActivity(intent);
    }*/


}