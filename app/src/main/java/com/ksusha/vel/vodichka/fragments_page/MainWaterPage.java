package com.ksusha.vel.vodichka.fragments_page;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ksusha.vel.vodichka.MainActivity;
import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.data.DataForFragment;
import com.ksusha.vel.vodichka.model.Water;

public class MainWaterPage extends AppCompatActivity {
    public static boolean toBasketGo;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_page);
        toBasketGo = false;

        ImageView waterImage = findViewById(R.id.waterPageImage);
        TextView waterDesc = findViewById(R.id.waterDescWaretPage);
        TextView waterPrise = findViewById(R.id.priceWaterPage);
        TextView waterCount = findViewById(R.id.countWaterPage);

        waterImage.setImageResource(getIntent().getIntExtra("waterImage", 0));
        waterDesc.setText(getIntent().getStringExtra("waterDesc"));
        waterPrise.setText(getIntent().getStringExtra("waterPrice"));
        waterCount.setText(getIntent().getStringExtra("waterCount"));
        id = getIntent().getStringExtra("waterId");

        Button waterButtonMinus = findViewById(R.id.waterButtonMinus);
        Button waterButtonPlus = findViewById(R.id.waterButtonPlus);

        waterButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Water w : DataForFragment.waterList) {
                    if (w.getId().equals(id)) {
                        if (w.getCount() > w.getStartCount()) {
                            w.setCount(w.getCount() - 1);
                        } else {
                            w.setMaskClicable(true);
                            w.setMaskVisible(0);
                        }
                        waterCount.setText(Integer.toString(w.getCount()));
                    }
                }
                for (Water w : DataForFragment.stockList) {
                    if (w.getId().equals(id)) {
                        if (w.getCount() > w.getStartCount()) {
                            w.setCount(w.getCount() - 1);
                        } else {
                            w.setMaskClicable(true);
                            w.setMaskVisible(0);
                        }
                        waterCount.setText(Integer.toString(w.getCount()));
                    }
                }
            }
        });

        waterButtonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Water w : DataForFragment.waterList) {
                    if (w.getId().equals(id)) {
                        w.setCount(w.getCount() + 1);
                        w.setMaskClicable(false);
                        w.setMaskVisible(8);
                        waterCount.setText(Integer.toString(w.getCount()));
                    }
                }
                for (Water w : DataForFragment.stockList) {
                    if (w.getId().equals(id)) {
                        w.setCount(w.getCount() + 1);
                        w.setMaskClicable(false);
                        w.setMaskVisible(8);
                        waterCount.setText(Integer.toString(w.getCount()));
                    }
                }
            }
        });


        ImageButton imageButton = findViewById(R.id.buttonHomeWaterPage);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToBasket(View view) {
        toBasketGo = true;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}