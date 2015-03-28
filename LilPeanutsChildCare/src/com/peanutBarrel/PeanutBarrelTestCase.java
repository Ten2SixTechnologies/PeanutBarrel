package com.peanutBarrel;

import junit.framework.TestCase;

public class PeanutBarrelTestCase extends TestCase
{

    public PeanutBarrelTestCase(String name)
    {
        super(name);
    }

    public void test_javaTesting()
    {
        Long number = new Long(5L);
        long convertedNumber = number.longValue();
        assertNotNull(Long.valueOf(convertedNumber));
    }
}
