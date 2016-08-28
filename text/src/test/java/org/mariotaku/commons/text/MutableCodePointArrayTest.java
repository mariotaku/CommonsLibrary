package org.mariotaku.commons.text;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mariotaku on 16/8/28.
 */
public class MutableCodePointArrayTest {

    @Test
    public void testSet() throws Exception {
        MutableCodePointArray array = new MutableCodePointArray("Hello");
        array.set(0, 'B');
        Assert.assertEquals("Bello", array.toString());
    }

    @Test
    public void testSetRange() throws Exception {
        MutableCodePointArray array = new MutableCodePointArray("Hello");
        array.setRange(0, 1, new CodePointArray("HHHHHH").codePoints, 0, 6);
        Assert.assertEquals("HHHHHHello", array.toString());
        array.setRange(0, 6, new CodePointArray("H").codePoints, 0, 1);
    }

    @Test
    public void testSubCodePointArray() throws Exception {

    }
}