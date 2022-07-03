package com.ksusha.vel.vodichka.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.FirebaseAuth;
import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.databinding.ActivityUserInfoBinding;

public class UserInfo extends AppCompatActivity {
    FirebaseAuth auth;
    ActivityUserInfoBinding activityUserInfoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityUserInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);

        auth = FirebaseAuth.getInstance();

        activityUserInfoBinding.userEmail.setText(auth.getCurrentUser().getEmail());
        activityUserInfoBinding.startPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfo.this, MainActivity.class));
            }
        });

        activityUserInfoBinding.singOut.setOnClickListener(new View.OnClickListener() {
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