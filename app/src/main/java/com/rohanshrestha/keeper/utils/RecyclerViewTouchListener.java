package com.rohanshrestha.keeper.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by rohan on 3/31/16.
 */
public class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector detector;
    private TouchListener touchListener;

    public RecyclerViewTouchListener(Context context, RecyclerView recyclerView, TouchListener touchListener) {
        this.touchListener = touchListener;
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && touchListener != null && detector.onTouchEvent(e)) {
            touchListener.onClick(child, rv.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface TouchListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

}
