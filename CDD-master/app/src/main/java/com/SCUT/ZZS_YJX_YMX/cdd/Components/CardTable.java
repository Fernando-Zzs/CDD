package com.SCUT.ZZS_YJX_YMX.cdd.Components;

import com.SCUT.ZZS_YJX_YMX.cdd.Basic.MonoBehavior;
import com.SCUT.ZZS_YJX_YMX.cdd.Basic.Vector3;

public class CardTable extends MonoBehavior {

    private int direction;//0 is horizontal, 1 is vertical;
    private float gapDistance = 30;

    public CardTable(){
        this(0);
    }
    public CardTable(int direction){
        this.direction = direction;
    }
    public Vector3 calculatePosition(int cardIndex, int cardAmount){
        Vector3 position = new Vector3();
        float centerCard = (float)cardAmount / 2;
        position.x = (((float)cardIndex - centerCard) * gapDistance) * (1 - direction);
        position.y = (((float)cardIndex - centerCard) * gapDistance * 2) * direction;
        position.z = cardIndex;
        return position;
    }
    public Vector3 calculateOutPosition(int cardIndex, int cardAmount, int turn){
        Vector3 position = new Vector3();
        float centerCard = (float)cardAmount / 2;
        position.x = (((float)cardIndex - centerCard) * gapDistance);
        position.z = (cardIndex + 1) + turn * 13;
        return position;
    }
}
