package com.nhs.game.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.nhs.game.Screens.PlayScreen;
import com.nhs.game.mariobros;

import javax.swing.Box;

import static com.nhs.game.Global.global.BRICK_BIT;
import static com.nhs.game.Global.global.COINS_BIT;
import static com.nhs.game.Global.global.DEADZONE_BIT;
import static com.nhs.game.Global.global.ENERMY_BIT;
import static com.nhs.game.Global.global.ENERMY_HEAD_BIT;
import static com.nhs.game.Global.global.GROUND_BIT;
import static com.nhs.game.Global.global.ITEM_BIT;
import static com.nhs.game.Global.global.MARIO_BIT;
import static com.nhs.game.Global.global.MARIO_HEAD_BIT;
import static com.nhs.game.Global.global.NONCOLLISION_BIT;
import static com.nhs.game.Global.global.OBJECT_BIT;
import static com.nhs.game.Global.global.PPM;

public class Mario extends Sprite {
    public  enum  State{STANDDING,RUNNING,JUMPING,FALLING,GROWING,DEAD}
    public World world;
    public Body b2body;
    public State currentState;
    public  State preState;
    private TextureRegion marioStand;
    private TextureRegion marioDead;
    private TextureRegion bigMarioStand;
    private TextureRegion bigMarioJump;
    private Animation bigMarioRun;
    private Animation growMario;
    private Animation marioRun;
    private TextureRegion marioJump;
    private float stateTimer;
    private  boolean isRight;
    public  boolean hitGround;
    public boolean isBig;
    private boolean isGrow;
    private  boolean defineBigMario;
    private boolean refedinemario;
    private boolean isDead;
    public  boolean isTouchGround;

