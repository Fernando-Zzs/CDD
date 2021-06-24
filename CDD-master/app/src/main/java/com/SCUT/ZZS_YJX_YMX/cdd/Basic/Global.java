package com.SCUT.ZZS_YJX_YMX.cdd.Basic;

import android.content.Context;

import com.SCUT.ZZS_YJX_YMX.cdd.Components.Card;

import java.util.ArrayList;

public class Global {
    public static Context surfaceContext;
    public static boolean proxy = false; // 记录是否托管
    public static String encodedString = "";
    public static String SendCard = ""; // 用于接收另一玩家发送的消息
    public static boolean isSend = false; // 判断当前是否是出牌后的那一帧
    public static String outCards; // 当前控制的玩家的出牌信息，仅用于发送
    public static boolean isServer = false;
    public static int player_id;
    public static boolean firstHand = true; // 用于记录是否为开局第一步
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
