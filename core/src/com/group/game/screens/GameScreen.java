package com.group.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.group.game.TBWGame;
import com.group.game.bodies.PlayerCharacter;
import com.group.game.physics.WorldManager;
import com.group.game.utility.BackgroundManager;
import com.group.game.utility.BonusManager;
import com.group.game.utility.CameraManager;
import com.group.game.utility.Constants;
import com.group.game.utility.HUD;

import static com.group.game.utility.Constants.BACKGROUND_PATH;
import static com.group.game.utility.Constants.PLAYER_ATLAS_PATH;
import static com.group.game.utility.Constants.SMALL;
import static com.group.game.utility.Constants.START_POSITION;
import static com.group.game.utility.Constants.UNITSCALE;
import static com.group.game.utility.Constants.VIRTUAL_HEIGHT;
import static com.group.game.utility.Constants.VIRTUAL_WIDTH;
import static com.group.game.utility.Constants.SOUNDTRACK_PATH;

/**
 * Created by gerard on 12/02/2017.
 */

public class GameScreen extends ScreenAdapter {
    private TBWGame game;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private PlayerCharacter smif;
    private BonusManager bonusManager;
    private BackgroundManager backgroundManager;
    private HUD gameHUD;
    private CameraManager cameraManager;
    private float frameDelta = 0;
    Sound sound;

    public GameScreen(TBWGame tbwGame){this.game = tbwGame;}

    @Override
    public void resize(int width, int height) {
        game.camera.setToOrtho(false, VIRTUAL_WIDTH * UNITSCALE, VIRTUAL_HEIGHT * UNITSCALE);
        game.batch.setProjectionMatrix(game.camera.combined);
    }

    @Override
    public void show() {
        super.show();
        tiledMap = game.getAssetManager().get(Constants.BACKGROUND);
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(this.tiledMap,UNITSCALE);
        orthogonalTiledMapRenderer.setView(game.camera);
        if(!WorldManager.isInitialised()){WorldManager.initialise(game,tiledMap);}
        //player
        smif = new PlayerCharacter(PLAYER_ATLAS_PATH,SMALL,START_POSITION);
        bonusManager = new BonusManager(smif);
        cameraManager = new CameraManager(game.camera,tiledMap);
        cameraManager.setTarget(smif);
        backgroundManager = new BackgroundManager(BACKGROUND_PATH,6,4, cameraManager);
        gameHUD = new HUD(game.batch,smif,game);

        sound = Gdx.audio.newSound(Gdx.files.internal(SOUNDTRACK_PATH));

        sound.play(1.0f);
    }

    @Override
    public void render(float delta) {
        frameDelta += delta;
        backgroundManager.update(delta);
        smif.update(frameDelta);
        bonusManager.update(frameDelta);
        gameHUD.update(delta);
        game.batch.setProjectionMatrix(game.camera.combined);
        clearScreen();
        draw();
        WorldManager.getInstance().doPhysicsStep(delta);
    }

    private void draw() {
        cameraManager.update();
        game.batch.begin();
        backgroundManager.draw(game.batch);
        game.batch.end();
        orthogonalTiledMapRenderer.setView(game.camera);
        orthogonalTiledMapRenderer.render();
        game.batch.begin();
        smif.draw(game.batch);
        bonusManager.draw(game.batch);
        game.batch.end();

        gameHUD.stage.draw();
        WorldManager.getInstance().debugRender();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
                Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}