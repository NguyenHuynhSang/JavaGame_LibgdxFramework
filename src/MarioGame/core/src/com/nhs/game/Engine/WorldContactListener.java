

///================================
// Lớp quản lý va chạm
// được gọi khi 2 vật thể trong world game va chạm nhau
// quăng log ra cho dev biết là có va chạm
//=================================



package com.nhs.game.Engine;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.nhs.game.Object.Enermy.Enermy;
import com.nhs.game.Object.GameObject;
import com.nhs.game.Object.Items.Item;
import com.nhs.game.Object.Player.weapon.FireBall;
import com.nhs.game.Object.Player.Mario;

import static com.nhs.game.Global.global.BRICK_BIT;
import static com.nhs.game.Global.global.COINS_BIT;
import static com.nhs.game.Global.global.DEADZONE_BIT;
import static com.nhs.game.Global.global.ENERMY_BIT;
import static com.nhs.game.Global.global.ENERMY_HEAD_BIT;
import static com.nhs.game.Global.global.FIREBALL_BIT;
import static com.nhs.game.Global.global.ITEM_BIT;
import static com.nhs.game.Global.global.MARIO_BIT;
import static com.nhs.game.Global.global.MARIO_HEAD_BIT;
import static com.nhs.game.Global.global.PIPE_BIT;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
      // Gdx.app.log("Begin Contact","[Complete]");

        Fixture fixtureA=contact.getFixtureA();
        Fixture fixtureB=contact.getFixtureB();

        int cDef=fixtureA.getFilterData().categoryBits|fixtureB.getFilterData().categoryBits;
        //if (fixtureA.getUserData()=="head" || fixtureB.getUserData()=="head" )
        //{
        //    Fixture head=fixtureA.getUserData()=="head"?fixtureA:fixtureB;
        //    Fixture object=head==fixtureA?fixtureB:fixtureA;
        //
        //    if (object.getUserData()!=null && GameObject.class.isAssignableFrom(object.getUserData().getClass()))
        //    {
        //       ((GameObject) object.getUserData()).onHeadHit();
        //
        //    }
        //}
        switch (cDef){
            case MARIO_HEAD_BIT|BRICK_BIT:
            case MARIO_HEAD_BIT|COINS_BIT:
                if (fixtureA.getFilterData().categoryBits==MARIO_HEAD_BIT)
                {
                    ((GameObject)fixtureB.getUserData()).isHeadHit((Mario) fixtureA.getUserData());
                }
                else
                {
                  ((GameObject)fixtureA.getUserData()).isHeadHit((Mario)fixtureB.getUserData());
                }
                break;
            case MARIO_BIT|PIPE_BIT:{
                if (fixtureA.getFilterData().categoryBits==MARIO_BIT)
                {
                    ((GameObject)fixtureB.getUserData()).isNextScene((Mario) fixtureA.getUserData());
                }
                else
                {
                    ((GameObject)fixtureA.getUserData()).isNextScene((Mario)fixtureB.getUserData());
                }
                break;
            }
            case ENERMY_HEAD_BIT| MARIO_BIT:
                if (fixtureA.getFilterData().categoryBits==ENERMY_HEAD_BIT)
                    ((Enermy)fixtureA.getUserData()).hitOnHead((Mario)fixtureB.getUserData());
                else
                    ((Enermy)fixtureB.getUserData()).hitOnHead((Mario)fixtureA.getUserData());
                break;
            case ENERMY_BIT| PIPE_BIT:
                if (fixtureA.getFilterData().categoryBits==ENERMY_BIT)
                    ((Enermy)fixtureA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enermy)fixtureB.getUserData()).reverseVelocity(true,false);
                break;
            //fcase FIREBALL_BIT|PIPE_BIT:
            case FIREBALL_BIT | ENERMY_BIT:
                if(fixtureA.getFilterData().categoryBits == FIREBALL_BIT)
                {
                    ((FireBall)fixtureA.getUserData()).setToDestroy();
                    ((Enermy)fixtureB.getUserData()).killEnermy();
                }

                else
                {
                    ((FireBall)fixtureB.getUserData()).setToDestroy();
                    ((Enermy)fixtureA.getUserData()).killEnermy();
                }

                break;

        case ENERMY_BIT|BRICK_BIT:
                if (fixtureA.getFilterData().categoryBits==ENERMY_BIT)
                    ((Enermy)fixtureA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enermy)fixtureB.getUserData()).reverseVelocity(true,false);
                break;
            case ENERMY_BIT|ENERMY_BIT:
                ((Enermy)fixtureA.getUserData()).onEnermyHit(((Enermy)fixtureB.getUserData()));
                ((Enermy)fixtureB.getUserData()).onEnermyHit(((Enermy)fixtureA.getUserData()));
                break;
            case MARIO_BIT|DEADZONE_BIT:
            {
                if (fixtureA.getFilterData().categoryBits==MARIO_BIT)
                {
                    ((Mario) fixtureA.getUserData()).MarioDead();
                }
                else
                {
                    ((Mario) fixtureB.getUserData()).MarioDead();
                }
                break;

            }
            case MARIO_BIT|ENERMY_BIT:
            {
                if (fixtureA.getFilterData().categoryBits==MARIO_BIT)
                {
                    ((Mario) fixtureA.getUserData()).hit((Enermy)fixtureB.getUserData());
                }
                else
                {
                   ((Mario)fixtureB.getUserData()).hit((Enermy)fixtureA.getUserData());
                }
                break;
            }
            case ITEM_BIT| PIPE_BIT:
                if (fixtureA.getFilterData().categoryBits==ITEM_BIT)
                    ((Item)fixtureA.getUserData()).reverseVelocity(true,false);
                else
                    ((Item)fixtureB.getUserData()).reverseVelocity(true,false);
                break;
            case ITEM_BIT|MARIO_BIT:
                if (fixtureA.getFilterData().categoryBits==ITEM_BIT)
                {
                    ((Item)fixtureA.getUserData()).useItem((Mario)fixtureB.getUserData());

                }
                else
                {
                    ((Item)fixtureB.getUserData()).useItem((Mario)fixtureA.getUserData());
                }
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {
     //   Gdx.app.log("End Contact","[Complete]");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
