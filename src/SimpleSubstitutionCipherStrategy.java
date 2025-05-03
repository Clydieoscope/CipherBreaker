import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SimpleSubstitutionCipherStrategy implements SearchingStrategy{

    Cipher cipher;
    private static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    boolean debug = false;
    int iteration;

    public SimpleSubstitutionCipherStrategy(){
        this.cipher = new SimpleSubstitutionCipher();
        this.iteration = 1;
    }

    public SimpleSubstitutionCipherStrategy(int i) {
        this.iteration = i;
        this.cipher = new SimpleSubstitutionCipher();
    }

    public SimpleSubstitutionCipherStrategy(Cipher simpleSubstitutionCipher) {
        this.iteration = 1;
        this.cipher = simpleSubstitutionCipher;
    }

    public SimpleSubstitutionCipherStrategy(Cipher simpleSubstitutionCipher, int i) {
        this.iteration = i;
        this.cipher = simpleSubstitutionCipher;
    }
    @Override
    public String searchKey(String cipherText, Tester tester, Analyzer analyzer) {
        HashMap<String, Double> candidates = new HashMap<>();
        String plainText, bestKey = "";
        double fitness, bestFitness;
        char[] key;
        int count = 0;
        boolean improved;

        while (count < iteration) {
            fitness = Double.MAX_VALUE;
            bestFitness = Double.MAX_VALUE;
            key = ALPHABET.toCharArray();
            shuffleKey(key);
            improved = true;

            while (improved) {
                improved = false;

                for (int i = 0; i < key.length; i++) {
                    for (int j = 0; j < key.length; j++) {
                        swapSymbol(key, i, j);
                        fitness = tester.getFitness(analyzer.countFrequencies(cipher.decrypt(cipherText, new String(key))));

                        if (fitness < bestFitness) {
                            bestFitness = fitness;
                            bestKey = new String(key);
                            improved = true;

                            if (debug) System.out.println("  Improved " + ++count + " time(s). Fitness: " + bestFitness + ".");
                        } else {
                            swapSymbol(key, i, j);
                        }
                    }
                }
            }

            if (debug) System.out.println("Best key: " + bestKey + "\nFitness: " + bestFitness + "\n");
            candidates.put(bestKey, bestFitness);
            count++;
        }

        bestFitness = Double.MAX_VALUE;
        for (String k: candidates.keySet()) {
            fitness = candidates.get(k);
            if (fitness < bestFitness) {
                bestFitness = fitness;
                if (debug) System.out.println(k + ": " + fitness);
                bestKey = k;
            }
        }

        return bestKey;
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
