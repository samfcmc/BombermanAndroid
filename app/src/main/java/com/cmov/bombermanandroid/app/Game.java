package com.cmov.bombermanandroid.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import com.cmov.bombermanandroid.app.commands.*;
import com.cmov.bombermanandroid.app.constants.Constants;
import com.cmov.bombermanandroid.app.constants.Levels;
import com.cmov.bombermanandroid.app.model.*;
import com.cmov.bombermanandroid.app.modes.GameMode;
import com.cmov.bombermanandroid.app.states.*;
import com.cmov.bombermanandroid.app.text.PauseText;
import com.cmov.bombermanandroid.app.threads.TimeOutThread;
import de.greenrobot.event.EventBus;

import java.util.*;


public class Game {

    private static int currentLevel;
    private static GameMode currentGameMode;
    private static int gameDuration;

    /*
     * Movables lists
     */
    private static Map<Integer, Bomberman> players;
    private static List<Enemy> enemies;
    private static List<Movable> deadPlayers;
    private static Map<Integer, Bomberman> freePlayerSlots;

    /*
     * Game state control flags
     */
    private static State currentState;

    /*
     * Special drawables
     */
    private static PauseText pauseText;
    private static Wallpaper gameOverWallpaper;

    private static Queue<Command> commands;
    private static Queue<Bomb> bombs;
    private static Grid grid;

    private static EventBus eventBus;
    private static Context currentContext;
    private static Timer timer;
    private static TimeOutThread timeOutThread;
    private static int localPlayerNumber;

    static {
        init();
    }

    private static void init() {
        players = new HashMap<Integer, Bomberman>();
        freePlayerSlots = new HashMap<Integer, Bomberman>();
        enemies = new ArrayList<Enemy>();
        deadPlayers = new ArrayList<Movable>();
        pauseText = new PauseText(Color.WHITE);
        commands = new LinkedList<Command>();
        bombs = new LinkedList<Bomb>();
        eventBus = EventBus.getDefault();
        timer = new Timer();
    }

    public static void reset() {
        freePlayerSlots.clear();
        enemies.clear();
        deadPlayers.clear();
        commands.clear();
        bombs.clear();
        timeOutThread.cancel();
        timer.cancel();
    }

    private static void startTimeOutThread() {
        timer = new Timer();
        timeOutThread = new TimeOutThread(gameDuration);
        timer.schedule(timeOutThread, 0, 1000);
    }

    public static void stopTimeOutThread() {
        gameDuration = timeOutThread.getEvent().getTimeLeft();
        timeOutThread.cancel();
        timer.cancel();
    }

    public static void resumeTimeOutThread() {
        startTimeOutThread();
    }

    public static void stop() {
        commands.clear();
        timeOutThread.cancel();
        timer.cancel();
    }

    public static Map<Integer, Bomberman> getPlayersMap(){
        return players;
    }

    public static void timeOut() {
        currentState = new TimeOutState();
    }

    public static void setGameDuration(int gameDuration) {
        Game.gameDuration = gameDuration;
    }

    public static Queue<Bomb> getBombs() {
        return bombs;
    }

    public static Grid getGrid() {
        return grid;
    }

    public static State getCurrentState() {
        return currentState;
    }

    public static Collection<Bomberman> getPlayers() {
        return players.values();
    }

    public static List<Enemy> getEnemies() { return  enemies; }

    public static Enemy getEnemy(int enemyIndex) { return enemies.get(enemyIndex); }

    public static void start(Context context, GameMode gameMode, int level) {
        init();
        currentGameMode = gameMode;
        currentLevel = level;
        currentContext = context;
        GameLoader.getInstance().loadGameLevel(context, gameMode, currentLevel);
        currentGameMode.getManager().start();
        currentState = new RunningState();
        startTimeOutThread();
    }

    public static void startNextLevel() {
        reset();
        currentLevel = (currentLevel + 1) % Levels.getLevelsCount();
        GameLoader.getInstance().loadGameLevel(currentContext, currentGameMode, currentLevel);
        players.clear();
        currentGameMode.getManager().start();
        currentState = new RunningState();
        startTimeOutThread();
    }

    private static Bomberman getFreeSlot(int playerNumber) {
        return freePlayerSlots.get(playerNumber);
    }

    public static Bomberman getNextFreeSlot() {
        return freePlayerSlots.values().iterator().next();
    }

    public static boolean hasFreeSlots(){
        return !freePlayerSlots.isEmpty();
    }

