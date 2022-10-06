package com.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.utils.ScreenUtils;
import com.platformer.utils.Controls;
import com.platformer.utils.TiledObjects;

import static com.platformer.utils.Constants.PPM;

public class Platformer extends ApplicationAdapter {
	private boolean DEBUG = false;
	private final float SCALE = 2.0f;
	private OrthographicCamera camera;
	private Box2DDebugRenderer b2dr;

	private World world; //laws of physics
	private Body player;

	private SpriteBatch batch;
	private Texture ggTex;

	private OrthogonalTiledMapRenderer tmr;
	private TiledMap map;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w/SCALE), (h/SCALE));

		world = new World(new Vector2(0, -9.8f), false);
		b2dr = new Box2DDebugRenderer();

		//createBox(70, 70, 70, 20, true);
		player = createBox(90, 110, 48, 48, false);

		batch = new SpriteBatch();
		ggTex = new Texture("images/floppa.png");

		map = new TmxMapLoader().load("maps/map.tmx");
		tmr = new OrthogonalTiledMapRenderer(map);
		TiledObjects.parseTiledObjectLayer(world, map.getLayers().get("Collision-layer_1").getObjects());
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());
		ScreenUtils.clear(Color.BLACK);
		//Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		batch.draw(ggTex, player.getPosition().x/PPM + Gdx.graphics.getWidth()/2 - ggTex.getWidth()/2, player.getPosition().y/PPM + Gdx.graphics.getHeight()/2- ggTex.getHeight()/2);
		batch.end();

		tmr.render();
		b2dr.render(world, camera.combined.scl(PPM));
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
	}

	@Override
	public void resize (int width, int height) {

		camera.setToOrtho(false, width/SCALE, height/SCALE);

	}

	@Override
	public void dispose () { //loads big amount of info, saves resources of the computer
		world.dispose();
		b2dr.dispose();
		batch.dispose();
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
