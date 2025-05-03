import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Tester {

	boolean debug = false;
	HashMap<String, Double> bigramFrequency_eng;
	HashMap<String, Double> unigramFrequency_eng;
	HashMap<String, Double> trigramFrequency_eng;
	static double[] weight = {0.17, 0.33, 0.5};
	static String[] filePath = {"data/unigrams.xml", "data/bigrams.xml", "data/trigrams.xml"};
	public Tester() {
		this.bigramFrequency_eng = new HashMap<>();
		this.unigramFrequency_eng = new HashMap<>();
		this.trigramFrequency_eng = new HashMap<>();
		
		loadUnigrams();
		loadBigrams();
		loadTrigrams();
		
		if (debug) {
			double sum=0;
			
			for (String i : bigramFrequency_eng.keySet()) {
			      sum += bigramFrequency_eng.get(i);
			}
			
			System.out.println("Sum of English bigram frequencies: " + sum);

			sum = 0.0;
			for (String i : trigramFrequency_eng.keySet()) {
				sum += trigramFrequency_eng.get(i);
			}

			System.out.println("Sum of English trigram frequencies: " + sum);
		}
		
	}

	private void loadUnigrams() {
		try {
			File xmlFile = new File(filePath[0]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("letter");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					String unigram = element.getAttribute("text");
					double frequency = Double.parseDouble(element.getAttribute("frequency"));

					unigramFrequency_eng.put(unigram, frequency);
				}
			}

		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
			System.out.println("Failed to load " + filePath[0]);
		}
	}

	private void loadBigrams(){
		try {
			File xmlFile = new File(filePath[1]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("bigram");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					String bigram = element.getAttribute("text");
					double frequency = Double.parseDouble(element.getAttribute("frequency"));

					bigramFrequency_eng.put(bigram, frequency);
				}
			}

		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
			System.out.println("Failed to load " + filePath[1]);
		}
	}

	private void loadTrigrams(){
		try {
			File xmlFile = new File(filePath[2]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("trigram");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					String trigram = element.getAttribute("text");
					double frequency = Double.parseDouble(element.getAttribute("frequency"));

					trigramFrequency_eng.put(trigram, frequency);
				}
			}

		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
			System.out.println("Failed to load " + filePath[2]);
		}
	}

	public double getFitness(ArrayList<HashMap<String, Double>> frequencies) {
		HashMap<String, Double> bigramFrequency = frequencies.get(0);
		HashMap<String, Double> unigramFrequency = frequencies.get(1);
		HashMap<String, Double> trigramFrequency = frequencies.get(2);
		double fitness = 0;
		
		for (String i : unigramFrequency.keySet()) {
			fitness += weight[0] * Math.abs(unigramFrequency_eng.getOrDefault(i, 0.0) - unigramFrequency.get(i));
		}
		
		for (String i : bigramFrequency.keySet()) {
			fitness += weight[1] * Math.abs(bigramFrequency_eng.getOrDefault(i, 0.0) - bigramFrequency.get(i));
		}

		for (String i : trigramFrequency.keySet()) {
			fitness += weight[2] * Math.abs(trigramFrequency_eng.getOrDefault(i, 0.0) - trigramFrequency.get(i));
		}
		
		return fitness;
	}
}
