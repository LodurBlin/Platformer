package com.platformer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.platformer.Platformer;

public class Button {
    private Texture active, inactive;
    private Vector2 position, endPosition;
    Platformer game;
    public Button(Texture active, Texture inactive, float x, float y, Platformer game){
        this.game=game;
        this.active=active;
        this.inactive = inactive;
        position = new Vector2(x, y);
        endPosition = new Vector2(x+active.getWidth(), y-active.getHeight());
    }
    /*
    public Vector2 getPos(){
        return position;
    }
    public Vector2 getEndPos(){
        return endPosition;
    }

     */
    public boolean checkCollision(){
        if(Gdx.input.getX()<=endPosition.x && Gdx.input.getX()>=position.x && Gdx.input.getY()<=position.y && Gdx.input.getY()>=endPosition.y){
        //if (Gdx.input.getX()>=1000 && Gdx.input.getY()<=500) {
            return true;
        }
        return false;
    }
    public void drawButton(){
        game.batch.begin();
        if (this.checkCollision()){
            game.batch.draw(active, position.x, position.y);
        } else {
            game.batch.draw(inactive, position.x, position.y);
        }
        game.batch.end();
    }

}
