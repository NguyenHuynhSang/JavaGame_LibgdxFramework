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
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.nhs.game.Global.global._height;
import static com.nhs.game.Global.global._width;

public class Controller implements Disposable {

    Viewport viewport;
    Stage stage;
    boolean upPressed, downPressed, leftPressed, rightPressed,firePressed;
    boolean startPressed,exitPressed;
    boolean resetPress,growPress,killPress,imMortalPress,changeScreenPress,activeBodyPress;
    OrthographicCamera camera;
    public  boolean justPress=false;


    public Controller(boolean isMenuScene) {
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
                        justPress=false;
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
        if (isMenuScene){
            loadControllerforMenuScreen();
        }
        else
            loadControllerforPlayScreen();
    }

    private  void loadControllerforPlayScreen(){



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
                //    justPress=false;
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
                //justPress=false;
            }
        });


        Image fireImg = new Image(new Texture("btnfire.png"));
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


        Image changeScreenImg = new Image(new Texture("btsm.png"));
        changeScreenImg.setSize(50, 50);
        changeScreenImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                changeScreenPress = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                changeScreenPress = false;
                justPress=false;
            }
        });

        Table table = new Table();
        table.setSize(40,40);
        table.left().bottom();
        table.pad(0,300,20,0);
        table.add();

        table.add(upImg).size(40,40);



        Table table1=new Table();

        table1.left().bottom();
        table1.add();
        table1.pad(0,20,10,0);
        table1.add().size(40,40);
        table1.row();
        table1.add(leftImg).size(40,40);
        table1.add();
        table1.add(rightImg).size(40,40);
        table.row().pad(1, 1, 1, 1);
        //table.add();



        Table table2 = new Table();
        table2.setSize(40,40);
        table2.left().bottom();
        table2.pad(0,350,40,0);
        table2.add();
        table2.add(fireImg).size(40,40);


        Table table3 = new Table();
        table3.setSize(20,20);
        table3.left().bottom();
        table3.pad(0,380,180,0);
        table3.add();
        table3.add(changeScreenImg).size(20,20);


        stage.addActor(table);
        stage.addActor(table1);
        stage.addActor(table2);
        stage.addActor(table3);
        //stage.setDebugAll(true);

    }

    private void loadControllerforMenuScreen(){

        Image startImg = new Image(new Texture("start.png"));
        startImg.setSize(50, 50);
        startImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                startPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                startPressed = false;
                justPress=false;
            }
        });

        Image exitImg = new Image(new Texture("exit.png"));
        exitImg.setSize(50, 50);
        exitImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                exitPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                exitPressed = false;
                justPress=false;
            }
        });

        Table table2 = new Table();
        table2.setSize(40,40);
        table2.left().bottom();
        table2.pad(0,80,10,0);
        table2.add();
        table2.add(startImg).size(40,40);


        Table table = new Table();
        table.setSize(40,40);
        table.left().bottom();
        table.pad(0,260,10,0);
        table.add();
        table.add(exitImg).size(40,40);

        stage.addActor(table2);
        stage.addActor(table);

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
    public boolean isStartPressed(){return  startPressed;}
    public boolean isExitPressed(){return  exitPressed;}



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

    @Override
    public void dispose() {
        stage.dispose();
    }
}

