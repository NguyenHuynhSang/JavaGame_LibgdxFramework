package com.nhs.game.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.nhs.game.Object.Items.ItemDef;
import com.nhs.game.Object.Items.Mushroom;
import com.nhs.game.UiManager.Hud;
import com.nhs.game.Screens.PlayScreen;
import com.nhs.game.mariobros;

import static com.nhs.game.Global.global.COINS_BIT;
import static com.nhs.game.Global.global.PPM;


public class Coins extends GameObject {
    private static TiledMapTileSet tileset;
    private  final int BLANK_COIN=94;


    public  Coins(PlayScreen screen, MapObject object)
    {
     super(screen, object);
        tileset=map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(COINS_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin","[Collision]");
        if (getCell().getTile().getId()==BLANK_COIN)
            mariobros.manager.get("audio/sounds/bump.wav",Sound.class).play();
        else
        {
            if (object.getProperties().containsKey("mushroom"))
            {
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x,body.getPosition().y+16/PPM),
                        Mushroom.class));
                Hud.UpdateScore(100);
                mariobros.manager.get("audio/sounds/pwspawn.wav",Sound.class).play();
            }
            else


            mariobros.manager.get("audio/sounds/coin.wav",Sound.class).play();
        }
        getCell().setTile(tileset.getTile(BLANK_COIN));


    }


}
