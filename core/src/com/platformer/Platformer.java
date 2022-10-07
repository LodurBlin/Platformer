package com.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.physics.box2d.*;

import com.platformer.screens.Menu;
import com.platformer.utils.Controls;

public class Platformer extends Game {

	public SpriteBatch batch;
	public BitmapFont font; //renders text



	@Override
	public void create () {
		setScreen(new Menu(this));

		batch = new SpriteBatch();
		font = new BitmapFont();
	}

	@Override
	public void render () {


		super.render();
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void dispose () { //loads big amount of info, saves resources of the computer
		batch.dispose();
		font.dispose();
	}






}
