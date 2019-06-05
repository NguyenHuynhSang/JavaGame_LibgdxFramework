package com.nhs.game.Object.Effect;

import com.badlogic.gdx.math.Vector2;

public class EffectDef {
    public Vector2 position;
    public Class<?> type;

    public EffectDef(Vector2 position, Class<?> type) {
        this.position = position;
        this.type = type;
    }
}
