package com.nhs.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nhs.game.Sprites.Bricks;
import com.nhs.game.Sprites.Coins;

import static com.nhs.game.Global.global.PPM;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map)
    {


        BodyDef bdef=new BodyDef();
        PolygonShape shape=new PolygonShape();
        FixtureDef fdef=new FixtureDef();
        Body body;




        //create body for ground
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){ //get the ground object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody; // like bricks,ground nói chung là mấy object k di chuyển đc
            bdef.position.set((rec.getX()+rec.getWidth()/2)/PPM,(rec.getY()+rec.getHeight()/2)/PPM);

            body=world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2)/PPM,(rec.getHeight()/2)/PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }
        //create body for brick
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            new Bricks(world,map,rec);
        }


        //create body for coins
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            new Coins(world,map,rec);
        }


        //create body for pipes
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody; // like bricks,ground nói chung là mấy object k di chuyển đc
            bdef.position.set((rec.getX()+rec.getWidth()/2)/PPM,(rec.getY()+rec.getHeight()/2)/PPM);

            body=world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2)/PPM,(rec.getHeight()/2)/PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }


    }

}