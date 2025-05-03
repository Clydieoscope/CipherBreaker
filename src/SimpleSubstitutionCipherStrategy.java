import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class SimpleSubstitutionCipherStrategy implements SearchingStrategy{

    Cipher cipher;
    private static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    boolean debug = false;
    int rounds;
    static int DEFAULT = 5;

    public SimpleSubstitutionCipherStrategy(){
        this.cipher = new SimpleSubstitutionCipher();
        this.rounds = DEFAULT;
    }

    public SimpleSubstitutionCipherStrategy(int i) {
        this.rounds = i;
        this.cipher = new SimpleSubstitutionCipher();
    }

    public SimpleSubstitutionCipherStrategy(Cipher simpleSubstitutionCipher) {
        this.rounds = DEFAULT;
        this.cipher = simpleSubstitutionCipher;
    }

    public SimpleSubstitutionCipherStrategy(Cipher simpleSubstitutionCipher, int i) {
        this.rounds = i;
        this.cipher = simpleSubstitutionCipher;
    }
    @Override
    public DecryptionResult searchKey(String cipherText, Tester tester, Analyzer analyzer) {
        List<DecryptionResult> candidates = new ArrayList<>();
        String plainText, bestKey = "";
        double fitness;
        char[] key;
        int count = 0;
        boolean improved;

        DecryptionResult result = new DecryptionResult(cipherText, "", ALPHABET, Double.MAX_VALUE, "Simple Substitution Cipher");

        while (count < rounds) {
            fitness = Double.MAX_VALUE;
            result.fitness = Double.MAX_VALUE;
            key = ALPHABET.toCharArray();
            shuffleKey(key);
            // System.out.println("NEW KEY: " + new String(key));
            improved = true;

            while (improved) {
                improved = false;

                for (int i = 0; i < key.length; i++) {
                    for (int j = 0; j < key.length; j++) {
                        swapSymbol(key, i, j);
                        plainText = cipher.decrypt(cipherText, new String(key));
                        fitness = tester.getFitness(analyzer.countFrequencies(plainText));

                        if (fitness < result.fitness) {
                            result.fitness = fitness;
                            result.key = new String(key);
                            result.plainText = plainText;
                            improved = true;

                            if (debug) System.out.println("  Improved " + ++count + " time(s). Fitness: " + result.fitness + ".");
                        } else {
                            swapSymbol(key, i, j);
                        }
                    }
                }
            }

            if (debug) System.out.println("Best key: " + result.key + "\nFitness: " + result.fitness + "\n");
            candidates.add(new DecryptionResult(result));
            count++;
        }

        System.out.println("\nCANDIDATE KEYS");
        result.fitness = Double.MAX_VALUE;
        for (DecryptionResult r: candidates) {
            System.out.println("Fitness: " + String.format("%.2f", r.fitness) + ", Key: " + r.key);
            if (r.fitness < result.fitness) {
                result = r;
            }
        }

        return result;
    }

    private void swapSymbol(char[] key, int first, int second) {
        char temp = key[first];
        key[first] = key[second];
        key[second] = temp;
    }

    private void shuffleKey(char[] key) {
        List<Character> list = new ArrayList<>();
        for (char c : key) list.add(c);

        Collections.shuffle(list);

        for (int i = 0; i < key.length; i++) {
            key[i] = list.get(i);
        }
    }


}
