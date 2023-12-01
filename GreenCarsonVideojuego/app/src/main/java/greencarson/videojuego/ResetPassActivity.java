/*
    Nombre del archivo: ResetPassActivity.java
    Nombre del proyecto: Green Carson Reecicla! Game

    Creado y Desarrollado por:

    Alejandro Daniel Moctezuma Cruz

    En colaboración con el Instituto Tecnológico y
    de Estudios Superiores de Monterrey y la empresa Green Carson.
*/

package greencarson.videojuego;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends Activity {
    Button btnBack, btnReset;
    EditText edtEmail;
    FirebaseAuth mAuth;
    String strEmail;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);

        btnBack = findViewById(R.id.buttonBack);
        btnReset = findViewById(R.id.buttonLeft);
        edtEmail = findViewById(R.id.textEmailReset);

        mAuth = FirebaseAuth.getInstance();

        //Resetear button listener
        btnReset.setOnClickListener(v -> {
            strEmail = edtEmail.getText().toString().trim();
            if (!TextUtils.isEmpty(strEmail)){
                ResetPassword();
            } else {
                edtEmail.setError("Email field can't be empty");
            }
        });
    }

    //Función de reset
    private void ResetPassword(){
        btnReset.setVisibility(View.INVISIBLE);
        mAuth.sendPasswordResetEmail(strEmail)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(ResetPassActivity.this, getString(R.string.reset_successful), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetPassActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ResetPassActivity.this, getString(R.string.reset_failed), Toast.LENGTH_SHORT).show();
                    Toast.makeText(ResetPassActivity.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                    btnReset.setVisibility(View.INVISIBLE);
                });

    }

    //Para regresar a pantalla de login
    public void goToLogin(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //Función nativa de regresar
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
