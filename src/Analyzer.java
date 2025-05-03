import java.util.ArrayList;
import java.util.HashMap;

public class Analyzer {

	final boolean debug = false;

	public Analyzer() {}

	public HashMap<String, Double> countUnigramFrequency(String text) {
		HashMap<String, Double> unigramFrequency = new HashMap<>();
		int length = text.length();
		double count = 0.0;

		for (int i=0; i<length; i++) {
			char c = text.charAt(i);
			if (Character.isLetter(c)) {
				String unigram = String.valueOf(c);
				unigramFrequency.computeIfPresent(unigram, (key, value) -> value + 1.0);
				unigramFrequency.computeIfAbsent(unigram, k -> 1.0);
				count++;
			}
		}

		for (String k: unigramFrequency.keySet()) {	
			double frequency = (unigramFrequency.get(k) / count) * 100;
			unigramFrequency.replace(k, frequency);
		}

		if (debug) {
			double sum=0;
			for (String k: unigramFrequency.keySet()) {
				sum += unigramFrequency.get(k);
			}
			
			System.out.println("Sum of letter frequencies: " + sum);
		}

		return unigramFrequency;
	}

	public HashMap<String, Double> countBigramFrequency(String text) {
		HashMap<String, Double> bigramFrequency = new HashMap<>();
		int length = text.length();
		double count = 0.0;

		for (int i=0; i<length-1; i++) {
			String bigram = text.substring(i, i+2);
			if (bigram.matches("[a-zA-Z]+")) {
				bigramFrequency.computeIfPresent(bigram, (key, value) -> value + 1.0);
				bigramFrequency.computeIfAbsent(bigram, k -> 1.0);
				count++;
			}
		}

		for (String k: bigramFrequency.keySet()) {	
			double frequency = (bigramFrequency.get(k) / count) * 100;
			bigramFrequency.replace(k, frequency);
		}		

		if(debug) {
			System.out.println("Sum of bigram frequencies: " + getSum(bigramFrequency));
			printFrequency(bigramFrequency);
		}

		return bigramFrequency;
	}

	public HashMap<String, Double> countTrigramFrequency(String text) {
		HashMap<String, Double> trigramFrequency = new HashMap<>();
		int length = text.length();
		double count = 0.0;

		for (int i=0; i<length-2; i++) {
			String trigram = text.substring(i, i+3);
			if (trigram.matches("[a-zA-Z]+")) {
				trigramFrequency.computeIfPresent(trigram, (key, value) -> value + 1.0);
				trigramFrequency.computeIfAbsent(trigram, k -> 1.0);
				count++;
			}
		}

		for (String k: trigramFrequency.keySet()) {
			double frequency = (trigramFrequency.get(k) / count) * 100;
			trigramFrequency.replace(k, frequency);
		}

		if(debug) {
			System.out.println("Sum of trigram frequencies: " + getSum(trigramFrequency));
			printFrequency(trigramFrequency);
		}

		return trigramFrequency;
	}

	public ArrayList<HashMap<String, Double>> countFrequencies(String text) {
		text = text.toUpperCase(); // required as frequency data are in uppercase
		ArrayList<HashMap<String, Double>> frequencies = new ArrayList<>();
		frequencies.add(countUnigramFrequency(text));
		frequencies.add(countBigramFrequency(text));
		frequencies.add(countTrigramFrequency(text));

		return frequencies;
	}

	private void printFrequency(HashMap<String, Double>  frequencies) {
		for (String i : frequencies.keySet()) {
			System.out.println(i + ": " + String.format( "%.2f", frequencies.get(i)) + "\t");
		}
	}

	private double getSum(HashMap<String, Double>  frequencies) {
		double sum=0;

		for (String i : frequencies.keySet()) {
			sum += frequencies.get(i);
		}

		return sum;
	}

}
