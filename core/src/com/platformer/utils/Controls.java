package com.platformer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;

public class Controls {
    public static int jumpNumber = 0;
    public static void inputUpdate(float delta, Body player){
        int horizontalForce = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            horizontalForce -=1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            horizontalForce +=1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if(jumpNumber == 0 ) {
                player.applyForceToCenter(0, 4000, false);
                jumpNumber++;
            }
            else if(jumpNumber == 1 ) {
                player.applyForceToCenter(0, 500, false);
                jumpNumber++;
            }
        }
        if(player.getLinearVelocity().y == 0) jumpNumber = 0;
        player.setLinearVelocity(horizontalForce *400*delta, player.getLinearVelocity().y);
    }
}
