package com.jeff.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.jeff.game.GameScreens.ProgramScreen;
import com.jeff.game.Manager.AssetManager;
import com.jeff.game.Manager.GameManager;
import com.jeff.game.Manager.SoundManager;
import com.jeff.game.Util.CoordToPosition;

public class JeffCoinCollectorGame extends Game {

	@Override
	public void create () {
		AssetManager.initialize();
		CoordToPosition.initCoords();

		setScreen(new MenuScreen(this));
	}



	@Override
	public void dispose () {
		AssetManager.dispose();
	}
}
