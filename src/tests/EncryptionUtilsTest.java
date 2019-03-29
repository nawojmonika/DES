package tests;

import org.testng.Assert;
import org.testng.AssertJUnit;
import sample.Algorithm;
import sample.EncryptionUtils;
import sample.PermutationTables;

import java.util.ArrayList;

public class EncryptionUtilsTest {

    @org.testng.annotations.Test
    public void testGetBites() {
        String encryptedMessage = "Ç3A\n×\u0087\u0088þ";
        Assert.assertEquals(EncryptionUtils.getBinary("KOCHAM CIE"), "01001011010011110100001101001000010000010100110100100000010000110100100101000101");
        Assert.assertEquals(EncryptionUtils.getBinary(encryptedMessage), "1100011100110011010000010000101011010111100001111000100011111110");
        Assert.assertEquals(EncryptionUtils.getBinary("Ç"), "11000111");
    }

    @org.testng.annotations.Test
    public void testPermutation() {
        // Dodaj tutaj test dla permutacji. Chociaż jednej
    }

    @org.testng.annotations.Test
    public void testLeftShift() {
        Assert.assertEquals(EncryptionUtils.leftShift("1000000101000000000001111111", 2), "0000010100000000000111111110");

        Assert.assertEquals(EncryptionUtils.leftShift("0000000001111111100000010100", 2), "0000000111111110000001010000");
        Assert.assertEquals(EncryptionUtils.leftShift("1111111100000010100000000000", 2), "1111110000001010000000000011");
        Assert.assertEquals(EncryptionUtils.leftShift("1111111100000010100000000000", 1), "1111111000000101000000000001");
    }

    @org.testng.annotations.Test
    public void testPerformXOR() {
        Assert.assertEquals(EncryptionUtils.performXOR("0000", "1111"), "1111");
        Assert.assertEquals(EncryptionUtils.performXOR("1100", "1111"), "0011");
    }

    @org.testng.annotations.Test
    public void subkeyPermutation() {
        ArrayList<String> subkeyPermutations = new ArrayList<>();
        subkeyPermutations.add("00000000011111111000000101001001100001011100001010100000");
        subkeyPermutations.add("00000000111111110000001010000011000010111000010101000001");
        subkeyPermutations.add("00000011111111000000101000001100001011100001010100000100");
        subkeyPermutations.add("00001111111100000010100000000000101110000101010000010011");
        subkeyPermutations.add("00111111110000001010000000000010111000010101000001001100");
        subkeyPermutations.add("11111111000000101000000000001011100001010100000100110000");
        subkeyPermutations.add("11111100000010100000000000111110000101010000010011000010");
        subkeyPermutations.add("11110000001010000000000011111000010101000001001100001011");
        subkeyPermutations.add("11100000010100000000000111110000101010000010011000010111");
        subkeyPermutations.add("10000001010000000000011111110010101000001001100001011100");
        subkeyPermutations.add("00000101000000000001111111101010100000100110000101110000");
        subkeyPermutations.add("00010100000000000111111110001010000010011000010111000010");
        subkeyPermutations.add("01010000000000011111111000001000001001100001011100001010");
        subkeyPermutations.add("01000000000001111111100000010000100110000101110000101010");
        subkeyPermutations.add("00000000000111111110000001010010011000010111000010101000");
        subkeyPermutations.add("00000000001111111100000010100100110000101110000101010000");

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

        for (int i = 0 ; i < keysFromExcel.size() ; i++) {
            AssertJUnit.assertEquals("Key " + i, keysFromExcel.get(i), EncryptionUtils.permutation(subkeyPermutations.get(i), PermutationTables.subkeyPermutation));
        }
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

        ArrayList<String> possibleKyes = Algorithm.getEncryptedKeys(key);

        for (int i = 0 ; i < keysFromExcel.size() ; i++) {
            AssertJUnit.assertEquals("Key " + i, keysFromExcel.get(i), possibleKyes.get(i));
        }
    }
    @org.testng.annotations.Test
    public void encryptionTest() {
        String key = "IEOFIT#1";
        String originalMessage = "IEOFIT#1";
        String encryptedMessage = "Ç3A\n×\u0087\u0088þ";

        AssertJUnit.assertEquals("Should encrypt properly", encryptedMessage, Algorithm.encryptMessage(originalMessage, key));

        String originalMessage2 = "Ala ma kota";
        String encryptedMessage2 = "\u009EÉV\u001Cm}Ëïæ\u0019NÁ\u0081ÓwÉ";

        AssertJUnit.assertEquals("Should encrypt properly " + originalMessage2, encryptedMessage2, Algorithm.encryptMessage(originalMessage2, key));

        String originalMessage3 = "Monika ma 2 koty i psa";
        String encryptedMessage3 = "?×a\u0094*\u0080¥\u001CAýñ;ïÐc[\u0099:µ\u0085[ÆÈÛ";

        AssertJUnit.assertEquals("Should encrypt properly " + originalMessage3, encryptedMessage3, Algorithm.encryptMessage(originalMessage3, key));

        String originalMessage4 = "To jest calkiem dlugi tekst to zaszyfrowania.";
        String encryptedMessage4 = "àK\u0006A\u0085\u0093å\u0085\u0098ßÒ\u0003âÝ»Â\u009CJ\u0080\u001BvvtzûÚ(³\u001EÉ'C\u0013D(zøb?Õ\u0005\u0085\u0005\u0099¾Ñ\u000Bº";

        AssertJUnit.assertEquals("Should encrypt properly " + originalMessage4, encryptedMessage4, Algorithm.encryptMessage(originalMessage4, key));
    }

    @org.testng.annotations.Test
    public void decryptionTest() {
        String encryptedMessage = "Ç3A\n×\u0087\u0088þ";
        String key = "IEOFIT#1";
        String originalMessage = "IEOFIT#1";

        AssertJUnit.assertEquals("Should decrypt properly ", originalMessage, Algorithm.decryptMessage(encryptedMessage, key));

        String encryptedMessage2 = "\u009EÉV\u001Cm}Ëïæ\u0019NÁ\u0081ÓwÉ";
        String originalMessage2 = "Ala ma kota";
        AssertJUnit.assertEquals("Should decrypt properly ", originalMessage2, Algorithm.decryptMessage(encryptedMessage2, key));

        String originalMessage3 = "Monika ma 2 koty i psa";
        String encryptedMessage3 = "?×a\u0094*\u0080¥\u001CAýñ;ïÐc[\u0099:µ\u0085[ÆÈÛ";
        AssertJUnit.assertEquals("Should decrypt properly ", originalMessage3, Algorithm.decryptMessage(encryptedMessage3, key));

        String originalMessage4 = "To jest calkiem dlugi tekst to zaszyfrowania.";
        String encryptedMessage4 = "àK\u0006A\u0085\u0093å\u0085\u0098ßÒ\u0003âÝ»Â\u009CJ\u0080\u001BvvtzûÚ(³\u001EÉ'C\u0013D(zøb?Õ\u0005\u0085\u0005\u0099¾Ñ\u000Bº";

        AssertJUnit.assertEquals("Should decrypt properly ", originalMessage4, Algorithm.decryptMessage(encryptedMessage4, key));

    }
}