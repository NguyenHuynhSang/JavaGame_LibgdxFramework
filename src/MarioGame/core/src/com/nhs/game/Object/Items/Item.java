package com.nhs.game.Object.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nhs.game.Object.Mario;
import com.nhs.game.Screens.PlayScreen;

import static com.nhs.game.Global.global.PPM;

public abstract  class Item extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean setDestroy=false;
    protected boolean Destroyed=false;
    protected Body body;

    public  Item(PlayScreen screen,float x,float y){
        this.screen=screen;
        this.world=screen.getWorld();
        setPosition(x,y);
        setDestroy=false;
        Destroyed=false;
        setBounds(getX(),getY(),16/PPM,16/PPM);
        defineItem();

    }

    public  abstract  void defineItem();
    public  abstract  void useItem(Mario mario);

    public  void update(float dt){
        if (setDestroy && !Destroyed){
            world.destroyBody(body);
            Destroyed=true;
        }

        }


    public  void draw(Batch batch)
    {

        if (!Destroyed) {

            //super.draw(batch);

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
