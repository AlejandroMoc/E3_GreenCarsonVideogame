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

    TextView nameRanking, pointsRanking, numberRanking, n1, p1, n2, p2, n3, p3, n4, p4, n5, p5;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        nameRanking = findViewById(R.id.nameRanking);
        pointsRanking = findViewById(R.id.pointsRanking);
        numberRanking = findViewById(R.id.numberRanking);

        n1 = findViewById(R.id.nameRanking1);
        n2 = findViewById(R.id.nameRanking2);
        n3 = findViewById(R.id.nameRanking3);
        n4 = findViewById(R.id.nameRanking4);
        n5 = findViewById(R.id.nameRanking5);

        p1 = findViewById(R.id.pointsRanking1);
        p2 = findViewById(R.id.pointsRanking2);
        p3 = findViewById(R.id.pointsRanking3);
        p4 = findViewById(R.id.pointsRanking4);
        p5 = findViewById(R.id.pointsRanking5);

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

                                if (position == 1) {
                                    n1.setText(document.getString("nombre_s"));
                                    p1.setText(document.getLong("rank_points").toString());
                                } else if (position == 2) {
                                    n2.setText(document.getString("nombre_s"));
                                    p2.setText(document.getLong("rank_points").toString());
                                } else if (position == 3) {
                                    n3.setText(document.getString("nombre_s"));
                                    p3.setText(document.getLong("rank_points").toString());
                                } else if (position == 4) {
                                    n4.setText(document.getString("nombre_s"));
                                    p4.setText(document.getLong("rank_points").toString());
                                } else if (position == 5) {
                                    n5.setText(document.getString("nombre_s"));
                                    p5.setText(document.getLong("rank_points").toString());
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
