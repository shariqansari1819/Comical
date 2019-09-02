package com.codebosses.comical.utils;

import android.content.Context;
import android.widget.Toast;

public class L {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
