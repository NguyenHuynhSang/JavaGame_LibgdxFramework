package com.nhs.game.Object.Player.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.nhs.game.Screens.ScreenManagement;

import static com.nhs.game.Global.global.BRICK_BIT;
import static com.nhs.game.Global.global.COINS_BIT;
import static com.nhs.game.Global.global.ENERMY_BIT;
import static com.nhs.game.Global.global.FIREBALL_BIT;
import static com.nhs.game.Global.global.GROUND_BIT;
import static com.nhs.game.Global.global.PIPE_BIT;
import static com.nhs.game.Global.global.PPM;

public class FireBall extends Sprite {
    ScreenManagement screen;
    World world;
    Array<TextureRegion> frames;
    Animation fireAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;
    Body b2body;
    public  boolean isDestroyed;
        public FireBall(ScreenManagement screen, float x, float y, boolean fireRight){
        this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();
        frames = new Array<TextureRegion>();
        for (int i=0;i<4;i++)
        frames.add(new TextureRegion(screen.getAtlas().findRegion("fireball"), i*8, 0, 8, 8));
        fireAnimation = new Animation(0.2f, frames);
        setRegion((TextureRegion) fireAnimation.getKeyFrame(0));
        setBounds(x, y, 8/ PPM, 8 / PPM);
        defineFireBall();
        isDestroyed=false;
    }
    public void defineFireBall(){
        b2body=null;
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 12 /PPM : getX() - 12 /PPM, getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.bullet=true;
        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / PPM);
        fdef.filter.categoryBits = FIREBALL_BIT;
        fdef.filter.maskBits = GROUND_BIT |
                COINS_BIT|
                BRICK_BIT |
                ENERMY_BIT |
                PIPE_BIT;

        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        shape.dispose();
        b2body.setLinearVelocity(new Vector2(fireRight ? 2 : -2, 2.5f));
    }
    public void update(float dt){
        if (destroyed) return;
        stateTime += dt;
        setRegion((TextureRegion) fireAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((stateTime > 3 || setToDestroy) && !destroyed) {
            b2body.setUserData(null);
            world.destroyBody(b2body);
            b2body=null;
            destroyed = true;
            isDestroyed=true;
            return;
        }
        if(b2body.getLinearVelocity().y > 2f)
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);
        if((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0))
            setToDestroy();
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public  void dispose(){

    }

}
