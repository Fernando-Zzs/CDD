package com.SCUT.ZZS_YJX_YMX.cdd.Renderers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.SCUT.ZZS_YJX_YMX.cdd.Basic.Global;
import com.SCUT.ZZS_YJX_YMX.cdd.Basic.Renderer;
import com.SCUT.ZZS_YJX_YMX.cdd.Basic.Transform;
import com.SCUT.ZZS_YJX_YMX.cdd.R;

import java.util.ArrayList;

public class ButtonRenderer extends Renderer{

    static public ArrayList<ButtonRenderer> renderersList = new ArrayList<>();
    public static boolean clear = false;

    protected Transform transform;
    private Bitmap normalResource, focusResource, disableResource;
    private int state;
    private int bitMapHeight, bitMapWidth;
    private int normalID, focusID, disableID;
    private boolean set = false;

    public ButtonRenderer() {
        this(R.mipmap.default_sprite, R.mipmap.default_sprite, R.mipmap.default_sprite);
    }

    public ButtonRenderer(int normal, int focus, int disable) {
        normalID = normal;
        focusID = focus;
        disableID = disable;
        clear = false;
        synchronized (renderersList) {
            renderersList.add(this);
        }
        state = 0;
    }

    public void setNormal(){
        state = 0;
    }
    public void setFocus(){
        state = 1;
    }
    public void setDisable(){
        state = 2;
    }

    @Override
    public void setBitmapResource(int id) {
    }

    public Bitmap getBitmapResource() {
        return normalResource;
    }

    public void Draw(Canvas canvas, Paint paint) {

        if (transform == null) {
            transform = (Transform) getComponent(Transform.class);
        } else {
            if (Global.surfaceContext != null) {
                if (normalResource == null) {
                    normalResource = BitmapFactory.decodeResource(Global.surfaceContext.getResources(), normalID);
                    bitMapWidth = normalResource.getWidth();
                    bitMapHeight = normalResource.getHeight();
                }
                if (focusResource == null) {
                    focusResource = BitmapFactory.decodeResource(Global.surfaceContext.getResources(), focusID);
                }
                if (disableResource == null) {
                    disableResource = BitmapFactory.decodeResource(Global.surfaceContext.getResources(), disableID);
                }
            }
            if(normalResource != null && focusResource != null && disableResource != null){
                switch (state){
                    case 0:
                        canvas.drawBitmap(normalResource, transform.transformMatrix, null);
                        break;
                    case 1:
                        canvas.drawBitmap(focusResource, transform.transformMatrix, null);
                        break;
                    case 2:
                        canvas.drawBitmap(disableResource, transform.transformMatrix, null);
                        break;
                    default:
                }

            }
            zDepth = transform.getPosition().z;
        }
        if (gameObject.toBeDestroy) {
            Destroy();
        }
    }

    public int getBitMapWidth() {
        return bitMapWidth;
    }

    public int getBitMapHeight() {
        return bitMapHeight;
    }

}
