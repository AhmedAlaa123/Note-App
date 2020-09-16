package com.example.noteproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.noteproject.R;

public class SplashScreen extends AppCompatActivity {
    private TextView textView_save, textView_your, textView_notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textView_notes = findViewById(R.id.text_View_notes);
        textView_save = findViewById(R.id.text_View_save);
        textView_your = findViewById(R.id.text_View_your);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.save_animation);
        textView_save.startAnimation(animation1);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.your_animation);
        textView_your.startAnimation(animation2);
        Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.notes_animation);
        textView_notes.startAnimation(animation3);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(7000);
                            startActivity(new Intent(SplashScreen.this, MainActivity.class));
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }
}