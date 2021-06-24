package com.SCUT.ZZS_YJX_YMX.cdd.Components;

import com.SCUT.ZZS_YJX_YMX.cdd.Basic.MonoBehavior;
import com.SCUT.ZZS_YJX_YMX.cdd.Basic.Renderer;
import com.SCUT.ZZS_YJX_YMX.cdd.Basic.Transform;
import com.SCUT.ZZS_YJX_YMX.cdd.Basic.Vector3;

public class AutoCore extends MonoBehavior {
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
