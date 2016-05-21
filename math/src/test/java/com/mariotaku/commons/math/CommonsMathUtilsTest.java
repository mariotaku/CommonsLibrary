package com.mariotaku.commons.math;

import org.junit.Test;
import org.mariotaku.commons.math.CommonsMathUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by mariotaku on 16/1/23.
 */
public class CommonsMathUtilsTest {

    public void testClamp() throws Exception {

    }

    public void testClamp1() throws Exception {

    }

    public void testNextPowerOf2() throws Exception {

    }

    public void testPrevPowerOf2() throws Exception {

    }

    public void testSum() throws Exception {

    }

    public void testSum1() throws Exception {

    }

    public void testSum2() throws Exception {

    }

    @Test
    public void testInRange() {
        assertTrue(CommonsMathUtils.inRange(5, 0, 10, CommonsMathUtils.RANGE_INCLUSIVE_INCLUSIVE));
        assertFalse(CommonsMathUtils.inRange(0, 0, 10, CommonsMathUtils.RANGE_EXCLUSIVE_EXCLUSIVE));
        assertFalse(CommonsMathUtils.inRange(0, 5, 10, CommonsMathUtils.RANGE_INCLUSIVE_INCLUSIVE));
        assertFalse(CommonsMathUtils.inRange(5, 5, 10, CommonsMathUtils.RANGE_EXCLUSIVE_INCLUSIVE));
        assertFalse(CommonsMathUtils.inRange(10, 5, 10, CommonsMathUtils.RANGE_INCLUSIVE_EXCLUSIVE));
    }

    @Test
    public void testInRange1() {
        assertTrue(CommonsMathUtils.inRange(5f, 0f, 10f, CommonsMathUtils.RANGE_INCLUSIVE_INCLUSIVE));
        assertFalse(CommonsMathUtils.inRange(0f, 0f, 10f, CommonsMathUtils.RANGE_EXCLUSIVE_EXCLUSIVE));
        assertFalse(CommonsMathUtils.inRange(0f, 5f, 10f, CommonsMathUtils.RANGE_INCLUSIVE_INCLUSIVE));
        assertFalse(CommonsMathUtils.inRange(5f, 5f, 10f, CommonsMathUtils.RANGE_EXCLUSIVE_INCLUSIVE));
        assertFalse(CommonsMathUtils.inRange(10f, 5f, 10f, CommonsMathUtils.RANGE_INCLUSIVE_EXCLUSIVE));
    }
}