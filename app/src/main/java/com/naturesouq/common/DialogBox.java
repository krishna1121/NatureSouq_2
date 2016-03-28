package com.naturesouq.common;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by SI-Andriod on 7/16/2015.
 */
public class DialogBox {
    MaterialDialog mMaterialDialog;
    Activity activity;

    public DialogBox(Activity activity) {
        this.activity = activity;
    }

    public boolean checkInternetConnection() {
        boolean isConnected = true;
        try {
            if (!haveNetworkConnection()) {
                mMaterialDialog = new MaterialDialog(activity);
                mMaterialDialog.setTitle("Internet Problem");
                mMaterialDialog.setMessage("Sorry,No internet connectivity detected ,Please try and reconnect again .");
                mMaterialDialog.setPositiveButton("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        if (!haveNetworkConnection())
                            mMaterialDialog.show();
                    }
                });
                mMaterialDialog.show();
                isConnected = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}
