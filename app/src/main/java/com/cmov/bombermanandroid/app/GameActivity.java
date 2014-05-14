package com.cmov.bombermanandroid.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.cmov.bombermanandroid.app.modes.GameMode;

//It is a main activity
public class GameActivity extends ActionBarActivity {

    public final static String NICK = "com.cmov.bombermanandroid.NICK";
    public final static String MODE = "com.cmov.bombermanandroid.MODE";
    public final static String LEVEL = "com.cmov.bombermanandroid.LEVEL";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loads game activity layout
        setContentView(R.layout.activity_game);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = (EditText) findViewById(R.id.nick_name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void playGame(GameMode gameMode) {

        Intent intent = new Intent(this, RunGameActivity.class);
        //get user nick name

        if(editText.getText() != null) {
            String nick = editText.getText().toString();
            intent.putExtra(NICK, nick);
            intent.putExtra(MODE, gameMode);
            startActivity(intent);
        }
    }

    public void launchSingleplayer(View view) {
        playGame(GameMode.SINGLEPLAYER);
    }

    public void launchMultiplayer(View view) {
        Intent intent = new Intent(this, MultiplayerActivity.class);
        intent.putExtra(NICK, editText.getText().toString());
        startActivity(intent);
    }

    //this method show the game settings
    public void openSettings() {

    }

    public void quitApp(View view) {
        finish();
    }
}
