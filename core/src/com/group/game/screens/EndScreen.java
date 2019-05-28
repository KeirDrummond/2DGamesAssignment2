package com.group.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.group.game.utility.GameData;

import static com.group.game.utility.Constants.VIRTUAL_HEIGHT;
import static com.group.game.utility.Constants.VIRTUAL_WIDTH;

/**
 * Created by gerard on 23/04/2017.
 *
 * Last updated by Keir Drummond on 28/05/2019
 *
 */

public class EndScreen extends ScreenAdapter {
    private Stage stage;
    Table tableData;
    //Scene2D Widgets
    private Label countdownLabel, headerLabel, linkLabel, linkLabel2;
    private static Label scoreLabel, ammoLabel;

    public EndScreen(boolean win){
        stage = new Stage(new FitViewport(VIRTUAL_WIDTH/3, VIRTUAL_HEIGHT/3));
        Gdx.input.setInputProcessor(stage);
        tableData = new Table();
        tableData.setFillParent(true);

        //Different result text based on if the player cleared the level.
        if (win)
            createScoreAndTimer("LEVEL WON", 200);
        else
            createScoreAndTimer("LEVEL LOST", 0);

        stage.addActor(tableData);
    }

    public void show() {

    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        clearScreen();
        stage.draw();
    }

    private void createScoreAndTimer(String text, int bonus){
        //define labels using the String, and a Label style consisting of a font and color
        int score = GameData.getInstance().getScore() + (GameData.getInstance().getAmmo() * 5) + MathUtils.floor(GameData.getInstance().getTime()) + bonus;
        headerLabel = new Label(text, new Label.LabelStyle(new BitmapFont(), Color.LIME));
        ammoLabel = new Label(String.format("%03d", GameData.getInstance().getAmmo()), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        linkLabel = new Label("AMMO", new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        linkLabel2 = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        //add labels to table
        tableData.add(headerLabel).padLeft(150);
        tableData.row();
        tableData.add(linkLabel).padLeft(60);
        tableData.add(ammoLabel).expandX();
        tableData.row();
        tableData.add(linkLabel2).padLeft(60);
        tableData.add(scoreLabel).expandX();

    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
                Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
