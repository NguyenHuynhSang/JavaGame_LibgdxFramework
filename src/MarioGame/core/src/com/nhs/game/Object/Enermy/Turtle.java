package com.nhs.game.Object.Enermy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.nhs.game.Object.Effect.EffectDef;
import com.nhs.game.Object.Effect.ScoreText;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Screens.ScreenManagement;

import static com.nhs.game.Global.global.BRICK_BIT;
import static com.nhs.game.Global.global.COINS_BIT;
import static com.nhs.game.Global.global.ENERMY_BIT;
import static com.nhs.game.Global.global.ENERMY_HEAD_BIT;
import static com.nhs.game.Global.global.FIREBALL_BIT;
import static com.nhs.game.Global.global.GROUND_BIT;
import static com.nhs.game.Global.global.MARIO_BIT;
import static com.nhs.game.Global.global.NONCOLLISION_BIT;
import static com.nhs.game.Global.global.PIPE_BIT;
import static com.nhs.game.Global.global.PPM;

public class Turtle extends  Enermy {
    public  static final int KICK_LEFT_SPEED=-2;
    public  static final int KICK_RIGHT_SPEED=2;
    public  enum State {WALKING,SHELL,MOVING_SHELL,DEAD};
    public  State currentState;
    public  State preState;
    private float stateTime;
    private  TextureRegion shell;
    private Animation wallAnimation;
    private  float deadRotation;
    private com.badlogic.gdx.utils.Array<TextureRegion> frames;
    private boolean Destroyed;
    public Turtle(ScreenManagement screen, float x, float y) {

        super(screen, x, y);
        frames=new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"),0,0,16,27));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"),16,0,16,27));
        shell=new TextureRegion(screen.getAtlas().findRegion("turtle"),64,0,16,27);
        wallAnimation=new Animation(0.2f,frames);
        currentState=preState=State.WALKING;
        setBounds(getX(),getY(),16/PPM,32/PPM);


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
        fdef.filter.maskBits= GROUND_BIT |MARIO_BIT| COINS_BIT |BRICK_BIT| PIPE_BIT |ENERMY_BIT|FIREBALL_BIT;

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
        fdef.restitution=1.5f;    //mario bi day la i mot chut khi nhay len dau goomba
        fdef.filter.categoryBits=ENERMY_HEAD_BIT;

        //set mask de goi lai trong collision
        b2body.createFixture(fdef).setUserData(this);
        shape.dispose();
        deadRotation=0;


    }

    public  TextureRegion getFrame(float dt){
        TextureRegion region;
        switch (currentState)
        {
            case MOVING_SHELL:
            case SHELL:
                region=shell;
                break;
            case WALKING:
                default:
                    region=(TextureRegion)wallAnimation.getKeyFrame(stateTime,true);
                    break;

        }
        if (velocity.x>0 && region.isFlipX()==false){
            region.flip(true,false);
        }
        if (velocity.x<0 && region.isFlipX()==true){
            region.flip(true,false);
        }
        stateTime=currentState==preState?stateTime+=dt:0;
        preState=currentState;
        return region;
    }


    @Override
    public void update(float dt) {
        if (eDestroyed) return;

        else {
            setRegion(getFrame(dt));
            if (currentState==State.SHELL && stateTime>5){
                currentState=State.WALKING;
                velocity.x=1;
            }
            setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-8/PPM);
            if (currentState==State.DEAD)
            {
                deadRotation+=2;
                rotate(deadRotation);
                if (stateTime>5 && !Destroyed){
                    b2body.setUserData(null);
                    world.destroyBody(b2body);
                    b2body=null;
                    Destroyed=true;
                    eDestroyed=true;
                    return;
                }
            } else
                b2body.setLinearVelocity(velocity);
        }

    }

    @Override
    public void hitOnHead(Mario mario) {
        if (currentState!=State.SHELL)
        {
            currentState=State.SHELL;
            velocity.x =0;
        }else{
            wasKicked(mario.getX()<=this.getX()?KICK_RIGHT_SPEED:KICK_LEFT_SPEED);

        }

    }

    @Override
    public void onEnermyHit(Enermy enermy) {
        if (enermy instanceof Turtle){
            if (((Turtle)enermy).currentState==State.MOVING_SHELL && currentState!=State.MOVING_SHELL)
                killed();
            else if (currentState==State.MOVING_SHELL&&((Turtle)enermy).currentState==State.WALKING) return;
            else
                reverseVelocity(true,false);
        } else if(currentState!=State.MOVING_SHELL){
            reverseVelocity(true,false);
        }


    }

    @Override
    public void killEnermy() {
        killed();
    }

    public void wasKicked(int speed){
        velocity.x=speed;
        currentState=State.MOVING_SHELL;
    }
    public State getCurrentState(){return  currentState;}

    public  void draw(Batch batch){
      if (!Destroyed) super.draw(batch );
    }


    public void killed(){
        (screen).spawnEffect(new EffectDef(new Vector2(b2body.getPosition().x,b2body.getPosition().y+16/PPM),
                ScoreText.class));
        currentState=State.DEAD;
        Filter filter=new Filter();
        filter.maskBits=NONCOLLISION_BIT;
        for (Fixture fixture:b2body.getFixtureList())
            fixture.setFilterData(filter);

        b2body.applyLinearImpulse(new Vector2(0,5f),b2body.getWorldCenter(),true);

    }
}
