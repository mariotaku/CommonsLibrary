package org.mariotaku.commons.text

/**
 * Created by mariotaku on 16/8/28.
 */
operator fun CodePointArray.get(range: IntRange): IntArray {
    return subarray(range.start, range.endInclusive)
}
