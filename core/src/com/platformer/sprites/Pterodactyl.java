package com.platformer.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.platformer.screens.GameScreen;

import static com.platformer.utils.Constants.PPM;

public class Pterodactyl extends Enemy{
    private float stateTime;
    private Animation <TextureRegion> flyAnimation;
    private Array<TextureRegion> frames;
    public Pterodactyl(GameScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();

        for (int i=0;i<2;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Ptero"), 248+i*50, 201, 50, 34));
        }
        flyAnimation = new Animation(0.4f, frames);
        stateTime=0;
        setBounds(getX(), getY(), 50/PPM, 34/PPM);
        frames.clear();
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();

        bdef.position.set(500/PPM, 1000/PPM); //put info
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef); //initialisation

        bdef.fixedRotation = true; //false - rotate when hitting objects

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox( 50/PPM, 34/PPM); //counts from center
        fdef.shape=shape;

        body.createFixture(fdef);
        shape.dispose();
    }

    public void update (float dt){
        stateTime+=dt;
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        setRegion(flyAnimation.getKeyFrame(stateTime, true));
    }
}
