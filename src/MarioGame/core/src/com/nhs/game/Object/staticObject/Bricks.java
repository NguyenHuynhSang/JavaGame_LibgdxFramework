package com.nhs.game.Object.staticObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.nhs.game.Object.GameObject;
import com.nhs.game.Object.Mario;
import com.nhs.game.UiManager.Hud;
import com.nhs.game.Screens.PlayScreen;
import com.nhs.game.mariobros;

import static com.nhs.game.Global.global.BRICK_BIT;
import static com.nhs.game.Global.global.DISTROYED_BIT;

public class Bricks extends GameObject {
    public  boolean isBreaking;
    public Bricks(PlayScreen screen, MapObject object)
    {
        super(screen,object);
        //set fixture để set va chạm trường hợp này lấy hết bbox
        //get fixture to check collision in this case is get all the bbox
        fixture.setUserData(this);
        setCategoryFilter(BRICK_BIT);
        isBreaking=false;
    }
    @Override
    public void isHeadHit(Mario mario) {

        //Gdx.app.log("Bricks","[Collision]");
        if (mario.isBig)
        {

            setCategoryFilter(DISTROYED_BIT);
            getCell().setTile(null);
            Hud.UpdateScore(200);
            mariobros.manager.get("audio/sounds/breakblock.wav",Sound.class).play();
        }
        else {
            mariobros.manager.get("audio/sounds/bump.wav",Sound.class).play();
        }

    }


}
