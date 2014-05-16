package com.cmov.bombermanandroid.app.constants;


import android.content.Context;

import com.cmov.bombermanandroid.app.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Mapping between level resources and level numbers
 */
public class Levels {

    private static LevelInfo[] levels = {
            new LevelInfo(R.raw.level_1, R.raw.level_1_layout),
            new LevelInfo(R.raw.level_2, R.raw.level_2_layout),
            new LevelInfo(R.raw.level_3, R.raw.level_3_layout)
            // For more levels just fill this array with the right values
    };

    private static LevelInfo getLevelInfo(int level) {
        return levels[level];
    }

    private static BufferedReader getBufferedReaderFromResource(Context context, int res) {
        InputStream is = context.getResources().openRawResource(res);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        return br;
    }

    public static BufferedReader getLevelSettingsBufferedReader(Context context, int level) {
        LevelInfo levelInfo = getLevelInfo(level);
        return getBufferedReaderFromResource(context, levelInfo.getLevelResource());
    }

    public static BufferedReader getLevelLayoutBufferedReader(Context context, int level) {
        LevelInfo levelInfo = getLevelInfo(level);
        return getBufferedReaderFromResource(context, levelInfo.getLevelLayoutResource());
    }

    public static int getLevelsCount() {
        return levels.length;
    }

    private static class LevelInfo {
        private int levelResource;
        private int levelLayoutResource;

        private LevelInfo(int levelResource, int levelLayoutResource) {
            this.levelResource = levelResource;
            this.levelLayoutResource = levelLayoutResource;
        }

        public int getLevelResource() {
            return levelResource;
        }

        public int getLevelLayoutResource() {
            return levelLayoutResource;
        }
    }
}
