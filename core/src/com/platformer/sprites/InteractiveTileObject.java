package com.platformer.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.platformer.screens.GameScreen;
import com.platformer.utils.Constants;

public abstract class InteractiveTileObject {
    private World world;
    private TiledMap map;
    private TiledMapTile tile;
    private Body body;
    private Rectangle bounds;

    protected Fixture fixture;
    public InteractiveTileObject(GameScreen screen, Rectangle bounds){
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX()+bounds.getWidth()/2)/ Constants.PPM, (bounds.getY()+bounds.getHeight()/2)/Constants.PPM);
        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth()/2/Constants.PPM,bounds.getHeight()/2/Constants.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public abstract void onHeadHit();

}
