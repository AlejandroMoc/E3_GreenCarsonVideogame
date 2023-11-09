package greencarson.videojuego;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    //Variables
    EditText aTxt, bTxt;
    Button buttonLog;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), SelectLevelActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Ocultar barras
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        //Imprimir pantalla
        setContentView(R.layout.activity_login);

        //Ligar elementos a la pantalla
        aTxt=findViewById(R.id.textEmail);
        bTxt=findViewById(R.id.textPassword);
        buttonLog = findViewById(R.id.buttonLogin);
        mAuth = FirebaseAuth.getInstance();

        buttonLog.setOnClickListener(v -> {
            String email = String.valueOf(aTxt.getText());
            String password = String.valueOf(bTxt.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(LoginActivity.this, getString(R.string.userEmail), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, getString(R.string.userPassword), Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SelectLevelActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        });
    }

    //Para ir a dialog_warningquit.xml
    public void dialogWarningQuit(View v) {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(LoginActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_warningquit, viewGroup, false);

        Button buttonBack = dialogView.findViewById(R.id.buttonBack);
        Button buttonQuit = dialogView.findViewById(R.id.buttonQuit);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        buttonBack.setOnClickListener(v1 -> alertDialog.dismiss());
        buttonQuit.setOnClickListener(v1 -> {
            alertDialog.dismiss();
            //Cerrar app
            finishAffinity();
        });

        alertDialog.show();
    }
}