package com.nhs.game.Object.Effect;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.nhs.game.Object.GameObject;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Screens.PlayScreen;

public abstract class effects extends Sprite {



    protected TextureAtlas textureAtlas;

    public effects(PlayScreen screen, MapObject object) {

        this.textureAtlas=screen.getAtlas();
    }

}
