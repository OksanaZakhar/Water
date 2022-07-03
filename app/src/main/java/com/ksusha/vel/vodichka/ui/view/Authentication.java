package com.ksusha.vel.vodichka.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.databinding.ActivityAuthenticationBinding;

public class Authentication extends AppCompatActivity {
    FirebaseAuth auth;
    static final String TAG = "Authentication";
    boolean isLoginModeActive;
    ActivityAuthenticationBinding activityAuthenticationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityAuthenticationBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_authentication);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Authentication.this, UserInfo.class));
        }
    }


    private boolean validateEmail() {
        String emailInput = activityAuthenticationBinding.textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            activityAuthenticationBinding.textInputEmail.setError("Будь ласка введіть ваш Email");
            return false;
        } else {
            activityAuthenticationBinding.textInputEmail.setError("");
            return true;
        }
    }

    private boolean validateName() {
        String nameInput = activityAuthenticationBinding.textInputName.getEditText().getText().toString().trim();

        if (nameInput.isEmpty()) {
            activityAuthenticationBinding.textInputName.setError("Будь ласка введіть ваше ім'я");
            return false;
        } else if (nameInput.length() > 15) {
            activityAuthenticationBinding.textInputName.setError("Довжина ім'я має бути не більше ніж 15 символів");
            return false;
        } else {
            activityAuthenticationBinding.textInputName.setError("");
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = activityAuthenticationBinding.textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            activityAuthenticationBinding.textInputPassword.setError("Введіть пароль");
            return false;
        } else if (passwordInput.length() < 7) {
            activityAuthenticationBinding.textInputPassword.setError("Пароль має бути більше 6 символів");
            return false;
        } else {
            activityAuthenticationBinding.textInputPassword.setError("");
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String passwordInput = activityAuthenticationBinding.textInputPassword.getEditText().getText().toString().trim();
        String confirmPasswordInput = activityAuthenticationBinding.textInputConfirmPassword.getEditText().getText().toString().trim();

        if (!passwordInput.equals(confirmPasswordInput)) {
            activityAuthenticationBinding.textInputPassword.setError("Паролі не співпадають");
            return false;
        } else {
            activityAuthenticationBinding.textInputPassword.setError("");
            return true;
        }
    }

    public void loginSingUpUser(View view) {
        if (!validateEmail() | !validateName() | !validatePassword()) {
            return;
        }
        if (isLoginModeActive) {
            auth.signInWithEmailAndPassword(
                            activityAuthenticationBinding.textInputEmail.getEditText().getText().toString().trim(),
                            activityAuthenticationBinding.textInputPassword.getEditText().getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(Authentication.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            if (!validateEmail() | !validateName() | !validatePassword() |
                    !validateConfirmPassword()) {
                return;
            }

            auth.createUserWithEmailAndPassword(
                            activityAuthenticationBinding.textInputEmail.getEditText().getText().toString().trim(),
                            activityAuthenticationBinding.textInputPassword.getEditText().getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Authentication.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
        startActivity(new Intent(Authentication.this, MainActivity.class));
    }

    public void toggleLoginSingUp(View view) {

        if (isLoginModeActive) {
            isLoginModeActive = false;
            activityAuthenticationBinding.loginSingUpButton.setText("Зареєструватися");
            activityAuthenticationBinding.toggleLoginSingUpTextView.setText("Або війти");
            activityAuthenticationBinding.textInputConfirmPassword.setVisibility(View.VISIBLE);
        } else {
            isLoginModeActive = true;
            activityAuthenticationBinding.loginSingUpButton.setText("Увійти");
            activityAuthenticationBinding.toggleLoginSingUpTextView.setText("Або зареєструватися");
            activityAuthenticationBinding.textInputConfirmPassword.setVisibility(View.GONE);
        }
    }

}