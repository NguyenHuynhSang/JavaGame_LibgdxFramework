///**************************************************
//*Lá»›p láº¥y object tá»« file map
//*
//****************************************************
package com.nhs.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.nhs.game.Object.Enermy.Enermy;
import com.nhs.game.Object.Enermy.Goomba;
import com.nhs.game.Object.Enermy.PirannhaPlant;
import com.nhs.game.Object.Enermy.Turtle;
import com.nhs.game.Object.Items.Coins;
import com.nhs.game.Object.Items.ItemDef;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Object.staticObject.DeadZone;
import com.nhs.game.Object.staticObject.MapBound;
import com.nhs.game.Screens.PlayScreen.FirstScreen;
import com.nhs.game.Object.staticObject.Bricks;
import com.nhs.game.Object.staticObject.CoinBox;
import com.nhs.game.Screens.PlayScreen.SecondScreen;
import com.nhs.game.Screens.PlayScreen.UnderGroundScreen;
import com.nhs.game.Screens.ScreenManagement;

import static com.nhs.game.Global.global.OBJECT_BIT;
import static com.nhs.game.Global.global.PPM;
import static com.nhs.game.Global.global._mapWidth;
import static com.nhs.game.Global.global._mapWidthX2;

public class B2WorldCreator implements Disposable {

    private  World world;
    private TiledMap map;
    private BodyDef bdef;
    private PolygonShape shape;
    private FixtureDef fdef;
    private  Body body;


    private Mario player;
    private Array<Goomba> goombas;
    private Array<Turtle> turtle;
    private Array<PirannhaPlant> pplant;

    public Mario getPlayer(){return  player;}
    public Array<Enermy> getEnermy(){
        Array<Enermy> enermies=new Array<Enermy>();
        enermies.addAll(goombas);
        enermies.addAll(turtle);
        enermies.addAll(pplant);
        return  enermies;
    }
    public B2WorldCreator(ScreenManagement screen)
    {

        world=screen.getWorld();
        map=screen.getMap();
        bdef=new BodyDef();
        shape=new PolygonShape();
        fdef=new FixtureDef();
        if (screen instanceof FirstScreen) getFistScreenObject(screen);
        else if (screen instanceof UnderGroundScreen) {
            getUGScreenObject(screen);
        } else if (screen instanceof SecondScreen){
            getSecondScreenObject(screen);

        }
    }

