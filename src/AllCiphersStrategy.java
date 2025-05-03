import java.util.ArrayList;

public class AllCiphersStrategy implements SearchingStrategy{

    @Override
    public DecryptionResult searchKey(String cipherText, Tester tester, Analyzer analyzer) {
        ArrayList<DecryptionResult> candidates = new ArrayList<>();
        DecryptionResult bestResult = null;
        double bestFitness;
        SearchingContext sc = new SearchingContext();

        sc.setSearchingStrategy(new CaesarCipherStrategy(new CaesarCipher()));
        candidates.add(sc.searchKey(cipherText));

        sc.setSearchingStrategy(new SimpleSubstitutionCipherStrategy(new SimpleSubstitutionCipher()));
        candidates.add(sc.searchKey(cipherText));

        sc.setSearchingStrategy(new VigenereCipherStrategy(new VigenereCipher()));
        candidates.add(sc.searchKey(cipherText));

        System.out.println("\nCANDIDATE RESULTS");
        bestFitness = Double.MAX_VALUE;
        for (DecryptionResult result: candidates) {
            System.out.println("Fitness: " + String.format("%.2f", result.fitness) + ", Key: " + result.key);
            if (result.fitness < bestFitness) {
                bestFitness = result.fitness;
                bestResult = result;
            }
        }

        System.out.println("\nFOUND BEST RESULT (Fitness: " + String.format("%.2f", bestResult.fitness) + ", Key: " + bestResult.key + ")");
        return bestResult;
    }
}
