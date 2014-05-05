package com.cmov.bombermanandroid.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cmov.bombermanandroid.app.commands.Command;
import com.cmov.bombermanandroid.app.commands.TurnPauseOnOffCommand;
import com.cmov.bombermanandroid.app.events.TimePassedEvent;
import com.cmov.bombermanandroid.app.events.UpdatedGameStateEvent;


public class RunGameActivity extends ActionBarActivity {

    private static final int FIRST_PLAYER = 0;
    private TextView scoreTextView;
    private TextView timeLeftTextView;
    private TextView numberPlayersTextView;
    private UpdateGameStateThread updatedGameStateThread;

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
        textView.setText(textView.getText() + "\n" + nick);

        initViews();
        initThreads();
        subscribeToEvents();
    }

    private void initViews() {
        this.scoreTextView = (TextView) findViewById(R.id.player_score);
        this.timeLeftTextView = (TextView) findViewById(R.id.time_left);
        this.numberPlayersTextView = (TextView) findViewById(R.id.numb_players);
    }

    private void initThreads() {
        this.updatedGameStateThread = new UpdateGameStateThread();
    }

    private void subscribeToEvents() {
        Game.getEventBus().register(this);
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
        Game.sendUpCommand(FIRST_PLAYER);
    }

    public void downPressed(View view) {
        Game.sendDownCommand(FIRST_PLAYER);
    }

    public void leftPressed(View view) {
        Game.sendLeftCommand(FIRST_PLAYER);
    }

    public void rightPressed(View view) {
        Game.sendRightCommand(FIRST_PLAYER);
    }

    public void bombPressed(View view) {
        Game.dropBomb(FIRST_PLAYER);
    }

    public void pauseGame(View view) {
        Command command = new TurnPauseOnOffCommand();
        Game.addCommand(command);
    }

    public void quitGame(View view) {
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void onEvent(UpdatedGameStateEvent event) {
        runOnUiThread(updatedGameStateThread);
    }

    public void onEvent(final TimePassedEvent event) {
        runOnUiThread(new Thread() {
            public void run() {
                updateTimerTextView(event.getTimeLeft());
            }
        });
    }

    private void updateNumberOfPlayersTextView(){
        int number_players = Game.getNumberOfPlayers();
        String displayText = getResources().getString(R.string.numb_players) + "\n" + Integer.toString(number_players);
        this.numberPlayersTextView.setText(displayText);
    }

    private void updateScoreTextView() {
        int score = Game.getPlayerScore(FIRST_PLAYER);
        String displayText = getResources().getString(R.string.player_score) + "\n" + Integer.toString(score);
        this.scoreTextView.setText(displayText);
    }

    private void updateTimerTextView(int timeLeft) {
        String displayText = getResources().getString(R.string.time_left) + "\n" + timeLeft + "";
        this.timeLeftTextView.setText(displayText);
    }

    private class UpdateGameStateThread extends Thread {
        public void run() {
            updateNumberOfPlayersTextView();
            updateScoreTextView();
        }
    }
}
