package com.nhs.game.Engine;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.nhs.game.Object.Goomba;
import com.nhs.game.Object.staticObject.DeadZone;
import com.nhs.game.Object.staticObject.MapBound;
import com.nhs.game.Screens.PlayScreen;
import com.nhs.game.Object.staticObject.Bricks;
import com.nhs.game.Object.staticObject.Coins;

import static com.nhs.game.Global.global.OBJECT_BIT;
import static com.nhs.game.Global.global.PPM;
import static com.nhs.game.Global.global._mapWidth;
import static com.nhs.game.Global.global._mapWidthX2;

public class B2WorldCreator {

    private Array<Goomba> goombas;

    public Array<Goomba> getGoombas() {
        return goombas;
    }

    public B2WorldCreator(PlayScreen screen)
     {

        World world=screen.getWorld();
        TiledMap map=screen.getMap();
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
           // Rectangle rec=((RectangleMapObject) object).getRectangle();
            new Bricks(screen,object );
        }


        //create body for coins
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
           // Rectangle rec=((RectangleMapObject) object).getRectangle();
            new Coins(screen,object);
        }


        //create body for pipes
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody; // like bricks,ground nói chung là mấy object k di chuyển đc
            bdef.position.set((rec.getX()+rec.getWidth()/2)/PPM,(rec.getY()+rec.getHeight()/2)/PPM);

            body=world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2)/PPM,(rec.getHeight()/2)/PPM);
            fdef.shape=shape;
            fdef.filter.categoryBits=OBJECT_BIT;
            body.createFixture(fdef);
        }

        int count=0;
        //create body for mapbound
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            new MapBound(screen,object);
            count++;
            if (count==1)
            _mapWidth=rec.getX()+rec.getWidth();
            else
            _mapWidthX2=rec.getX();


        }


        //get goomba vì goomba tạo trong mapeditor trực tiếp bằng insert tileset(chi tiết xem trong file map: android//assets//level1.tmx: ) nên khi get phải get TiledMapTileMapObject

         goombas=new Array<Goomba>();
         for (MapObject object : map.getLayers().get(8).getObjects().getByType(TiledMapTileMapObject.class)){ //get the coins object in tilemap
             TiledMapTileMapObject tile=((TiledMapTileMapObject)object);
           //  Rectangle rec=((RectangleMapObject) object).getRectangle();
             goombas.add(new Goomba(screen,tile.getX()/PPM,tile.getY()/PPM));

         }


         //create body for deadzone
         for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
           //  Rectangle rec=((RectangleMapObject) object).getRectangle();
             new DeadZone(screen,object);
         }

    }

}
