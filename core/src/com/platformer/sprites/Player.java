package com.platformer.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.platformer.Platformer;

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
        width = (int) (tex.getWidth()/PPM);
        height = (int) (tex.getHeight()/ PPM);
    }


    public void definePlayer(int x, int y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x/PPM, y/PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        //bdef.fixedRotation = true; //false - rotate when hitting objects
        body = world.createBody(bdef); //initialisation

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox( width/2, height/2); //counts from center, put info
        fdef.shape=shape;

        body.createFixture(fdef);
        shape.dispose();
    }
    public void drawPlayer(){
        game.batch.begin();
        game.batch.draw(tex, body.getPosition().x/PPM, body.getPosition().y/PPM);
        game.batch.end();
    }



}
