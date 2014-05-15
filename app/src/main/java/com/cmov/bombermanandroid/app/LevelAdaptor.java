package com.cmov.bombermanandroid.app;

import android.view.View;
import android.widget.AdapterView;
import com.cmov.bombermanandroid.app.constants.Levels;

public class LevelAdaptor implements AdapterView.OnItemSelectedListener {

    private String[] array_lvl;
    private int level;

    public LevelAdaptor() {
        array_lvl = new String[Levels.getLevelsCount()];
        for(int i = 0; i < Levels.getLevelsCount(); i++) {
            array_lvl[i] = R.string.level + " " + i + 1;
        }
        this.level = 0;

    }

    private int getLevel(String level) {
        for(int i = 0; i < array_lvl.length; i++) {
            if(array_lvl[i].equals(level)) {
                return i;
            }
        }
        return 0;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object item = parent.getItemAtPosition(position);
        String level = item.toString();
        setLevel(getLevel(level));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
