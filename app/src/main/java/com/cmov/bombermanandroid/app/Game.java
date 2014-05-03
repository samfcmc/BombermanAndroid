package com.cmov.bombermanandroid.app;

import android.graphics.Canvas;
import android.graphics.Color;

import com.cmov.bombermanandroid.app.commands.*;
import com.cmov.bombermanandroid.app.events.UpdatedGameStateEvent;
import com.cmov.bombermanandroid.app.model.*;
import com.cmov.bombermanandroid.app.text.PauseText;
import com.cmov.bombermanandroid.app.threads.ExplosionThread;

import java.util.*;

import de.greenrobot.event.EventBus;


public class Game {

    private static List<Bomberman> players;
    private static List<Enemy> enemies;
    private static List<Movable> deadPlayers;
    private static boolean paused;
    private static PauseText pauseText;
    private static Queue<Command> commands;
    private static Queue<Bomb> bombs;
    private static Grid grid;
    private static boolean gameOver;
    private static Wallpaper gameOverWallpaper;
    private static EventBus eventBus;

    static {
        init();
    }

    private static void init() {
        players = new ArrayList<Bomberman>();
        enemies = new ArrayList<Enemy>();
        deadPlayers = new ArrayList<Movable>();
        paused = false;
        pauseText = new PauseText(Color.WHITE);
        commands = new LinkedList<Command>();
        bombs = new LinkedList<Bomb>();
        gameOver = false;
        eventBus = EventBus.getDefault();
    }

    public static void setGrid(Grid grid1) {
        grid = grid1;
    }

    public static void updateGameState(Canvas canvas) {
        if(gameOver) {
            commands.clear();
        }
        else {
            processCommands();

            if(!paused) {
                update(canvas);
                eventBus.post(new UpdatedGameStateEvent());
            }
        }
    }

    public static int getPlayerScore(int playerIndex) {
        Bomberman player = getPlayer(playerIndex);
        if(player == null) {
            return 0;
        }
        else {
            return player.getScore();
        }

    }

    private static void update(Canvas canvas) {
        //Check for game over
        if(noMorePlayersAlive()) {
            gameOver = true;
        }
        else {
            detectMovableCollisions(enemies, players);
            updateMovables(players, deadPlayers, canvas);
            updateMovables(enemies, canvas);
            updateBombs();
            generateCommandForEnemies();
        }
    }

    private static boolean noMorePlayersAlive() {
        return players.isEmpty();
    }

    private static void updateBombs() {
        Iterator<Bomb> iterator = bombs.iterator();
        while (iterator.hasNext()) {
            Bomb bomb = iterator.next();
            if(bomb.isTriggered()) {
                bomb.update(System.currentTimeMillis());
            } else {
                grid.removeModel(bomb);
                iterator.remove();
            }
        }
    }

    private static void processCommands() {
        if(!commands.isEmpty()) {
            Command command = commands.remove();
            command.execute();
        }
    }

    public static void setGameOverWallpaper(Wallpaper wallpaper) {
        gameOverWallpaper = wallpaper;
    }

    public static void addCommand(Command command) {
        commands.add(command);
    }

    private static void detectMovableCollisions(List<? extends Movable> players, List<? extends Movable> enemies){
        for(Movable m : enemies){

            Iterator<? extends Movable> iterator =  players.iterator();
            while(iterator.hasNext()){
                Movable e = iterator.next();
                if((m.getX() == e.getX()) && (m.getY() == e.getY())){
                    m.touchedByMovable(e);
                    iterator.remove();
                }
            }
        }
    }

    private static void updateMovables(List<? extends Movable> characters, Canvas canvas) {
        updateMovables(characters, null, canvas);
    }

    private static void updateMovables(List<? extends Movable> characters,
                                       List<Movable> deadCharacters, Canvas canvas) {
        Iterator<? extends Movable> iterator = characters.iterator();
        while(iterator.hasNext()) {
            Movable character = iterator.next();
            // Update life state
            if(character.isDead()) {
                if(deadCharacters != null) {
                    deadCharacters.add(character);
                }
                iterator.remove();
                grid.removeModel(character);
            }

            else {
                if (!(character.getCommand() == null)) {
                    CharacterCommand command = character.getCommand();
                    command.execute();
                }
                if (character.isMoving()) {
                    if (character.shouldStop(character.getScaledBitmap(canvas))) {
                        character.stopAndUpdatePosition(grid);
                    }
                }
            }
        }
    }

    public static boolean isPaused() {
        return paused;
    }

    public static void setPaused(boolean paused) {
        Game.paused = paused;
    }

    public static Bomberman getPlayer(int player) {
        if(players.isEmpty()) {
            return null;
        }
        else {
            return players.get(player);
        }

    }

    public static EventBus getEventBus() {
        return eventBus;
    }

    public static void dropBomb(int player) {
        if(!gameOver) {
            Bomb bomb = getPlayer(player).dropBomb(grid, bombs, System.currentTimeMillis());
            if (bomb != null) {
                new Timer().schedule(new ExplosionThread(bomb), bomb.getTimeout());
            }
        }
    }

    public static void addPlayer(Bomberman bomberman) {
        players.add(bomberman);
    }

    public static void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public static void reset() {
        players.clear();
        enemies.clear();
        deadPlayers.clear();
        paused = false;
        commands.clear();
        bombs.clear();
        gameOver = false;
    }

    public static void draw(Canvas canvas) {
        if(gameOver) {
            gameOverWallpaper.draw(canvas);
        }
        else if(paused) {
            pauseText.draw(canvas);
        }
        else {
            grid.draw(canvas);
        }
    }

    public static boolean checkCollision(Movable character, int x, int y) {
        Model model;
        if (character.isMoving()) {
            model = grid.getModel(character.getXAfterMovement(), character.getYAfterMovement());
        } else {
            model = grid.getModel(x, y);
        }

        if (model == null || model == character) {
            return false;
        } else return model.isCollidable();
    }

    /*
     * Enemies behaviour
     */
    public static void generateCommandForEnemies() {
        for (Movable enemy : enemies) {
            if (!enemy.isMoving()) {
                CharacterCommand randomCommand = generateRandomCommand(enemy);
                enemy.setCommand(randomCommand);
            }
        }
    }

    private static CharacterCommand generateRandomCommand(Movable character) {
        int rand = (int) (Math.random() * 4);
        CharacterCommand command;
        switch (rand) {
            case 0:
                command = new UpCommand(character);
                break;
            case 1:
                command = new DownCommand(character);
                break;
            case 2:
                command = new LeftCommand(character);
                break;
            default:
                command = new RightCommand(character);
                break;
        }

        return command;
    }

    private static void trySendCommand(Bomberman player, CharacterCommand command) {
        if(player != null) {
            player.setCommand(command);
        }
    }

    public static void sendUpCommand(int playerIndex) {
        Bomberman player = getPlayer(playerIndex);
        trySendCommand(player, new UpCommand(player));
    }

    public static void sendDownCommand(int playerIndex) {
        Bomberman player = getPlayer(playerIndex);
        trySendCommand(player, new DownCommand(player));
    }

    public static void sendLeftCommand(int playerIndex) {
        Bomberman player = getPlayer(playerIndex);
        trySendCommand(player, new LeftCommand(player));
    }

    public static void sendRightCommand(int playerIndex) {
        Bomberman player = getPlayer(playerIndex);
        trySendCommand(player, new RightCommand(player));
    }



}