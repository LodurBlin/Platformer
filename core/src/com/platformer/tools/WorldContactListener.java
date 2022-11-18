package com.platformer.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.platformer.sprites.InteractiveTileObject;
import com.platformer.utils.Controls;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Controls.jumpNumber = 0;
        Controls.isInJump = false;
        Gdx.app.log("Begin Contact", "");

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }


        }
    }

    @Override
    public void endContact(Contact contact) {
        Controls.isInJump = true;

        Gdx.app.log("End Contact", "");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
