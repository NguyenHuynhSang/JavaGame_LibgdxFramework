package com.nhs.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nhs.game.Screens.PlayScreen;

public abstract class Enermy extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;


    public  Enermy(PlayScreen screen,float x,float y)
    {
        this.world=screen.getWorld();
        this.screen=screen;
        setPosition(x,y);
        defineEnermy();
    }


    protected abstract void defineEnermy();

}
