package com.saveandstudio.mario.cdd.GameBasic;

import android.util.Log;

import static android.content.ContentValues.TAG;

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

    public ArrayList<Card> decode() {
        String substring = message.substring(0, message.length());
        String[] result = substring.split(",");
        CardPackages = new ArrayList<>();
        for (int i = 0; i < result.length; i += 2) {
            //Log.d(TAG, "decode() called with: str = [" + result[i] + "]");
            Card card = new Card();
            card.setCard(Integer.parseInt(result[i + 1]), Integer.parseInt(result[i]));
            CardPackages.add(card);
        }
        return CardPackages;
    }
}
