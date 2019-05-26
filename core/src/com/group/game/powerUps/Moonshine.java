package com.group.game.powerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.group.game.bodies.AnimatedSprite;
import com.group.game.bodies.PlayerCharacter;

import static com.group.game.utility.Constants.PICKUP_PATH;

public class Moonshine extends AnimatedSprite implements PowerUpSprite {

    private boolean isDisplayed;

    private float timer;
    private boolean active = true;

    private Rectangle rectangle;
    Sound sound;

    public Moonshine(String atlasString, Texture t, Vector2 pos) {
        super(atlasString, t, pos);
        isDisplayed = true;
        playmode = Animation.PlayMode.LOOP;
        animation.setPlayMode(playmode);

        rectangle = this.getBoundingRectangle();
        sound = Gdx.audio.newSound(Gdx.files.internal(PICKUP_PATH));
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    @Override
    public Rectangle GetRectangle() {
        return rectangle;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void intersected(PlayerCharacter thePlayer) {
        isDisplayed = false;
        sound.play(1.0f);
        thePlayer.changeSpeed(20.0f);
        if (active) {timer = 5f; active = false;}
    }

    public void update(float delta, PlayerCharacter thePlayer){
        if (timer>0){
            timer=-delta;
            if (timer<=0){
               thePlayer.changeSpeed(50.0f);
            }
        }
    }
}