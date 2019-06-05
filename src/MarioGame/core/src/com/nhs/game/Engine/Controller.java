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
    boolean upPressed, downPressed, leftPressed, rightPressed,firePressed;
    boolean resetPress,growPress,killPress,imMortalPress,changeScreenPress,activeBodyPress;
    OrthographicCamera camera;
    public  boolean justPress;
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
                    case Input.Keys.F:
                        firePressed=true;
                        Gdx.app.log("Press","  F");
                        break;
                    case Input.Keys.H:
                        imMortalPress=true;
                        Gdx.app.log("Press"," H");
                        break;
                    case Input.Keys.A:
                        changeScreenPress=true;
                        Gdx.app.log("Press"," A");
                        break;
                    case Input.Keys.G:
                        activeBodyPress=true;
                        Gdx.app.log("Press"," G");
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
                        justPress=false;
                        break;
                    case Input.Keys.W:
                        growPress=false;
                        justPress=false;
                        //Gdx.app.log("Press"," Q");
                        break;
                    case Input.Keys.E:
                        justPress=false;
                        killPress=false;
                        //Gdx.app.log("Press"," Q");
                        break;
                    case Input.Keys.F:
                        firePressed=false;
                        justPress=false;
                        // Gdx.app.log("Press"," E");
                        break;
                    case Input.Keys.H:
                        imMortalPress=false;
                        justPress=false;
                        // Gdx.app.log("Press"," E");
                        break;
                    case Input.Keys.A:
                        changeScreenPress=false;
                        justPress=false;
                        // Gdx.app.log("Press"," E");
                        break;
                    case Input.Keys.G:
                        activeBodyPress=false;
                        justPress=false;
                        // Gdx.app.log("Press"," E");
                        break;
                }
                return true;
            }
        });

        Gdx.input.setInputProcessor(stage);



        Image upImg = new Image(new Texture("btnup.png"));
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
                justPress=false;
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
                justPress=false;
            }
        });

        Image rightImg = new Image(new Texture("btnright.png"));
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
                justPress=false;
            }
        });

        Image leftImg = new Image(new Texture("btnleft.png"));
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
                justPress=false;
            }
        });


        Image fireImg = new Image(new Texture("fire.png"));
        fireImg.setSize(50, 50);
        fireImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                firePressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                firePressed = false;
                justPress=false;
            }
        });



        Table table = new Table();
        table.setSize(50,50);
        table.left().bottom();
        table.pad(0,300,20,0);
        table.add();

        table.add(upImg).size(upImg.getWidth()/2, upImg.getHeight()/2);



        Table table1=new Table();

        table1.left().bottom();
        table1.add();
        table1.pad(0,20,10,0);
        table1.add().size(upImg.getWidth()/2, upImg.getHeight()/2);
        table1.row();
        table1.add(leftImg).size(rightImg.getWidth()/2, rightImg.getHeight()/2);
        table1.add();
        table1.add(rightImg).size(downImg.getWidth()/2, downImg.getHeight()/2);
        table.row().pad(1, 1, 1, 1);
        //table.add();



        Table table2 = new Table();
        table2.setSize(50,50);
        table2.left().bottom();
        table2.pad(0,350,40,0);
        table2.add();
        table2.add(fireImg).size(fireImg.getWidth()/2, fireImg.getHeight()/2);


        stage.addActor(table);
        stage.addActor(table1);
        stage.addActor(table2);
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

    public boolean isFirePressed(){return firePressed;}

    ///Dev support key (only on computer)

    public boolean isReset(){ return resetPress;}
    public boolean isGrowPlayer(){return growPress;}
    public  boolean isKill(){return killPress;}

    public  boolean isImMortal(){return imMortalPress;}


    public  boolean isChangeScreen(){return changeScreenPress;}

    public  boolean isActiveBodyPress(){return activeBodyPress;}
    public void resize(int width, int height){
        viewport.update(width, height);
    }


}

