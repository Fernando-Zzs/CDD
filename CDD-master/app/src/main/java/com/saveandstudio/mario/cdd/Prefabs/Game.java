package com.saveandstudio.mario.cdd.Prefabs;

import com.saveandstudio.mario.cdd.Components.GameSystem;
import com.saveandstudio.mario.cdd.GameBasic.GameObject;
import com.saveandstudio.mario.cdd.GameBasic.Transform;

public class Game extends GameObject {
    public Game(){
        super(new Transform());
        addComponent(GameSystem.getInstance());
        GameSystem.getInstance().newGame();
    }
}
