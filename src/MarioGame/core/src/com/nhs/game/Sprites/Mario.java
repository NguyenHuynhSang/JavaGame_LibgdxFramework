package com.nhs.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.nhs.game.Screens.Global.global.PPM;

public class Mario extends Sprite {
    public World world;
    public Body b2body;


    public  Mario(World world)
    {
        this.world=world;
        defineMario();
    }


    public  void defineMario(){
        BodyDef bdef=new BodyDef();
        bdef.position.set(100/PPM,20/PPM);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);
        FixtureDef fdef=new FixtureDef();
        CircleShape share=new CircleShape();
        share.setRadius(5/PPM);

        fdef.shape=share;

        b2body.createFixture(fdef);


    }

}
