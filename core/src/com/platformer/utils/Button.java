package com.platformer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.platformer.Platformer;

public class Button {
    private Texture active, inactive;
    private Vector2 position;
    int sizeW, sizeH;
    Platformer game;
    public Button(Texture active, Texture inactive, float x, float y, int sizeW, int sizeH, Platformer game){
        this.game=game;
        this.active=active;
        this.inactive = inactive;
        position = new Vector2(x, y);
        this.sizeW = sizeW;
        this.sizeH = sizeH;
    }

    public boolean checkCollision(){
        if(Gdx.input.getX()<=position.x + sizeW && Gdx.input.getX()>=position.x && Gdx.input.getY()<=Gdx.graphics.getHeight() - position.y && Gdx.input.getY()>= Gdx.graphics.getHeight() - sizeH -position.y){

            return true;
        }
        return false;
    }
    public void drawButton(){
        game.batch.begin();
        if (this.checkCollision()){
            game.batch.draw(active, position.x, position.y, sizeW+20, sizeH+20);
        } else {
            game.batch.draw(inactive, position.x, position.y, sizeW, sizeH);
        }
        game.batch.end();
    }

}
