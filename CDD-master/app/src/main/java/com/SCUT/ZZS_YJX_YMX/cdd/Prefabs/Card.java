package com.SCUT.ZZS_YJX_YMX.cdd.Prefabs;

import com.SCUT.ZZS_YJX_YMX.cdd.Basic.*;
import com.SCUT.ZZS_YJX_YMX.cdd.Components.*;
import com.SCUT.ZZS_YJX_YMX.cdd.Renderers.*;
import com.SCUT.ZZS_YJX_YMX.cdd.R;

public class Card extends GameObject {
    public Card(){
        this(0, 0);
    }
    public Card(int suit, int figure){
        this(suit, figure, new Transform(), 0);
    }
    public Card(int suit, int figure, Transform transform, int ID){
        super(transform);
        com.SCUT.ZZS_YJX_YMX.cdd.Components.Card card =
                (com.SCUT.ZZS_YJX_YMX.cdd.Components.Card) addComponent(new com.SCUT.ZZS_YJX_YMX.cdd.Components.Card());
        card.setCard(suit, figure);
        card.setID(ID);
        addComponent(new CardRenderer(R.mipmap.default_sprite, R.mipmap.default_sprite));
        addComponent(new AutoCore());
        addComponent(new TransformAnimation());
    }
}
