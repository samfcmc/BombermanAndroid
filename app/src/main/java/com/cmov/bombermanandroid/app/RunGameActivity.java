package com.cmov.bombermanandroid.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.TextView;


public class RunGameActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_run_game);

        //get the nick from the intent
        Intent intent = getIntent();
        String nick = intent.getStringExtra(GameActivity.EXTRA_MESSAGE);

        //create the text view
        TextView textView = (TextView) findViewById(R.id.player_name);
        textView.setText(textView.getText()+ " " + nick);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.run_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void upPressed(View view) {
    }

    public void downPressed(View view) {
    }

    public void leftPressed(View view) {
    }

    public void rightPressed(View view) {
    }

    public void bombPressed(View view) {
    }

    public void pauseGame(View view) {
    }

    public void quitGame(View view) {
    }
}
