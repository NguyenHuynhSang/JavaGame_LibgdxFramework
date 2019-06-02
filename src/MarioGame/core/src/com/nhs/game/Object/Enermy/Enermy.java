package com.nhs.game.Object.Enermy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Screens.PlayScreen;
import com.nhs.game.Screens.ScreenManagement;

public abstract class Enermy extends Sprite {
    protected World world;
    protected ScreenManagement screen;
    public Body b2body;
    public Vector2 velocity;
    public boolean eDestroyed;
    public  Enermy(ScreenManagement screen, float x, float y)
    {
        this.world=screen.getWorld();
        this.screen=screen;
        setPosition(x,y);
        defineEnermy();
        velocity=new Vector2(0.5f,0.0f);
        b2body.setActive(false);
        eDestroyed=false;
    }


    protected abstract void defineEnermy();

    public abstract  void update(float dt);
    public   abstract void hitOnHead(Mario mario);

    public  abstract  void onEnermyHit(Enermy enermy);


    public  abstract  void killEnermy();
    //đảo ngược chiều của vận tốc
    public void reverseVelocity(boolean x,boolean  y){
        if (x){
            velocity.x=-velocity.x;
        }
        if (y){
            velocity.y=-velocity.y;
        }
    }
}
