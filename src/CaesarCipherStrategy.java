

public class CaesarCipherStrategy implements SearchingStrategy {
    Cipher cipher;

    public CaesarCipherStrategy() {
        this.cipher = new CaesarCipher();
    }

    public CaesarCipherStrategy(Cipher ceasarCipher) {
        this.cipher = ceasarCipher;
    }

    public DecryptionResult searchKey(String cipherText, Tester tester, Analyzer analyzer) {
        String plainText;
        DecryptionResult result = new DecryptionResult(cipherText, "", "", Double.MAX_VALUE,  "Caesar Cipher");

        for (int i=0; i<26; i++) {
            String key = String.valueOf(i);
            plainText = cipher.decrypt(cipherText, key);
            double fitness = tester.getFitness(analyzer.countFrequencies(plainText));

            if (fitness < result.fitness) {
                result.fitness = fitness;
                result.key = key;
                result.plainText = plainText;
            }
        }

        return result;
    }
}
