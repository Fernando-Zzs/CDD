package com.saveandstudio.mario.cdd.Components;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.saveandstudio.mario.cdd.GameBasic.Decoder;

import com.saveandstudio.mario.cdd.GameActivity;
import com.saveandstudio.mario.cdd.GameBasic.*;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.ContentValues.TAG;

public class HandCardManager extends MonoBehavior {
    public ArrayList<Card> handCards;
    private ArrayList<Card> outCards;
    public ArrayList<Card> cardPackages;
    private CardDesk cardDesk;
    private boolean isPlayer = false;
    private Transform transform;
    private boolean updatePosition = false;
    private ArrayList<Card> chosenCards;
    private int id;
    private TransformToTarget passT;
    private Transform pass;

    public boolean enableShowCard = false;
    public boolean enablePass = false;
    public boolean turn;
    public static int count = 0;
    public CardSystem cardSystem = new CardSystem();
    public GameActivity gameActivity = new GameActivity();

    public HandCardManager(boolean isPlayer, int id, GameObject pass) {
        this.isPlayer = isPlayer;
        this.id = id;
        this.pass = (Transform) pass.getComponent(Transform.class);
        passT = (TransformToTarget) pass.getComponent(TransformToTarget.class);
    }

    @Override
    public void Start() {
        cardDesk = (CardDesk) getComponent(CardDesk.class);
        transform = (Transform) getComponent(Transform.class);
        chosenCards = new ArrayList<>();
        Vector3 position = transform.getPosition();
        handCards = new ArrayList<>();
        outCards = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            Card card = CardSystem.getInstance().deliverCard();

            card.setManager(this);
            if (isPlayer && Global.player_id == this.id) {
                card.addComponent(new BoxCollider());
                card.addComponent(new AutoCollider());
                card.addComponent(new TouchCardEvents());
                card.setSide(true);
            }

            handCards.add(card);

            Collections.sort(handCards);
            if (card.getSuit() + card.getFigure() == 0) {
                CardSystem.getInstance().setFirstTurn(id);
            }

            if (i == 12) {
                count++;
            }
            if (count == 4) {
                Global.encodedString = encode();
                if (Global.player_id == 1) {
                    cardPackages = CardSystem.getInstance().clientGetCards();
                }
            }
        }
        updatePositions();
    }

    public void updatePositions() {
        float speed = 30 * GameViewInfo.deltaTime;
        for (int i = 0; i < handCards.size(); i++) {
            Card card = handCards.get(i);
            card.position = transform.getPosition().add(
                    cardDesk.calculatePosition(i, handCards.size()));
//            Log.d(TAG, "updatePositions: " + card.position.x + card.position.y + card.position.z);
            card.transformToTarget.beginMove(card.position, speed);
        }
    }

    public void outCardAnimation() {
        float speed = 30 * GameViewInfo.deltaTime;
        for (int i = 0; i < outCards.size(); i++) {
            Card card = outCards.get(i);
            card.position = Vector3.lerp(new Vector3(GameViewInfo.centerW, GameViewInfo.centerH - 100, 0), transform.getPosition(), (float) 0.3).add(
                    cardDesk.calculateOutPosition(i, outCards.size(), CardSystem.getInstance().getTurnAmount()));
            card.transformToTarget.beginMove(card.position, speed);
            card.intractable = false;
        }
        passT.beginScale(Vector3.zero, speed);
    }

    public void clearOutCards() {
        //Animation
        float speed = 30 * GameViewInfo.deltaTime;
        for (int i = 0; i < outCards.size(); i++) {
            Card card = outCards.get(i);
            card.transformToTarget.beginScale(Vector3.zero, speed);
        }
        pass.setScale(Vector3.zero);
        passT.beginScale(Vector3.one, speed);
        outCards.clear();

    }

    public void addChosenCard(Card card) {
        chosenCards.add(card);
        Collections.sort(chosenCards);
    }

    public void removeChosenCard(Card card) {
        chosenCards.remove(card);
        Collections.sort(chosenCards);
    }

    public void showCardHandler() {
        clearOutCards();
        outCards.addAll(chosenCards);
        //Animation
        outCardAnimation();
        //Judge
        CardSystem.getInstance().judgeCards(chosenCards);
        //Show
        for (int i = 0; i < chosenCards.size(); i++) {
            chosenCards.get(i).setSide(true);
        }
        CardSystem.getInstance().showCards(chosenCards);
        handCards.removeAll(chosenCards);
        updatePositions();
        chosenCards.clear();
        turn = false;
    }

    public void passHandler() {
        clearOutCards();
        CardSystem.getInstance().pass();
        turn = false;
    }

    @Override
    public void Update() {
        enableShowCard = CardSystem.getInstance().judgeCards(chosenCards) && turn;
        if (CardSystem.getInstance().getTurnAmount() == 0 || CardSystem.getInstance().getLastPlayerID() == id)
            enablePass = false;
        else
            enablePass = turn;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Card> getCards() {
        return handCards;
    }

    public String encode() {
//        String play0_str = "";
//        String play1_str = "";
//        String play2_str = "";
//        String play3_str = "";
//
//        for (int i = 0; i < 13; i++) {
//            play0_str += CardSystem.players.get(0).handCards.get(i).figure + "," + CardSystem.players.get(0).handCards.get(i).suit + ",";
//        }
//        for (int i = 0; i < 13; i++) {
//            play1_str += CardSystem.players.get(1).handCards.get(i).figure + "," + CardSystem.players.get(1).handCards.get(i).suit + ",";
//        }
//        for (int i = 0; i < 13; i++) {
//            play2_str += CardSystem.players.get(2).handCards.get(i).figure + "," + CardSystem.players.get(2).handCards.get(i).suit + ",";
//        }
//        for (int i = 0; i < 13; i++) {
//            play3_str += CardSystem.players.get(3).handCards.get(i).figure + "," + CardSystem.players.get(3).handCards.get(i).suit + ",";
//        }
//        Log.d(TAG, "encode: " + play0_str + play1_str + play2_str + play3_str);
//
//        return play0_str + play1_str + play2_str + play3_str;
        return "0,1,1,0,2,0,2,3,4,0,5,0,6,0,8,0,8,1,10,0,10,2,10,3,11,1,1,2,3,0,3,2,4,1,4,2,5,1,7,0,7,1,7,3,8,2,8,3,9,0,12,1,0,2,0,3,1,3,4,3,6,1,6,2,7,2,9,2,10,1,11,0,11,2,12,2,12,3,0,0,1,1,2,1,2,2,3,1,3,3,5,2,5,3,6,3,9,1,9,3,11,3,12,0";
    }
}
