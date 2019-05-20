package com.nhs.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.nhs.game.Screens.PlayScreen;

import static com.nhs.game.Global.global.BRICK_BIT;
import static com.nhs.game.Global.global.COINS_BIT;
import static com.nhs.game.Global.global.ENERMY_BIT;
import static com.nhs.game.Global.global.GROUND_BIT;
import static com.nhs.game.Global.global.MARIO_BIT;
import static com.nhs.game.Global.global.OBJECT_BIT;
import static com.nhs.game.Global.global.PPM;

public class Mario extends Sprite {
    public  enum  State{STANDDING,RUNNING,JUMPING,FALLING}
    public World world;
    public Body b2body;
    public State currentState;
    public  State preState;
    private TextureRegion marioStand;
    private Animation marioRun;
    private Animation marioJump;
    private float stateTimer;
    private  boolean isRight;
    public  Mario(PlayScreen screen)
    {
        super(screen.getAtlas().findRegion("little_mario"));
        this.world=screen.getWorld();
        defineMario();

        currentState=State.STANDDING;
        preState=State.STANDDING;
        stateTimer=0;
        isRight=true;

        Array<TextureRegion> frames=new Array<TextureRegion>();
        for (int i=1;i<4;i++)
        {
            frames.add(new TextureRegion(getTexture(),i*16,10,16,16));
        }

        marioRun=new Animation(0.1f,frames);
        frames.clear();


        for (int i=4;i<6;i++)
        {
            frames.add(new TextureRegion(getTexture(),i*16,10,16,16));
        }
        marioJump=new Animation(0.1f,frames);


        marioStand=new TextureRegion(getTexture(),0,10,16,16);
        setBounds(0,0,16/PPM,14/PPM);
        setRegion(marioStand);

    }


    public  void Update(float dt)
    {
        setPosition(this.b2body.getPosition().x-getWidth()/2,this.b2body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));
    }


    public TextureRegion getFrame(float dt) {
        currentState=getState();

        TextureRegion reg;
        switch (currentState)
        {
            case JUMPING:
                reg= (TextureRegion) marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                reg= (TextureRegion) marioRun.getKeyFrame(stateTimer,true);
                break;
            case STANDDING:
                default:
                    reg=marioStand;
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
        if (b2body.getLinearVelocity().y > 0 ||(b2body.getLinearVelocity().y <0 && preState==State.JUMPING)) // mario đang nhảy
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
        fdef.filter.maskBits= GROUND_BIT | COINS_BIT |BRICK_BIT|ENERMY_BIT|OBJECT_BIT;

        fdef.shape=shape;

        b2body.createFixture(fdef);


        //Create sensor call head to check the collision between mario's head and brick or coins,stuffs...

        EdgeShape head=new EdgeShape();
        head.set(new Vector2(-2/PPM,6/PPM ),new Vector2(2/PPM,6/PPM ));
        fdef.shape=head;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData("head");


    }

}
