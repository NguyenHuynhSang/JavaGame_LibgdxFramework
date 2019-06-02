package com.nhs.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nhs.game.Engine.B2WorldCreator;
import com.nhs.game.Engine.Controller;
import com.nhs.game.Engine.WorldContactListener;
import com.nhs.game.UiManager.Hud;
import com.nhs.game.mariobros;

import static com.nhs.game.Global.global.PPM;
import static com.nhs.game.Global.global._height;
import static com.nhs.game.Global.global._width;

public abstract class ScreenManagement implements Screen {
    protected mariobros game;
    protected OrthographicCamera gameCam;
    protected Viewport gamePort;
    protected Hud hud;
    protected Controller controller;
    protected TextureAtlas atlas;
    protected Music music;
    protected TmxMapLoader mapLoader;
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer renderer;
    protected B2WorldCreator creator;
    //box 2d variables
    protected World world;
    protected Box2DDebugRenderer b2dr;
    public ScreenManagement(mariobros game) {
        this.game=game;
        atlas=new TextureAtlas("actors.atlas");
        gameCam=new OrthographicCamera();
        //gamePort=new ScreenViewport(gameCam);  //Sẽ phát sinh lỗi nếu màn hình có size lớn thì Camera sẽ lớn hơn, đã fix ở dưới
        gamePort=new FitViewport(_width /PPM,_height/PPM,gameCam);
        hud=new Hud(game.batch);
        gameCam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        world=new World(new Vector2(0,-10),true);// "true" is make an object sleeping

        b2dr=new Box2DDebugRenderer();
        controller = new Controller();


        world.setContactListener(new WorldContactListener());
    }

    public World getWorld()
    {
        return  world;

    }

    public  TextureAtlas   getAtlas()
    {
        return  atlas;

    }

    public  TiledMap getMap()
    {
        return  map;
    }

    @Override
    public  void show(){} ;

    @Override
    public  void render(float delta) {};
    @Override
    public  void resize(int width, int height){
        Gdx.app.log("resize","game");
        gamePort.update(width,height);
        controller.resize(width, height);
    };

    @Override
    public  void pause(){};
    @Override
    public  void resume(){};

    @Override
    public  void hide(){};

    @Override
    public  void dispose(){
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        Gdx.app.log("Dispose","game");
    };
}
