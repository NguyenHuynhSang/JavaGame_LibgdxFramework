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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nhs.game.Engine.B2WorldCreator;
import com.nhs.game.Engine.Controller;
import com.nhs.game.Engine.WorldContactListener;
import com.nhs.game.Object.Effect.BreakingBrick;
import com.nhs.game.Object.Effect.EffectDef;
import com.nhs.game.Object.Effect.Effects;
import com.nhs.game.Object.Effect.FlippingCoin;
import com.nhs.game.Object.Effect.ScoreText;
import com.nhs.game.Object.Enermy.Enermy;
import com.nhs.game.Object.Items.Coins;
import com.nhs.game.Object.Items.Flower;
import com.nhs.game.Object.Items.Item;
import com.nhs.game.Object.Items.ItemDef;
import com.nhs.game.Object.Items.Mushroom;
import com.nhs.game.UiManager.Hud;
import com.nhs.game.mariobros;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

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
    protected Array<Item> items;
    protected Array<Effects> effects;

    protected LinkedBlockingQueue<ItemDef> itemstoSpawn;
    protected LinkedBlockingQueue<EffectDef> effectstoSpawn;

    public ScreenManagement(mariobros game) {
        this.game=game;
        atlas=new TextureAtlas("mariobro.pack");
        gameCam=new OrthographicCamera();
        //gamePort=new ScreenViewport(gameCam);  //Sẽ phát sinh lỗi nếu màn hình có size lớn thì Camera sẽ lớn hơn, đã fix ở dưới
        gamePort=new FitViewport(_width /PPM,_height/PPM,gameCam);
        hud=new Hud(game.batch);
        gameCam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        world=new World(new Vector2(0,-10),true);// "true" is make an object sleeping
        b2dr=new Box2DDebugRenderer();
        controller = new Controller();
        world.setContactListener(new WorldContactListener());



        items=new Array<Item>();
        itemstoSpawn=new LinkedBlockingQueue<ItemDef>();
        effects=new Array<Effects>();
        effectstoSpawn=new LinkedBlockingQueue<EffectDef>();
    }


    public  void spawnItem(ItemDef itemDef){
        itemstoSpawn.add(itemDef);
    }

    public  void spawnEffect(EffectDef effectDef){
        effectstoSpawn.add(effectDef);
    }

    public  void handleSpawningItem(){
        if (!itemstoSpawn.isEmpty())
        {
            ItemDef idef=itemstoSpawn.poll();
            if (idef.type==Mushroom.class)
            {
                items.add(new Mushroom(this,idef.position.x,idef.position.y));

            }else if (idef.type==Flower.class)
            {
                items.add(new Flower(this,idef.position.x,idef.position.y));
            } else if(idef.type==Coins.class){
                items.add(new Coins(this,idef.position.x,idef.position.y));
            }
        }
    }


    public  void handleSpawningEffects(){
        if (!effectstoSpawn.isEmpty())
        {
            EffectDef edef=effectstoSpawn.poll();
            if (edef.type==FlippingCoin.class)
            {
                effects.add(new FlippingCoin(this,edef.position.x,edef.position.y));
            }else if (edef.type==BreakingBrick.class)
            {
                effects.add(new BreakingBrick(this,edef.position.x,edef.position.y,2f,2f));
                effects.add(new BreakingBrick(this,edef.position.x,edef.position.y,-2f,2f));
                effects.add(new BreakingBrick(this,edef.position.x,edef.position.y,2f,-2f));
                effects.add(new BreakingBrick(this,edef.position.x,edef.position.y,-2f,-2f));
            } else if (edef.type==ScoreText.class)
            {
                effects.add(new ScoreText(this,edef.position.x+15/PPM,edef.position.y));
            }
        }
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
    public  void show(){}

    @Override
    public  void render(float delta) {};
    @Override
    public  void resize(int width, int height){
        Gdx.app.log("resize","game");
        gamePort.update(width,height);
        controller.resize(width, height);
    };

    @Override
    public  void pause(){

    }
    @Override
    public  void resume(){}

    @Override
    public  void hide(){}

    @Override
    public  void dispose(){

        Gdx.app.log("Dispose","game");
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
