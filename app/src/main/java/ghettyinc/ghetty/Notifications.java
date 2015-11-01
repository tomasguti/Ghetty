package ghettyinc.ghetty;

import android.content.Context;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import ghettyinc.ghetty.R;

/**
 * Created by Tomás on 30/09/2015.
 */
public class Notifications extends SlidingMenu{

    public Notifications(Context context) {
        super(context);
        setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        setMode(SlidingMenu.LEFT);
        setShadowWidthRes(R.dimen.shadow_width);
        setShadowDrawable(R.drawable.shadow_left);
        setBehindOffsetRes(R.dimen.slidingmenu_offset);
        setMenu(R.layout.chat_list);
    }
}
