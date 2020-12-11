package com.best.movie.note.ui.splash;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.best.movie.note.R;

public class SplashActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
    }
}