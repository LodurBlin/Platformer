package com.platformer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.platformer.sprites.Player;

public class Controls {
    public static int jumpNumber = 0;
    public static void inputUpdate(float delta, Player player){
        int horizontalForce = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            horizontalForce -=1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            horizontalForce +=1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if(jumpNumber == 0 ) {
                player.body.applyForceToCenter(0, 400, false);
                jumpNumber++;
            }
            else if(jumpNumber == 1 ) {
                player.body.applyForceToCenter(0, 50, false);
                jumpNumber++;
            }
        }
        if(player.body.getLinearVelocity().y == 0) jumpNumber = 0;
        player.body.setLinearVelocity(horizontalForce *400*delta, player.body.getLinearVelocity().y);
    }
}
