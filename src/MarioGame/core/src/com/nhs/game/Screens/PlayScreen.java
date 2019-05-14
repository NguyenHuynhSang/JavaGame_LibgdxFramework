package com.nhs.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nhs.game.Scenes.Hud;
import com.nhs.game.Sprites.Mario;
import com.nhs.game.mariobros;

import static com.nhs.game.Screens.Global.global.PPM;
import static com.nhs.game.Screens.Global.global._height;
import static com.nhs.game.Screens.Global.global._width;

public class PlayScreen implements Screen {

    private mariobros game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;


    //box 2d variables


    private World world;
    private Box2DDebugRenderer b2dr;


    //dynamics object

    private Mario player;



    public  PlayScreen(mariobros game){
        this.game=game;
        gameCam=new OrthographicCamera();
        //gamePort=new ScreenViewport(gameCam);  //Sẽ phát sinh lỗi nếu màn hình có size lớn thì Camera sẽ lớn hơn, đã fix ở dưới
        gamePort=new FitViewport(_width /PPM,_height/PPM,gameCam);
        hud=new Hud(game.batch);

        mapLoader=new TmxMapLoader();
        map=mapLoader.load("maplv1-1.tmx");
        renderer=new OrthogonalTiledMapRenderer(map,1/PPM);
        gameCam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        world=new World(new Vector2(0,-10),true);// "true" is make an object sleeping

        b2dr=new Box2DDebugRenderer();
        player=new Mario(world);


        BodyDef bdef=new BodyDef();
        PolygonShape shape=new PolygonShape();
        FixtureDef fdef=new FixtureDef();
        Body body;




        //create body for ground
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){ //get the ground object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody; // like bricks,ground nói chung là mấy object k di chuyển đc
            bdef.position.set((rec.getX()+rec.getWidth()/2)/PPM,(rec.getY()+rec.getHeight()/2)/PPM);

            body=world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2)/PPM,(rec.getHeight()/2)/PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }
        //create body for brick
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody; // like bricks,ground nói chung là mấy object k di chuyển đc
            bdef.position.set((rec.getX()+rec.getWidth()/2)/PPM,(rec.getY()+rec.getHeight()/2)/PPM);

            body=world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2)/PPM,(rec.getHeight()/2)/PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }


        //create body for coins
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody; // like bricks,ground nói chung là mấy object k di chuyển đc
            bdef.position.set((rec.getX()+rec.getWidth()/2)/PPM,(rec.getY()+rec.getHeight()/2)/PPM);

            body=world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2)/PPM,(rec.getHeight()/2)/PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }


        //create body for pipes
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){ //get the coins object in tilemap
            Rectangle rec=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody; // like bricks,ground nói chung là mấy object k di chuyển đc
            bdef.position.set((rec.getX()+rec.getWidth()/2)/PPM,(rec.getY()+rec.getHeight()/2)/PPM);

            body=world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2)/PPM,(rec.getHeight()/2)/PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }







    }


    @Override
    public void show() {

    }

    public  void handleInput(float dt)
    {
       // if (Gdx.input.isTouched())  gameCam.position.x+=100*dt;
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            player.b2body.applyLinearImpulse(new Vector2(0,3.8f),player.b2body.getWorldCenter(),true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) &&player.b2body.getLinearVelocity().x<=1.2)
        {
            player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(),true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x>=-1.2)
        {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true);
        }



    }

    public  void Update(float dt)
    {
        handleInput(dt);
        world.step(1/50f,6,2);
        gameCam.position.x=player.b2body.getPosition().x;
        gameCam.update();
        renderer.setView(gameCam);// set vi tri ve map

    }


    @Override
    public void render(float delta) {

            Update(delta);
        Gdx.gl.glClearColor(0,0,0,1); //clear the screen to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        renderer.render();
        b2dr.render(world,gameCam.combined); // hiện boundingbox lên để kiểm tra va chạm
        //draw hud riêng vì hud k chạy theo cam world
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);//tell the game where the camera is in our game world
        hud.stage.draw();
       // game.batch.begin(); //open the box

        //game.batch.end(); //close the box and draw it to the screen

    }

    @Override
    public void resize(int width, int height)
    {
     gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
