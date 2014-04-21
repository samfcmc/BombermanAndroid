package com.cmov.bombermanandroid.app;

import com.cmov.bombermanandroid.app.model.Bomberman;
import com.cmov.bombermanandroid.app.model.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Bomberman> players;
    private List<Enemy> enemies;

    public Game() {
        this.players = new ArrayList<Bomberman>();
        this.enemies = new ArrayList<Enemy>();
    }

}