package com.fearthebadger.studio.rotator;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fearthebadger.studio.utils.Utils;
import com.fearthebadger.studio.utils.RotatorConstants;

public class MainGame extends InputAdapter implements Screen {
	private static final String TAG = "Rotator Main";
	
	private static final Color backgroundColor = new Color(0.50f, 0.18f, 0.92f, 1.0f);
	
	private MainRotator game;
	
	static Sprite gamePiece;
	
	private int offsetAngle;
	
	private OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch batch;
	private TextureAtlas atlas;
	
	
	private AtlasRegion atlasRegion;
//	private Sprite background;

	private float deviceAngle;
	private float lastPos;
	private int rotationTolerance;
	
	private int changeCount;
	private int minChanges;
	
	private int matchCount;
	private int minMatch;
	
	private int minDegree;
	private int maxDegree;
	
	public MainGame(MainRotator game) {
		this.game = game;
		create();
	}
	
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		camera = new OrthographicCamera();
		viewport = new FitViewport(RotatorConstants.worldWidth, RotatorConstants.worldHeight, camera);
		batch = new SpriteBatch();
		
		atlas = new TextureAtlas(Gdx.files.internal("prehistoric.atlas"));
		atlasRegion = atlas.findRegion("caveman");
		gamePiece = new Sprite(atlasRegion);
		gamePiece.setScale(2.0f);

//		background = new Sprite(atlas.findRegion("background"));
//		background.setPosition(-background.getWidth() * 0.5f, -background.getHeight() * 0.5f);
		
		minDegree = 45;
		maxDegree = 90;
		
		//ScreenPieces.setPieces(minDegree, maxDegree);
		
		offsetAngle = Utils.randLocation(minDegree, maxDegree);
		
		lastPos = 90;
		rotationTolerance = 1;
		changeCount = 0;
		minChanges = 2;
		
		matchCount = 0;
		minMatch = 30;
	
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(backgroundColor.r,
							backgroundColor.g,
							backgroundColor.b,
							backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
	
		deviceAngle = Utils.phoneAngle();
		
		Gdx.app.debug(TAG, "Blah: " + lastPos + " : " + deviceAngle);
		
		if (deviceAngle > (lastPos+rotationTolerance) || deviceAngle < (lastPos-rotationTolerance)) {
			if (changeCount < minChanges) {
				changeCount++;
			} else {
				gamePiece.setRotation(deviceAngle-offsetAngle);
				lastPos = deviceAngle;
				changeCount = 0;
			}
		} else {
			changeCount = 0;
		}

		if ( gamePiece.getRotation()+offsetAngle > 80 && gamePiece.getRotation()+offsetAngle < 100 ) {
			if ( matchCount < minMatch ) {
				matchCount++;
			} else {
				Gdx.app.debug(TAG, " You Win ");
			}
		} else {
			matchCount = 0;
		}
	
		if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
			Gdx.app.log(TAG, "this.game = " + this.game);
			game.setScreen(new MainMenu(this.game));
		}
		
		batch.begin();
//		background.draw(batch);	
		
		gamePiece.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, false);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		atlas.dispose();
	}

	@Override
	public void show() {
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}