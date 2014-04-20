package com.cmov.bombermanandroid.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

//It is a main activity
public class GameActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.cmov.bombermanandroid.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loads game activity layout
        setContentView(R.layout.activity_game);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGameSurfaceView as the View
        setContentView(new MainGameSurfaceView(this));
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

    public void playGame(View view) {

        Intent intent = new Intent(this, RunGameActivity.class);
        //get user nick name
        EditText editText = (EditText) findViewById(R.id.nick_name);
        if(editText.getText() != null) {
            String nick = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, nick);
            startActivity(intent);
        }
    }

    //this method show the game settings
    public void openSettings() {

    }

}
