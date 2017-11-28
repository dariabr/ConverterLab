package com.joyfullkiwi.converterlab.Handlers;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.joyfullkiwi.converterlab.Activities.DetailActivity;
import com.joyfullkiwi.converterlab.Activities.MapActivity;
import com.joyfullkiwi.converterlab.Model.InfoModel;
import com.joyfullkiwi.converterlab.Utils.Constants;

public class MenuClickHandler {
    private final Context mContext;

    public MenuClickHandler(Context _context) {
        mContext = _context;
    }

    public void itemClick(int item_Id, InfoModel infoModel){
        Intent intent;
        switch (item_Id) {
            case Constants.MENU_LINK:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(infoModel.getLink()));
                mContext.startActivity(intent);
                break;
            case Constants.MENU_PHONE:

                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("phone:" + infoModel.getPhone()));
                mContext.startActivity(intent);
                break;
            case  Constants.MENU_MAP:
                intent = new Intent(mContext, MapActivity.class);
                mContext.startActivity(intent);
                break;
            case Constants.MENU_MORE:
                intent = new Intent(mContext, DetailActivity.class);
                mContext.startActivity(intent);
                break;

        }
    }
}
