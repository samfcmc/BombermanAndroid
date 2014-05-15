package com.cmov.bombermanandroid.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cmov.bombermanandroid.app.events.JoinedMultiplayerGameEvent;
import com.cmov.bombermanandroid.app.events.MultiplayerGameFoundEvent;
import com.cmov.bombermanandroid.app.modes.GameMode;
import com.cmov.bombermanandroid.app.multiplayer.FoundMultiplayerGameInfo;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerGameInfo;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;
import com.cmov.bombermanandroid.app.multiplayer.communication.WDSimCommunicationManager;

import java.util.ArrayList;
import java.util.List;

public class MultiplayerActivity extends ActionBarActivity {

    private String gameName;
    private int maxPlayers;

    private List<FoundMultiplayerGameInfo> multiplayerGamesList;

    private ListView listView;
    private MultiplayerGamesListAdapter listAdapter;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        this.multiplayerGamesList = new ArrayList<FoundMultiplayerGameInfo>();

        this.nickname = getIntent().getStringExtra(GameActivity.NICK);
        initViews();

        MultiplayerManager.init(new WDSimCommunicationManager(this));

        Game.getEventBus().register(this);
    }

    private void initViews() {
        this.listView = (ListView) findViewById(R.id.listView_multiplayer_games_list);
        this.listAdapter = new MultiplayerGamesListAdapter();
        this.listView.setAdapter(this.listAdapter);

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoundMultiplayerGameInfo gameInfo = MultiplayerActivity.
                        this.listAdapter.getItem(position);
                MultiplayerManager.askToJoinGame(gameInfo);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.multiplayer, menu);
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

    public void createNewMultiplayerGame(View view) {
        NewMultiplayerGameDialog dialog = new NewMultiplayerGameDialog();
        dialog.show(getFragmentManager(), "");
    }

    public void refreshMultiplayerGamesList(View view) {
        MultiplayerManager.discoverGames();
    }

    private void refreshMultiplayerGamesList() {
        runOnUiThread(new Thread() {
            @Override
            public void run() {
                MultiplayerActivity.this.multiplayerGamesList = MultiplayerManager.getFoundGames();
                MultiplayerActivity.this.listAdapter.clear();
                MultiplayerActivity.this.listAdapter.addAll(
                        MultiplayerActivity.this.multiplayerGamesList);
                MultiplayerActivity.this.listAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onEvent(MultiplayerGameFoundEvent event) {
        refreshMultiplayerGamesList();
    }

    public void onEvent(JoinedMultiplayerGameEvent event) {
        runOnUiThread(new Thread() {
            @Override
            public void run() {
                Intent intent = new Intent(MultiplayerActivity.this, RunGameActivity.class);
                intent.putExtra(GameActivity.NICK, MultiplayerActivity.this.nickname);
                intent.putExtra(GameActivity.MODE, GameMode.MULTIPLAYER);
                startActivity(intent);
            }
        });

    }

    public void doCreateMultiplayerClick(String gameName, String maxPlayers){
        this.gameName = gameName;
        this.maxPlayers = Integer.parseInt(maxPlayers);

        Intent intent = new Intent(this, RunGameActivity.class);
        intent.putExtra(GameActivity.NICK, this.nickname);
        intent.putExtra(GameActivity.MODE, GameMode.MULTIPLAYER);
        MultiplayerManager.createGame(gameName, this.maxPlayers);
        startActivity(intent);

    }

    private class MultiplayerGamesListAdapter extends ArrayAdapter<FoundMultiplayerGameInfo> {

        public MultiplayerGamesListAdapter() {
            super(MultiplayerActivity.this, R.layout.list_item_multiplayer_list,
                    MultiplayerActivity.this.multiplayerGamesList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item_multiplayer_list,
                        parent, false);
            }
            MultiplayerGameInfo item = getItem(position);
            TextView nameTextView = (TextView) convertView.
                    findViewById(R.id.textView_multiplayer_list_item_name);
            TextView numberOfPlayersTextView = (TextView) convertView.
                    findViewById(R.id.textView_multiplayer_list_item_players_number);

            nameTextView.setText(item.getName());
            numberOfPlayersTextView.setText(item.getNumberOfPlayers() + "");

            return convertView;
        }
    }

 }
