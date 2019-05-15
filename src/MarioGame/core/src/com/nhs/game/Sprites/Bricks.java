package com.nhs.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Bricks extends  InteractiveTileObject {

    public Bricks(World world, TiledMap  map, Rectangle bbox){
        super(world,map,bbox);
    }
}
