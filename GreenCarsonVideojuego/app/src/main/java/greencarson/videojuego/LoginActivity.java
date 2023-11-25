package greencarson.videojuego;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    // Variables
    EditText aTxt, bTxt;
    Button buttonLog, buttonSee;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    private GoogleSignInClient client;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Si el usuario ya inició sesión, redirigirlo a niveles
        if (currentUser != null) {
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

        setContentView(R.layout.activity_login);

        //Recuperar elementos
        aTxt = findViewById(R.id.textEmail);
        bTxt = findViewById(R.id.textPassword);
        buttonLog = findViewById(R.id.buttonLogin);
        buttonSee = findViewById(R.id.buttonSee);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        buttonSee.setOnClickListener(v -> {
            if (bTxt.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                bTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                buttonSee.setBackground(getDrawable(R.drawable.logo_visible));
            } else {
                bTxt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                buttonSee.setBackground(getDrawable(R.drawable.logo_visibleno));
            }
        });

        //Iniciar login de Google
        // Login Google
        TextView textViewGoogle = findViewById(R.id.buttonLoginGoogle);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(this, options);

        textViewGoogle.setOnClickListener(view -> {
            Intent i = client.getSignInIntent();
            startActivityForResult(i, 1234);
        });

        buttonLog.setOnClickListener(view -> {
            String email = aTxt.getText().toString();
            String password = bTxt.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(LoginActivity.this, getString(R.string.userEmail), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, getString(R.string.userPassword), Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SelectLevelActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                            aTxt.setBackgroundResource(R.drawable.gradient_textview2);
                            bTxt.setBackgroundResource(R.drawable.gradient_textview2);
                        }
                    });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(this, task1 -> {
                                if (task1.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.logingoogle_success), Toast.LENGTH_SHORT).show();
                                    aTxt.setBackgroundResource(R.drawable.gradient_textview);
                                    bTxt.setBackgroundResource(R.drawable.gradient_textview);
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    createDocument(user);
                                    Intent intent = new Intent(getApplicationContext(), SelectLevelActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, getString(R.string.logingoogle_failed), Toast.LENGTH_SHORT).show();
                                    aTxt.setBackgroundResource(R.drawable.gradient_textview2);
                                    bTxt.setBackgroundResource(R.drawable.gradient_textview2);
                                }
                            });
                }
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, getString(R.string.logingoogle_failed), Toast.LENGTH_SHORT).show();
                aTxt.setBackgroundResource(R.drawable.gradient_textview2);
                bTxt.setBackgroundResource(R.drawable.gradient_textview2);
            }
        }
    }

    private void createDocument(FirebaseUser user) {
        DocumentReference docRef = db.collection("usuarios").document(user.getUid());
        String fullName = user.getDisplayName();
        String firstName = fullName.split(" ")[0];

        Map<String, Object> userData = new HashMap<>();
        userData.put("nombres", firstName);
        userData.put("highest1", 0);
        userData.put("rank_points", 0);

        docRef.set(userData).addOnSuccessListener(unused -> Log.d("30", "Documento")).addOnFailureListener(e -> Log.d("30", "No Documento :C"));
    }

    //Diálogo de advertencia
    public void dialogWarningQuit(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
            //Cerrar la app
            finishAffinity();
        });

        alertDialog.show();
    }

    //Ir a reestablecer contraseña
    public void goToResetPass(View v){
        Intent intent = new Intent(this, ResetPassActivity.class);
        startActivity(intent);
        finish();
    }

}