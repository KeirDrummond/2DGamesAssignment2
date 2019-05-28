package com.group.game.powerUps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.group.game.bodies.PlayerCharacter;

//Created interface to allow multiple pickups to be created using the same code.
public interface PowerUpSprite {

    boolean isDisplayed();

    Rectangle GetRectangle();

    void draw(SpriteBatch batch);

    void update(float delta, float frameDelta);

    void intersected(PlayerCharacter thePlayer);
}
