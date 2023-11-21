package greencarson.videojuego;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends Activity {
    Button btnBack, btnReset;
    EditText edtEmail;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String strEmail;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);

        btnBack = findViewById(R.id.buttonBack);
        btnReset = findViewById(R.id.buttonReset);
        edtEmail = findViewById(R.id.textEmailReset);

        mAuth = FirebaseAuth.getInstance();

        //Reset button listener
        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                strEmail = edtEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(strEmail)){
                    ResetPassword();
                } else {
                    edtEmail.setError("Email field can't be empty");
                }
            }
        });
    }

    //Función de reset
    private void ResetPassword(){
        btnReset.setVisibility(View.INVISIBLE);
        mAuth.sendPasswordResetEmail(strEmail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ResetPassActivity.this, "Reset password link has been sent to your requested email", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPassActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPassActivity.this, "Reset password link has failed", Toast.LENGTH_SHORT).show();
                        btnReset.setVisibility(View.INVISIBLE);
                    }
                });

    }

    //Para regresar a pantalla de login
    public void goToLogin(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
