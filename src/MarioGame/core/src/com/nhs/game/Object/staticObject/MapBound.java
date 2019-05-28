package com.nhs.game.Object.staticObject;

import com.badlogic.gdx.maps.MapObject;
import com.nhs.game.Object.GameObject;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Screens.PlayScreen;

import static com.nhs.game.Global.global.MAPZONE_BIT;

public class MapBound extends GameObject {
    @Override
    public void isHeadHit(Mario mario) {
        return;
    }

    public MapBound(PlayScreen screen, MapObject object)
    {
        super(screen,object);
        //set fixture để set va chạm trường hợp này lấy hết bbox
        //get fixture to check collision in this case is get all the bbox
        fixture.setUserData(this);
        setCategoryFilter(MAPZONE_BIT);
    }


}
