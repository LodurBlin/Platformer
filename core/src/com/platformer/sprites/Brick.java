package com.platformer.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.platformer.screens.GameScreen;

public class Brick extends InteractiveTileObject{
    public Brick(GameScreen screen, Rectangle bounds){
        super(screen, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
    }
}
