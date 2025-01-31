package com.platformer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.platformer.Platformer;
import com.platformer.utils.Button;

public class Menu implements Screen {
    Platformer game;
    Button exitButton, playButton;
    public Menu(Platformer game){
        this.game = game;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        int wExit = 132, hExit = 52, wPlay = 264, hPlay = 104;
        exitButton = new Button(new Texture("images/exit_a.png"), new Texture("images/exit_i.png"), w-wExit-w/8, hExit+h/8, wExit, hExit, game);
        playButton =  new Button(new Texture("images/play_a.png"), new Texture("images/play_i.png"), w-wPlay-w/2, h-hPlay-h/2, wPlay, hPlay, game);


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
