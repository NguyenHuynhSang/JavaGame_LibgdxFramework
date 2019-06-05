package com.nhs.game.Object.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Screens.PlayScreen.FirstScreen;
import com.nhs.game.Screens.ScreenManagement;
import com.nhs.game.mariobros;

import static com.nhs.game.Global.global.COINS_BIT;
import static com.nhs.game.Global.global.ITEM_BIT;
import static com.nhs.game.Global.global.MARIO_BIT;
import static com.nhs.game.Global.global.PPM;

public class Flower extends  Item {

    private com.badlogic.gdx.utils.Array<TextureRegion> frames;
    private Animation flowerAni;
    public Flower(ScreenManagement screen, float x, float y) {
        super(screen, x, y);
        frames=new Array<TextureRegion>();
        for (int i=0;i<4;i++)
        {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("flower"),i*16,0,16,16));
        }

        flowerAni=new Animation(0.4f,frames);

    }

    @Override
    public void defineItem() {
        BodyDef bdef=new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type=BodyDef.BodyType.DynamicBody;
        body=world.createBody(bdef);
        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        //PolygonShape shape=new PolygonShape();
        //shape.setAsBox(16/2/PPM,16/2/PPM);
        shape.setRadius(6/PPM);
        // mỗi fixture có category và mask riêng
        // category để nhận biết đó là object nào
        // mask là các object và object đang xét có thể va chạm
        fdef.filter.categoryBits=ITEM_BIT;
        fdef.filter.maskBits= MARIO_BIT| COINS_BIT;
        fdef.shape=shape;
        body.createFixture(fdef).setUserData(this);

    }


    @Override
    public void useItem(Mario mario) {
        destroy();
        if (!mario.isBig)
        {
            mario.Grow();
        }
        else
            mariobros.manager.get("audio/sounds/lifeup.wav",Sound.class).play();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        stateTimer+=dt;
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        setRegion( (TextureRegion)flowerAni.getKeyFrame(stateTimer,true));

    }
}
