package com.nhs.game.Object.staticObject;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.nhs.game.Object.GameObject;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Screens.ScreenManagement;

import static com.nhs.game.Global.global.DISTROYED_BIT;
import static com.nhs.game.Global.global.PIPE_BIT;

public class Pipes extends GameObject {
    private static TiledMapTileSet tileset;
    public Pipes(ScreenManagement screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(PIPE_BIT);
        tileset=map.getTileSets().getTileSet("background");
    }

    @Override
    public void isNextScene(Mario mario) {
        if(object.getProperties().containsKey("next"))
        {
            setCategoryFilter(DISTROYED_BIT);
            mario.isNextScene=true;
        }
    }

    @Override
    public void isHeadHit(Mario mario) {

    }
}
