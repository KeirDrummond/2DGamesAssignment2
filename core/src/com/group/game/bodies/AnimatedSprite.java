package com.group.game.bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

import static com.group.game.utility.Constants.FRAME_DURATION;
import static com.group.game.utility.Constants.PLAYER_HEIGHT;
import static com.group.game.utility.Constants.PLAYER_WIDTH;


/**
 * Created by gerard on 09/11/2016.
 * updated 02/03/18
 */

public abstract class AnimatedSprite extends Sprite {
    protected Animation animation;
    protected Animation.PlayMode playmode;
    private TextureAtlas atlas;

    public AnimatedSprite(String atlasString, Texture t, Vector2 pos){
        super(t,PLAYER_WIDTH,PLAYER_HEIGHT);
        this.setX(pos.x);
        this.setY(pos.y);
        playmode = Animation.PlayMode.LOOP;
        initAtlas(atlasString);
    }

    public void update(float animationTime){
        this.setRegion((TextureRegion) animation.getKeyFrame(animationTime));
    }

    //Creates default animation using entire atlas file.
    private void initAtlas(String atlasString){
        atlas = new TextureAtlas(Gdx.files.internal(atlasString));
        //load animations
        Array<TextureAtlas.AtlasRegion> regions = new
                Array<TextureAtlas.AtlasRegion>(atlas.getRegions());
        regions.sort(new RegionComparator());
        animation = new Animation(FRAME_DURATION,regions, Animation.PlayMode.LOOP);
    }

    //New method added for animated sprites with multiple animations.
    public Animation newAnimation(String atlasString, String tag){
        atlas = new TextureAtlas(Gdx.files.internal(atlasString));
        Animation theAnimation;
        //load animations
        Array<TextureAtlas.AtlasRegion> regions = new
                Array<TextureAtlas.AtlasRegion>(atlas.findRegions(tag));
        regions.sort(new RegionComparator());
        theAnimation = new Animation(FRAME_DURATION,regions, Animation.PlayMode.LOOP);
        return theAnimation;
    }

    private static class RegionComparator implements Comparator<TextureAtlas.AtlasRegion> {
        @Override
        public int compare(TextureAtlas.AtlasRegion region1, TextureAtlas.AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }

    //New method added for changing animations on one object.
    public void setAnimation(Animation theAnimation){this.animation = theAnimation;}

}