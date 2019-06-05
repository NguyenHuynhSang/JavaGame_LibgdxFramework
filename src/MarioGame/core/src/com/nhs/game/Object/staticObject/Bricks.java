package com.nhs.game.Object.staticObject;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.nhs.game.Object.Effect.BreakingBrick;
import com.nhs.game.Object.Effect.EffectDef;
import com.nhs.game.Object.Effect.FlippingCoin;
import com.nhs.game.Object.GameObject;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Screens.PlayScreen.FirstScreen;
import com.nhs.game.Screens.PlayScreen.SecondScreen;
import com.nhs.game.Screens.ScreenManagement;
import com.nhs.game.UiManager.Hud;
import com.nhs.game.mariobros;

import static com.nhs.game.Global.global.BRICK_BIT;
import static com.nhs.game.Global.global.DISTROYED_BIT;
import static com.nhs.game.Global.global.PPM;

public class Bricks extends GameObject {
    public  boolean isBreaking;
    private  int countCoin;
    private static TiledMapTileSet tileset;
    private   int BLANK_COIN;
    public Bricks(ScreenManagement screen, MapObject object)
    {

        super(screen,object);
        //set fixture để set va chạm trường hợp này lấy hết bbox
        //get fixture to check collision in this case is get all the bbox
        fixture.setUserData(this);
        setCategoryFilter(BRICK_BIT);
        isBreaking=false;
        countCoin=10;
        if (screen instanceof FirstScreen) BLANK_COIN=117;
        else if (screen instanceof SecondScreen){
            BLANK_COIN=3042; // mò :D k biết tại sao lỗi
        }
        tileset=map.getTileSets().getTileSet("background");
    }
    @Override
    public void isHeadHit(Mario mario) {

        if(object.getProperties().containsKey("coin") &&countCoin!=0)
        {
            ((screen)).spawnEffect(new EffectDef(new Vector2(body.getPosition().x,body.getPosition().y+16/PPM),
                    FlippingCoin.class));
            Hud.UpdateScore(100);
            mariobros.manager.get("audio/sounds/coin.wav",Sound.class).play();
            countCoin--;
            return;
        } else if (countCoin==0){
            getCell().setTile(tileset.getTile(BLANK_COIN));
            return;
        }

        //Gdx.app.log("Bricks","[Collision]");
        if (mario.isBig)
        {
            (screen).spawnEffect(new EffectDef(new Vector2(body.getPosition().x,body.getPosition().y+16/PPM),
                    BreakingBrick.class));
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
