package com.group.game.powerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.group.game.bodies.AnimatedSprite;
import com.group.game.bodies.PlayerCharacter;
import com.group.game.utility.HUD;

import static com.group.game.utility.Constants.BUBBLESIMPLE_PICKUP_PATH;
import static com.group.game.utility.Constants.BUBBLE_SPEED;
import static com.group.game.utility.Constants.MAX_VELOCITY;
import static com.group.game.utility.Constants.PICKUP_PATH;

public class SpeedUp extends Sprite implements PowerUpSprite {

    private boolean isDisplayed;

    private Animation pickupAnimation;

    private float timer;
    private boolean active = true;


    private PlayerCharacter playerCharacter;

    private Rectangle rectangle;

    Sound sound;

    private HUD hud;

    public SpeedUp(Texture t, Vector2 pos){
        super(t,128/32, 128/32);
        setX(MathUtils.floor(pos.x));
        setY(MathUtils.floor(pos.y));
        isDisplayed = true;
        //playmode = Animation.PlayMode.LOOP;
        //animation.setPlayMode(playmode);

        rectangle = super.getBoundingRectangle();
        sound = Gdx.audio.newSound(Gdx.files.internal(PICKUP_PATH));
        //pickupAnimation = super.newAnimation(atlasString, "bubbleSimple");


    }




    public boolean isDisplayed() {return isDisplayed;}

    public Rectangle GetRectangle() {
        return rectangle;
    }
    public void draw (SpriteBatch batch) {batch.draw(getTexture(), getX(), getY(), 128/32, 128/32);}


    public void speed(PlayerCharacter thePlayer) {
        isDisplayed = false;
        if (active) {
            playerCharacter = thePlayer;
            sound.play(1.0f);
            thePlayer.changeSpeed(BUBBLE_SPEED);
            hud.setScore(30);
            timer = 40f;
            active = false;
        }
    }
        public void update (float delta, float frameDelta){
            if(timer>0){
                timer-=delta;
                if(timer<=0){
                    playerCharacter.changeSpeed(MAX_VELOCITY);
                }
            }
            //super.update(delta);
        }

    @Override
    public void intersected(PlayerCharacter thePlayer) {
        speed(thePlayer);
    }




}