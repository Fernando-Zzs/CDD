package com.SCUT.ZZS_YJX_YMX.cdd.Components;

import android.util.Log;

import com.SCUT.ZZS_YJX_YMX.cdd.Basic.MonoBehavior;

import com.SCUT.ZZS_YJX_YMX.cdd.Basic.Global;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class OtherPlayer extends MonoBehavior {
    Player manager;
    public ArrayList<Card> handCards;

    @Override
    public void Start() {
        manager = (Player) getComponent(Player.class);
    }

    public void Update() {
        if (manager.turn) { // 轮到了这个玩家
            if (!Global.firstHand) { // 不是先手
                if (GameSystem.getInstance().someOneWin) { // 判断当前牌局是否结束
                    GameSystem.getInstance().showWinState();
                }
                if (Global.isSend){ // 判断是否是刚收到信息的那一帧
                    Log.d(TAG, "Update: sendCard"+Global.SendCard);
                    if (Global.SendCard.equals("-1")) { // 过
                        manager.passHandler();
                        Log.d("0", "Update: pass");
                    } else { // 出牌
                        handCards = new ArrayList<>();
                        handCards = manager.getCards();
                        String[] result = Global.SendCard.split(",");
                        for (int i = 0; i < result.length; i++) { //外层循环遍历字符串
                            for (int j = 0; j < handCards.size(); j++) { // 内层循环遍历手牌
                                if (handCards.get(j).ID == Integer.parseInt(result[i])) {
                                    manager.addChosenCard(handCards.get(j));
                                }
                            }
                        }
                        manager.showCardHandler();
                    }
                    Global.isSend = false;
                }
            } else { // 是先手，则等待global消息
                Log.d(TAG, "Update: 先手");
                if (Global.isSend){ // 判断是否是刚收到信息的那一帧
                    if (Global.SendCard == "-1") { // 过
                        manager.passHandler();
                    } else { // 出牌
                        handCards = new ArrayList<>();
                        handCards = manager.getCards();
                        String[] result = Global.SendCard.split(",");
                        for (int i = 0; i < result.length; i++) { //外层循环遍历字符串
                            for (int j = 0; j < handCards.size(); j++) { // 内层循环遍历手牌
                                if (handCards.get(j).ID == Integer.parseInt(result[i])) {
                                    manager.addChosenCard(handCards.get(j));
                                }
                            }
                        }
                        manager.showCardHandler();
                    }
                    Global.isSend = false;
                }
            }
        }
    }
}
