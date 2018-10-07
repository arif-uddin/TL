package com.lazydevs.tinylens;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    Context context;

    public Helper()
    {

    }

    public Helper(Context context)
    {
        this.context= context;
    }


    public boolean isConnected() {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public boolean isNameValid(String name)
    {
        int charCount = 0;
        String alphabet = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        if (name.length() == 0) {
            return false; //zero length string ain't alpha
        }

        for (int i = 0; i < name.length(); i++) {
            for (int j = 0; j < alphabet.length(); j++) {
                if (name.substring(i, i + 1).equals(alphabet.substring(j, j + 1))
                        || name.substring(i, i + 1).equals(alphabet.substring(j, j + 1).toLowerCase())) {
                    charCount++;
                }
            }

            if (charCount != (i + 1)) {
                //System.out.println("\n**Invalid input! Enter alpha values**\n");
                return false;
            }
        }

        return true;
    }

    public boolean isEmailValid(String email)
    {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isNumberValid(String number)
    {
        if(number.length()>7 && number.length()<=14)
        {
            return true;
        }
        else
            return false;
    }

    public Typeface init_TypeFace(String name)
    {
        AssetManager am = context.getAssets();
        Typeface typeFace = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", name));
        return typeFace;
    }




}