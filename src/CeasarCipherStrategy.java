public class CeasarCipherStrategy implements SearchingStrategy {
    Cipher cipher;

    public CeasarCipherStrategy() {
        this.cipher = new CeasarCipher();
    }

    public CeasarCipherStrategy(Cipher ceasarCipher) {
        this.cipher = ceasarCipher;
    }

    public String searchKey(String cipherText, Tester tester, Analyzer analyzer) {
        String plainText, bestKey = "";
        double bestFitness = Double.MAX_VALUE;

        for (int i=0; i<26; i++) {
            String key = String.valueOf(i);
            plainText = cipher.decrypt(cipherText, key);
            double fitness = tester.getFitness(analyzer.countFrequencies(plainText));

            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestKey = key;
            }
        }

        return bestKey;
    }
}
