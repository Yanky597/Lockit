package com.mcon521.lockit.lib;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceManager;

import com.mcon521.lockit.R;

public class Utils {

    public static final String mLOGINPASSWORD =  "LoginPassword";
    public static final String mISLOGGEDIN =  "IsLoggedIn";

    public static final boolean REQUIRE_LOGIN_YES = true;

    public static final boolean REQUIRE_LOGIN_NO = false;

    public static void setLoginOnOffFromPreferenceValue(Context context, String loginMode) {
        setRequireLogin(doesRequireLogin(context, loginMode));
    }

    public static boolean setRequireLogin(boolean setToOn) {
        boolean onMode  = setToOn ? REQUIRE_LOGIN_YES : REQUIRE_LOGIN_NO;
        return onMode;
    }

    public static boolean doesRequireLogin(Context context, String loginMode) {
        SharedPreferences loginPreference = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEdit = loginPreference.edit();
        if(loginPreference.contains(mLOGINPASSWORD) && loginPreference.contains(mISLOGGEDIN)){
            return loginPreference.getBoolean(loginMode, false);
        }
        else{
            myEdit.putString(mLOGINPASSWORD, "");
            myEdit.putBoolean(mISLOGGEDIN, false);
            return loginPreference.getBoolean(loginMode, false);
        }
    }

    public static void LoggedStatusTrue(Context context){
        SharedPreferences loginPreference = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEdit = loginPreference.edit();
        myEdit.putBoolean(mISLOGGEDIN, true);
        myEdit.apply();
    }

    public static void setToLoggedOut(Context context){
        SharedPreferences loginPreference = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEdit = loginPreference.edit();
        myEdit.putBoolean(mISLOGGEDIN, false);
        myEdit.apply();
    }

    public static boolean getLoginStatus(Context context){
        SharedPreferences loginPreference = PreferenceManager.getDefaultSharedPreferences(context);
        return loginPreference.getBoolean(mISLOGGEDIN, false);
    }


    public static String passwordIsSet(Context context){
        SharedPreferences loginPreference = PreferenceManager.getDefaultSharedPreferences(context);
        return loginPreference.getString(mLOGINPASSWORD, "");
    }







    /**
     * Shows an Android (nicer) equivalent to JOptionPane
     *
     * @param strTitle Title of the Dialog box
     * @param strMsg   Message (body) of the Dialog box
     */
    public static void showInfoDialog (Context context, String strTitle, String strMsg)
    {
        // create the listener for the dialog
        final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener ()
        {
            @Override
            public void onClick (DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        };

        // Create the AlertDialog.Builder object
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder (context);

        // Use the AlertDialog's Builder Class methods to set the title, icon, message, et al.
        // These could all be chained as one long statement, if desired
        alertDialogBuilder.setTitle (strTitle);
        alertDialogBuilder.setIcon (R.mipmap.ic_launcher);
        alertDialogBuilder.setMessage (strMsg);
        alertDialogBuilder.setCancelable (true);
        alertDialogBuilder.setNeutralButton (context.getString (android.R.string.ok), listener);

        // Create and Show the Dialog
        alertDialogBuilder.show ();
    }

// --Commented out by Inspection START:
//    /**
//     * Overloaded XML version of showInfoDialog(String, String) method
//     *
//     * @param titleID Title stored in XML resource (e.g. strings.xml)
//     * @param msgID   Message (body) stored in XML resource (e.g. strings.xml)
//     */
//    public static void showInfoDialog (Context context, int titleID, int msgID)
//    {
//        showInfoDialog (context, context.getString (titleID), context.getString (msgID));
//    }
// --Commented out by Inspection STOP

}
