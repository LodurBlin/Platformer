package com.platformer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.platformer.sprites.Player;

public class Controls {
    public static int jumpNumber = 0;
    public static void inputUpdate(float delta, Player player){
        int horizontalVector = 0;
        float verticalVector = 0;
        boolean up = Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP);
        boolean left = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        float verticalForce = 500;
        float horizontalForce = (verticalForce+100)*delta;

        if (left){
            horizontalVector -=1;
        }
        if (right){
            horizontalVector +=1;
        }
        if (up){
            if(jumpNumber == 0) {
                verticalVector = 1;
                jumpNumber++;
            }
            else if(jumpNumber == 1 ) {
                verticalVector = 1/8;
                jumpNumber++;
            }
            if (left && !right){
                horizontalVector = -1;
            } else if (right && !left){
                horizontalVector = 1;
            }
            player.body.applyForceToCenter(horizontalVector * horizontalForce, verticalForce * verticalVector, false);
        }
        else {
            player.body.setLinearVelocity(horizontalVector * horizontalForce, player.body.getLinearVelocity().y);
        }
        if(player.body.getLinearVelocity().y == 0) jumpNumber = 0;

    }
}
