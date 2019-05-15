package com.nhs.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;

import com.badlogic.gdx.physics.box2d.World;


public class Coins extends  InteractiveTileObject {
    public  Coins(World world, TiledMap map, com.badlogic.gdx.math.Rectangle bbox)
    {
     super(world, map, bbox);

    }


}
