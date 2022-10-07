package com.platformer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.platformer.Platformer;
import com.platformer.utils.Button;

import static com.platformer.utils.Constants.PPM;

public class Menu implements Screen {
    Platformer game;
    Texture exitTex, playTex;
    float w, h;
    Button exitButton, playButton;
    public Menu(Platformer game){
        this.game = game;
        playTex = new Texture("images/play_a.png");
        exitTex = new Texture("images/exit_a.png");
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        exitButton = new Button(exitTex, new Texture("images/exit_i.png"), w-exitTex.getWidth()-w/8, exitTex.getHeight()+h/8, 300, 100, game);
        playButton =  new Button(playTex, new Texture("images/play_i.png"), w-playTex.getWidth()-w/8, h-playTex.getHeight()-h/8, 152, 52, game);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.BLACK);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        exitButton.drawButton();
        playButton.drawButton();
        if (exitButton.checkCollision() && Gdx.input.isTouched()){
            Gdx.app.exit();
        }
        if (playButton.checkCollision() && Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
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

    }

}
