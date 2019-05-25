package com.group.game.bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Logger;
import com.group.game.physics.WorldManager;
import com.group.game.utility.CurrentDirection;
import com.group.game.utility.IWorldObject;

import static com.group.game.utility.Constants.DENSITY;
import static com.group.game.utility.Constants.FRICTION;
import static com.group.game.utility.Constants.JUMP_FORCE;
import static com.group.game.utility.Constants.MOVESPEED;
import static com.group.game.utility.Constants.PLAYER_OFFSET_X;
import static com.group.game.utility.Constants.PLAYER_OFFSET_Y;
import static com.group.game.utility.Constants.RESTITUTION;


/**
 * Created by gja10 on 13/02/2017.
 * Updated 02/03/18
 */

public class PlayerCharacter extends AnimatedSprite implements IWorldObject {
    private Body playerBody;
    private boolean facingRight =true;

    private float movespeed = MOVESPEED;
    private float jumpforce = JUMP_FORCE;

    private Logger logger;

    private Animation idleAnimation;
    private Animation runAnimation;
    private int ammo = 0;

    CurrentDirection currentDirection = CurrentDirection.NONE;

    public PlayerCharacter(String atlas, Texture t, Vector2 pos) {
        super(atlas, t, pos);
        idleAnimation = super.newAnimation(atlas, "character_idle");
        super.setAnimation(idleAnimation);
        runAnimation = super.newAnimation(atlas, "character_run");
        buildBody();
        logger = new Logger("Player", Logger.DEBUG);
    }

    @Override
    public void buildBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX(),getY());

        playerBody = WorldManager.getInstance().getWorld().createBody(bodyDef);
        playerBody.setUserData(this);
        playerBody.setFixedRotation(true);
        playerBody.createFixture(getFixtureDef(DENSITY,FRICTION,RESTITUTION));
    }

    @Override
    public void update(float stateTime) {
        super.update(stateTime);
        this.setPosition(playerBody.getPosition().x-PLAYER_OFFSET_X,playerBody.getPosition().y-PLAYER_OFFSET_Y);
        if(!facingRight){flip(true,false);}

        Vector2 vel = playerBody.getLinearVelocity();
        Vector2 pos = playerBody.getPosition();

        switch(currentDirection){
            case UP:
                if (vel.y == 0) {
                    playerBody.applyLinearImpulse(0, jumpforce, pos.x, pos.y, true);
                }
                break;
            case LEFT:
                facingRight = false;
                playerBody.setLinearVelocity(-movespeed, vel.y);
                break;
            case RIGHT:
                facingRight = true;
                playerBody.setLinearVelocity(movespeed, vel.y);
                break;
            case NONE:
                playerBody.setLinearVelocity(0, vel.y);
        }
    }

    public void move(CurrentDirection direction){
        switch(direction){
            case LEFT:
                this.currentDirection = CurrentDirection.LEFT;
                setAnimation(runAnimation);
                break;
            case RIGHT:
                this.currentDirection = CurrentDirection.RIGHT;
                setAnimation(runAnimation);
                break;
            case UP:
                this.currentDirection = CurrentDirection.UP;
                break;
            case NONE:
                this.currentDirection = CurrentDirection.NONE;
                setAnimation(idleAnimation);
        }
    }

    @Override
    public FixtureDef getFixtureDef(float density, float friction, float restitution) {
        //prepare for Fixture definition
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth()/2)-.75f,getHeight()/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution=restitution;
        return fixtureDef;
    }

    @Override
    public void reaction() {

    }

    public void addAmmo(int theAmmo)
    {
        ammo += theAmmo;
    }
}
