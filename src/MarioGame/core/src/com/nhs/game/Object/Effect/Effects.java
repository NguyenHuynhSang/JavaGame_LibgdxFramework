package com.nhs.game.Object.Effect;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nhs.game.Screens.PlayScreen.FirstScreen;
import com.nhs.game.Screens.ScreenManagement;

import static com.nhs.game.Global.global.PPM;

public abstract class Effects extends Sprite {
    protected ScreenManagement screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean setDestroy=false;
    protected boolean Destroyed=false;
    protected Body body;
    protected float stateTimer;
    public  boolean isDestroyed;

    protected TextureAtlas textureAtlas;

    public Effects(ScreenManagement screen, float x, float y) {
        this.screen=screen;
        this.world=screen.getWorld();
        setPosition(x,y);
        setDestroy=false;
        Destroyed=false;
        isDestroyed=false;
        defineEffect();
        stateTimer=0;

    }

    public   void update(float dt){
        if (setDestroy && !Destroyed){
            world.destroyBody(body);
            Destroyed=true;
            isDestroyed=true;
            stateTimer=0;
            return;
        }


    }

//
    protected abstract void defineEffect();

    public  void destroy(){
        setDestroy=true;
    }

}
