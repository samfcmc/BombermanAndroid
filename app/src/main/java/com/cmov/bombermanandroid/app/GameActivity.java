package com.cmov.bombermanandroid.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.cmov.bombermanandroid.app.modes.GameMode;

//It is a main activity
public class GameActivity extends ActionBarActivity {

    public final static String NICK = "com.cmov.bombermanandroid.NICK";
    public final static String MODE = "com.cmov.bombermanandroid.MODE";
    public final static String LEVEL = "com.cmov.bombermanandroid.LEVEL";
    private EditText editText;

    private LevelAdaptor lvlAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loads game activity layout
        setContentView(R.layout.activity_game);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = (EditText) findViewById(R.id.nick_name);

        //load spinner
        Spinner spinner = (Spinner) findViewById(R.id.level_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.levels_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        lvlAdaptor = new LevelAdaptor();

        spinner.setOnItemSelectedListener(lvlAdaptor);
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

    public void playGame(GameMode gameMode, Class clazz) {

        Intent intent = new Intent(this, clazz);

        if(editText.getText() != null) {
            String nick = editText.getText().toString();
            intent.putExtra(NICK, nick);
            intent.putExtra(MODE, gameMode);
            intent.putExtra(LEVEL, lvlAdaptor.getLevel());
            startActivity(intent);
        }
    }

    public void launchSingleplayer(View view) {
        playGame(GameMode.SINGLEPLAYER, RunGameActivity.class);
    }

    public void launchMultiplayer(View view) {
        playGame(GameMode.SINGLEPLAYER, MultiplayerActivity.class);
    }

    //this method show the game settings
    public void openSettings() {

    }

    public void quitApp(View view) {
        finish();
    }
}
