package com.fearthebadger.studio.menus;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fearthebadger.studio.rotator.MainRotator;
import com.fearthebadger.studio.utils.RotatorConstants;

public class Settings extends InputAdapter implements Screen {
	
	private static final String TAG = "Rotator Settings";
	
	MainRotator game;
	
	//private TextureAtlas atlas;
	private Skin skin;
	
	private Viewport viewport;
	
	private Texture checkBoxOn, checkBoxOff;
	private CheckBox.CheckBoxStyle cbs;
	
	private CheckBox timer, flip, indicator;
	
	private BitmapFont font;
	
	private Stage stage;

	public Settings(final MainRotator game) {
		create();
		this.game = game;
	}

	public Settings() {
		create();		
	}

	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
	
		viewport = new FitViewport(RotatorConstants.worldWidth, RotatorConstants.worldHeight);
		
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"));
		
		skin = new Skin();
		//atlas = new TextureAtlas(Gdx.files.internal("button.atlas"));
		//skin.addRegions(atlas);
		
		Gdx.app.log(TAG, "Rotator Before Map");
		
		checkBoxOn = new Texture(Gdx.files.internal("checkBoxOn.png"));
		checkBoxOff = new Texture(Gdx.files.internal("checkBoxOff.png"));
		
		cbs = new CheckBox.CheckBoxStyle();
		cbs.checkboxOn = new TextureRegionDrawable( new TextureRegion(checkBoxOn) );
		cbs.checkboxOff = new TextureRegionDrawable( new TextureRegion(checkBoxOff) );
		cbs.font = font;
		cbs.fontColor = Color.WHITE;
		
		skin.add("default", cbs);
		
		timer = new CheckBox("Show timer.", skin);
		timer.getCells().get(0).size(60, 60);
		timer.setPosition(Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()/2)+60);
		stage.addActor(timer);
		
		flip = new CheckBox("Allow image flipping.", skin);
		flip.getCells().get(0).size(60, 60);
		flip.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		stage.addActor(flip);
		
		indicator = new CheckBox("Show correct location inicator.", skin);
		indicator.getCells().get(0).size(60, 60);
		indicator.setPosition(Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()/2)-60);
		stage.addActor(indicator);
		
		//btnPlay = new TextButton("PLAY", skin);
		//btnPlay.sizeBy(180.0f, 60.0f);
		//btnPlay.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	}
	
	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(RotatorConstants.bgColor.r,
				RotatorConstants.bgColor.g,
				RotatorConstants.bgColor.b,
				RotatorConstants.bgColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/60f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, false);
	}

	@Override
	public void dispose() {
		checkBoxOff.dispose();
		checkBoxOn.dispose();
		
		stage.dispose();
	}
	
	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}
}