    public static void joinPlayer(int playerNumber) {
        Bomberman player = getFreeSlot(playerNumber);
        joinPlayer(player);
    }

    public static void joinPlayerInPosition(int playerNumber, int x, int y) {
        Bomberman player = getFreeSlot(playerNumber);
        player.setX(x);
        player.setY(y);
        joinPlayer(player);
    }

    private static void joinPlayer(Bomberman player) {
        freePlayerSlots.remove(player.getPlayerNumber());
        players.put(player.getPlayerNumber(), player);
        grid.addBomberman(player);
    }

    public static void setLocalPlayerNumber(int playerNumber) {
        localPlayerNumber = playerNumber;
    }

    public static void setGrid(Grid grid1) {
        grid = grid1;
    }

    public static void updateGameState(Canvas canvas) {
        currentState.update(canvas);
    }

    public static int getNumberOfPlayers(){
        return players.size();
    }

    public static void setCurrentState(State currentState) {
        Game.currentState = currentState;
    }

    public static int getPlayerScore(int playerNumber) {
        Bomberman player = getPlayer(playerNumber);
        if(player == null) {
            return 0;
        }
        else {
            return player.getScore();
        }
    }

    public static void updateLocal(Canvas canvas){
        //Check for game over
        if(noMorePlayersAlive()) {
            currentState = new GameOverState();
        }
        else if(noMoreEnemiesAlive()) {
            currentState = new AllEnemiesAreDeadState();
        }
        else {
            detectMovableCollisions(enemies, players.values());
            updateMovables(players.values(), deadPlayers, canvas);
            updateMovables(enemies, canvas);
            updateBombs();
            currentGameMode.getManager().updateEnemies();
         }
    }

    public static void update(Canvas canvas) {
       currentGameMode.getManager().update(canvas);
    }

    private static boolean noMorePlayersAlive() {
        return players.isEmpty();
    }

    private static boolean noMoreEnemiesAlive() {
        return enemies.isEmpty();
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

    public static void processCommands() {
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

    private static void detectMovableCollisions(Collection<? extends Movable> players, Collection<? extends Movable> enemies){
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

    private static void updateMovables(Collection<? extends Movable> characters,
                                       Collection<Movable> deadCharacters, Canvas canvas) {
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

    public static Bomberman getLocalPlayer(){
        if(players.isEmpty()) {
            return null;
        }
        else {
            return players.get(localPlayerNumber);
        }
    }

    public static Bomberman getPlayer(int player) {
        if(players.isEmpty() || player > players.size() - 1) {
            return null;
        }
        else {
            return players.get(player);
        }

    }

    public static EventBus getEventBus() {
        return eventBus;
    }

    public static void dropBomb() {
       currentGameMode.getManager().bombPressed();
    }

    public static Bomberman addPlayerSlot(Context context, int x, int y, int playerNumber,
                                          int pointsPerRobotKilled) {
        Bomberman player = getPlayer(playerNumber - 1);

        if(player == null) {
            player = new Bomberman(BitmapLib.getBombermanBitmap(context), x, y, playerNumber,
                    Constants.BOMBERMAN_LIVES, Constants.BOMBERMAN_SPEED, false,
                    pointsPerRobotKilled);
        }
        else {
            player.setX(x);
            player.setY(y);
            player.setPointsPerRobotKilled(pointsPerRobotKilled);
            players.remove(playerNumber);
        }
        freePlayerSlots.put(playerNumber, player);

        return player;
    }

    public static void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public static void draw(Canvas canvas) {
        currentState.draw(canvas);
    }

    public static void drawGameOver(Canvas canvas) {
        gameOverWallpaper.draw(canvas);
    }

    public static void drawPauseText(Canvas canvas) {
        pauseText.draw(canvas);
    }

    public static void drawGrid(Canvas canvas) {
        grid.draw(canvas);
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

    public static void trySendCommand(Bomberman player, CharacterCommand command) {
        if(player != null) {
            player.setCommand(command);
        }
    }

    public static void sendCommandToLocalPlayer(CharacterCommand command) {
        trySendCommand(getLocalPlayer(), command);
    }

    public static void sendUpCommand() {
        currentGameMode.getManager().upPressed();
    }

    public static void sendDownCommand() {
        currentGameMode.getManager().downPressed();
    }

    public static void sendLeftCommand() {
        currentGameMode.getManager().leftPressed();
    }

    public static void sendRightCommand() {
        currentGameMode.getManager().rightPressed();
    }

}