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
import com.nhs.game.Object.Enermy.Enermy;
import com.nhs.game.Object.Mario;
import com.nhs.game.Screens.PlayScreen;
import com.nhs.game.mariobros;


import sun.security.krb5.internal.crypto.Des;

import static com.nhs.game.Global.global.BRICK_BIT;
import static com.nhs.game.Global.global.COINS_BIT;
import static com.nhs.game.Global.global.ENERMY_HEAD_BIT;
import static com.nhs.game.Global.global.GROUND_BIT;
import static com.nhs.game.Global.global.ENERMY_BIT;
import static com.nhs.game.Global.global.MARIO_BIT;
import static com.nhs.game.Global.global.OBJECT_BIT;
import static com.nhs.game.Global.global.PPM;
import static com.nhs.game.UiManager.Hud.UpdateScore;

public class Goomba extends Enermy
{

    private float stateTime;
    private Animation wallAnimation;
    private com.badlogic.gdx.utils.Array<TextureRegion> frames;
    private boolean setDestroy;
    private boolean Destroyed;
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
        setDestroy=false;
        Destroyed=false;


    }

    public void update(float dt)
    {
        stateTime+=dt;
        if (setDestroy && !Destroyed)
        {
            world.destroyBody(b2body);
            Destroyed=true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"),32,0,16,16));
            stateTime=0;

        } else if (!Destroyed)
        {


            setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);

            setRegion( (TextureRegion)wallAnimation.getKeyFrame(stateTime,true));

            velocity.y=b2body.getLinearVelocity().y;
            b2body.setLinearVelocity(velocity);

        }




    }

    @Override
    protected void defineEnermy() {
        BodyDef bdef=new BodyDef();
        bdef.position.set(getX(),getY());
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
        fdef.filter.maskBits= GROUND_BIT |MARIO_BIT| COINS_BIT |BRICK_BIT|OBJECT_BIT|ENERMY_BIT;

        fdef.shape=shape;

        b2body.createFixture(fdef).setUserData(this);

        //create the head here

        PolygonShape head=new PolygonShape();
        Vector2[] vertice=new Vector2[4];
        vertice[0]=new Vector2(-5,8).scl(1/PPM);
        vertice[1]=new Vector2(5,8).scl(1/PPM);
        vertice[2]=new Vector2(-3,3).scl(1/PPM);
        vertice[3]=new Vector2(3,3).scl(1/PPM);
        head.set(vertice);

        fdef.shape=head;
        fdef.restitution=0.5f; //mario bi day la i mot chut khi nhay len dau goomba
        fdef.filter.categoryBits=ENERMY_HEAD_BIT;

        //set mask de goi lai trong collision
        b2body.createFixture(fdef).setUserData(this);








    }

    public  void draw(Batch batch){
        if (!Destroyed ||stateTime<1)
            super.draw(batch);

    }

    @Override
    public void hitOnHead(Mario mario) {
        //xóa b2body của goomba để k xét va chạm nữa
        setDestroy=true;
        UpdateScore(100);
        mariobros.manager.get("audio/sounds/stomp.wav",Sound.class).play();
    }

    @Override
    public void onEnermyHit(Enermy enermy) {
        Gdx.app.log("Coll","Turtle with goomba");
        if(enermy instanceof  Turtle &&((Turtle)enermy).currentState==Turtle.State.MOVING_SHELL)
        {
            Gdx.app.log("Coll","Turtle with goomba");
            setDestroy=true;
            mariobros.manager.get("audio/sounds/stomp.wav",Sound.class).play();
        }
    }
}
