package com.nhs.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.nhs.game.Screens.PlayScreen;


import static com.nhs.game.Global.global.BRICK_BIT;
import static com.nhs.game.Global.global.COINS_BIT;
import static com.nhs.game.Global.global.GROUND_BIT;
import static com.nhs.game.Global.global.ENERMY_BIT;
import static com.nhs.game.Global.global.MARIO_BIT;
import static com.nhs.game.Global.global.OBJECT_BIT;
import static com.nhs.game.Global.global.PPM;

public class Goomba extends  Enermy
{

    private float stateTime;
    private Animation wallAnimation;
    private com.badlogic.gdx.utils.Array<TextureRegion> frames;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        frames=new Array<TextureRegion>();
        for (int i=0;i<2;i++)
        {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"),i*16,0,16,16));
        }

        wallAnimation=new Animation(0.4f,frames);
        stateTime=0;
        setBounds(getX(),getY(),16/PPM,16/PPM);
    }

    public void Update(float dt)
    {
        stateTime+=dt;
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);

        setRegion( (TextureRegion)wallAnimation.getKeyFrame(stateTime,true));


    }

    @Override
    protected void defineEnermy() {
        BodyDef bdef=new BodyDef();
        bdef.position.set(100/PPM,32/PPM);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);
        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        //PolygonShape shape=new PolygonShape();
        //shape.setAsBox(16/2/PPM,16/2/PPM);
        shape.setRadius(6/PPM);

        // mỗi fixture có category và mask riêng
        // category để nhận biết đó là object nào
        // mask là các object và object đang xét có thể va chạm
        fdef.filter.categoryBits=ENERMY_BIT;
        fdef.filter.maskBits= GROUND_BIT |MARIO_BIT| COINS_BIT |BRICK_BIT|OBJECT_BIT;

        fdef.shape=shape;

        b2body.createFixture(fdef);

    }
}
