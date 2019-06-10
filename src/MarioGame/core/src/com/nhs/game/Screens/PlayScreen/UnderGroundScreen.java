package com.nhs.game.Screens.PlayScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nhs.game.Engine.B2WorldCreator;
import com.nhs.game.Object.Effect.Effects;
import com.nhs.game.Object.Enermy.Enermy;
import com.nhs.game.Object.Items.Coins;
import com.nhs.game.Object.Items.Item;
import com.nhs.game.Object.Items.ItemDef;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Screens.MenuScreen.MenuScreen;
import com.nhs.game.Screens.ScreenManagement;
import com.nhs.game.mariobros;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

import static com.nhs.game.Global.global.PPM;
import static com.nhs.game.Global.global._mapWidth;
import static com.nhs.game.Global.global._mapWidthX2;
import static com.nhs.game.Global.global._width;

public class UnderGroundScreen extends ScreenManagement {
    private Mario player;
   // private LinkedBlockingQueue<ItemDef> itemstoSpawn;
    public UnderGroundScreen(mariobros game) {
        super(game);
        mapLoader=new TmxMapLoader();
        map=mapLoader.load("undergroundMap.tmx");
        renderer=new OrthogonalTiledMapRenderer(map,1/PPM);
        //Array  items va LinkedBlockingQueue itemstoSpawn phai tao trc vi khi vao creator co goi 2 cai nay
        creator=new B2WorldCreator(this);
        player=creator.getPlayer();
//        music=mariobros.manager.get("audio/music/mario_music.ogg",Music.class);
////        music.setLooping(true);
////        music.play();

    }


    public  void handleInput(float dt)
    {

        devSupport();
        if (player.isNextScene){
            changeScreen();
        }

        if (player.currentState==Mario.State.DEAD)
            return;


        if (controller.isUpPressed()&& player.b2body.getLinearVelocity().y==0&&!controller.justPress)
        {
            controller.justPress=true;
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

        if (controller.isFirePressed())
        {
            if (!controller.justPress)
            {
                controller.justPress=true;
                player.fire();
                return;
            }

        }





    }
    public  void Update(float dt)
    {
        handleInput(dt);
        handleSpawningItem();
        handleSpawningEffects();
        world.step(1/50f,6,2);
        player.Update(dt);



        for (Item item:items)
        {
            item.update(dt);
            if (item.isDestroyed){
                items.removeValue(item,true);
                continue;
            }

        }
        for (Effects e:effects)
        {
            e.update(dt);
            if (e.isDestroyed){
                effects.removeValue(e,true);
                continue;
            }

        }
        hud.Update(dt);
        //stop cam when mario was dead
        if (player.b2body.getPosition().x>(_mapWidth+_width/2)/PPM&&player.b2body.getPosition().x<(_mapWidthX2-_width/2)/PPM &&player.currentState!=Mario.State.DEAD)
            gameCam.position.x=player.b2body.getPosition().x;
        gameCam.update();
        renderer.setView(gameCam);// set vi tri ve map
    }

    public void render(float delta) {

        Update(delta);
        Gdx.gl.glClearColor(0,0,0,1); //clear the screen to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        //b2dr.render(world,gameCam.combined); // hiện boundingbox lên để kiểm tra va chạm
        //draw hud riêng vì hud k chạy theo cam world
        game.batch.setProjectionMatrix(gameCam.combined);//tell the game where the camera is in our game world
        game.batch.begin(); //open the box
        player.draw(game.batch); //draw mario to the screen
        for (Item item:items)
        {
            if (item.getX()<=gameCam.position.x-_width/2/PPM ||item.getX()>gameCam.position.x+_width/2/PPM ) continue;
            item.draw(game.batch);
        }
        game.batch.end(); //close the box and draw it to the screen
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        controller.draw();


    }

    private void devSupport() {

        if (controller.isReset() && !controller.justPress) {
            player.resetPlayer();
            gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
            controller.justPress = true;
            Gdx.app.log("[Dev]", "reset Player");
            return;
        }
        if (controller.isGrowPlayer() && !controller.justPress) {

            player.growMarioforDev();
            controller.justPress = true;
            Gdx.app.log("[Dev]", "grow Player");
            return;
        }
        if (controller.isKill() && !controller.justPress) {
            player.killPlayer();
            controller.justPress = true;
            Gdx.app.log("[Dev]", "kill Player");
            return;
        }
        if (controller.isKill() && !controller.justPress) {
            player.killPlayer();
            controller.justPress = true;
            Gdx.app.log("[Dev]", "kill Player");
            return;
        }
        if (controller.isImMortal() && !controller.justPress) {
            player.setImMortalMario();
            controller.justPress = true;
            return;
        }
        if (controller.isChangeScreen()&&!controller.justPress)
        {
            Gdx.app.log("[DEV]","change screen");

            game.setScreen(new MenuScreen(game));

            return;

        }

    }
    private  void changeScreen(){
        dispose();
        game.setScreen(new SecondScreen(game));
    }

    @Override
    public void dispose() {
        Gdx.app.log("Dispose","UnderGroundScreen");

    }

}
