package com.group.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.group.game.screens.MainMenuScreen;

import static com.group.game.utility.Constants.VIRTUAL_HEIGHT;
import static com.group.game.utility.Constants.VIRTUAL_WIDTH;


/**
 * Created by gerard on 12/02/2017.
 * KDwhatever
 */

public class TBWGame extends Game {
    private AssetManager assetManager;
    public OrthographicCamera camera;
    public Viewport viewport;
    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new
                InternalFileHandleResolver()));
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        batch = new SpriteBatch();
        //Using LibGDX's default Arial font.
        font = new BitmapFont();
        setScreen(new MainMenuScreen(this));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
