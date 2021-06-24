package com.SCUT.ZZS_YJX_YMX.cdd.Prefabs;

import com.SCUT.ZZS_YJX_YMX.cdd.Components.GameSystem;
import com.SCUT.ZZS_YJX_YMX.cdd.Basic.GameObject;
import com.SCUT.ZZS_YJX_YMX.cdd.Basic.Transform;

public class Game extends GameObject {
    public Game(){
        super(new Transform());
        addComponent(GameSystem.getInstance());
        GameSystem.getInstance().newGame();
    }
}
