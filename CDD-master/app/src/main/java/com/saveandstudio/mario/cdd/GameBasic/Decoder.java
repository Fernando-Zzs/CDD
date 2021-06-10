package com.saveandstudio.mario.cdd.GameBasic;

import android.util.Log;
import static android.content.ContentValues.TAG;

import com.saveandstudio.mario.cdd.Components.Card;

import java.util.ArrayList;

public class Decoder {
    String message = "0,1,1,0,2,0,2,3,4,0,5,0,6,0,8,0,8,1,10,0,10,2,10,3,11,1,1,2,3,0,3,2,4,1,4,2,5,1,7,0,7,1,7,3,8,2,8,3,9,0,12,1,0,2,0,3,1,3,4,3,6,1,6,2,7,2,9,2,10,1,11,0,11,2,12,2,12,3,0,0,1,1,2,1,2,2,3,1,3,3,5,2,5,3,6,3,9,1,9,3,11,3,12,0";
    String substring = message.substring(0, message.length());
    String[] result = substring.split(",");

    public int suit;
    public int figure;

    public ArrayList<Card> CardPackages;

    public Decoder(){}

    public ArrayList<Card> decode(){
        CardPackages = new ArrayList<>();
        for (int i = 0; i < result.length; i+=2){
            //Log.d(TAG, "decode() called with: str = [" + result[i] + "]");
            Card card = new Card();
            card.setCard(Integer.parseInt(result[i+1]),Integer.parseInt(result[i]));
            CardPackages.add(card);
        }
        return CardPackages;
    }
}
