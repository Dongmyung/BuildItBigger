package com.dongmyungahn.android.jokedisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "extra_joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        TextView tv_jokedisplay_joke = (TextView) findViewById(R.id.tv_jokedisplay_joke);

        String joke = getIntent().getStringExtra(EXTRA_JOKE);

        tv_jokedisplay_joke.setText(joke);

    }
}
