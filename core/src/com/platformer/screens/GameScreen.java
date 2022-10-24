package com.platformer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.platformer.Platformer;
import com.platformer.sprites.Player;
import com.platformer.utils.Controls;
import com.platformer.utils.TiledObjects;

import static com.platformer.utils.Constants.PPM;

public class GameScreen implements Screen {
    Platformer game;
    //private boolean DEBUG = false;
    private OrthographicCamera camera;
    private Viewport gamePort;
    private Box2DDebugRenderer b2dr;

    private World world; //laws of physics
    private Player player;

    private final OrthogonalTiledMapRenderer tiledMapRenderer;
    private TiledMap map;
    private Music music;
    private TextureAtlas atlas;
    public GameScreen(Platformer game){
        this.game=game;
        camera = new OrthographicCamera();

        gamePort = new ScreenViewport(camera); // работает не так как должно(
        camera.setToOrtho(false);
        camera.position.set(gamePort.getScreenWidth()/2, gamePort.getScreenHeight()/2, 0);

        world = new World(new Vector2(0, -20f), false);
        b2dr = new Box2DDebugRenderer();
        atlas = new TextureAtlas(Gdx.files.internal("images/Nick.pack"));
        player = new Player(world, 399, 800, game, this);

        music = Gdx.audio.newMusic(Gdx.files.internal("music/Oblivion.mp3"));
        music.setLooping(true);
        music.play();


        map = new TmxMapLoader().load("maps/level0.tmx");
        TiledObjects.parseTiledObjectLayer(world, map.getLayers().get("surface").getObjects());
        TiledObjects.parseTiledObjectLayer(world, map.getLayers().get("text").getObjects());
        TiledObjects.parseTiledObjectLayer(world, map.getLayers().get("obstacles").getObjects());
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

    }

    public TextureAtlas getAtlas() {
        return atlas;

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime()); //delta позволяет с одинаковой скоростью двигаться при разных fps
        //правда на вертикальной оси у меня не получилось ее заставить работать
        ScreenUtils.clear(Color.SKY);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        tiledMapRenderer.render();
        b2dr.render(world, camera.combined.scl(PPM));
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        gamePort.update(width, height);
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
        tiledMapRenderer.dispose();
        map.dispose();
        player.dispose();

    }
    public void update(float delta){
        player.update(delta);
        world.step(1/60f, 6, 2);
        Controls.inputUpdate(delta, player);
        cameraUpdate(delta);
        tiledMapRenderer.setView(camera);

    }

    public void cameraUpdate(float delta){

        if (Gdx.input.isTouched()){
            camera.position.x+=100*delta;
        }
        Vector3 position = camera.position;
        camera.position.x = player.body.getPosition().x*PPM;//get info
        camera.position.y = player.body.getPosition().y*PPM;
        camera.position.set(position);
        camera.update();
    }
}
