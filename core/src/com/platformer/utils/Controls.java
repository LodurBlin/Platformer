package com.platformer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.platformer.sprites.Player;

public class Controls {
    public static int jumpNumber = 0;
    public static boolean isInJump = false;
    public static void inputUpdate(float delta, Player player){
        float horizontalVector = 0;
        float verticalVector = 0;
        boolean up = Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP);
        boolean left = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        float verticalForce = 700;
        float horizontalForce = (verticalForce+100)*delta;

        if (left){
            if(isInJump) horizontalVector -=0.3F;
            else horizontalVector -= 0.8F;
        }
        if (right){
            if(isInJump) horizontalVector +=0.3F;
            else horizontalVector += 0.8F;
        }
        if (up){
            if(jumpNumber == 0) {
                verticalVector = 0.9F;
                jumpNumber++;
            }
            else if(jumpNumber == 1 ) { //второй прыжок не получается изменить
                verticalVector = 0.7F;
                jumpNumber++;
            }
            if (left && !right){
                horizontalVector = -0.7F;
            } else if (right && !left){
                horizontalVector = 0.8F;
            }
            player.body.applyForceToCenter(horizontalVector * horizontalForce, verticalForce * verticalVector, false);
        }
        else {
            player.body.setLinearVelocity(horizontalVector * horizontalForce, player.body.getLinearVelocity().y);
        }
        //if(player.body.getLinearVelocity().y == 0) jumpNumber = 0;

    }
}
