package com.cmov.bombermanandroid.app.model;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    List<Model> models;

    public Cell(){
        this(new ArrayList<Model>());
    }

    public Cell(List<Model> models) {
        this.models = models;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public void addModel(Model model){
        this.models.add(model);
    }

    public void removeModel(Model model) {
        this.models.remove(model);
    }

    public void draw(Canvas canvas){
        for(Model model : this.models){
            model.draw(canvas);
        }
    }

    public boolean isEmpty(){
        return models.isEmpty();
    }
}
