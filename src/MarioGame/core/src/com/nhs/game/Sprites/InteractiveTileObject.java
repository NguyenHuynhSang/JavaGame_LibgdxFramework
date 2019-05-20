package com.nhs.game.Sprites;

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

import static com.nhs.game.Global.global.PPM;


public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bbox;
    protected Body body;
    protected Fixture fixture;

    public  InteractiveTileObject(PlayScreen screen, Rectangle bbox)
    {
        this.world=screen.getWorld();
        this.map=screen.getMap();
        this.bbox=bbox;
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


    public  abstract void onHeadHit();


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
