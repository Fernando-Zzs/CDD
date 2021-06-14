package com.saveandstudio.mario.cdd.GameBasic;

import android.content.Context;
import android.util.Log;

import com.saveandstudio.mario.cdd.Components.Card;
import com.saveandstudio.mario.cdd.Components.CardSystem;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Global {
    public static Context surfaceContext;
    public static String encodedString = "";
    public static String SendCard = "";
    public static boolean isServer = false;
    public static int player_id;
    public static boolean firstHand = true;
    public static ArrayList<Card> cardPackages = new ArrayList<>();
    public static int client_get_data_count = 0;
    public static int server_get_data_count = 0;
    public static int seed;

    public static void encode() {
        for (int i = 0, len = cardPackages.size(); i < len; i++) {
            encodedString += cardPackages.get(i).figure + "," + cardPackages.get(i).suit;
            if (i < len - 1) {
                encodedString += ",";
            }
        }
    }

    public Global() {
    }
}
