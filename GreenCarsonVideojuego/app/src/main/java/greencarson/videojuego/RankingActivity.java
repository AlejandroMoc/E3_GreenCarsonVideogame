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

    TextView nameRanking, pointsPersonal, numberPersonal;
    String pointsString, rankPoints, pointsSum;
    TextView n1, n2, n3, n4, n5, p1, p2, p3, p4, p5;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        nameRanking = findViewById(R.id.nameRanking);
        pointsPersonal = findViewById(R.id.pointsPersonal);
        numberPersonal = findViewById(R.id.numberPersonal);

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

                                    //pointsPersonal.setText(document.getLong("rank_points").toString(), TextView.BufferType.valueOf(getResources().getString(R.string.points_ranking)));

                                    pointsPersonal.setText(document.getLong("rank_points").toString());
                                    pointsString = getResources().getString(R.string.points_ranking);
                                    rankPoints = document.getLong("rank_points").toString();
                                    pointsSum = rankPoints + pointsString;
                                    pointsPersonal.setText(pointsSum);

                                    numberPersonal.setText(Integer.toString(position));
                                }

                                if (position == 1) {
                                    n1.setText(document.getString("nombre_s"));
                                    //p1.setText(document.getLong("rank_points").toString());
                                    pointsSum=document.getLong("rank_points").toString();
                                    pointsSum = pointsSum + pointsString;
                                    p1.setText(pointsSum);

                                } else if (position == 2) {
                                    n2.setText(document.getString("nombre_s"));
                                    pointsSum=document.getLong("rank_points").toString();
                                    pointsSum = pointsSum + pointsString;
                                    p2.setText(pointsSum);

                                } else if (position == 3) {
                                    n3.setText(document.getString("nombre_s"));
                                    pointsSum=document.getLong("rank_points").toString();
                                    pointsSum = pointsSum + pointsString;
                                    p3.setText(pointsSum);

                                } else if (position == 4) {
                                    n4.setText(document.getString("nombre_s"));
                                    pointsSum=document.getLong("rank_points").toString();
                                    pointsSum = pointsSum + pointsString;
                                    p4.setText(pointsSum);

                                } else if (position == 5) {
                                    n5.setText(document.getString("nombre_s"));
                                    pointsSum=document.getLong("rank_points").toString();
                                    pointsSum = pointsSum + pointsString;
                                    p5.setText(pointsSum);

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
