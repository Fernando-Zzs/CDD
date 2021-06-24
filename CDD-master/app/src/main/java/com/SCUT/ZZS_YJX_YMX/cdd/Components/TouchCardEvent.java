package com.SCUT.ZZS_YJX_YMX.cdd.Components;

import com.SCUT.ZZS_YJX_YMX.cdd.Basic.*;

public class TouchCardEvent extends MonoBehavior {
    Card card;
    BoxTouch collider;
    Transform transform;
    TransformAnimation transformAnimation;
    private boolean selecting = false;
    private boolean canSet = true;

    @Override
    public void Start() {
        card = (Card) getComponent(Card.class);
        collider = (BoxTouch) getComponent(BoxTouch.class);
        transform = (Transform) getComponent(Transform.class);
        transformAnimation = (TransformAnimation) getComponent(TransformAnimation.class);
        selecting = false;
        canSet = true;
    }

    @Override
    public void Update() {
        if (card.intractable) {
            if (Input.touching && canSet) {
                if (collider != null) {
                    if (Physics.raycast(Input.touchPosition) == collider) {
                        selectAnimation();
                        canSet = false;
                    }
                }
            }
            if (Input.touchUp) {
                canSet = true;
            }
        }
    }

    private void selectAnimation() {
        Vector3 targetPosition = card.position.clone();
        if (selecting) {
            card.getManager().removeChosenCard(card);
            selecting = false;
        } else {
            card.getManager().addChosenCard(card);
            targetPosition.y -= 50;
            selecting = true;
        }
        transformAnimation.beginMove(targetPosition, 30 * GameViewInfo.deltaTime);
    }

}
