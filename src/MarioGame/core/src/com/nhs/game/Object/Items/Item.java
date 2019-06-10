package com.nhs.game.Object.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Screens.PlayScreen.FirstScreen;
import com.nhs.game.Screens.ScreenManagement;

import static com.nhs.game.Global.global.PPM;

public abstract  class Item extends Sprite {
    protected ScreenManagement screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean setDestroy=false;
    protected boolean Destroyed=false;
    protected Body body;
    protected float stateTimer;
    public  boolean isDestroyed;
    public  Item(ScreenManagement screen, float x, float y){
        this.screen=screen;
        this.world=screen.getWorld();
        setPosition(x,y);
        setDestroy=false;
        Destroyed=false;
        isDestroyed=false;
        setBounds(getX(),getY(),16/PPM,16/PPM);
        defineItem();
        stateTimer=0;
    }

    public  abstract  void defineItem();
    public  abstract  void useItem(Mario mario);

    public  void update(float dt){
        if (Destroyed) return;
        if (setDestroy && !Destroyed){
            body.setUserData(null);
            world.destroyBody(body);
            body=null;
            Destroyed=true;
            isDestroyed=true;
            stateTimer=0;
            return;
        }

        }
     @Override
    public  void draw(Batch batch)
    {

        if (!Destroyed) {

            super.draw(batch);

        }

    }

    public  void destroy(){
        setDestroy=true;

    }

    public void reverseVelocity(boolean x,boolean  y){
        if (x){
            velocity.x=-velocity.x;
        }
        if (y){
            velocity.y=-velocity.y;
        }
    }
}
