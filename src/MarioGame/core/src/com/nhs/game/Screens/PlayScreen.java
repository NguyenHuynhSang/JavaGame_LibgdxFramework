package com.nhs.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
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
import com.nhs.game.Object.Enermy.Enermy;
import com.nhs.game.Object.Items.Item;
import com.nhs.game.Object.Items.ItemDef;
import com.nhs.game.Object.Items.Mushroom;
import com.nhs.game.UiManager.Hud;
import com.nhs.game.Object.Mario;
import com.nhs.game.Engine.B2WorldCreator;
import com.nhs.game.Engine.Controller;
import com.nhs.game.Engine.WorldContactListener;
import com.nhs.game.mariobros;

import java.util.concurrent.LinkedBlockingQueue;

import static com.nhs.game.Global.global.PPM;
import static com.nhs.game.Global.global._height;
import static com.nhs.game.Global.global._mapWidth;
import static com.nhs.game.Global.global._mapWidthX2;
import static com.nhs.game.Global.global._width;

public class PlayScreen implements Screen {

    private mariobros game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;
    private Controller controller;
    private TextureAtlas atlas;
    private Music music;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private  B2WorldCreator creator;


    //box 2d variables
    private World world;
    private Box2DDebugRenderer b2dr;



    //dynamics object

    private Mario player;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemstoSpawn;

    public  TiledMap getMap()
    {
        return  map;
    }

    public World getWorld()
    {
        return  world;

    }

    public  PlayScreen(mariobros game){
        atlas=new TextureAtlas("Mario_and_Enemies.pack");
        this.game=game;
        gameCam=new OrthographicCamera();
        //gamePort=new ScreenViewport(gameCam);  //Sẽ phát sinh lỗi nếu màn hình có size lớn thì Camera sẽ lớn hơn, đã fix ở dưới
        gamePort=new FitViewport(_width /PPM,_height/PPM,gameCam);
        hud=new Hud(game.batch);

        mapLoader=new TmxMapLoader();
        map=mapLoader.load("level1.tmx");
        renderer=new OrthogonalTiledMapRenderer(map,1/PPM);
        gameCam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        world=new World(new Vector2(0,-10),true);// "true" is make an object sleeping

        b2dr=new Box2DDebugRenderer();

        controller = new Controller();

        player=new Mario(this);

        creator=new B2WorldCreator(this);


        world.setContactListener(new WorldContactListener());
        music=mariobros.manager.get("audio/music/mario_music.ogg",Music.class);
        music.setLooping(true);
        music.play();

        items=new Array<Item>();
        itemstoSpawn=new LinkedBlockingQueue<ItemDef>();

    }


    public  void spawnItem(ItemDef itemDef){
        itemstoSpawn.add(itemDef);
    }

    public  void handleSpawningItem(){
        if (!itemstoSpawn.isEmpty()){
            ItemDef idef=itemstoSpawn.poll();
            if (idef.type==Mushroom.class){
                items.add(new Mushroom(this,idef.position.x,idef.position.y));

            }
        }

    }

    public  TextureAtlas   getAtlas()
    {
     return  atlas;

    }


    @Override
    public void show() {

    }

    public  void handleInput(float dt)
    {

        devSupport();


        if (player.currentState==Mario.State.DEAD)
            return;

        if (controller.isUpPressed()&& player.b2body.getLinearVelocity().y==0)
        {

            player.b2body.applyLinearImpulse(new Vector2(0,3.8f),player.b2body.getWorldCenter(),true);
            if (player.isBig==true)
                 mariobros.manager.get("audio/sounds/bigjump.wav",Sound.class).play();
            else
                mariobros.manager.get("audio/sounds/jump.wav",Sound.class).play();
            player.hitGround=false;
        }
        if (controller.isRightPressed()&& player.b2body.getLinearVelocity().x<=1.2)
        {
            player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(),true);
        }
        if (controller.isLeftPressed()&& player.b2body.getLinearVelocity().x>=-1.2)
        {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true);
        }





       // if (Gdx.input.isTouched() &&player.b2body.getLinearVelocity().x<=1.2)  {
        //    player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(),true);
        //}
        //if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && player.b2body.getLinearVelocity().y==0)
        //{
         //   player.b2body.applyLinearImpulse(new Vector2(0,3.8f),player.b2body.getWorldCenter(),true);
         //   mariobros.manager.get("audio/sounds/jump.wav",Sound.class).play();
        //}

        //if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) &&player.b2body.getLinearVelocity().x<=1.2)
        //{
        //    player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(),true);
        //}
        //
        //if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x>=-1.2)
        //{
        //    player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true);
       // }



    }

    public  void Update(float dt)
    {
        handleInput(dt);
        handleSpawningItem();
        world.step(1/50f,6,2);
        player.Update(dt);
        for (Enermy e:creator.getEnermy())
        {
            e.update(dt);
            if (e.getX()< gameCam.position.x+224/PPM)
                e.b2body.setActive(true);
        }

        for (Item item:items)
        {
            item.update(dt);
        }
        hud.Update(dt);
        //stop cam when mario was dead
        if (player.b2body.getPosition().x>(_mapWidth+_width/2)/PPM&&player.b2body.getPosition().x<(_mapWidthX2-_width/2)/PPM &&player.currentState!=Mario.State.DEAD)
        gameCam.position.x=player.b2body.getPosition().x;
        gameCam.update();
        renderer.setView(gameCam);// set vi tri ve map

    }


    @Override
    public void render(float delta) {

        Update(delta);
        Gdx.gl.glClearColor(0,0,0,1); //clear the screen to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        renderer.render();
      //  b2dr.render(world,gameCam.combined); // hiện boundingbox lên để kiểm tra va chạm
        //draw hud riêng vì hud k chạy theo cam world
        game.batch.setProjectionMatrix(gameCam.combined);//tell the game where the camera is in our game world
        game.batch.begin(); //open the box
        player.draw(game.batch); //draw mario to the screen
        for (Enermy e:creator.getEnermy())
        {
            e.draw(game.batch);

        }
        for (Item item:items)
        {
            item.draw(game.batch);
        }
        game.batch.end(); //close the box and draw it to the screen
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        controller.draw();

        if (isGameOver()) {
            game.setScreen(new GameOver(game));
            dispose();
        }

    }


    private void devSupport(){

        if (controller.isReset()){
            player.resetPlayer();
            gameCam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        }
        if (controller.isGrowPlayer())
        {
            player.growMarioforDev();
        }
        if (controller.isKill())
        {
            player.killPlayer();
        }

    }

    @Override
    public void resize(int width, int height)
    {
     gamePort.update(width,height);
        controller.resize(width, height);
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
    map.dispose();
    renderer.dispose();
    world.dispose();
    b2dr.dispose();
    hud.dispose();

    }


    public  boolean isGameOver(){
        if (player.currentState==Mario.State.DEAD && player.getStateTimer()>3)
        {
            return  true;
        }
        return  false;
    }
}
