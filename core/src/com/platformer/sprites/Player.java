package com.platformer.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.platformer.Platformer;
import com.platformer.screens.GameScreen;

import static com.platformer.utils.Constants.PPM;

public class Player extends Sprite{
    public World world;
    public Body body;
    //public Texture tex;
    public enum State {FALLING, JUMPING, STANDING, RUNNING};
    public State currentState, previousState;
    private float width, height;
    private Animation nickRun, nickJump;
    private boolean runningRight;
    private float stateTimer;
    private TextureRegion nickStand;
    public Player(World world, int x, int y, GameScreen screen){
        super(screen.getAtlas().findRegion("Nick Names"));
        this.world = world;
        //tex = new Texture("images/Nick.png"); //32x102
        this.width = 32;
        this.height = 92;
        definePlayer(x, y);
        this.stateTimer = 0;
        //runningRight = true;
        currentState = previousState = State.STANDING;

        nickStand = new TextureRegion(getTexture(), 189, 1, width, height);
        super.setBounds(x/PPM, y/PPM, width/PPM, height/PPM);
        super.setRegion(nickStand);
        /*
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=1; i<4; i++){
            frames.add(new TextureRegion(getTexture(), i*32, 0, width, height);
        }
        nickRun = new Animation(0.1f, frames);

         */
    }


    private void definePlayer(int x, int y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x/PPM, y/PPM); //put info
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = true; //false - rotate when hitting objects
        body = world.createBody(bdef); //initialisation

        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        shape.setAsBox( width/2/PPM, height/2/PPM); //counts from center
        fdef.shape=shape;

        body.createFixture(fdef);
        shape.dispose();
    }

    public void update(float delta){
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);

    }
    public void dispose() {
        //tex.dispose();
        world.dispose();
    }


}
