

///================================
// Lớp quản lý va chạm
// được gọi khi 2 vật thể trong world game va chạm nhau
// quăng log ra cho dev biết là có va chạm
//=================================



package com.nhs.game.Tools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.nhs.game.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
      // Gdx.app.log("Begin Contact","[Complete]");

        Fixture fixtureA=contact.getFixtureA();
        Fixture fixtureB=contact.getFixtureB();

        if (fixtureA.getUserData()=="head" || fixtureB.getUserData()=="head" )
        {
            Fixture head=fixtureA.getUserData()=="head"?fixtureA:fixtureB;
            Fixture object=head==fixtureA?fixtureB:fixtureA;

            if (object.getUserData()!=null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass()))
            {
                ((InteractiveTileObject) object.getUserData()).onHeadHit();

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
