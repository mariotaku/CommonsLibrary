/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mariotaku.commons.emojione;

import org.apache.commons.text.translate.CharSequenceTranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;

class AbsShortnameToUnicodeTranslator extends CharSequenceTranslator {

    private final HashMap<CharSequence, String> lookupMap;
    private final HashSet<Character> prefixSet;
    private final int shortest;
    private final int longest;

    /**
     * @param reader Mapper data reader, this reader will be closed
     */
    AbsShortnameToUnicodeTranslator(BufferedReader reader) {
        HashMap<CharSequence, String> _lookupMap = null;
        HashSet<Character> _prefixSet = new HashSet<>();
        int _shortest = Integer.MAX_VALUE;
        int _longest = 0;
        try {
            int mapSize = 0;
            int currentLine = 0;
            String lineContent;
            while ((lineContent = reader.readLine()) != null) {
                if (currentLine == 0) {
                    mapSize = Integer.parseInt(lineContent);
                    _lookupMap = new HashMap<>(mapSize);
                } else {
                    int index = currentLine - 1;
                    if (index >= mapSize) break;
                    final int equalIndex = lineContent.indexOf('=');
                    final String key = lineContent.substring(0, equalIndex);
                    final String value = lineContent.substring(equalIndex + 1, lineContent.length());
                    _lookupMap.put(key, value);
                    _prefixSet.add(key.charAt(1));
                    final int sz = key.length();
                    if (sz < _shortest) {
                        _shortest = sz;
                    }
                    if (sz > _longest) {
                        _longest = sz;
                    }
                }
                currentLine++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
        lookupMap = _lookupMap;
        prefixSet = _prefixSet;
        shortest = _shortest;
        longest = _longest;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int translate(final CharSequence input, final int index, final Writer out) throws IOException {
        final int inputLength = input.length();
        if (index + 1 >= inputLength) return 0;
        // check if translation exists for the input at position index
        if (':' == input.charAt(index) && prefixSet.contains(input.charAt(index + 1))) {
            int max = longest;
            if (index + longest > inputLength) {
                max = inputLength - index;
            }
            // implement greedy algorithm by trying maximum match first
            for (int i = max; i >= shortest; i--) {
                final CharSequence subSeq = input.subSequence(index, index + i);
                final String result = lookupMap.get(subSeq);

                if (result != null) {
                    out.write(result);
                    return i;
                }
            }
        }
        return 0;
    }
}
