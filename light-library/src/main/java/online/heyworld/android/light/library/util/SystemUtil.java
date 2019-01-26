package online.heyworld.android.light.library.util;

import android.content.Context;

import java.util.Locale;

public class SystemUtil {
    public static String getLanguage(Context context) {
        return Locale.getDefault().getLanguage();
    }
}
