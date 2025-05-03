public class CeasarCipher extends Cipher{

    public CeasarCipher() {}

    @Override
    public String decrypt(String cipherText, String key) {
        StringBuilder sb = new StringBuilder();
        int k = Integer.parseInt(key);

        for (int i = 0; i < cipherText.length(); i++) {
            char c = cipherText.charAt(i);
            sb.append((c == ' ') ? ' ' : shift(c, k * -1));
        }

        return sb.toString();
    }

    @Override
    public String encrypt(String text, String key) {
        StringBuilder sb = new StringBuilder();
        int k = Integer.parseInt(key);

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            sb.append((c == ' ') ? ' ' : shift(c, k));
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
