package com.SCUT.ZZS_YJX_YMX.cdd.Basic;

import java.util.ArrayList;
import java.util.Collections;

public class Physics {
    public static ArrayList<BoxTouch> colliders;
    public static void UpdatePhysics(){   }
    public static BoxTouch raycast(Vector2 ray){
        if(colliders != null){
            Collections.sort(colliders);
            for (int i = 0; i < colliders.size(); i++) {
                BoxTouch collider = colliders.get(i);
                Transform transform = (Transform)collider.getComponent(Transform.class);
                Vector2 offset = collider.getOffset();
                Vector2 size = collider.getSize();
                if(ray.x < transform.getPosition().x + offset.x - size.x / 2 )
                    continue;
                if(ray.x > transform.getPosition().x + offset.x + size.x / 2 )
                    continue;
                if(ray.y < transform.getPosition().y + offset.y - size.y / 2 )
                    continue;
                if(ray.y > transform.getPosition().y + offset.y + size.y / 2 )
                    continue;
                return collider;
            }
        }
        return null;
    }
    public static void Clear(){
        if(colliders != null){
            colliders.clear();
        }
    }
}
