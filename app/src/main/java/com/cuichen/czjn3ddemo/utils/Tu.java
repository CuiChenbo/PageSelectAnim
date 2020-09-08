package com.cuichen.czjn3ddemo.utils;

import android.content.Context;
import android.widget.Toast;

public class Tu {

    private static Toast t;
    public static void show(Context c , String stirng){
        if (t == null)
            t = Toast.makeText(c , "" , Toast.LENGTH_SHORT);
        t.setText(stirng);
        t.show();
    }
}
