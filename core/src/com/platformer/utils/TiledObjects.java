package com.platformer.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class TiledObjects {
    public static void parseTiledObjectLayer(World world, MapObjects objects){
        for(MapObject object: objects){
            Shape shape = null;
            if(object instanceof PolylineMapObject){
                shape = createLine((PolylineMapObject) object);
            }
            Body body;
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(def);
            body.createFixture(shape, 1.0f);
            shape.dispose();
        }
    }

    private static ChainShape createLine (PolylineMapObject pol){
        float[] vertices = pol.getPolyline().getVertices();


        return null;
    }
}
