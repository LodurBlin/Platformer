package com.platformer.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.platformer.screens.GameScreen;

import static com.platformer.utils.Constants.PPM;

public class Player extends Sprite{
    public World world;
    public Body body;
    //public Texture tex;
    public enum State {FALLING, JUMPING,STANDING, RUNNING};
    public State currentState, previousState;
    private float width, height;
    private Animation nickRun;
    private boolean runningRight;
    private float stateTimer;
    private TextureRegion nickStand, nickJump;
    public Player(int x, int y, GameScreen screen){
        super(screen.getAtlas().findRegion("Nick"));
        this.world = screen.getWorld();
        width = 32;
        height = 92;
        definePlayer(x, y);

        this.stateTimer = 0;
        runningRight = true;
        currentState = previousState = State.STANDING;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=1; i<6; i++){
            frames.add(new TextureRegion(getTexture(), i*31, 143, (int)width-1, (int)(height-1)));
        }
        nickRun = new Animation(0.1f, frames);
        frames.clear();
        nickJump = new TextureRegion(super.getTexture(), 220, 143, (int)width-5, (int)height);

        nickStand = new TextureRegion(super.getTexture(), 188, 143, (int)width+1, (int)height);
        super.setBounds(x, y, width, height);
        super.setRegion(nickStand);

    }


    private void definePlayer(int x, int y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x/PPM+width/2/PPM, y/PPM+height/2/PPM); //put info
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = true; //false - rotate when hitting objects

        body = world.createBody(bdef); //initialisation

        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        shape.setAsBox( width/2/PPM, height/2/PPM); //counts from center
        fdef.shape=shape;

        body.createFixture(fdef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(x/PPM+width/2/PPM, y/PPM+height/2/PPM), new Vector2(x/PPM+width/2/PPM, y/PPM+height/2/PPM));
        fdef.shape = head;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("head");
        shape.dispose();
    }

    public void update(float delta){
        setPosition(body.getPosition().x*PPM- width/2, body.getPosition().y*PPM - height/2); //Проблемы в getPosition
        setRegion(getFrame(delta));
    }
    public State getState(){
        if (body.getLinearVelocity().y >0) {
            return State.JUMPING;
        } else if (body.getLinearVelocity().y <0){
            return State.FALLING;
        } else if (body.getLinearVelocity().x !=0){
            return State.RUNNING;
        } else {
            return State.STANDING;
        }
    }
    public TextureRegion getFrame(float delta){
        currentState = getState();
        TextureRegion region = null;
        switch(currentState){
            case RUNNING:
                region = (TextureRegion) nickRun.getKeyFrame(stateTimer, true);
                break;
            case JUMPING:
                region = nickJump;
                if  (region.isFlipY()){
                    region.flip(false, true);
                }
                break;
            case FALLING:
                region = nickJump;
                if (!region.isFlipY()){
                    region.flip(false, true);
                }
                break;

            case STANDING:
            default:
                region = nickStand;
                break;
        }

        if ((body.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX()){ //отражаем челика при повороте налево
            region.flip(true, false);
            runningRight=false;
        } else if ((body.getLinearVelocity().x>0 || runningRight) && region.isFlipX()){ //отражаем челика при повороте направо
            region.flip(true, false);
            runningRight=true;
        }
        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    public void dispose() {
        world.dispose();
    }


}
