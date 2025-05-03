import java.util.Arrays;

public class VigenereCipherStrategy implements SearchingStrategy {

    int max;
    boolean definite, debug = false;
    Cipher cipher;

    public VigenereCipherStrategy() {
        this.max = 4;
        this.definite = false;
        this.cipher = new VigenereCipher();
    }

    public VigenereCipherStrategy(int max) {
        this.max = max;
        this.definite = true;
        this.cipher = new VigenereCipher();
    }

    public VigenereCipherStrategy(Cipher vigenereCipher) {
        this.cipher = vigenereCipher;
        this.max = 4;
        this.definite = false;
    }

    public VigenereCipherStrategy(Cipher vigenereCipher, int max) {
        this.cipher = vigenereCipher;
        this.max = max;
        this.definite = true;
    }

    public void setMax(int max) {
        if (max == -1) {
            this.max = 4;
            this.definite = false;
            return;
        }

        this.max = max;
        this.definite = true;
    }

    @Override
    public String searchKey(String cipherText, Tester tester, Analyzer analyzer) {
        String plainText, bestKey = "";
        double fitness, bestFitness = Double.MAX_VALUE;
        char[] key;

        for (int i=2; i < max; i++) {
            key = new char[i];
            Arrays.fill(key, 'A');
            while(hasNext(key)) {
                nextKey(key);
                plainText = cipher.decrypt(cipherText, new String(key));
                fitness = tester.getFitness(analyzer.countFrequencies(plainText));

                if (fitness < bestFitness) {
                    bestFitness = fitness;
                    bestKey = new String(key);

                    if (debug) System.out.println("Key: " + bestKey + " Fitness: " + bestFitness + ".");
                }
            }
        }

        System.out.println("\nFOUND KEY (Fitness: " + String.format("%.2f", bestFitness) + ", Key: " + bestKey + ")");
        return bestKey;
    }

    private static void nextKey(char[] key) {
        int i = key.length - 1;

        while (i >= 0) {
            if (key[i] == 'Z') {
                key[i] = 'A';
                i--;
            } else {
                key[i]++;
                break;
            }
        }
    }

    public boolean hasNext(char[] key) {
        for (char c : key) {
            if (c != 'Z') return true;
        }
        return false;
    }
}
