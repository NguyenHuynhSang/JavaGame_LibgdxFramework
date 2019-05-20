package com.nhs.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.nhs.game.Scenes.Hud;
import com.nhs.game.Screens.PlayScreen;
import com.nhs.game.mariobros;

import static com.nhs.game.Global.global.BRICK_BIT;
import static com.nhs.game.Global.global.DISTROYED_BIT;

public class Bricks extends  InteractiveTileObject {

    public Bricks(PlayScreen screen, Rectangle bbox)
    {
        super(screen,bbox);
        //set fixture để set va chạm trường hợp này lấy hết bbox
        //get fixture to check collision in this case is get all the bbox
        fixture.setUserData(this);
        setCategoryFilter(BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Bricks","[Collision]");
        setCategoryFilter(DISTROYED_BIT);
        getCell().setTile(null);
        Hud.UpdateScore(200);
        mariobros.manager.get("audio/sounds/breakblock.wav",Sound.class).play();
    }
}
