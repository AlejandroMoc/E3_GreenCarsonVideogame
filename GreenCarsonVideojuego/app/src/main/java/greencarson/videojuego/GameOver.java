package greencarson.videojuego;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class GameOver extends AppCompatActivity {

    TextView tvPoints;
    TextView tvHighest;
    SharedPreferences sharedPreferences;
    ImageView ivNewHighest;
    int winningState, points, levelNumber, highest1, highest2, highest3, highest4;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

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

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("usuarios").document(userId);

        db.collection("usuarios").document(userId).get().
                addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists())  {
                                highest1 = document.contains("highest1") ? document.getLong("highest1").intValue() : 0;
                                highest2 = document.contains("highest2") ? document.getLong("highest2").intValue() : 0;
                                highest3 = document.contains("highest3") ? document.getLong("highest3").intValue() : 0;
                                highest4 = document.contains("highest4") ? document.getLong("highest4").intValue() : 0;

                                if (levelNumber == 1 && points > highest1) {
                                    ivNewHighest.setVisibility(View.VISIBLE);
                                    highest1 = points;
                                    docRef.update("highest1", points);
                                }
                                if (levelNumber == 1) {
                                    tvHighest.setText(getString(R.string.highest_placeholder, highest1));
                                }
                                if (levelNumber == 2 && points > highest2) {
                                    ivNewHighest.setVisibility(View.VISIBLE);
                                    highest2 = points;
                                    docRef.update("highest2", points);
                                }
                                if (levelNumber == 2) {
                                    tvHighest.setText(getString(R.string.highest_placeholder, highest2));
                                }
                                if (levelNumber == 3 && points > highest3) {
                                    ivNewHighest.setVisibility(View.VISIBLE);
                                    highest3 = points;
                                    docRef.update("highest3", points);
                                }
                                if (levelNumber == 3) {
                                    tvHighest.setText(getString(R.string.highest_placeholder, highest3));
                                }
                                if (levelNumber == 4 && points > highest4) {
                                    ivNewHighest.setVisibility(View.VISIBLE);
                                    highest4 = points;
                                    docRef.update("highest4", highest4);
                                }
                                if (levelNumber == 4) {
                                    tvHighest.setText(getString(R.string.highest_placeholder, highest4));
                                }

                                int rank_points = highest1 + highest2 + highest3 + highest4;

                                docRef.update("rank_points", rank_points);
                                
                            }
                        }
                    }
                });

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