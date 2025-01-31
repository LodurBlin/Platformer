package com.platformer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.platformer.Platformer;
import com.platformer.utils.Controls;
import com.platformer.utils.TiledObjects;

import static com.platformer.utils.Constants.PPM;

public class GameScreen implements Screen {
    Platformer game;
    //private boolean DEBUG = false;
    private float SCALE = 2.0f;
    private OrthographicCamera camera;
    private Viewport gamePort;
    private Box2DDebugRenderer b2dr;

    private World world; //laws of physics
    private Body player;
    private Texture ggTex;

    private final OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    public GameScreen(Platformer game){
        this.game=game;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        gamePort = new ScreenViewport(camera); // работает не так как должно(
        camera = new OrthographicCamera();
        camera.setToOrtho(false); //, (w/SCALE), (h/SCALE)
        world = new World(new Vector2(0, -9.8f), false);
        b2dr = new Box2DDebugRenderer();

        player = createBox(90, 110, 48, 48, false);
        ggTex = new Texture("images/floppa.png");

        map = new TmxMapLoader().load("maps/level1.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
        TiledObjects.parseTiledObjectLayer(world, map.getLayers().get("Object Layer 1").getObjects());
    }
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime()); //delta позволяет с одинаковой скоростью двигаться при разных fps
        //правда на вертикальной оси у меня не получилось ее заставить работать
        ScreenUtils.clear(Color.BLACK);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(ggTex, player.getPosition().x/PPM + Gdx.graphics.getWidth()/2 - ggTex.getWidth()/2, player.getPosition().y/PPM + Gdx.graphics.getHeight()/2- ggTex.getHeight()/2);
        game.batch.end();

        tmr.render();
        b2dr.render(world, camera.combined.scl(PPM));
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width/SCALE, height/SCALE);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        ggTex.dispose();
        tmr.dispose();
        map.dispose();
    }
    public void update(float delta){
        world.step(1/60f, 6, 2);
        Controls.inputUpdate(delta, player);
        cameraUpdate(delta);
        tmr.setView(camera);
        //batch.setProjectionMatrix(camera.combined); //Оно ломает текстуру и по идее это resize
    }

    public void cameraUpdate(float delta){
        Vector3 position = camera.position;
        position.x = player.getPosition().x * PPM; //get info
        position.y = player.getPosition().y * PPM;
        camera.position.set(position);
        camera.update();
    }
    public Body createBox(int x, int y, int width, int height, boolean isStatic){
        Body pBody;
        BodyDef def = new BodyDef();
        if (isStatic){
            def.type = BodyDef.BodyType.StaticBody;
        }
        else{
            def.type = BodyDef.BodyType.DynamicBody;
        }

        def.position.set(x/PPM, y/PPM);
        def.fixedRotation = true; //false - rotate when hitting objects
        pBody = world.createBody(def); //initialisation

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/ SCALE / PPM, height / SCALE / PPM); //counts from center, put info

        pBody.createFixture(shape, 1.0f);
        shape.dispose(); //cleaning

        return pBody;
    }
}
