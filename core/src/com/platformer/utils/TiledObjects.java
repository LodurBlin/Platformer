package com.platformer.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;


public class TiledObjects {
    static Array<Body> bodies = new Array<Body>();
    public static void parseTiledObjectLayer(World world, MapObjects objects){
        for(MapObject object: objects){
            Shape shape;
            if(object instanceof PolygonMapObject){
                shape = createPolyLine((PolygonMapObject) object);
            } else {
                continue;
            }
            Body body;
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(def);
            body.createFixture(shape, 1.0f);
            bodies.add(body);
            shape.dispose();
        }
    }

    private static ChainShape createPolyLine (PolygonMapObject polygon){
        float[] vertices = polygon.getPolygon().getTransformedVertices();
        Vector2[] normalVertices = new Vector2[vertices.length/2];
        for (int i=0; i<normalVertices.length; i++){
            normalVertices[i] = new Vector2(vertices[i*2]/Constants.PPM, vertices[i*2+1]/Constants.PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(normalVertices);
        return cs;
    }
}