    private void getFistScreenObject(ScreenManagement screen){

        int count=0;
        //create body for mapbound
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            new MapBound(screen,object);
            count++;
            if (count==1)
                _mapWidth=rec.getX()+rec.getWidth();
            else
                _mapWidthX2=rec.getX();


        }
        //create body for deadzone
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            //  Rectangle rec=((RectangleMapObject) object).getRectangle();
            new DeadZone(screen,object);
        }

        Gdx.app.log("First Screen","created objects from tile");
        //get Mario position in MTile ap
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(TiledMapTileMapObject.class)){ //get the coins object in tilemap
            TiledMapTileMapObject tile=((TiledMapTileMapObject)object);
            //  Rectangle rec=((RectangleMapObject) object).getRectangle();
            player=new Mario(screen,tile.getX()/PPM,tile.getY()/PPM);
        }




        //create body for ground
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){ //get the ground object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody; // like bricks,ground nÃ³i chung lÃ  máº¥y object k di chuyá»ƒn Ä‘c
            bdef.position.set((rec.getX()+rec.getWidth()/2)/PPM,(rec.getY()+rec.getHeight()/2)/PPM);

            body=world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2)/PPM,(rec.getHeight()/2)/PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }
        //create body for brick
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            // Rectangle rec=((RectangleMapObject) object).getRectangle();
            new Bricks(screen,object );
        }


        //create body for coins
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            // Rectangle rec=((RectangleMapObject) object).getRectangle();
            new CoinBox(screen,object);
        }


        //create body for pipes
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody; // like bricks,ground nÃ³i chung lÃ  máº¥y object k di chuyá»ƒn Ä‘c
            bdef.position.set((rec.getX()+rec.getWidth()/2)/PPM,(rec.getY()+rec.getHeight()/2)/PPM);

            body=world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2)/PPM,(rec.getHeight()/2)/PPM);
            fdef.shape=shape;
            fdef.filter.categoryBits=OBJECT_BIT;
            body.createFixture(fdef);
        }




        //get goomba vÃ¬ goomba táº¡o trong mapeditor trá»±c tiáº¿p báº±ng insert tileset(chi tiáº¿t xem trong file map: android//assets//level1.tmx: ) nÃªn khi get pháº£i get TiledMapTileMapObject

        goombas=new Array<Goomba>();
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(TiledMapTileMapObject.class)){ //get the coins object in tilemap
            TiledMapTileMapObject tile=((TiledMapTileMapObject)object);
            //  Rectangle rec=((RectangleMapObject) object).getRectangle();
            goombas.add(new Goomba(screen,tile.getX()/PPM,tile.getY()/PPM));

        }




        turtle=new Array<Turtle>();
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(TiledMapTileMapObject.class)){ //get the coins object in tilemap
            TiledMapTileMapObject tile=((TiledMapTileMapObject)object);
            //  Rectangle rec=((RectangleMapObject) object).getRectangle();
            turtle.add(new Turtle(screen,tile.getX()/PPM,tile.getY()/PPM));

        }

        pplant=new Array<PirannhaPlant>();
        for (MapObject object : map.getLayers().get(11).getObjects().getByType(TiledMapTileMapObject.class)){ //get the coins object in tilemap
            TiledMapTileMapObject tile=((TiledMapTileMapObject)object);
            //  Rectangle rec=((RectangleMapObject) object).getRectangle();
            pplant.add(new PirannhaPlant(screen,tile.getX()/PPM,tile.getY()/PPM));

        }

    }

    private void getUGScreenObject(ScreenManagement screen){


        int count=0;
        //create body for mapbound
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            new MapBound(screen,object);
            count++;
            if (count==1)
                _mapWidth=rec.getX()+rec.getWidth();
            else
                _mapWidthX2=rec.getX();


        }

        //get Mario position in Tilemap
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(TiledMapTileMapObject.class)){ //get the coins object in tilemap
            TiledMapTileMapObject tile=((TiledMapTileMapObject)object);
            //  Rectangle rec=((RectangleMapObject) object).getRectangle();
            player=new Mario(screen,tile.getX()/PPM,tile.getY()/PPM);
        }

        //create body for ground
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){ //get the ground object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody; // like bricks,ground nÃ³i chung lÃ  máº¥y object k di chuyá»ƒn Ä‘c
            bdef.position.set((rec.getX()+rec.getWidth()/2)/PPM,(rec.getY()+rec.getHeight()/2)/PPM);

            body=world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2)/PPM,(rec.getHeight()/2)/PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }

        //create body for pipes
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody; // like bricks,ground nÃ³i chung lÃ  máº¥y object k di chuyá»ƒn Ä‘c
            bdef.position.set((rec.getX()+rec.getWidth()/2)/PPM,(rec.getY()+rec.getHeight()/2)/PPM);

            body=world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2)/PPM,(rec.getHeight()/2)/PPM);
            fdef.shape=shape;
            fdef.filter.categoryBits=OBJECT_BIT;
            body.createFixture(fdef);
        }




        for (MapObject object : map.getLayers().get(6).getObjects().getByType(TiledMapTileMapObject.class)){ //get the coins object in tilemap
            TiledMapTileMapObject tile=((TiledMapTileMapObject)object);
            //  Rectangle rec=((RectangleMapObject) object).getRectangle();
            ((UnderGroundScreen)screen).spawnItem(new ItemDef(new Vector2(tile.getX()/PPM,tile.getY()/PPM),
                    Coins.class));
        }




    }


    private  void getSecondScreenObject(ScreenManagement screen){
        int count=0;
        //create body for mapbound
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            new MapBound(screen,object);
            count++;
            if (count==1)
                _mapWidth=rec.getX()+rec.getWidth();
            else
                _mapWidthX2=rec.getX();


        }
        //create body for deadzone
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            //  Rectangle rec=((RectangleMapObject) object).getRectangle();
            new DeadZone(screen,object);
        }

        Gdx.app.log("First Screen","created objects from tile");
        //get Mario position in MTile ap
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(TiledMapTileMapObject.class)){ //get the coins object in tilemap
            TiledMapTileMapObject tile=((TiledMapTileMapObject)object);
            //  Rectangle rec=((RectangleMapObject) object).getRectangle();
            player=new Mario(screen,tile.getX()/PPM,tile.getY()/PPM);
        }




        //create body for ground
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){ //get the ground object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody; // like bricks,ground nÃ³i chung lÃ  máº¥y object k di chuyá»ƒn Ä‘c
            bdef.position.set((rec.getX()+rec.getWidth()/2)/PPM,(rec.getY()+rec.getHeight()/2)/PPM);

            body=world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2)/PPM,(rec.getHeight()/2)/PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }
        //create body for brick
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            // Rectangle rec=((RectangleMapObject) object).getRectangle();
            new Bricks(screen,object );
        }


        //create body for coins
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            // Rectangle rec=((RectangleMapObject) object).getRectangle();
            new CoinBox(screen,object);
        }


        //create body for pipes
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody; // like bricks,ground nÃ³i chung lÃ  máº¥y object k di chuyá»ƒn Ä‘c
            bdef.position.set((rec.getX()+rec.getWidth()/2)/PPM,(rec.getY()+rec.getHeight()/2)/PPM);

            body=world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2)/PPM,(rec.getHeight()/2)/PPM);
            fdef.shape=shape;
            fdef.filter.categoryBits=OBJECT_BIT;
            body.createFixture(fdef);
        }




        //get goomba vÃ¬ goomba táº¡o trong mapeditor trá»±c tiáº¿p báº±ng insert tileset(chi tiáº¿t xem trong file map: android//assets//level1.tmx: ) nÃªn khi get pháº£i get TiledMapTileMapObject

        goombas=new Array<Goomba>();
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(TiledMapTileMapObject.class)){ //get the coins object in tilemap
            TiledMapTileMapObject tile=((TiledMapTileMapObject)object);
            //  Rectangle rec=((RectangleMapObject) object).getRectangle();
            goombas.add(new Goomba(screen,tile.getX()/PPM,tile.getY()/PPM));

        }




        turtle=new Array<Turtle>();
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(TiledMapTileMapObject.class)){ //get the coins object in tilemap
            TiledMapTileMapObject tile=((TiledMapTileMapObject)object);
            //  Rectangle rec=((RectangleMapObject) object).getRectangle();
            turtle.add(new Turtle(screen,tile.getX()/PPM,tile.getY()/PPM));

        }


        for (MapObject object : map.getLayers().get(11).getObjects().getByType(TiledMapTileMapObject.class)){ //get the coins object in tilemap
            TiledMapTileMapObject tile=((TiledMapTileMapObject)object);
            //  Rectangle rec=((RectangleMapObject) object).getRectangle();
            ((SecondScreen)(screen)).spawnItem(new ItemDef(new Vector2(tile.getX()/PPM,tile.getY()/PPM),
                    Coins.class));
        }


    }

    @Override
    public void dispose() {

    }
}
