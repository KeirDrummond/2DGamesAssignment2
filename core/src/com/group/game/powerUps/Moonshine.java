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
import static com.group.game.utility.Constants.MOVESPEED;
import static com.group.game.utility.Constants.MOONSHINE_SPEED;

public class Moonshine extends AnimatedSprite implements PowerUpSprite {

    private boolean isDisplayed;

    private Animation idleAnimation;
    private Animation pickupAnimation;

    private float timer;
    private boolean active = true;

    private PlayerCharacter playerCharacter;

    private Rectangle rectangle;
    Sound sound;

    public Moonshine(String atlasString, Texture t, Vector2 pos) {
        super(atlasString, t, pos);
        isDisplayed = true;
        playmode = Animation.PlayMode.LOOP;
        animation.setPlayMode(playmode);

        rectangle = this.getBoundingRectangle();
        sound = Gdx.audio.newSound(Gdx.files.internal(PICKUP_PATH));

        idleAnimation = super.newAnimation(atlasString, "idleMoonshine");
        super.setAnimation(idleAnimation);
        pickupAnimation = super.newAnimation(atlasString, "smashMoonshine");
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
        playerCharacter = thePlayer;
        isDisplayed = false;
        sound.play(1.0f);
        thePlayer.changeSpeed(MOONSHINE_SPEED);
        if(active){timer = 5f; active = false;}
    }

    public void update (float delta){
        if(timer>0){
            timer=-delta;
            if(timer<=0){
                playerCharacter.changeSpeed(MOVESPEED);
            }
        }
    }
}