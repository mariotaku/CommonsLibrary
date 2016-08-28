package org.mariotaku.commons.text;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mariotaku on 16/8/28.
 */
public class CodePointArrayTest {

    @Test
    public void testGet() throws Exception {
        CodePointArray array = new CodePointArray("Hello");
        Assert.assertArrayEquals(new char[]{'H'}, Character.toChars(array.get(0)));
    }

    @Test
    public void testLength() throws Exception {
        CodePointArray array = new CodePointArray("Hello");
        Assert.assertEquals(5, array.length());
    }

    @Test
    public void testToString() throws Exception {
        CodePointArray array = new CodePointArray("Hello");
        Assert.assertEquals("Hello", array.toString());
    }

    @Test
    public void testEquals() throws Exception {
        CodePointArray array1 = new CodePointArray("Hello");
        CodePointArray array2 = new CodePointArray("Test Hello").subCodePointArray(5, 10);
        Assert.assertEquals(array1, array2);
    }

    @Test
    public void testHashCode() throws Exception {
        CodePointArray array1 = new CodePointArray("Hello");
        CodePointArray array2 = new CodePointArray("Test Hello").subCodePointArray(5, 10);
        Assert.assertEquals(array1.hashCode(), array2.hashCode());
    }
}