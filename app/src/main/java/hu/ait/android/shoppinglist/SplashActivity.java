package hu.ait.android.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Animation scaleAnim = AnimationUtils.loadAnimation(
                SplashActivity.this,
                R.anim.scale_animation);

        final ImageView logo = (ImageView) findViewById(
                R.id.shoppingCar);

        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startMainActivity();
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        logo.startAnimation(scaleAnim);


    }


    public void startMainActivity() {
        Intent intentStart = new Intent(SplashActivity.this,
                MainActivity.class);
        startActivity(intentStart);
    }

}
