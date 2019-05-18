package com.nhs.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;

import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nhs.game.Scenes.Hud;

import static com.nhs.game.Global.global.COINS_BIT;


public class Coins extends  InteractiveTileObject {
    private static TiledMapTileSet tileset;
    private  final int BLANK_COIN=94;


    public  Coins(World world, TiledMap map, com.badlogic.gdx.math.Rectangle bbox)
    {
     super(world, map, bbox);
        tileset=map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(COINS_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin","[Collision]");
        getCell().setTile(tileset.getTile(BLANK_COIN));
        Hud.UpdateScore(100);
    }


}
