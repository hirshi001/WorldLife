package com.hirshi001.billions;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.hirshi001.billions.registry.Registry;

import java.awt.Dimension;

public class MainApp extends ApplicationAdapter{
	SpriteBatch batch;
	Game game;

	public static final Dimension size = new Dimension(1200,1000);

	public OrthographicCamera camera;

	@Override
	public void create () {
		/* Create OrthographicCamera*/
		camera = new OrthographicCamera(size.width, size.height);


		/*create spritebatch*/
		batch = new SpriteBatch();

		/*create game*/
		game = new Game(camera).setSpriteBatch(batch);



	}

	@Override
	public void render () {
		game.update();
		camera.update();


		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		game.draw();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		game.dispose();
		Registry.dispose();
	}


}
