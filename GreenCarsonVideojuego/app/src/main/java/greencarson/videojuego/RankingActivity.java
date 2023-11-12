package greencarson.videojuego;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RankingActivity extends Activity {

    TextView nameRanking, pointsRanking, numberRanking;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        nameRanking = findViewById(R.id.nameRanking);
        pointsRanking = findViewById(R.id.pointsRanking);
        numberRanking = findViewById(R.id.numberRanking);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String userId = mAuth.getCurrentUser().getUid();

        db.collection("usuarios").orderBy("rank_points", Query.Direction.DESCENDING).get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int position = 1;
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if (document.getId().equals(userId)) {
                                    nameRanking.setText(document.getString("nombre_s"));
                                    pointsRanking.setText(document.getLong("rank_points").toString());
                                    numberRanking.setText(Integer.toString(position));
                                }

                                Log.d("20", position + " - " + document.getId() + " => " + document.getData());
                                position++;
                            }
                        } else {
                            Log.w("20", "Error getting documents.", task.getException());
                        }

                    }
                });

    }

    //Para ir a niveles
    public void goToLevels(View v){
        Intent intent = new Intent(this, SelectLevelActivity.class);
        startActivity(intent);
        finish();
    }

}
