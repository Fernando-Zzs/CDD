package com.saveandstudio.mario.cdd.Components;

import com.saveandstudio.mario.cdd.GameBasic.MonoBehavior;
import com.saveandstudio.mario.cdd.GameBasic.Renderer;
import com.saveandstudio.mario.cdd.GameBasic.Transform;
import com.saveandstudio.mario.cdd.GameBasic.Vector3;
import com.saveandstudio.mario.cdd.Renderers.CardRenderer;

public class AutoPivot extends MonoBehavior {
    private Transform transform;
    private Renderer renderer;

    @Override
    public void Start() {
        transform = (Transform)getComponent(Transform.class);
        renderer = (Renderer) getComponent(Renderer.class);
    }

    @Override
    public void Update() {
        if(renderer != null){
            transform.setPivot(new Vector3((float) renderer.getBitMapWidth() / 2, (float) renderer.getBitMapHeight() / 2, 0));
        }
    }
}
