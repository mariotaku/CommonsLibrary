/*
 *                 Twidere - Twitter client for Android
 *
 *  Copyright (C) 2012-2015 Mariotaku Lee <mariotaku.lee@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mariotaku.commons.text;


/**
 * Created by mariotaku on 15/11/4.
 */
public class CodePointArray {

    protected int[] codePoints;
    protected int offset;
    protected int length;

    public CodePointArray(int[] codePoints) {
        this(codePoints, 0, codePoints.length);
    }

    public CodePointArray(int[] codePoints, int offset, int length) {
        this.codePoints = codePoints;
        this.offset = offset;
        this.length = length;
    }

    public CodePointArray(final CharSequence cs) {
        final int inputLength = cs.length();
        codePoints = new int[inputLength];
        int codePointsLength = 0;
        for (int charOffset = 0; charOffset < inputLength; ) {
            final int codePoint = Character.codePointAt(cs, charOffset);
            codePoints[offset + codePointsLength] = codePoint;
            codePointsLength += 1;
            charOffset += Character.charCount(codePoint);
        }
        this.length = codePointsLength;
    }

    public final int get(int pos) {
        return codePoints[offset + pos];
    }

    public final int length() {
        return length;
    }

    public final int indexOfText(int codePoint, int start) {
        int index = 0;
        for (int i = 0; i < length; i++) {
            final int current = get(i);
            if (current == codePoint && i >= start) return index;
            index += Character.charCount(current);
        }
        return -1;
    }

    public final String substring(int start, int end) {
        final StringBuilder sb = new StringBuilder(end - start);
        for (int i = start; i < end; i++) {
            sb.appendCodePoint(get(i));
        }
        return sb.toString();
    }

    public final int[] subarray(int start, int end) {
        int[] result = new int[end - start];
        System.arraycopy(codePoints, offset + start, result, 0, end - start);
        return result;
    }

    public CodePointArray subCodePointArray(int start, int end) {
        return new CodePointArray(subarray(start, end));
    }

    @Override
    public final String toString() {
        return substring(0, length);
    }

    public final int charCount(int start, int end) {
        int count = 0;
        for (int i = start; i < end; i++) {
            count += Character.charCount(get(i));
        }
        return count;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodePointArray that = (CodePointArray) o;
        if (length != that.length) return false;
        for (int i = 0, j = length; i < j; i++) {
            if (get(i) != that.get(i)) return false;
        }
        return true;

    }

    @Override
    public final int hashCode() {
        int result = 1;
        for (int i = offset, j = length; i < j; i++)
            result = 31 * result + codePoints[i];

        return result;
    }
}
