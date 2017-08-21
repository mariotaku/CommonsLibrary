package org.mariotaku.commons.emojione;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by mariotaku on 2017/8/21.
 */
public final class ShortnameToUnicodeTranslator extends AbsShortnameToUnicodeTranslator {
    public ShortnameToUnicodeTranslator() {
        super(mapperReader());
    }

    private static BufferedReader mapperReader() {
        return new BufferedReader(new InputStreamReader(ShortnameToUnicodeTranslator.class
                .getResourceAsStream("/emojione/emojione.map"), Charset.forName("UTF-8")));
    }
}
