package com.nhs.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;
import static com.nhs.game.Screens.Global.global._height;
import static com.nhs.game.Screens.Global.global._width;

public class Hud {
    public Stage stage;
    private Viewport viewport; // dùng 1 cam khác để lock hud tránh trường hợp hub "bị bỏ lại" khi game cam thay đổi

    private  Integer worldTimer;
    private  float timeCount;
    private  Integer score;

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLable;
    Label marioLable;

    public Hud(SpriteBatch spriteBatch)
    {
        worldTimer=300;
        timeCount=0;
        score=0;
        viewport=new FitViewport(_width,_height,new OrthographicCamera());
        stage=new Stage(viewport,spriteBatch);


        Table table=new Table();
        table.top();// đặt hub trên cùng
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLable = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLable = new Label("Mario", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        table.add(marioLable).expandX().padTop(10);

        table.add(worldLable).expandX().padTop(10);

        table.add(timeLabel).expandX().padTop(10);

        table.row();
        table.add(scoreLabel).expandX();

        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);

    }


}
