package com.saveandstudio.mario.cdd.GameBasic;

import com.saveandstudio.mario.cdd.Components.Card;

import java.util.ArrayList;

public class Decoder {
    public String message;
    public int suit;
    public int figure;
    public ArrayList<Card> CardPackages;

    public Decoder(String str) {
        message = str;
    }

    public ArrayList<Card> decode() { // 解码初始化牌局的卡包
        String substring = message.substring(0, message.length());
        String[] result = substring.split(",");
        CardPackages = new ArrayList<>();
        for (int i = 0; i < result.length; i += 2) {
            Card card = new Card();
            card.setCard(Integer.parseInt(result[i + 1]), Integer.parseInt(result[i]));
            CardPackages.add(card);
        }
        return CardPackages;
    }

    public ArrayList<Card> decodeCard(String str) { // 解码另一玩家发送的信息（局中操作）
        ArrayList<Card> cards = new ArrayList<>();
        String[] result = str.split(",");
        for (int i = 0; i < result.length; i += 2) {
            Card card = new Card();
            card.setCard(Integer.parseInt(result[i + 1]), Integer.parseInt(result[i]));
            cards.add(card);
        }
        return cards;
    }
}
