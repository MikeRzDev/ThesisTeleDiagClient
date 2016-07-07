package co.edu.unab.gti.thesistelediagclient.ui.util;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import co.edu.unab.gti.thesistelediagclient.util.AppContext;

/**
 * Created by user on 27/05/2016.
 */
public class ScrollToolbarController {
    MCoordinatorLayout coordinatorLayout;
    AppBarLayout appBarLayout;
    FloatingActionButton mainFAB;
    FloatingActionButton scrollToolbarFAB;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView scrollToolbarImage;


    public ScrollToolbarController(MCoordinatorLayout coordinatorLayout,
                                   AppBarLayout appBarLayout , FloatingActionButton mainFAB,
                                   FloatingActionButton scrollToolbarFAB,
                                   CollapsingToolbarLayout collapsingToolbarLayout, ImageView scrollToolbarImage){
        this.coordinatorLayout = coordinatorLayout;
        this.appBarLayout = appBarLayout;
        this.mainFAB = mainFAB;
        this.scrollToolbarFAB = scrollToolbarFAB;
        this.collapsingToolbarLayout = collapsingToolbarLayout;
        this.scrollToolbarImage = scrollToolbarImage;
    }

    public void setTitle(String title){
        collapsingToolbarLayout.setTitle(title);
    }

    public void setImage(int drawableResId){
        ImageLoader.getInstance().displayImage("drawable://" +drawableResId,
                scrollToolbarImage,
                AppContext.getImageOptions());
    }

    public void setImage(String imgFilePath){
        ImageLoader.getInstance().displayImage("file://" +imgFilePath,
                scrollToolbarImage,
                AppContext.getImageOptions());
    }

    public void setMainFABOnClickListener(View.OnClickListener listener){
        mainFAB.setOnClickListener(listener);
    }

    public void setScrollToolbarFABOnClickListener(View.OnClickListener listener){
        scrollToolbarFAB.setOnClickListener(listener);
    }

    public void setMainFABIcon( int drawableResId){
        mainFAB.setImageResource(drawableResId);
    }

    public void setScrollToolbarFABIcon( int drawableResId){
        scrollToolbarFAB.setImageResource(drawableResId);
    }

    public void isMainFABVisible(boolean isVisible){
        if (isVisible){
            mainFAB.setVisibility(View.VISIBLE);
        } else {
            mainFAB.setVisibility(View.INVISIBLE);
        }
    }

    public void setScrollBarMode(boolean animate){
        coordinatorLayout.setAllowScroll(true);

        if (animate){
            appBarLayout.setExpanded(true,true);
        } else {
            appBarLayout.setExpanded(true);
        }
    }

    public void setActionBarMode(boolean animate){
        coordinatorLayout.setAllowScroll(false);

        if (animate){
            appBarLayout.setExpanded(false,true);
        } else {
            appBarLayout.setExpanded(false);
        }

    }

}
