package com.cmov.bombermanandroid.app;

import android.content.Context;
import android.util.Log;
import com.cmov.bombermanandroid.app.model.*;

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
    private static final int OBSTACLE_HIT_POINTS = 1;
    private static final int BOMBERMAN_LIVES = 3;
    private static final float BOMBERMAN_SPEED = 2.0f;
    private static final int MAX_PLAYERS = 3;

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

    public void loadGameSettings(Context context) {

        Log.d(TAG, "Loading Settings ....");

        InputStream is = context.getResources().openRawResource(R.raw.level_1);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

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

    private void loadOtherFeatures(Context context) {
        BitmapLib.loadBomb(context);
        BitmapLib.loadBombExplosion(context);
    }

    public void loadGameMap(Context context) {
        Log.d(TAG, "Loading Map ....");
        loadOtherFeatures(context);
        int i = 0;

        InputStream is = context.getResources().openRawResource(R.raw.level_1_layout);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        grid = new Grid(context);
        Game.setGrid(grid);

        try {
            String line = br.readLine();

            while (line != null) {
                // Converts the entire line into a char array to pick each element individually
                char[] tiles = line.toCharArray();
                // Reads the entire line
                // Fill the grid with the elements
                Log.d(TAG, "Adding the tiles ....");
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
                            addBomberman(j, i, context, tiles);
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
        Obstacle obstacle = new Obstacle(BitmapLib.getObstacleBitmap(context), x, y, OBSTACLE_HIT_POINTS, true);
        this.grid.addObstacle(obstacle);
    }

    private void addRobot(int x, int y, Context context) {
        Robot robot = new Robot(BitmapLib.getRobotBitmap(context), x, y, getSetting(GameLoader.GAME_SETTINGS.RS), false);
        this.grid.addRobot(robot);
        Game.addEnemy(robot);
    }

    private void addWall(int x, int y, Context context) {
        Wall wall = new Wall(BitmapLib.getWallBitmap(context), x, y);
        this.grid.addWall(wall);
    }

    private void addBomberman(int x, int y, Context context, char[] tiles) {
        int playerNumber = Character.getNumericValue(tiles[x]);

        if (playerNumber > MAX_PLAYERS || playerNumber == -1) {
            throw new RuntimeException("Wrong input value");
        }

        Bomberman bomberman = new Bomberman(BitmapLib.getBombermanBitmap(context, playerNumber), x,
                y, playerNumber, BOMBERMAN_LIVES, BOMBERMAN_SPEED, false);
        this.grid.addBomberman(bomberman);
        Game.addPlayer(bomberman);
    }
}
