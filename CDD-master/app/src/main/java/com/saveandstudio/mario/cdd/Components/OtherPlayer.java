package com.saveandstudio.mario.cdd.Components;

import android.util.Log;
import com.saveandstudio.mario.cdd.GameBasic.MonoBehavior;

import com.saveandstudio.mario.cdd.GameBasic.Global;
import com.saveandstudio.mario.cdd.GameBasic.Decoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import static android.content.ContentValues.TAG;

public class OtherPlayer extends MonoBehavior {
    HandCardManager manager;
    private boolean sendMessage = true; // 是否收到了消息
    public Decoder decoder;
    public ArrayList<Card> handCards;

    @Override
    public void Start() {
        manager = (HandCardManager) getComponent(HandCardManager.class);
    }

    public void Update() {
        if (manager.turn) { // 轮到了这个玩家
            if (!Global.firstHand) { // 不是先手
                if (CardSystem.getInstance().someOneWin) { // 判断当前牌局是否结束
                    CardSystem.getInstance().showWinState();
                }
                if (sendMessage) { // 如果收到信息
                    if (Global.SendCard == "") { // 过
                        manager.passHandler();
                    } else { // 出牌
                        ArrayList<Card> ret_card = new ArrayList<>();
                        ret_card = decoder.decodeCard(Global.SendCard);
                        for (int i = 0; i < ret_card.size(); i++) {
                            manager.addChosenCard(ret_card.get(i));
                            Log.d(TAG, "出的牌是: " + ret_card.get(i).suit + ret_card.get(i).figure);
                        }
                        manager.showCardHandler();
                    }
                }
            }
            else {
                if(sendMessage) {
//                    ArrayList<Card> ret_card = new ArrayList<>();
//                    ret_card = decoder.decodeCard(Global.SendCard);
//                    for (int i = 0; i < ret_card.size(); i++) {
//                        manager.addChosenCard(ret_card.get(i));
//                        Log.d(TAG, "出的牌是: " + ret_card.get(i).suit + ret_card.get(i).figure);
//                    }
//                    manager.showCardHandler();
                    // 当前为了测试，默认打出第一张牌
                    handCards = new ArrayList<>();
                    handCards = manager.getCards();
                    manager.addChosenCard(handCards.get(0));
                    manager.showCardHandler();
                }
            }
        }
    }
}
