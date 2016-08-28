package org.mariotaku.commons.text;

/**
 * Created by mariotaku on 16/8/28.
 */
public class MutableCodePointArray extends CodePointArray {
    public MutableCodePointArray(int[] codePoints) {
        super(codePoints);
    }

    public MutableCodePointArray(int[] codePoints, int offset, int length) {
        super(codePoints, offset, length);
    }

    public MutableCodePointArray(CharSequence cs) {
        super(cs);
    }

    public final void set(int position, int codePoint) {
        codePoints[offset + position] = codePoint;
    }

    public final void setRange(int srcStart, int srcEnd, int[] dst, int dstStart, int dstEnd) {
        int srcLength = srcEnd - srcStart, dstLength = dstEnd - dstStart;
        int newLength = length - srcLength + dstLength;
        // New length is longer than internal array,
        if (newLength > codePoints.length) {
            int[] newArray = new int[newLength];
            // Copy old array content into new
            System.arraycopy(codePoints, offset, newArray, 0, length);
            // Swap to new array
            codePoints = newArray;
            // Reset internal array offset
            offset = 0;
        }
        // Move anything after srcEnd forward (dstLength - srcLength) elements
        System.arraycopy(codePoints, srcEnd, codePoints, srcEnd + (dstLength - srcLength), length - srcEnd);
        // Copy dst into internal array
        System.arraycopy(dst, dstStart, codePoints, srcStart, dstEnd - dstStart);
        // Set new length
        length = newLength;
    }

    @Override
    public MutableCodePointArray subCodePointArray(int start, int end) {
        return new MutableCodePointArray(subarray(start, end));
    }
}
