package com.nhs.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import static com.nhs.game.Global.global._height;
import static com.nhs.game.Global.global._width;

public class Controller {

    Viewport viewport;
    Stage stage;
    boolean upPressed, downPressed, leftPressed, rightPressed;
    boolean resetPress,growPress,killPress;
    OrthographicCamera camera;

    public Controller() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(_width, _height, camera);
        stage = new Stage(viewport);
        stage.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.SPACE:
                        upPressed = true;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                        //dev support
                    case Input.Keys.Q:
                        resetPress=true;
                        Gdx.app.log("Press"," Q");
                        break;
                    case Input.Keys.W:
                        growPress=true;
                        Gdx.app.log("Press"," W");
                        break;
                    case Input.Keys.E:
                        killPress=true;
                        Gdx.app.log("Press"," E");
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.SPACE:
                        upPressed = false;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                    case Input.Keys.Q:
                        resetPress=false;
                        break;
                    case Input.Keys.W:
                        growPress=false;
                        //Gdx.app.log("Press"," Q");
                        break;
                    case Input.Keys.E:
                        killPress=false;
                        //Gdx.app.log("Press"," Q");
                        break;
                }
                return true;
            }
        });

        Gdx.input.setInputProcessor(stage);



        Image upImg = new Image(new Texture("flatDark25.png"));
        upImg.setSize(50, 50);
        upImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image downImg = new Image(new Texture("flatDark26.png"));
        downImg.setSize(50, 50);
        downImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });

        Image rightImg = new Image(new Texture("flatDark24.png"));
        rightImg.setSize(50, 50);
        rightImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image leftImg = new Image(new Texture("flatDark23.png"));
        leftImg.setSize(50, 50);
        leftImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });
        Table table = new Table();
        table.setSize(_width,50);
        table.left().bottom();
        table.pad(0,300,20,0);
        table.add();

        table.add(upImg).size(upImg.getWidth()/2, upImg.getHeight()/2);



        Table table1=new Table();

        table1.left().bottom();
        table1.add();
        table1.pad(0,0,10,0);
        table1.add().size(upImg.getWidth()/2, upImg.getHeight()/2);
        table1.row();
        table1.add(leftImg).size(rightImg.getWidth()/2, rightImg.getHeight()/2);
        table1.add();
        table1.add(rightImg).size(downImg.getWidth()/2, downImg.getHeight()/2);
        table.row().pad(1, 1, 1, 1);
        //table.add();

        stage.addActor(table);
        stage.addActor(table1);
        //stage.setDebugAll(true);
    }
    public void draw(){

        stage.draw();
    }


    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }


    ///Dev support key (only on computer)

    public boolean isReset(){ return resetPress;}
    public boolean isGrowPlayer(){return growPress;}
    public  boolean isKill(){return killPress;}



    public void resize(int width, int height){
        viewport.update(width, height);
    }
}

