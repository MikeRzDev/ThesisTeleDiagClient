package co.edu.unab.gti.thesistelediagclient.ui.util;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MCoordinatorLayout extends CoordinatorLayout {

    private boolean allowScroll = false;

    public MCoordinatorLayout(Context context) {
        super(context);
    }

    public MCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!allowScroll){
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    public void setAllowScroll(boolean allowScroll) {
        this.allowScroll = allowScroll;
    }
}