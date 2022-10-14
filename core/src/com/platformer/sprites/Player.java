package com.platformer.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.platformer.Platformer;

import java.awt.*;

import static com.platformer.utils.Constants.PPM;

public class Player extends Sprite{
    public World world;
    public Body body;
    public Texture tex;
    Platformer game;
    int width, height;
    public Player(World world, int x, int y, Platformer game){
        this.world = world;
        this.game = game;
        tex = new Texture("images/gg (2).png");
        definePlayer(x, y);
        width = (int) (tex.getWidth()/2*PPM);
        height = (int) (tex.getHeight() /2 * PPM);
    }


    public void definePlayer(int x, int y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x/PPM, y/PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = true; //false - rotate when hitting objects
        body = world.createBody(bdef); //initialisation

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox( width, height); //counts from center, put info
        fdef.shape=shape;

        body.createFixture(fdef);
        shape.dispose();
    }
    public void drawPlayer(){
        game.batch.begin();
        game.batch.draw(tex, body.getPosition().x/PPM - width/2, body.getPosition().y/PPM - height/2);
        game.batch.end();
    }



}
