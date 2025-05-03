import java.util.Arrays;

public class VigenereCipher extends Cipher{

    @Override
    public String decrypt(String cipherText, String key) {
        StringBuilder sb = new StringBuilder();
        int[] k = new int[key.length()];

        for (int i=0; i<key.length(); i++) {
            char c = key.charAt(i);
            c -= (Character.isLowerCase(c)) ? 'a' : 'A';
            k[i] = c;
        }

        for (int i = 0; i < cipherText.length(); i++) {
            char c = cipherText.charAt(i);
            sb.append((c == ' ') ? ' ' : shift(c, k[i % k.length] * -1));
        }

        return sb.toString();
    }

    @Override
    public String encrypt(String cipherText, String key) {
        StringBuilder sb = new StringBuilder();
        int[] k = new int[key.length()];

        for (int i=0; i<key.length(); i++) {
            char c = key.charAt(i);
            c -= (Character.isLowerCase(c)) ? 'a' : 'A';
            k[i] = c;
        }

        for (int i = 0; i < cipherText.length(); i++) {
            char c = cipherText.charAt(i);
            sb.append((c == ' ') ? ' ' : shift(c, k[i % k.length]));
        }

        return sb.toString();
    }

    private static char shift(char c, int shift) {

        if (Character.isLowerCase(c)) {
            return (char) ('a' + (c - 'a' + shift + 26) % 26);
        } else if (Character.isUpperCase(c)) {
            return (char) ('A' + (c - 'A' + shift + 26) % 26);
        }

        return c;
    }
}
