package com.platformer.sprites;

import com.badlogic.gdx.Gdx;
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
    float width, height;
    public Player(World world, int x, int y, Platformer game){
        this.world = world;
        this.game = game;
        tex = new Texture("images/Nick.png"); //32x102
        width = tex.getWidth()/2;
        height = tex.getHeight()/2;
        definePlayer(x, y);
    }


    private void definePlayer(int x, int y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x/PPM, y/PPM); //put info
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = true; //false - rotate when hitting objects
        body = world.createBody(bdef); //initialisation

        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        shape.setAsBox( width/PPM, height/PPM); //counts from center
        fdef.shape=shape;

        body.createFixture(fdef);
        shape.dispose();
    }
    public void drawPlayer(){
        game.batch.begin();
        game.batch.draw(tex, body.getPosition().x*PPM - width, body.getPosition().y*PPM - height);
        game.batch.end();
    }
    public void dispose() {
        tex.dispose();
        world.dispose();
        game.dispose();
    }


}
