package com.nhs.game.Object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nhs.game.Screens.PlayScreen;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Screens.ScreenManagement;

import static com.nhs.game.Global.global.PPM;


public abstract class GameObject extends Sprite {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bbox;
    protected Body body;
    protected Fixture fixture;
    protected ScreenManagement screen;
    protected MapObject object;
    public GameObject(ScreenManagement screen, MapObject object)
    {
        this.object=object;
        this.screen=screen;
        this.world=screen.getWorld();
        this.map=screen.getMap();
        this.bbox=((RectangleMapObject)object).getRectangle();
        BodyDef bdef=new BodyDef();
        FixtureDef fdef=new FixtureDef();
        PolygonShape shape=new PolygonShape();

        bdef.type=BodyDef.BodyType.StaticBody; // like bricks,ground nói chung là mấy object k di chuyển đc
        bdef.position.set((bbox.getX()+bbox.getWidth()/2)/PPM,(bbox.getY()+bbox.getHeight()/2)/PPM);

        body=world.createBody(bdef);

        shape.setAsBox((bbox.getWidth()/2)/PPM,(bbox.getHeight()/2)/PPM);
        fdef.shape=shape;
        fixture=body.createFixture(fdef);
    }


    public  abstract  void isHeadHit(Mario mario);
    public  void setCategoryFilter(short filternit){
        Filter filter=new Filter();
        filter.categoryBits=filternit;
        fixture.setFilterData(filter);

    }
public TiledMapTileLayer.Cell getCell(){
    TiledMapTileLayer layer=(TiledMapTileLayer)map.getLayers().get(1);
    return  layer.getCell((int)(body.getPosition().x*PPM/16),
            (int)(body.getPosition().y*PPM/16)
    );



}

}
