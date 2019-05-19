package com.group.game.utility;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.group.game.bodies.PlayerCharacter;
import com.group.game.physics.WorldManager;
import com.group.game.powerUps.PowerUpSprite;
import com.group.game.powerUps.RevolverAmmo;

import java.util.Iterator;

//import static com.group.game.utility.Constants.MAX_TIME_TO_NEXT_BONUS;
import static com.group.game.utility.Constants.MEDIUM;
import static com.group.game.utility.Constants.REVOLVER_AMMO_PICKUP_PATH;
import static com.group.game.utility.Constants.SMALL;
import static com.group.game.utility.Constants.TILE_SIZE;

/* Uses references that will point to the assignment 1 work.
* Will not function correctly until the proper references are fixed.
*
*/

public class BonusManager {
    PowerUpSprite[] bonusCollection;
    PlayerCharacter playerCharacter;
    /* UNUSED
    private int bonusSpriteToDisplay;
    private float timeCount;
    private float timeToNextBonus = MAX_TIME_TO_NEXT_BONUS;

    public static boolean handlingCollision = false;
    private Vector2 previousBonusPosition;
    */

    public BonusManager(PlayerCharacter playerCharacter){
        this.playerCharacter = playerCharacter;
        GetPowerUps(WorldManager.getInstance().getMap(), "PowerUps");
    }

    public void GetPowerUps(Map map, String layerName)
    {
        MapLayer layer = map.getLayers().get(layerName);

        if (layer == null) {
            return;
        }

        MapObjects objects = layer.getObjects();
        Iterator<MapObject> objectIt = objects.iterator();

        bonusCollection = new PowerUpSprite[objects.getCount()];

        int i = 0;

        while(objectIt.hasNext()) {
            MapObject object = objectIt.next();
            String powerName = "null";

            if (((TiledMapTileMapObject) object).getTile().getProperties().containsKey("Name")) {
                powerName = ((TiledMapTileMapObject) object).getTile().getProperties().get("Name").toString();
            }

            float x = ((TiledMapTileMapObject)object).getX();
            float y = ((TiledMapTileMapObject)object).getY();
            float units = TILE_SIZE;
            Vector2 pos = new Vector2(x / units, y / units);

            if (powerName.equals("RevolverAmmo"))
            {
                bonusCollection[i] = new RevolverAmmo(REVOLVER_AMMO_PICKUP_PATH, SMALL, pos);
            }
            else
            {
                bonusCollection[i] = null;
            }

            i++;
        }
    }

    public void draw(SpriteBatch batch){
        if (bonusCollection != null)
            for(int i=0;i<bonusCollection.length;i++){
                if (bonusCollection[i] != null)
                    if(bonusCollection[i].isDisplayed()){
                        bonusCollection[i].draw(batch);
                    }
            }
    }

    public void update(float frameDelta) {
        if (bonusCollection != null)
            for(int i=0;i<bonusCollection.length;i++)
            {
                if(bonusCollection[i] != null)
                    if(bonusCollection[i].isDisplayed()){
                        bonusCollection[i].update(frameDelta);
                        if(Intersector.overlaps(playerCharacter.getBoundingRectangle(), bonusCollection[i].GetRectangle()))
                        {
                            bonusCollection[i].intersected(playerCharacter);
                        }
                    }
            }
    }
}