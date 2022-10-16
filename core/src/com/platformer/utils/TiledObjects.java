package com.platformer.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import static com.platformer.utils.Constants.PPM;

public class TiledObjects {
    static Array<Body> bodies = new Array<>();
    public static void parseTiledObjectLayer(World world, MapObjects objects){
        for(MapObject object: objects){
            Shape shape;
            if(object instanceof PolygonMapObject){
                shape = getPolyLine((PolygonMapObject) object);
            } else if(object instanceof RectangleMapObject){
                shape = getRectangle((RectangleMapObject) object);
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

    private static ChainShape getPolyLine (PolygonMapObject object){
        float[] vertices = object.getPolygon().getTransformedVertices();
        Vector2[] normalVertices = new Vector2[vertices.length/2];
        for (int i=0; i<normalVertices.length; i++){
            normalVertices[i] = new Vector2(vertices[i*2]/PPM, vertices[i*2+1]/PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(normalVertices);
        return cs;
    }

    private static PolygonShape getRectangle (RectangleMapObject object){
        Rectangle rectangle = object.getRectangle();
        PolygonShape ps = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x+ rectangle.width * 0.5f)/PPM, (rectangle.y+rectangle.height * 0.5f)/PPM);
        ps.setAsBox(rectangle.getWidth() * 0.5f/PPM, rectangle.getHeight() * 0.5f/PPM, size, 0.0f);
        return ps;
    }
}
