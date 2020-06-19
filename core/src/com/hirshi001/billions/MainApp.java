package com.hirshi001.billions;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hirshi001.billions.game.GameApplication;
import com.hirshi001.billions.registry.Registry;

import java.awt.Dimension;

public class MainApp extends ApplicationAdapter{
	SpriteBatch batch;
	GameApplication gameApplication;

	public static final Dimension size = new Dimension(1200,1000);

	public OrthographicCamera camera;

	@Override
	public void create () {
		/* Create OrthographicCamera*/
		camera = new OrthographicCamera(size.width, size.height);


		/*create spritebatch*/
		batch = new SpriteBatch();

		/*create game*/
		gameApplication = new GameApplication();
		gameApplication.setCamera(camera);
		gameApplication.startup();



	}

	@Override
	public void render () {
		gameApplication.update();
		camera.update();


		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		gameApplication.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		gameApplication.dispose();
		Registry.dispose();
	}


}
