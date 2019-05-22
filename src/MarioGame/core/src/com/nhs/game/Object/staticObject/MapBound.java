package com.nhs.game.Object.staticObject;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.nhs.game.Object.GameObject;
import com.nhs.game.Screens.PlayScreen;

import static com.nhs.game.Global.global.GROUND_BIT;

public class MapBound extends GameObject {

    public MapBound(PlayScreen screen, MapObject object)
    {
        super(screen,object);
        //set fixture để set va chạm trường hợp này lấy hết bbox
        //get fixture to check collision in this case is get all the bbox
        fixture.setUserData(this);
        setCategoryFilter(GROUND_BIT);
    }

    @Override
    public void onHeadHit() {
        //Gdx.app.log("Bricks","[Collision]");
        //setCategoryFilter(DISTROYED_BIT);
        //getCell().setTile(null);
        //Hud.UpdateScore(200);
        //mariobros.manager.get("audio/sounds/breakblock.wav",Sound.class).play();
    }
}
