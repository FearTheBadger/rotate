package com.fearthebadger.studio.rotator;

import com.badlogic.gdx.Game;

public class MainRotator extends Game {

	@Override
	public void create() {
		setScreen(new MainMenu(this));
	}

}