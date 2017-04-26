package org.mariotaku.commons.emojione;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Emojione {
    private static final HashMap<String, String> _shortNameToUnicode = new HashMap<String, String>();
    private static final Pattern SHORTNAME_PATTERN = Pattern.compile(":([-+\\w]+):");

    /**
     * Replace shortnames to unicode characters.
     */
    public static String shortnameToUnicode(String input) {
        Matcher matcher = SHORTNAME_PATTERN.matcher(input);

        while (matcher.find()) {
            String unicode = _shortNameToUnicode.get(matcher.group(1));
            if (unicode == null) {
                continue;
            }

            input = input.replace(":" + matcher.group(1) + ":", unicode);
        }

        return input;
    }

    static {
        String[][] arrays = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(ShortnameToUnicodeTranslator.class
                    .getResourceAsStream("/assets/emojione/emojione.map")));

            int mapSize = 0;
            int currentLine = 0;
            String lineContent;
            while ((lineContent = reader.readLine()) != null) {
                if (currentLine == 0) {
                    mapSize = Integer.parseInt(lineContent);
                    arrays = new String[mapSize][2];
                } else {
                    int index = currentLine - 1;
                    if (index >= mapSize) break;
                    final int equalIndex = lineContent.indexOf('=');
                    _shortNameToUnicode.put(lineContent.substring(0, equalIndex),
                            lineContent.substring(equalIndex + 1, lineContent.length()));
                }
                currentLine++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (arrays == null) {
            throw new NullPointerException();
        }
    }

}