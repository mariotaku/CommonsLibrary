package org.mariotaku.commons.text

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by mariotaku on 16/8/28.
 */
class CodePointArrayExtensionsTest {

    @Test
    fun testGet() {
        val codePoints = CodePointArray("Hello world")
        assertEquals("Hello", CodePointArray(codePoints[0..5]).toString())
    }
}