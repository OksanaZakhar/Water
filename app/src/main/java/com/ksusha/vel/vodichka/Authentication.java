package com.ksusha.vel.vodichka;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication extends AppCompatActivity {

    TextInputLayout textInputEmail, textInputName, textInputPassword, textInputConfirmPassword;

    Button loginSingUpButton;
    TextView toggleLoginSingUpTextView;

    FirebaseAuth auth;
    static final String TAG = "Authentication";

    boolean isLoginModeActive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);


        textInputEmail = findViewById(R.id.textInputEmail);
        textInputName = findViewById(R.id.textInputName);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);

        loginSingUpButton = findViewById(R.id.loginSingUpButton);
        toggleLoginSingUpTextView = findViewById(R.id.toggleLoginSingUpTextView);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() !=null){
            startActivity(new Intent(Authentication.this, UserInfo.class));
        }

    }


    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Будь ласка введіть ваш Email");
            return false;
        } else {
            textInputEmail.setError("");
            return true;
        }
    }

    private boolean validateName() {
        String nameInput = textInputName.getEditText().getText().toString().trim();

        if (nameInput.isEmpty()) {
            textInputName.setError("Будь ласка введіть ваше ім'я");
            return false;
        } else if (nameInput.length() > 15) {
            textInputName.setError("Довжина ім'я має бути не більше ніж 15 символів");
            return false;
        } else {
            textInputName.setError("");
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Введіть пароль");
            return false;
        } else if (passwordInput.length() < 7) {
            textInputPassword.setError("Пароль має бути більше 6 символів");
            return false;
        } else {
            textInputPassword.setError("");
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        String confirmPasswordInput = textInputConfirmPassword.getEditText().getText().toString().trim();

        if (!passwordInput.equals(confirmPasswordInput)) {
            textInputPassword.setError("Паролі не співпадають");
            return false;
        } else {
            textInputPassword.setError("");
            return true;
        }
    }

    public void loginSingUpUser(View view) {
        if (!validateEmail() | !validateName() | !validatePassword()) {
            return;
        }
        if (isLoginModeActive) {
            auth.signInWithEmailAndPassword(
                    textInputEmail.getEditText().getText().toString().trim(),
                    textInputPassword.getEditText().getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(Authentication.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        } else {
            if (!validateEmail() | !validateName() | !validatePassword() |
                    !validateConfirmPassword()) {
                return;
            }

            auth.createUserWithEmailAndPassword(
                    textInputEmail.getEditText().getText().toString().trim(),
                    textInputPassword.getEditText().getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Authentication.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });

        }
        startActivity(new Intent(Authentication.this, MainActivity.class));
    }

    public void toggleLoginSingUp(View view) {

        if (isLoginModeActive) {
            isLoginModeActive = false;
            loginSingUpButton.setText("Зареєструватися");
            toggleLoginSingUpTextView.setText("Або війти");
            textInputConfirmPassword.setVisibility(View.VISIBLE);
        } else {
            isLoginModeActive = true;
            loginSingUpButton.setText("Увійти");
            toggleLoginSingUpTextView.setText("Або зареєструватися");
            textInputConfirmPassword.setVisibility(View.GONE);
        }
    }

}