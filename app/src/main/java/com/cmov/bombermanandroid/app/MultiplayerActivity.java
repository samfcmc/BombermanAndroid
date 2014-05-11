package com.cmov.bombermanandroid.app;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cmov.bombermanandroid.app.multiplayer.MultiplayerGameInfo;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;

import java.util.ArrayList;
import java.util.List;

public class MultiplayerActivity extends ActionBarActivity {

    private List<MultiplayerGameInfo> multiplayerGamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        this.multiplayerGamesList = new ArrayList<MultiplayerGameInfo>();
        MultiplayerManager.init(this);

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
        MultiplayerManager.requestPeers();
    }

    private class MultiplayerGamesListAdapter extends ArrayAdapter<MultiplayerGameInfo> {

        public MultiplayerGamesListAdapter(Context context, int resource) {
            super(context, resource, MultiplayerActivity.this.multiplayerGamesList);
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
            numberOfPlayersTextView.setText(item.getNumberOfPlayers());

            return convertView;
        }
    }

 }
