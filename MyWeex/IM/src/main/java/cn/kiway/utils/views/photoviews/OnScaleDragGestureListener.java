package cn.kiway.utils.views.photoviews;

/**
 * Created by Administrator on 2017/2/22.
 */

public interface OnScaleDragGestureListener {
    void onDrag(float dx, float dy);

    void onFling(float startX, float startY, float velocityX, float velocityY);

    void onScale(float scaleFactor, float focusX, float focusY);

    void onScaleEnd();
}
