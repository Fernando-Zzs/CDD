package com.SCUT.ZZS_YJX_YMX.cdd.Scenes;

import com.SCUT.ZZS_YJX_YMX.cdd.Components.*;
import com.SCUT.ZZS_YJX_YMX.cdd.Basic.*;
import com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Game;
import com.SCUT.ZZS_YJX_YMX.cdd.R;
import com.SCUT.ZZS_YJX_YMX.cdd.Renderers.ButtonRenderer;

import java.util.ArrayList;

public class Scene {

    public ArrayList<GameObject> gameObjectsList;
    public static boolean prepared = false;
    public boolean clear = false;

    private static Scene instantce;

    public static Scene getInstance() {
        if (instantce == null) {
            instantce = new Scene();
            prepared = false;
        }
        return instantce;
    }

    public void prePareScene() {
        clear = false;
        Game game = new Game();
        if(Global.player_id == 0){
            com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player player = new com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player(0, new Transform(new Vector3(GameViewInfo.centerW, GameViewInfo.centerH + 610, 0), 0,
                    Vector3.one), true, 0);
            //player.addComponent(new Robot());
            com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player Rplayer1 = new com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player(1, new Transform(new Vector3(GameViewInfo.centerW + 400, GameViewInfo.centerH - 50, 0), 0,
                    Vector3.one), true, 1);
            Rplayer1.addComponent(new OtherPlayer());
            com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player Rplayer2 = new com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player(0, new Transform(new Vector3(GameViewInfo.centerW, GameViewInfo.centerH - 650, 0), 0,
                    Vector3.one), false, 2);
            Rplayer2.addComponent(new Robot());
            com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player Rplayer3 = new com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player(1, new Transform(new Vector3(GameViewInfo.centerW - 400, GameViewInfo.centerH - 50, 0), 0,
                    Vector3.one), false, 3);
            Rplayer3.addComponent(new Robot());

            //show card button
            GameObject showCardButton = new GameObject(new Transform(new Vector3(GameViewInfo.centerW + 120, GameViewInfo.centerH + 310, 100), 0,
                    new Vector3((float) 1.5, (float) 1.5, 1), Vector3.zero));
            showCardButton.addComponent(new ButtonRenderer(R.mipmap.show_card_up, R.mipmap.show_card_down, R.mipmap.show_card_lock));
            showCardButton.addComponent(new AutoCore());
            showCardButton.addComponent(new BoxTouch());
            showCardButton.addComponent(new AutoTouch());
            showCardButton.addComponent(new ShowCardButton((Player) player.getComponent(Player.class)));
            //pass button
            GameObject passButton = new GameObject(new Transform(new Vector3(GameViewInfo.centerW - 120, GameViewInfo.centerH + 310, 100), 0,
                    new Vector3((float) 1.5, (float) 1.5, 1), Vector3.zero));
            passButton.addComponent(new ButtonRenderer(R.mipmap.pass_up, R.mipmap.pass_down, R.mipmap.pass_lock));
            passButton.addComponent(new AutoCore());
            passButton.addComponent(new BoxTouch());
            passButton.addComponent(new AutoTouch());
            passButton.addComponent(new PassButton((Player) player.getComponent(Player.class)));
            prepared = true;
        }
        else if(Global.player_id == 1){
            com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player player = new com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player(1, new Transform(new Vector3(GameViewInfo.centerW - 400, GameViewInfo.centerH - 50, 0), 0,
                    Vector3.one), true, 0);
            player.addComponent(new OtherPlayer());
            com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player Rplayer1 = new com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player(0, new Transform(new Vector3(GameViewInfo.centerW, GameViewInfo.centerH + 610, 0), 0,
                    Vector3.one), true, 1);
            //Rplayer1.addComponent(new Robot());
            com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player Rplayer2 = new com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player(1, new Transform(new Vector3(GameViewInfo.centerW + 400, GameViewInfo.centerH - 50, 0), 0,
                    Vector3.one), false, 2);
            Rplayer2.addComponent(new Robot());
            com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player Rplayer3 = new com.SCUT.ZZS_YJX_YMX.cdd.Prefabs.Player(0, new Transform(new Vector3(GameViewInfo.centerW, GameViewInfo.centerH - 650, 0), 0,
                    Vector3.one), false, 3);
            Rplayer3.addComponent(new Robot());

            //show card button
            GameObject showCardButton = new GameObject(new Transform(new Vector3(GameViewInfo.centerW + 120, GameViewInfo.centerH + 310, 100), 0,
                    new Vector3((float) 1.5, (float) 1.5, 1), Vector3.zero));
            showCardButton.addComponent(new ButtonRenderer(R.mipmap.show_card_up, R.mipmap.show_card_down, R.mipmap.show_card_lock));
            showCardButton.addComponent(new AutoCore());
            showCardButton.addComponent(new BoxTouch());
            showCardButton.addComponent(new AutoTouch());
            showCardButton.addComponent(new ShowCardButton((Player) Rplayer1.getComponent(Player.class)));
            //pass button
            GameObject passButton = new GameObject(new Transform(new Vector3(GameViewInfo.centerW - 120, GameViewInfo.centerH + 310, 100), 0,
                    new Vector3((float) 1.5, (float) 1.5, 1), Vector3.zero));
            passButton.addComponent(new ButtonRenderer(R.mipmap.pass_up, R.mipmap.pass_down, R.mipmap.pass_lock));
            passButton.addComponent(new AutoCore());
            passButton.addComponent(new BoxTouch());
            passButton.addComponent(new AutoTouch());
            passButton.addComponent(new PassButton((Player) Rplayer1.getComponent(Player.class)));
            prepared = true;
        }
    }

    public void Clear() {
        if (clear) {
            clearGameObjects();
            clear = false;
            instantce = null;
        }
    }

    public void clearGameObjects() {
        for (int i = 0; i < gameObjectsList.size(); i++) {
            gameObjectsList.get(i).Destroy();
        }
        gameObjectsList.clear();
        instantce = null;

    }

}
