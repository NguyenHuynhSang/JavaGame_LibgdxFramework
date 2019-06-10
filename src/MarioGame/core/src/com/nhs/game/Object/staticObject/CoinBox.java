package com.nhs.game.Object.staticObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.nhs.game.Object.Effect.EffectDef;
import com.nhs.game.Object.Effect.FlippingCoin;
import com.nhs.game.Object.Effect.ScoreText;
import com.nhs.game.Object.GameObject;
import com.nhs.game.Object.Items.Flower;
import com.nhs.game.Object.Items.ItemDef;
import com.nhs.game.Object.Items.Mushroom;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Screens.PlayScreen.SecondScreen;
import com.nhs.game.Screens.ScreenManagement;
import com.nhs.game.UiManager.Hud;
import com.nhs.game.Screens.PlayScreen.FirstScreen;
import com.nhs.game.mariobros;

import static com.nhs.game.Global.global.COINS_BIT;
import static com.nhs.game.Global.global.PPM;


public class CoinBox extends GameObject {
    private   static TiledMapTileSet tileset;
    private   int BLANK_COIN=3042; //dont know why the ID equal 3042 not 117 , sometime it crash  the game
    public CoinBox(ScreenManagement screen, MapObject object)
    {
     super(screen, object);
        tileset=map.getTileSets().getTileSet("background");
     if (screen instanceof FirstScreen) BLANK_COIN=117;
     else if (screen instanceof SecondScreen){
         BLANK_COIN=3042; // mò :D k biết tại sao lỗi
     }

        fixture.setUserData(this);
        setCategoryFilter(COINS_BIT);
    }

    @Override
    public void isHeadHit(Mario mario) {

      //  Gdx.app.log("Cell","ID"+(int)getCell().getTile().getId());
        if (tileset==null) return;
        if (getCell()==null) return;
        if (getCell().getTile()==null) return;
        if (getCell().getTile().getId()==BLANK_COIN){
            mariobros.manager.get("audio/sounds/bump.wav",Sound.class).play();
            return;
        }
        else
        {
            if (object.getProperties().containsKey("mushroom"))
            {
                (screen).spawnItem(new ItemDef(new Vector2(body.getPosition().x,body.getPosition().y+16/PPM),
                        Mushroom.class));
                Hud.UpdateScore(100);
                mariobros.manager.get("audio/sounds/pwspawn.wav",Sound.class).play();
            }
            else if(object.getProperties().containsKey("flower"))
            {
                (screen).spawnItem(new ItemDef(new Vector2(body.getPosition().x,body.getPosition().y+16/PPM),
                        Flower.class));
                Hud.UpdateScore(500);
                mariobros.manager.get("audio/sounds/pwspawn.wav",Sound.class).play();
            }
            else if(object.getProperties().containsKey("coin"))
            {
                (screen).spawnEffect(new EffectDef(new Vector2(body.getPosition().x,body.getPosition().y+16/PPM),
                        FlippingCoin.class));
          Gdx.app.log("Brick spawn eff","pos X="+body.getPosition().x+":posY="+body.getPosition().y);
                (screen).spawnEffect(new EffectDef(new Vector2(body.getPosition().x,body.getPosition().y+16/PPM),
                        ScoreText.class));

                Hud.UpdateScore(500);
                mariobros.manager.get("audio/sounds/coin.wav",Sound.class).play();
            }
            else
                mariobros.manager.get("audio/sounds/coin.wav",Sound.class).play();
        }
          getCell().setTile(tileset.getTile(BLANK_COIN));

    }

    @Override
    public void isNextScene(Mario mario) {

    }


}
