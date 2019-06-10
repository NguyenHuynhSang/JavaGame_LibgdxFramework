package com.nhs.game.Screens.MenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nhs.game.Engine.Controller;
import com.nhs.game.Screens.PlayScreen.FirstScreen;
import com.nhs.game.mariobros;

import static com.nhs.game.Global.global.ENERMY_HEAD_BIT;
import static com.nhs.game.Global.global._height;
import static com.nhs.game.Global.global._width;

public class MenuScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    private Controller controller;
    public MenuScreen(Game game) {
        this.game=game;
        viewport=new FitViewport(_width,_height,new OrthographicCamera());
        stage=new Stage(viewport,((mariobros)game).getBatch());
        controller=new Controller(true);
        Image BG = new Image(new Texture("gbnew.jpg"));
        BG.setSize(_width, _height);

        Table table2 = new Table();
        table2.setSize(_width,_height);
        table2.left().bottom();
        table2.add(BG).size(_width,_height);
        stage.addActor(table2);

    }

    @Override
    public void show() {

    }


    private  void hanleInput(){
        if (controller.isStartPressed() && !controller.justPress)
        {
            controller.justPress=true;
            dispose();
            Gdx.app.log("change screen","pressed");
            game.setScreen(new FirstScreen((mariobros) game));

        }
        if (controller.isExitPressed() && !controller.justPress)
        {
            Gdx.app.exit();
            controller.justPress=true;
        }

    }


    @Override
    public void render(float delta) {

        hanleInput();

        stage.draw();
        controller.draw();

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
        stage.dispose();
        controller.dispose();
        Gdx.app.log("Dispose","menu");
    }
}
