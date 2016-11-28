package base.engine.lonefly.game.engine.util;

import base.engine.lonefly.game.engine.entity.Entity;

/*
 * Author: Rice Shelley
 * Version: 5/5/2016
 * Purpose: Interface for overriding the game collision method in Activity 
 */
public interface Collision {

    public void gameCollision(Entity a, Entity b);

}
