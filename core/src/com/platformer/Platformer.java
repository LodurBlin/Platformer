package com.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.utils.ScreenUtils;
import static com.platformer.utils.Constants.PPM;

public class Platformer extends ApplicationAdapter {
	//private boolean DEBUG = false;
	private final float SCALE = 2.0f;
	private OrthographicCamera camera;
	private World world; //laws of physics
	private Body player;
	private Box2DDebugRenderer b2dr;
	private SpriteBatch batch;
	private Texture ggTex;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w/SCALE), (h/SCALE));

		world = new World(new Vector2(0, -9.8f), false);
		b2dr = new Box2DDebugRenderer();

		createBox(0, 0, 80, 20, true);
		player = createBox(0, 20, 64, 64, false);

		batch = new SpriteBatch();
		ggTex = new Texture("floppa.png");
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());
		ScreenUtils.clear(Color.BLACK);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(ggTex, player.getPosition().x/PPM, player.getPosition().y/PPM);
		batch.end();

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
	}




	public void update(float delta){
		world.step(1/60f, 6, 2);
		inputUpdate(delta);
		cameraUpdate(delta);
		//batch.setProjectionMatrix(camera.combined);
	}
	public void inputUpdate(float delta){
		int horizontalForce = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			horizontalForce -=1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			horizontalForce +=1;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			player.applyForceToCenter(0, 700, false);
		}
		player.setLinearVelocity(horizontalForce *5, player.getLinearVelocity().y);
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
