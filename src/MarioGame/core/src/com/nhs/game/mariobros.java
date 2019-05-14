package com.nhs.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nhs.game.Screens.PlayScreen;

public class mariobros extends Game {
	public SpriteBatch batch; //this things hold all of our image,textures,stuffs...
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();//delegate the render method to the playscreen class
	}
	

}
