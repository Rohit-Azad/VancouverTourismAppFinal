package com.example.vancouvertourismappfinal.CurrencyConverterClasses;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.vancouvertourismappfinal.R;

public class CurrencySplashActivity extends AppCompatActivity {
    TextView title;
    Animation animation;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.SplashTheme);
        setContentView(R.layout.activity_splash);
        title=(TextView)findViewById(R.id.title);


        animation= AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        title.setAnimation(animation);
        animation.setDuration(1000);
        //animation.startNow();

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                Intent intent=new Intent(CurrencySplashActivity.this, CurrencyConverterMainActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });





    }
}
