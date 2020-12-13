package com.best.movie.note.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.best.movie.note.MainActivity;
import com.best.movie.note.R;

public class SplashActivity extends AppCompatActivity {

    private SplashActivityViewModel viewModel;
    private boolean stopFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        viewModel = new ViewModelProvider(this).get(SplashActivityViewModel.class);

        viewModel.getIsLock().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean lock) {
                if (!lock) {
                    startActivity();
                    viewModel.setLock(true);
                }
            }
        });
    }

    public void startActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }, 3000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopFlag = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (stopFlag) {
            startActivity();
        }
    }

}