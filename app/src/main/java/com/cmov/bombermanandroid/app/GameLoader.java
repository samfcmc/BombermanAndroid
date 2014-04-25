package com.cmov.bombermanandroid.app;

import android.content.Context;
import android.util.Log;

import com.cmov.bombermanandroid.app.model.Bomberman;
import com.cmov.bombermanandroid.app.model.Grid;
import com.cmov.bombermanandroid.app.model.Obstacle;
import com.cmov.bombermanandroid.app.model.Robot;
import com.cmov.bombermanandroid.app.model.Wall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class GameLoader {

    public static GameLoader instance;
    private static Grid grid;
    private final int parameter_index = 0;
    private final int value_index = 1;
    private final int obstacle_hit_points = 1;
    private final int bomberman_lives = 3;
    private final float bomberman_speed = 2.0f;
    public Map<String, Integer> gameSettings;


    public GameLoader() {
        this.gameSettings = new HashMap<String, Integer>();
    }


    public static GameLoader getInstance() {
        if (instance == null)
            instance = new GameLoader();
        return instance;
    }

    public void initializeSettings(String s) {
        Log.d("GameActivity", "Parameters line : " + s);
        String[] parameters = s.split(" ");
        String parameter = parameters[parameter_index];
        int value = Integer.parseInt(parameters[value_index]);
        this.gameSettings.put(parameter, value);
    }

    public void loadGameSettings(Context context) {

        Log.d("GameActivity", "Loading Settings ....");

        InputStream is = context.getResources().openRawResource(R.raw.level_1);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        try {
            String line = br.readLine();

            while (line != null) {
                initializeSettings(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            Log.d("GameActivity", "Catches Exception " + e.getMessage());
        }

    }

    private void loadOtherFeatures(Context context) {
        BitmapLib.loadBomb(context);
        BitmapLib.loadBombExplosion(context);
    }

    public void loadGameMap(Context context) {
        Log.d("GameActivity", "Loading Map ....");
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
                Log.d("GameActivity", "Adding the tiles ....");
                for (int j = 0; j < this.grid.WIDTH; j++)
                    switch (tiles[j]) {
                        case '-':
                            this.grid.addFloor(i, j);
                            break;
                        case 'O':
                            Obstacle obstacle = new Obstacle(BitmapLib.getObstacleBitmap(context), i, j, obstacle_hit_points, true);
                            this.grid.addObstacle(obstacle);
                            break;
                        case 'R':
                            Robot robot = new Robot(BitmapLib.getRobotBitmap(context), i, j, this.gameSettings.get("RS"), false);
                            this.grid.addRobot(robot);
                            Game.addEnemy(robot);

                            break;
                        case 'W':
                            Wall wall = new Wall(BitmapLib.getWallBitmap(context), i, j);
                            this.grid.addWall(wall);
                            break;
                        default:
                            Bomberman bomberman = new Bomberman(BitmapLib.getBombermanBitmap(context), i, j, Character.getNumericValue(tiles[j]), bomberman_lives, bomberman_speed, false);
                            this.grid.addBomberman(bomberman);
                            Game.addPlayer(bomberman);
                            break;
                    }
                i++;
                line = br.readLine();
            }


        } catch (IOException e) {
            Log.d("GameActivity", "Catches Exception " + e.getMessage());
        }
    }
}
