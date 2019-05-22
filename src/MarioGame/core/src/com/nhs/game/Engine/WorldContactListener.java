

///================================
// Lớp quản lý va chạm
// được gọi khi 2 vật thể trong world game va chạm nhau
// quăng log ra cho dev biết là có va chạm
//=================================



package com.nhs.game.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.nhs.game.Object.Enermy;
import com.nhs.game.Object.GameObject;
import com.nhs.game.Object.Mario;

import static com.nhs.game.Global.global.ENERMY_BIT;
import static com.nhs.game.Global.global.ENERMY_HEAD_BIT;
import static com.nhs.game.Global.global.GROUND_BIT;
import static com.nhs.game.Global.global.MARIO_BIT;
import static com.nhs.game.Global.global.OBJECT_BIT;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
      // Gdx.app.log("Begin Contact","[Complete]");

        Fixture fixtureA=contact.getFixtureA();
        Fixture fixtureB=contact.getFixtureB();

        int cDef=fixtureA.getFilterData().categoryBits|fixtureB.getFilterData().categoryBits;
        if (fixtureA.getUserData()=="head" || fixtureB.getUserData()=="head" )
        {
            Fixture head=fixtureA.getUserData()=="head"?fixtureA:fixtureB;
            Fixture object=head==fixtureA?fixtureB:fixtureA;

            if (object.getUserData()!=null && GameObject.class.isAssignableFrom(object.getUserData().getClass()))
            {
                ((GameObject) object.getUserData()).onHeadHit();

            }
        }
        switch (cDef){


            case ENERMY_HEAD_BIT| MARIO_BIT:
                if (fixtureA.getFilterData().categoryBits==ENERMY_HEAD_BIT)
                    ((Enermy)fixtureA.getUserData()).hitOnHead();
                else
                    ((Enermy)fixtureB.getUserData()).hitOnHead();
                break;
            case ENERMY_BIT|OBJECT_BIT:
                if (fixtureA.getFilterData().categoryBits==ENERMY_BIT)
                    ((Enermy)fixtureA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enermy)fixtureB.getUserData()).reverseVelocity(true,false);
                break;
            case MARIO_BIT|ENERMY_BIT:
            {
                Gdx.app.log("Mario","died!! \n");

            }



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
