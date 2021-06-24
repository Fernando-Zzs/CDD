package com.SCUT.ZZS_YJX_YMX.cdd.Components;

import com.SCUT.ZZS_YJX_YMX.cdd.Basic.*;
import com.SCUT.ZZS_YJX_YMX.cdd.Renderers.ButtonRenderer;

public class PassButton extends MonoBehavior {

    BoxTouch collider;
    Transform transform;
    TransformAnimation transformAnimation;
    ButtonRenderer renderer;
    Player manager;

    public PassButton(Player manager){
        this.manager = manager;
    }

    @Override
    public void Start() {
        collider = (BoxTouch)getComponent(BoxTouch.class);
        transform = (Transform)getComponent(Transform.class);
        transformAnimation = (TransformAnimation)getComponent(TransformAnimation.class);
        renderer = (ButtonRenderer)getComponent(ButtonRenderer.class);
    }

    @Override
    public void Update() {
        if(!manager.enablePass){
            renderer.setDisable();
        }
        else {
            renderer.setNormal();
            if(Input.touching){
                if(collider != null){
                    if(Physics.raycast(Input.touchPosition) == collider){
                        renderer.setFocus();
                    }
                    else {
                        renderer.setNormal();
                    }
                }

            }
            if(Input.touchUp && Physics.raycast(Input.touchPosition) == collider){
                manager.passHandler();
            }
        }
    }

}
