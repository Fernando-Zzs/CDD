package com.SCUT.ZZS_YJX_YMX.cdd.Prefabs;

import com.SCUT.ZZS_YJX_YMX.cdd.Components.*;
import com.SCUT.ZZS_YJX_YMX.cdd.Basic.*;
import com.SCUT.ZZS_YJX_YMX.cdd.R;

public class Player extends GameObject {
    public Player() {
        this(0, new Transform(), false, 100);
    }

    public Player(int direction, Transform transform, boolean isPlayer, int id) {
        super(transform);
        CardTable cardTable = (CardTable) addComponent(new CardTable(direction));

        GameObject pass = new GameObject(new Transform());
        ((Transform) pass.getComponent(Transform.class)).setScale(Vector3.zero);
        Vector3 position = Vector3.lerp(new Vector3(GameViewInfo.centerW, GameViewInfo.centerH - 100, 0), transform.getPosition(), (float) 0.5).add(
                cardTable.calculateOutPosition(0, 1, 0));
        pass.addComponent(new Renderer(R.mipmap.pass));
        pass.addComponent(new TransformAnimation());
        position.z = -120;
        ((Transform) pass.getComponent(Transform.class)).setPosition(position);
        pass.addComponent(new AutoCore());


        GameSystem.getInstance().addPlayer((com.SCUT.ZZS_YJX_YMX.cdd.Components.Player) addComponent(new com.SCUT.ZZS_YJX_YMX.cdd.Components.Player(isPlayer, id, pass)));
    }
}
