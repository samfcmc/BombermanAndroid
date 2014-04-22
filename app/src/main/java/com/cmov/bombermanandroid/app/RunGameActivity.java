package com.cmov.bombermanandroid.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.*;
import android.widget.TextView;

import com.cmov.bombermanandroid.app.commands.DownCommand;
import com.cmov.bombermanandroid.app.commands.LeftCommand;
import com.cmov.bombermanandroid.app.commands.RightCommand;
import com.cmov.bombermanandroid.app.commands.UpCommand;
import com.cmov.bombermanandroid.app.model.Bomberman;


public class RunGameActivity extends ActionBarActivity {

    private static final int FIRST_PLAYER = 0;

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
        textView.setText(textView.getText()+ "\n" + nick);

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
        Bomberman player = Game.getPlayer(FIRST_PLAYER);
        UpCommand command = new UpCommand(player);
        player.addCommand(command);
    }

    public void downPressed(View view) {
        Bomberman player = Game.getPlayer(FIRST_PLAYER);
        DownCommand command = new DownCommand(player);
        player.addCommand(command);
    }

    public void leftPressed(View view) {
        Bomberman player = Game.getPlayer(FIRST_PLAYER);
        LeftCommand command = new LeftCommand(player);
        player.addCommand(command);
    }

    public void rightPressed(View view) {
        Bomberman player = Game.getPlayer(FIRST_PLAYER);
        RightCommand command = new RightCommand(player);
        player.addCommand(command);
    }

    public void bombPressed(View view) {
    }

    public void pauseGame(View view) {
    }

    public void quitGame(View view) {
        //will be changed
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Game.reset();
    }
}
