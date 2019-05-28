package com.group.game.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.group.game.TBWGame;
import com.group.game.bodies.PlayerCharacter;
import com.group.game.screens.EndScreen;

import static com.group.game.utility.Constants.MEDIUM;

public class HUD implements Disposable {

    public Stage stage;
    private Viewport viewport;
    //structural elements for UI
    Table tableData;
    Table tableControls;
    // Navigation widgets
    private Button gunBtn, leftBtn,rightBtn,upBtn;
    private PlayerCharacter playerCharacter;
    private TBWGame game;

    //ammo && time tracking variables
    private Integer worldTimer;
    private float timeCount;
    private static Integer ammo, score;
    private boolean timeUp;

    //Scene2D Widgets
    private Label countdownLabel, timeLabel, linkLabel, linkLabel2;
    private static Label ammoLabel, scoreLabel;

    public HUD(SpriteBatch sb, PlayerCharacter playerCharacter, TBWGame tbwGame) {
        this.playerCharacter = playerCharacter;
        this.game = tbwGame;
        //define tracking variables
        worldTimer = Constants.LEVEL_TIME;
        timeCount = 0;
        ammo = 0;
        score = 0;
        playerCharacter.setHUDRef(this);
        //new camera used to setup the HUD viewport seperate from the main Game Camera
        //define stage using that viewport and games spritebatch
        viewport = new FitViewport(Constants.VIRTUAL_WIDTH,
                Constants.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        tableData = new Table();
        tableData.bottom();
        tableData.setFillParent(true);
        tableControls = new Table();
        tableControls.top();
        tableControls.setFillParent(true);

        createAmmoAndTimer();
        createNavButtons();
        stage.addActor(tableData);
        stage.addActor(tableControls);
        Gdx.input.setInputProcessor(stage);
    }

    private void createAmmoAndTimer(){
        countdownLabel = new Label(String.format("%03d", worldTimer),
                new Label.LabelStyle(new BitmapFont(), Color.RED));
        ammoLabel = new Label(String.format("%03d", ammo),
                new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        scoreLabel = new Label(String.format("%03d", score),
                new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        timeLabel = new Label("COUNTDOWN",
                new Label.LabelStyle(new BitmapFont(), Color.RED));
        linkLabel = new Label("AMMO",
                new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        linkLabel2 = new Label("SCORE",
                new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        //labels added to table using padding and expandX
        tableData.add(linkLabel).padBottom(5).padLeft(120);
        tableData.add(ammoLabel).expandX().padBottom(5);
        tableData.add(linkLabel2).padBottom(5);
        tableData.add(scoreLabel).expandX().padBottom(5);
        tableData.add(timeLabel).padBottom(5).padRight(20);
        tableData.add(countdownLabel).expandX().padBottom(5);
    }

    private void createNavButtons(){
        Texture actorGunBtn =
                new Texture(Gdx.files.internal("buttons/gun.png"));
        Texture actorUpBtn =
                new Texture(Gdx.files.internal("buttons/up.png"));
        Texture actorLeftBtn =
                new Texture(Gdx.files.internal("buttons/left.png"));
        Texture actorRightBtn =
                new Texture(Gdx.files.internal("buttons/right.png"));

        Button.ButtonStyle buttonStyleGun = new Button.ButtonStyle();
        buttonStyleGun.up =
                new TextureRegionDrawable(new TextureRegion(actorGunBtn));
        gunBtn = new Button(buttonStyleGun);

        Button.ButtonStyle buttonStyleUp = new Button.ButtonStyle();
        buttonStyleUp.up =
                new TextureRegionDrawable(new TextureRegion(actorUpBtn));
        upBtn = new Button( buttonStyleUp );

        Button.ButtonStyle buttonStyleLeft = new Button.ButtonStyle();
        buttonStyleLeft.up =
                new TextureRegionDrawable(new TextureRegion(actorLeftBtn));
        leftBtn = new Button( buttonStyleLeft );

        Button.ButtonStyle buttonStyleRight = new Button.ButtonStyle();
        buttonStyleRight.up =
                new TextureRegionDrawable(new TextureRegion(actorRightBtn));
        rightBtn = new Button( buttonStyleRight );

        //add buttons
        tableControls.add(gunBtn);
        tableControls.add(leftBtn).expandX().padLeft(25);
        tableControls.add(upBtn).expandX();
        tableControls.add(rightBtn).expandX().padRight(25);
        //add listeners to the buttons
        addButtonListeners();
    }

    private void addButtonListeners(){
        gunBtn.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                playerCharacter.fireGun();
                return true;
            }
        });

        /* Each button detects when it is pushed down on the button, and when the input comes back up again.
        *  This means that the game understand when the button is held down, and when it is not.
        */

        //up
        upBtn.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                playerCharacter.move(CurrentDirection.UP);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                playerCharacter.move(CurrentDirection.NONE);
            }
        });
        //left
        leftBtn.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                playerCharacter.move(CurrentDirection.LEFT);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                playerCharacter.move(CurrentDirection.NONE);
            }
        });
        //right
        rightBtn.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                playerCharacter.move(CurrentDirection.RIGHT);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                playerCharacter.move(CurrentDirection.NONE);
            }
        });
    }

    public void update(float dt) {
        timeCount += dt;
        if (timeCount >= 1) {
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
                game.setScreen(new EndScreen(false));
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            GameData.getInstance().setTime(worldTimer);
            timeCount = 0;
        }
    }

    public static void setScore(int value) {
        score += value;
        score = Math.max(score, 0);
        scoreLabel.setText(String.format("%03d", score));
        GameData.getInstance().setScore(score);
    }

    public static void setAmmo(int value) {
        ammo = value;
        ammoLabel.setText(String.format("%03d", ammo));
        GameData.getInstance().setAmmo(ammo);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public boolean isTimeUp() {
        return timeUp;
    }

    public static Label getAmmoLabel() {
        return ammoLabel;
    }

    public static Integer getAmmo() {
        return ammo;
    }


}