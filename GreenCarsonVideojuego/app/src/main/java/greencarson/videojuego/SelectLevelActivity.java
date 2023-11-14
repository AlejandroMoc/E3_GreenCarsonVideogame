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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SelectLevelActivity extends AppCompatActivity {

    //Variables
    SharedPreferences sharedPreferences;
    int levelNumber, viewId, highest1, highest2, highest3;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_selectlevel);

        Button buttonInter = findViewById(R.id.buttonInter);
        Button buttonAdvan = findViewById(R.id.buttonAdvan);
        Button buttonNightmare = findViewById(R.id.buttonNightmare);

        ImageView lock1 = findViewById(R.id.lock1);
        ImageView lock2 = findViewById(R.id.lock2);
        ImageView lock3 = findViewById(R.id.lock3);

        sharedPreferences=getSharedPreferences("my_pref",0);
        //progress = 3;

        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        db.collection("usuarios").document(userId).get().
                addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists())  {
                            highest1 = document.contains("highest1") ? document.getLong("highest1").intValue() : 0;
                            highest2 = document.contains("highest2") ? document.getLong("highest2").intValue() : 0;
                            highest3 = document.contains("highest3") ? document.getLong("highest3").intValue() : 0;

                            if (highest1 < 200) {
                                Log.d("10", "NINGUNO DESBLOQUEDADO");
                                buttonInter.setClickable(false);
                                buttonAdvan.setClickable(false);
                                buttonNightmare.setClickable(false);
                            } else if (highest2 < 500) {
                                Log.d("10", "NIVEL 2 DESBLOQUEADO");
                                buttonAdvan.setClickable(false);
                                buttonNightmare.setClickable(false);
                                lock1.setVisibility(View.INVISIBLE);
                            }
                            else if (highest3 < 800) {
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
                    }
                });

    }

    //Para seleccionar nivel
    public void selectLevel(View v) {

        //Ligar elementos
        Button buttonBasic = findViewById(R.id.buttonBasic);
        Button buttonInter = findViewById(R.id.buttonInter);
        Button buttonAdvan = findViewById(R.id.buttonAdvan);
        Button buttonNightmare = findViewById(R.id.buttonNightmare);

        viewId = v.getId();

        //FALTA AQUI SIMPLIFICAR PARA QUE LOS PRIMEROS 4 CASOS USEN LO MISMO
        if (levelNumber == 1 && viewId == R.id.buttonBasic){
            levelNumber = 0;
            Log.d("0", "Caso 0");
            buttonBasic.setBackgroundResource(R.drawable.gradient_button);
            buttonInter.setBackgroundResource(R.drawable.gradient_button2);
            buttonAdvan.setBackgroundResource(R.drawable.gradient_button);
            buttonNightmare.setBackgroundResource(R.drawable.gradient_button2);
            buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonNightmare.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        } else if (levelNumber == 2 && viewId == R.id.buttonInter){
            levelNumber = 0;
            Log.d("0", "Caso 0");
            buttonBasic.setBackgroundResource(R.drawable.gradient_button);
            buttonInter.setBackgroundResource(R.drawable.gradient_button2);
            buttonAdvan.setBackgroundResource(R.drawable.gradient_button);
            buttonNightmare.setBackgroundResource(R.drawable.gradient_button2);
            buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonNightmare.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        } else if (levelNumber == 3 && viewId == R.id.buttonAdvan){
            levelNumber = 0;
            Log.d("0", "Caso 0");
            buttonBasic.setBackgroundResource(R.drawable.gradient_button);
            buttonInter.setBackgroundResource(R.drawable.gradient_button2);
            buttonAdvan.setBackgroundResource(R.drawable.gradient_button);
            buttonNightmare.setBackgroundResource(R.drawable.gradient_button2);
            buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonNightmare.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        } else if (levelNumber == 4 && viewId == R.id.buttonNightmare){
            levelNumber = 0;
            Log.d("0", "Caso 0");
            buttonBasic.setBackgroundResource(R.drawable.gradient_button);
            buttonInter.setBackgroundResource(R.drawable.gradient_button2);
            buttonAdvan.setBackgroundResource(R.drawable.gradient_button);
            buttonNightmare.setBackgroundResource(R.drawable.gradient_button2);
            buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonNightmare.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

        } else if (viewId == R.id.buttonBasic) {
            levelNumber = 1;
            Log.d("1", "Caso 1");
            buttonBasic.setBackgroundResource(R.drawable.gradient_button);
            buttonInter.setBackgroundResource(R.drawable.gradient_button2_d);
            buttonAdvan.setBackgroundResource(R.drawable.gradient_button_d);
            buttonNightmare.setBackgroundResource(R.drawable.gradient_button2_d);
            buttonBasic.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            buttonInter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
            buttonAdvan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
            buttonNightmare.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.jade_deactivated));
        } else if (viewId == R.id.buttonInter) {
            levelNumber = 2;
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
            finish();
        } else {
            dialogWarningAlert(v);
            Log.d("--1", "Nunca de los nuncas debería pasar este error");
        }
    }

    //Para ir a dialog_warningexit.xml, cerrar sesión y regresar
    public void dialogWarningExit(View v) {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SelectLevelActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_warninglogout, viewGroup, false);

        Button buttonBack = dialogView.findViewById(R.id.buttonBack);
        Button buttonLogOut = dialogView.findViewById(R.id.buttonLogOut);
        mAuth=FirebaseAuth.getInstance();

        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        buttonBack.setOnClickListener(v1 -> alertDialog.dismiss());
        buttonLogOut.setOnClickListener(v1 -> {

            //Cerrar sesión
            alertDialog.dismiss();

            mAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(), getString(R.string.logout_success), Toast.LENGTH_SHORT).show();


/*            mAuth.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), getString(R.string.logout_success), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                //Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


            //Checar si sirve (MIO)
/*            mAuth.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), getString(R.string.logout_success), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            //Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                        }

                    }
                });*/

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

    //Para ir a ranking
    public void goToRanking(View v) {
        Intent intent = new Intent(this, RankingActivity.class);
        startActivity(intent);
        finish();
    }

}