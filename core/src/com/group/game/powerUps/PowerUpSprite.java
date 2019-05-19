package com.group.game.powerUps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.group.game.bodies.PlayerCharacter;

public interface PowerUpSprite {

    boolean isDisplayed();

    Rectangle GetRectangle();

    void draw(SpriteBatch batch);

    void update(float frameDelta);

    void intersected(PlayerCharacter thePlayer);
}
