package com.nhs.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nhs.game.Screens.MenuScreen.MenuScreen;
import com.nhs.game.Screens.PlayScreen.FirstScreen;

public class mariobros extends Game {

//	private  static  mariobros __instance=null;
	public SpriteBatch batch; //this things hold all of our image,textures,stuffs...

	public static AssetManager manager;


	public  SpriteBatch getBatch(){
		return batch;
	}
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager=new AssetManager();
		manager.load("audio/music/mario_music.ogg",Music.class);
		manager.load("audio/sounds/coin.wav",Sound.class);
		manager.load("audio/sounds/bump.wav",Sound.class);
		manager.load("audio/sounds/breakblock.wav",Sound.class);
		manager.load("audio/sounds/jump.wav",Sound.class);
		manager.load("audio/sounds/pwspawn.wav",Sound.class);
		manager.load("audio/sounds/powerup.wav",Sound.class);
		manager.load("audio/sounds/powerdown.wav",Sound.class);
		manager.load("audio/sounds/mariodie.wav",Sound.class);
		manager.load("audio/sounds/lifeup.wav",Sound.class);
		manager.load("audio/sounds/kick.wav",Sound.class);
		manager.load("audio/sounds/fireball.wav",Sound.class);
		manager.load("audio/sounds/bigjump.wav",Sound.class);
		manager.load("audio/sounds/stomp.wav",Sound.class);
		manager.finishLoading();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		batch.dispose();
		Gdx.app.log("Dispose","mariobro");

	}

	@Override
	public void render () {

		super.render(); //delegate the render method to the playscreen class

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width,height);
	}
}
