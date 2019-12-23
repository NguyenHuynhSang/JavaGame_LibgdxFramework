package com.nhs.game.Object.Effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.nhs.game.Screens.PlayScreen.FirstScreen;
import com.nhs.game.Screens.ScreenManagement;

import static com.nhs.game.Global.global.NONCOLLISION_BIT;
import static com.nhs.game.Global.global.PPM;

public class BreakingBrick extends Effects {
// 1
    private static Animation bbrickani;
    private static Array<TextureRegion> frames;
    private  static  boolean InitBB=false;
    public BreakingBrick(ScreenManagement screen, float x, float y, float vx, float vy) {
        super(screen, x, y);

        if (!InitBB){
            frames=new Array<TextureRegion>();
            frames.add(new TextureRegion(screen.getAtlas().findRegion("particle_brick"), 0, 0, 5, 5));
            bbrickani=new Animation(0.1f,frames);
            InitBB=true;
            Gdx.app.log("Breaking  Brick","Init Frame ");
        }
        setRegion( (TextureRegion)bbrickani.getKeyFrame(0));
        velocity=new Vector2(vx,vy);
        body.setLinearVelocity(velocity);
    }

    @Override
    protected void defineEffect() {
        setBounds(getX(),getY(),5/PPM,5/PPM);
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
        fdef.filter.categoryBits=NONCOLLISION_BIT;
        fdef.filter.maskBits= NONCOLLISION_BIT;
        fdef.shape=shape;
        body.createFixture(fdef).setUserData(this);
        shape.dispose();

    }
    @Override
    public void update(float dt) {
        super.update(dt);
        if (isDestroyed){
            setRegion( (TextureRegion)bbrickani.getKeyFrame(stateTimer,true));
            return;
        }
        if (stateTimer>0.45f) {
            setDestroy=true;
        }
        stateTimer+=dt;
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        setRegion( (TextureRegion)bbrickani.getKeyFrame(stateTimer,true));

    }
}
