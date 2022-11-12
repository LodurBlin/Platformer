package com.platformer.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.platformer.screens.GameScreen;

import static com.platformer.utils.Constants.PPM;

public class Pterodactyl extends Enemy{
    public Pterodactyl(GameScreen screen, float x, float y) {
        super(screen, x, y);
        setTexture(new Texture(Gdx.files.internal("maps/pngs/level0/Bird.png")));
        setBounds(getX(), getY(), 32/PPM, 32/PPM);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();

        bdef.position.set((399+32)/PPM, (800+32)/PPM); //put info
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef); //initialisation

        bdef.fixedRotation = true; //false - rotate when hitting objects

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox( 32/PPM, 32/PPM); //counts from center
        fdef.shape=shape;

        body.createFixture(fdef);
        shape.dispose();
    }

    public void update (float dt){
        SpriteBatch batch = new SpriteBatch();
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        batch.begin();
        draw(batch);
        batch.end();
        batch.dispose();
    }
}
