package com.nhs.game.Object.staticObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.nhs.game.Object.GameObject;
import com.nhs.game.Screens.PlayScreen;
import com.nhs.game.UiManager.Hud;
import com.nhs.game.mariobros;

import static com.nhs.game.Global.global.BRICK_BIT;
import static com.nhs.game.Global.global.DISTROYED_BIT;
import static com.nhs.game.Global.global.GROUND_BIT;

public class Ground extends GameObject {
    public Ground(PlayScreen screen, MapObject object)
    {
        super(screen,object);
        //set fixture để set va chạm trường hợp này lấy hết bbox
        //get fixture to check collision in this case is get all the bbox
        fixture.setUserData(this);
        setCategoryFilter(GROUND_BIT);
    }

    @Override
    public void onHeadHit() {

    }
}
