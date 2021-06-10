package com.saveandstudio.mario.cdd.GameBasic;

import android.util.Log;
import static android.content.ContentValues.TAG;

import com.saveandstudio.mario.cdd.Components.Card;

public class Decoder {
//    //suit
//    final static int DIAMOND = 0;
//    final static int CLUB = 1;
//    final static int HEART = 2;
//    final static int SPADE = 3;
//    //figure
//    final static int THREE = 0;
//    final static int FOUR = 1;
//    final static int FIVE = 2;
//    final static int SIX = 3;
//    final static int SEVEN = 4;
//    final static int EIGHT = 5;
//    final static int NINE = 6;
//    final static int TEN = 7;
//    final static int JOKER = 8;
//    final static int QUEEN = 9;
//    final static int KING = 10;
//    final static int ACE = 11;
//    final static int TWO = 12;
    String message = "0,1,1,0,2,0,2,3,4,0,5,0,6,0,8,0,8,1,10,0,10,2,10,3,11,1,1,2,3,0,3,2,4,1,4,2,5,1,7,0,7,1,7,3,8,2,8,3,9,0,12,1,0,2,0,3,1,3,4,3,6,1,6,2,7,2,9,2,10,1,11,0,11,2,12,2,12,3,0,0,1,1,2,1,2,2,3,1,3,3,5,2,5,3,6,3,9,1,9,3,11,3,12,0";
    String substring = message.substring(0, message.length()-1);
    String[] result = substring.split(",");

    public int suit;
    public int figure;

    public Decoder(){}

    public void decode(){
        Card card = new Card();

        for (int i = 0; i < result.length; i++){
            Log.d(TAG, "decode() called with: str = [" + result[i] + "]");

        }

    }
}
