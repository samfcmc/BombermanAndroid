package com.cmov.bombermanandroid.app;

import android.content.Context;
import android.util.Log;

import com.cmov.bombermanandroid.app.constants.Constants;
import com.cmov.bombermanandroid.app.constants.Levels;
import com.cmov.bombermanandroid.app.model.*;
import com.cmov.bombermanandroid.app.modes.GameMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class GameLoader {

    public static final String TAG = GameLoader.class.getSimpleName();

    private static final int PARAMETER_INDEX = 0;
    private static final int VALUE_INDEX = 1;


    public static enum GAME_SETTINGS {
        LN, //Level Name
        GD, //Game Duration
        ET, //Explosion Timeout
        ED, //Explosion Duration
        ER, //Explosion Range
        RS, //Robot Speed (cells/second)
        PR, //Point per Robot Killed
        PO  //Points per Opponent Killed
    }

    private Map<GAME_SETTINGS, Integer> gameSettings;

    private static GameLoader instance;
    private static Grid grid;

    private GameLoader() {
        this.gameSettings = new HashMap<GAME_SETTINGS, Integer>();
    }

    public static GameLoader getInstance() {
        if (instance == null)
            instance = new GameLoader();
        return instance;
    }

    public void addSetting(GAME_SETTINGS key, int value) {
        this.gameSettings.put(key, value);
    }

    public int getSetting(GAME_SETTINGS key) {
        return this.gameSettings.get(key);
    }

    public void initializeSettings(String s) {
        Log.d(TAG, "Parameters line : " + s);
        String[] parameters = s.split(" ");
        String parameter = parameters[PARAMETER_INDEX];
        int value = Integer.parseInt(parameters[VALUE_INDEX]);
        addSetting(GAME_SETTINGS.valueOf(parameter), value);
    }

    private void loadGameSettings(Context context, int level) {
        BufferedReader br = Levels.getLevelSettingsBufferedReader(context, level);

        try {
            String line = br.readLine();

            while (line != null) {
                initializeSettings(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            Log.d(TAG, "Catches Exception " + e.getMessage());
        }
    }

    public void loadGameLevel(Context context, GameMode gameMode, int level) {
        loadGameSettings(context, level);
        loadGameMap(context, gameMode, level);
    }

    private void loadOtherFeatures(Context context) {
        BitmapLib.loadBomb(context);
        BitmapLib.loadBombExplosion(context);
        Game.setGameOverWallpaper(new Wallpaper(BitmapLib.getGameOverBitmap(context)));
    }

    private void loadGameMap(Context context, GameMode gameMode, int level) {
        loadOtherFeatures(context);
        int i = 0;

        BufferedReader br = Levels.getLevelLayoutBufferedReader(context, level);

        grid = new Grid(context);
        Game.setGrid(grid);

        try {
            String line = br.readLine();

            while (line != null) {
                // Converts the entire line into a char array to pick each element individually
                char[] tiles = line.toCharArray();
                // Reads the entire line
                // Fill the grid with the elements
                for (int j = 0; j < this.grid.WIDTH; j++)
                    switch (tiles[j]) {
                        case '-':
                            addFloor(j, i);
                            break;
                        case 'O':
                            addObstacle(j, i, context);
                            break;
                        case 'R':
                            addRobot(j, i, context);
                            break;
                        case 'W':
                            addWall(j, i, context);
                            break;
                        default:
                            addBomberman(j, i, context, tiles, gameMode);
                            break;
                    }
                i++;
                line = br.readLine();
            }


        } catch (IOException e) {
            Log.d(TAG, "Catches Exception " + e.getMessage());
        }
    }

    private void addFloor(int x, int y) {
        this.grid.addFloor(x, y);
    }

    private void addObstacle(int x, int y, Context context) {
        Obstacle obstacle = new Obstacle(BitmapLib.getObstacleBitmap(context), x, y, Constants.OBSTACLE_HIT_POINTS, true);
        this.grid.addObstacle(obstacle);
    }

    private void addRobot(int x, int y, Context context) {
        Robot robot = new Robot(BitmapLib.getRobotBitmap(context), x, y, getSetting(GAME_SETTINGS.RS), false, true);
        this.grid.addRobot(robot);
        Game.addEnemy(robot);
    }

    private void addWall(int x, int y, Context context) {
        Wall wall = new Wall(BitmapLib.getWallBitmap(context), x, y);
        this.grid.addWall(wall);
    }

    private void addBomberman(int x, int y, Context context, char[] tiles, GameMode gameMode) {
        int playerNumber = Character.getNumericValue(tiles[x]);

        if (playerNumber > Constants.MAX_PLAYERS || playerNumber == -1) {
            throw new RuntimeException("Wrong input value");
        }

        if(playerNumber > gameMode.getMaxPlayers()) {
            addFloor(x, y);
        }

        else {
            Bomberman player = Game.addPlayer(context, x, y, playerNumber, getSetting(GAME_SETTINGS.PR));
            this.grid.addBomberman(player);
        }
    }
}
