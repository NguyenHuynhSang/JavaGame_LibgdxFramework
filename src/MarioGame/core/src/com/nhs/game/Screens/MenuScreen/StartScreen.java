package com.nhs.game.Screens.MenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nhs.game.mariobros;

import static com.nhs.game.Global.global._height;
import static com.nhs.game.Global.global._width;

public class StartScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    public StartScreen(Game game) {
        this.game=game;
        viewport=new FitViewport(_width,_height,new OrthographicCamera());
        stage=new Stage(viewport,((mariobros)game).getBatch());

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