    public  Mario(PlayScreen screen)
    {
        this.world=screen.getWorld();
        defineMario();

        currentState=State.STANDDING;
        preState=State.STANDDING;
        stateTimer=0;
        isRight=true;
        isTouchGround=false;
        Array<TextureRegion> frames=new Array<TextureRegion>();
        for (int i=1;i<4;i++)
        {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"),i*16,0,16,16));
        }

        marioRun=new Animation(0.1f,frames);
        frames.clear();
        for (int i=1;i<4;i++)
        {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),i*16,0,16,32));
        }

        bigMarioRun=new Animation(0.1f,frames);
        frames.clear();

        marioDead=new TextureRegion(screen.getAtlas().findRegion("little_mario"),96,0,16,16);

        marioJump=new TextureRegion(screen.getAtlas().findRegion("little_mario"),80,0,16,16);

        bigMarioJump=new TextureRegion(screen.getAtlas().findRegion("big_mario"),80,0,16,32);


        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),240,0,16,32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),0,0,16,32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),240,0,16,32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),0,0,16,32));

        growMario=new Animation(0.2f,frames);


        marioStand=new TextureRegion(screen.getAtlas().findRegion("little_mario"),0,0,16,16);
        bigMarioStand=new TextureRegion(screen.getAtlas().findRegion("big_mario"),0,0,16,32);
        setBounds(0,0,16/PPM,14/PPM);
        setRegion(marioStand);
        hitGround=false;
    }


    public  void Update(float dt)
    {

        if (isBig)
            setPosition(this.b2body.getPosition().x-getWidth()/2,this.b2body.getPosition().y-getHeight()/2-8/PPM);
        else
            setPosition(this.b2body.getPosition().x-getWidth()/2,this.b2body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));
        if (defineBigMario) defineBigMario();
        if (refedinemario) redefineMario();
    }

    public void Grow(){
        if (isBig) return;
        isGrow=true;
        isBig=true;
        setBounds(getX(),getY(),getWidth(),getHeight()*2);
        mariobros.manager.get("audio/sounds/powerup.wav",Sound.class).play();
        defineBigMario=true;
    }



    public TextureRegion getFrame(float dt) {
        currentState=getState();

        TextureRegion reg;
        switch (currentState)
        {
            case DEAD:
                reg=marioDead;
                break;
            case GROWING:
                reg= (TextureRegion) growMario.getKeyFrame(stateTimer);
                if (growMario.isAnimationFinished(stateTimer))
                    isGrow=false;
                break;
            case JUMPING:
                reg=isBig?bigMarioJump: marioJump;
                break;
            case RUNNING:
                reg= isBig?(TextureRegion) bigMarioRun.getKeyFrame(stateTimer,true):(TextureRegion) marioRun.getKeyFrame(stateTimer,true);
                break;
            case STANDDING:
                default:
                    reg=isBig?bigMarioStand: marioStand;
                    break;
        }
        if ((b2body.getLinearVelocity().x<0 || !isRight) && !reg.isFlipX())
        {
            reg.flip(true,false);
            isRight=false;
        } else if ((b2body.getLinearVelocity().x>0 || isRight) && reg.isFlipX())
        {
         reg.flip(true,false);
            isRight=true;
        }
        stateTimer=currentState==preState?stateTimer+=dt:0;
        preState=currentState;
        return reg;
    }

    public  State getState() {

        if (isDead)
        {
            return  State.DEAD;
        }
        if (isGrow==true)
        {
            return  State.GROWING;
        }

        else if (b2body.getLinearVelocity().y > 0 ||(b2body.getLinearVelocity().y <0 && preState==State.JUMPING)) // mario đang nhảy
        {
            return State.JUMPING;

        } else if (b2body.getLinearVelocity().y < 0)
        {
            return State.FALLING;

        } else if (b2body.getLinearVelocity().x != 0)
        {
            return State.RUNNING;

        }
        else
            return   State.STANDDING;
    }

    public  void defineMario(){

        if (b2body!=null) {
            world.destroyBody(b2body);
        }
        BodyDef bdef=new BodyDef();
        bdef.position.set(32/PPM,32/PPM);
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
        fdef.filter.categoryBits=MARIO_BIT;
        fdef.filter.maskBits= GROUND_BIT | COINS_BIT |BRICK_BIT|ENERMY_BIT|OBJECT_BIT|ENERMY_HEAD_BIT|ITEM_BIT|DEADZONE_BIT;

        fdef.shape=shape;

        b2body.createFixture(fdef).setUserData(this);


        //Create sensor call head to check the collision between mario's head and brick or coins,stuffs...

        EdgeShape head=new EdgeShape();
        head.set(new Vector2(-2/PPM,6/PPM ),new Vector2(2/PPM,6/PPM ));
        fdef.filter.categoryBits = MARIO_HEAD_BIT ;

        fdef.shape=head;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData(this);


    }

    public  void defineBigMario(){

        Vector2 currentPos=b2body.getPosition();
        world.destroyBody(b2body);
        BodyDef bdef=new BodyDef();
        bdef.position.set(currentPos.add(0,10/PPM));
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
        fdef.filter.categoryBits=MARIO_BIT;
        fdef.filter.maskBits= GROUND_BIT | COINS_BIT |BRICK_BIT|ENERMY_BIT|OBJECT_BIT|ENERMY_HEAD_BIT|ITEM_BIT|DEADZONE_BIT;

        fdef.shape=shape;

        b2body.createFixture(fdef).setUserData(this);

        shape.setPosition(new Vector2(0,-15/PPM));
        b2body.createFixture(fdef).setUserData(this);
        //Create sensor call head to check the collision between mario's head and brick or coins,stuffs...

        EdgeShape head=new EdgeShape();
        head.set(new Vector2(-2/PPM,6/PPM ),new Vector2(2/PPM,6/PPM ));
        fdef.filter.categoryBits = MARIO_HEAD_BIT ;

        fdef.shape=head;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData(this);
        defineBigMario=false;
    }

    public  void hit(){
        if (isBig)
        {
            isBig=false;
            refedinemario=true;
            setBounds(getX(),getY(),getWidth(),getHeight()/2);
            mariobros.manager.get("audio/sounds/powerdown.wav",Sound.class).play();
        }else{
            mariobros.manager.get("audio/music/mario_music.ogg",Music.class).stop();
            mariobros.manager.get("audio/sounds/mariodie.wav",Sound.class).play();
            isDead=true;
            Filter filer=new Filter();
            filer.maskBits=NONCOLLISION_BIT;
            for (Fixture fixture:b2body.getFixtureList()){
                fixture.setFilterData(filer);
            }
            //đẩy mario lên một khoảng
            b2body.applyLinearImpulse(new Vector2(0,4f),b2body.getWorldCenter(),true );
        }
    }
    public  void redefineMario(){
        Vector2 pos=b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef=new BodyDef();
        bdef.position.set(pos);
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
        fdef.filter.categoryBits=MARIO_BIT;
        fdef.filter.maskBits= GROUND_BIT | COINS_BIT |BRICK_BIT|ENERMY_BIT|OBJECT_BIT|ENERMY_HEAD_BIT|ITEM_BIT|DEADZONE_BIT;

        fdef.shape=shape;

        b2body.createFixture(fdef).setUserData(this);


        //Create sensor call head to check the collision between mario's head and brick or coins,stuffs...

        EdgeShape head=new EdgeShape();
        head.set(new Vector2(-2/PPM,6/PPM ),new Vector2(2/PPM,6/PPM ));
        fdef.filter.categoryBits = MARIO_HEAD_BIT ;

        fdef.shape=head;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData(this);
        refedinemario=false;

    }

    public  void resetPlayer(){
        if (!isDead) return;
        Gdx.app.log("Dev","reset Player");
        mariobros.manager.get("audio/music/mario_music.ogg",Music.class).play();
        defineMario();
        currentState=State.STANDDING;
        preState=State.STANDDING;
        isDead=false;
        isBig=false;
        isGrow=false;

    }
    public void killPlayer(){
        if (isDead) return;
        hit();
        Gdx.app.log("Dev","Kill Player");

    }
    public  void growMarioforDev(){
        if (isDead) return;
        Grow();
    }


}
