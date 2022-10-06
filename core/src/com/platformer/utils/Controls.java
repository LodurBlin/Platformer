package com.platformer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;

public class Controls {
    public static void inputUpdate(float delta, Body player){
        int horizontalForce = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            horizontalForce -=1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            horizontalForce +=1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            player.applyForceToCenter(0, 300, false);
        }
        player.setLinearVelocity(horizontalForce *5, player.getLinearVelocity().y);
    }
}
