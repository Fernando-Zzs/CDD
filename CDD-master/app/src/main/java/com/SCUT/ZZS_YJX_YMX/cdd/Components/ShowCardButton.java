package com.SCUT.ZZS_YJX_YMX.cdd.Components;

import com.SCUT.ZZS_YJX_YMX.cdd.Basic.*;
import com.SCUT.ZZS_YJX_YMX.cdd.Renderers.ButtonRenderer;

public class ShowCardButton extends MonoBehavior {

    BoxTouch collider;
    Transform transform;
    TransformAnimation transformAnimation;
    ButtonRenderer renderer;
    Player manager;
    private boolean waiting = false;
    private long startWaitTime = 0;
    private long waitTime = 1000;

    public ShowCardButton(Player manager) {
        this.manager = manager;
    }

    @Override
    public void Start() {
        collider = (BoxTouch) getComponent(BoxTouch.class);
        transform = (Transform) getComponent(Transform.class);
        transformAnimation = (TransformAnimation) getComponent(TransformAnimation.class);
        renderer = (ButtonRenderer) getComponent(ButtonRenderer.class);
        waiting = false;
    }

    @Override
    public void Update() {
        if (GameSystem.getInstance().someOneWin) {
            if (manager.turn) {
                if (!waiting) {
                    startWaitTime = System.currentTimeMillis();
                    waiting = true;
                } else {
                    if (System.currentTimeMillis() - startWaitTime >= waitTime) {
                        GameSystem.getInstance().showWinState();
                    }
                }
            }
        } else {
            if (!manager.enableShowCard) {
                renderer.setDisable();
            } else {
                renderer.setNormal();
                if (Input.touching) {
                    if (collider != null) {
                        if (Physics.raycast(Input.touchPosition) == collider) {
                            renderer.setFocus();
                        } else {
                            renderer.setNormal();
                        }
                    }

                }
                if (Input.touchUp && Physics.raycast(Input.touchPosition) == collider) {
                    manager.showCardHandler();
                }

            }

        }

    }
}
