package tests;

import org.testng.Assert;
import sample.EncryptionUtils;

public class EncryptionUtilsTest {

    @org.testng.annotations.Test
    public void testGetBites() {
        Assert.assertEquals(EncryptionUtils.getBites("KOCHAM CIE"), "01001011010011110100001101001000010000010100110100100000010000110100100101000101");
    }

    @org.testng.annotations.Test
    public void testPermutation() {
        // Dodaj tutaj test dla permutacji. Chociaż jednej
    }

    @org.testng.annotations.Test
    public void testLeftShift() {
        Assert.assertEquals(EncryptionUtils.leftShift("0001", 3), "1000");
        Assert.assertEquals(EncryptionUtils.leftShift("0001", 2), "0100");
        Assert.assertEquals(EncryptionUtils.leftShift("0101", 1), "1010");
    }

    @org.testng.annotations.Test
    public void testPerformXOR() {
        Assert.assertEquals(EncryptionUtils.performXOR("0000", "1111"), "1111");
        Assert.assertEquals(EncryptionUtils.performXOR("1100", "1111"), "0011");
    }
}