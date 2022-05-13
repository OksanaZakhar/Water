package com.ksusha.vel.vodichka;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class UserInfo extends AppCompatActivity {
    FirebaseAuth auth;

    TextView userEmail;
    Button singOut, startPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        auth = FirebaseAuth.getInstance();

        userEmail = findViewById(R.id.userEmail);
        singOut = findViewById(R.id.singOut);
        startPage = findViewById(R.id.startPage);

        userEmail.setText(auth.getCurrentUser().getEmail());

        startPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfo.this, MainActivity.class));
            }
        });

        singOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(UserInfo.this, Authentication.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UserInfo.this, MainActivity.class));
    }
}