package org.mariotaku.commons.emojione;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by mariotaku on 2017/8/21.
 */
public final class ShortnameToUnicodeTranslator extends AbsShortnameToUnicodeTranslator {
    public ShortnameToUnicodeTranslator(Context context) {
        super(mapperReader(context));
    }

    private static BufferedReader mapperReader(Context context) {
        return new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.emojione_mapping),
                Charset.forName("UTF-8")));
    }
}
