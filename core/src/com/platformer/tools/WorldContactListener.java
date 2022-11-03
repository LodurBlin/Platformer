package com.platformer.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.platformer.utils.Controls;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Controls.jumpNumber = 0;
        Controls.isInJump = false;
    }

    @Override
    public void endContact(Contact contact) {
        Controls.isInJump = true;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
