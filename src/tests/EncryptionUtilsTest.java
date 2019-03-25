package tests;

import org.testng.Assert;
import org.testng.AssertJUnit;
import sample.EncryptionUtils;
import sample.Main;

import java.util.ArrayList;
import java.util.Arrays;

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

    @org.testng.annotations.Test
    public void permutationTests() {
        String originalMessage = "IEOFIT#1";
        String key = "IEOFIT#1";

        ArrayList<String> keysFromExcel = new ArrayList<>();
        keysFromExcel.add("111100001001001010100010100010011010010000010011");
        keysFromExcel.add("101000001001101000100010101100010001001100101001");
        keysFromExcel.add("101000000011101001010010000100100001101000100010");
        keysFromExcel.add("001001000111011001010000010101000010100100110100");
        keysFromExcel.add("010001100101010101010000001000010010100011011000");
        keysFromExcel.add("010011101100000101010001011000011011000000010011");
        keysFromExcel.add("000011111100000100001011001001110000010000101110");
        keysFromExcel.add("001010110000000110001011000011000001100111000110");
        keysFromExcel.add("000110110001001010001001010111000010000100100000");
        keysFromExcel.add("000110010001100011001000111000000110100001001000");
        keysFromExcel.add("000100000110100011001100011000001011001000011010");
        keysFromExcel.add("000100000110110100000100101101010001010000101010");
        keysFromExcel.add("010000000010110100100101000011000001101001100010");
        keysFromExcel.add("110000011010010000100101000101001110100001110100");
        keysFromExcel.add("110000011000011010100010001000011000110011010000");
        keysFromExcel.add("111000001000001000101010110000100011001010011000");

        ArrayList<String> possibleKyes = Main.getEncryptedKeys(key);

        for (int i = 0 ; i < keysFromExcel.size() ; i++) {
            AssertJUnit.assertEquals("Key " + i, keysFromExcel.get(i), possibleKyes.get(i));
        }
    }
}