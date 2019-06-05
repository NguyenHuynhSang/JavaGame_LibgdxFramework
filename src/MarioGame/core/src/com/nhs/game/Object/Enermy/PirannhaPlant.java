package com.nhs.game.Object.Enermy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Screens.ScreenManagement;
import com.nhs.game.mariobros;


import static com.nhs.game.Global.global.ENERMY_BIT;
import static com.nhs.game.Global.global.FIREBALL_BIT;
import static com.nhs.game.Global.global.MARIO_BIT;
import static com.nhs.game.Global.global.NONCOLLISION_BIT;
import static com.nhs.game.Global.global.PPM;
import static com.nhs.game.UiManager.Hud.UpdateScore;

public class PirannhaPlant extends  Enermy {
    private Animation growani;
    private boolean setDestroy;
    private float stateTime;
    private com.badlogic.gdx.utils.Array<TextureRegion> frames;
    public PirannhaPlant(ScreenManagement screen, float x, float y) {
        super(screen, x, y);
        frames=new Array<TextureRegion>();
        for (int i=0;i<2;i++)
        {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("eatflower"),i*25,0,25,32));
        }

        growani=new Animation(0.4f,frames);
        stateTime=0;
        setBounds(x,y,25/PPM,32/PPM);
        setDestroy=false;
        eDestroyed=false;
    }

    @Override
    protected void defineEnermy() {
        BodyDef bdef=new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);
        FixtureDef fdef=new FixtureDef();

        PolygonShape shape=new PolygonShape();
        shape.setAsBox(25/2/PPM,32/2/PPM);
        // mỗi fixture có category và mask riêng
        // category để nhận biết đó là object nào
        // mask là các object và object đang xét có thể va chạm
        fdef.filter.categoryBits=ENERMY_BIT;
        fdef.filter.maskBits= MARIO_BIT|FIREBALL_BIT;
        fdef.shape=shape;


        b2body.createFixture(fdef).setUserData(this);
        b2body.setGravityScale(0);
        velocity=new Vector2(0,0.4f);
        b2body.setLinearVelocity(velocity);
        b2body.setTransform(b2body.getPosition().x+10/PPM,b2body.getPosition().y+10/PPM,b2body.getAngle());

    }

    @Override
    public void update(float dt) {
        stateTime+=dt;

        if (setDestroy && !eDestroyed)
        {
            world.destroyBody(b2body);
            eDestroyed=true;
            stateTime=0;
        } else if (!eDestroyed)
        {
            if (stateTime>1){

                stateTime=0;

                velocity=new Vector2(0,-b2body.getLinearVelocity().y);
                b2body.setLinearVelocity(velocity);
            }
            else{

                velocity=new Vector2(0,b2body.getLinearVelocity().y);
                b2body.setLinearVelocity(velocity);
            }


            setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
            setRegion( (TextureRegion)growani.getKeyFrame(stateTime,true));

        }
    }


    public  void draw(Batch batch){
        if (!eDestroyed ||stateTime<1)
        {
            super.draw(batch);

        }
        else
            eDestroyed=true;

    }

    @Override
    public void hitOnHead(Mario mario) {
        return;
    }

    @Override
    public void onEnermyHit(Enermy enermy) {

    }

    @Override
    public void killEnermy() {
        setDestroy=true;
        UpdateScore(100);
        mariobros.manager.get("audio/sounds/stomp.wav",Sound.class).play();
    }
}
