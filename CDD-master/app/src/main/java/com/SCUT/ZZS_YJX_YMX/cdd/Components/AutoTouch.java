package com.SCUT.ZZS_YJX_YMX.cdd.Components;

import com.SCUT.ZZS_YJX_YMX.cdd.Basic.*;

public class AutoTouch extends MonoBehavior {
    private Transform transform;
    private BoxTouch collider;
    private Renderer renderer;

    @Override
    public void Start() {
        transform = (Transform)getComponent(Transform.class);
        collider = (BoxTouch) getComponent(BoxTouch.class);
        renderer = (Renderer) getComponent(Renderer.class);
    }

    @Override
    public void Update() {
        if(collider != null && renderer != null){
            collider.setOffset(Vector2.zero);
            collider.setSize(new Vector2((float) renderer.getBitMapWidth() * transform.getScale().x,
                    (float) renderer.getBitMapHeight() * transform.getScale().y));
        }
    }

}
