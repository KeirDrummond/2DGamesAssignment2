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
import com.group.game.utility.HUD;

import static com.group.game.utility.Constants.REVOLVER_AMMO_PICKUP_SOUND_PATH;

public class RevolverAmmo extends AnimatedSprite implements PowerUpSprite {

    private boolean isDisplayed;
    private boolean active = true;

    private HUD hud;
    private Rectangle rectangle;
    Sound sound;

    public RevolverAmmo(String atlasString, Texture t, Vector2 pos, HUD hud) {
        super(atlasString, t, pos);
        isDisplayed = true;
        playmode = Animation.PlayMode.LOOP;
        animation.setPlayMode(playmode);

        rectangle = this.getBoundingRectangle();
        sound = Gdx.audio.newSound(Gdx.files.internal(REVOLVER_AMMO_PICKUP_SOUND_PATH));
    }

    public boolean isDisplayed()
    {
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
        if (active) {
            isDisplayed = false;
            active = false;
            sound.play(1.0f);
            thePlayer.addAmmo(10);
            hud.setScore(20);
        }
    }

}