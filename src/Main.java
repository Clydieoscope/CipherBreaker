
public class Main {
    public static void main(String[] args) {
        String secret = "If he had anything confidential to say, he wrote it" +
                " in cipher, that is, by so changing the order of " +
                "the letters of the alphabet, that not a word could";

        String walrus = "the time has come the walrus said to talk of many things of"
                + " shoes and ships and sealing wax of cabbages and kings and why the"
                + " sea is boiling hot and whether pigs have wings but wait a bit the"
                + " oysters cried before we have our chat for some of us are out of breath"
                + " and all of us are fat no hurry said the carpenter they thanked him much"
                + " for that a loaf of bread the walrus said is what we chiefly need pepper "
                + "and vinegar besides are very good indeed now if youre ready oysters dear "
                + "we can begin to feed";

        String sun = "The sun dipped below the horizon as the breeze rustled through the" +
                " trees, carrying with it the scent of pine and the promise of a quiet evening. " +
                "Birds called out in the distance, their songs fading into the calm that settled" +
                " over the forest. Somewhere nearby, a stream babbled gently over smooth stones, " +
                "its rhythm blending with the whispers of the wind. It was the kind of peaceful " +
                "moment that made time feel like it had slowed, inviting reflection and quiet wonder.";

        String war = "GBSXUCGSZQGKGSQPKQKGLSKASPCGBGBKGUKGCEUKUZKGGBSQEICACGK\n" +
                "GCEUERWKLKUPKQQGCIICUAEUVSHQKGCEUPCGBCGQOEVSHUNSUGKUZCG\n" +
                "QSNLSHEHIEEDCUOGEPKHZGBSNKCUGSUKUASERLSKASCUGBSLKACRCAC\n" +
                "UZSSZEUSBEXHKRGSHWKLKUSQSKCHQTXKZHEUQBKZAENNSUASZFENFCU\n" +
                "OCUEKBXGBSWKLKUSQSKNFKQQKZEHGEGBSXUCGSZQGKGSQKUZBCQAEII\n" +
                "SKOXSZSICVSHSZGEGBSQSAHSGKHMERQGKGSKREHNKIHSLIMGEKHSASU\n" +
                "GKNSHCAKUNSQQKOSPBCISGBCQHSLIMQGKGSZGBKGCGQSSNSZXQSISQQ\n" +
                "GEAEUGCUXSGBSSJCQGCUOZCLIENKGCAUSOEGCKGCEUQCGAEUGKCUSZU\n" +
                "EGBHSKGEHBCUGERPKHEHKHNSZKGGKAD";


        // CEASAR CIPHER

        Cipher caesarCipher = new CaesarCipher();
        testCipher(secret, "13", caesarCipher);

        // SIMPLE SUBSTITUTION CIPHER

        Cipher simpleSubCipher = new SimpleSubstitutionCipher();

        testCipher(walrus, "DHPUWEBRYLKGZJFTAQXOVINSMC", simpleSubCipher);
        testCipher(war, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", simpleSubCipher);

        // VIGENERE CIPHER

        Cipher vigenereCipher = new VigenereCipher();
        testCipher(sun, "CAT", vigenereCipher);

        // UNKNOWN CIPHER
        SearchingContext sc = new SearchingContext(new AllCiphersStrategy());
        DecryptionResult result = sc.searchKey(war);
        System.out.println("\nCipher used: " + result.cipher + "\n" +
                "Decrypted with Key: " + result.key + "\n" +
                "Fitness: " + result.fitness + "\n" +
                "Plaintext: " + wrapString(result.plainText, 50));
    }

    public static void testCipher(String plainText, String e_key, Cipher cipher) {
        String cipherText;
        DecryptionResult result;
        SearchingContext sc = new SearchingContext();

        if (cipher instanceof CaesarCipher) {
            sc.setSearchingStrategy(new CaesarCipherStrategy(cipher));
        } else if (cipher instanceof SimpleSubstitutionCipher) {
            sc.setSearchingStrategy(new SimpleSubstitutionCipherStrategy(cipher));
        } else if (cipher instanceof VigenereCipher) {
            sc.setSearchingStrategy(new VigenereCipherStrategy(cipher));
        }

        cipherText = cipher.encrypt(plainText, e_key);
        System.out.println("\nEncrypted with Key: " + e_key + "\n" +
                "Ciphertext: " + wrapString(cipherText, 50));

        result = sc.searchKey(cipherText);
        System.out.println("\nDecrypted with Key: " + result.key + "\n" +
                "Fitness: " + result.fitness + "\n" +
                "Plaintext: " + wrapString(result.plainText, 50));
    }

    public static String wrapString(String input, int n) {
        StringBuilder s = new StringBuilder();
        int count = 0;

        for (char c : input.toCharArray()) {
            if (count > n && c == ' ') {
                count = 0;
                s.append("\n");
            } else {
                s.append(c);
                count++;
            }
        }

        return s.toString();
    }

    /*
    public static void testFitnessFunction (Cipher cipher, Tester tester) {
        String filePath = "data/eng_news_2005_10K-sentences.txt";
        double total = 0.0;
        double average = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {

                cipher.setCipherText(line);
                double fitness = cipher.getFitness();
                System.out.println("English fitness: " + fitness);
                System.out.println(cipher.decrypt());

                total++;
                average += fitness;
            }

            average /= total;

            System.out.println("Lines: " + total);
            System.out.println("Average Fitness: " + average);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void testFitnessFunctionEncrypted (Cipher cipher, Tester tester) {
        String filePath = "data/eng_news_2005_10K-sentences.txt";
        double total = 0.0;
        double average = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {

                cipher.setCipherText(line);
                cipher.changeKey(shuffleString("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
                double fitness = cipher.getFitness();
                System.out.println("English fitness: " + fitness);
                System.out.println(cipher.decrypt());

                total++;
                average += fitness;
            }

            average /= total;

            System.out.println("Lines: " + total);
            System.out.println("Average Fitness: " + average);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    */
}