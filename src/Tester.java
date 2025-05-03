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
	ArrayList<HashMap<String, Double>> EnglishFrequencies;
	static double[] weight = {0.075, 0.225, 0.7};
	static String[] filePath = {"data/unigrams.xml", "data/bigrams.xml", "data/trigrams.xml"};
	public Tester() {
		this.EnglishFrequencies = new ArrayList<>();

		loadUnigrams();
		loadBigrams();
		loadTrigrams();
		
		if (debug) {
			double sum=0;

			HashMap<String, Double> bigramFrequency = EnglishFrequencies.get(1);
			HashMap<String, Double> trigramFrequency = EnglishFrequencies.get(2);

			for (String i : bigramFrequency.keySet()) {
			      sum += bigramFrequency.get(i);
			}
			
			System.out.println("Sum of English bigram frequencies: " + sum);

			sum = 0.0;
			for (String i : trigramFrequency.keySet()) {
				sum += trigramFrequency.get(i);
			}

			System.out.println("Sum of English trigram frequencies: " + sum);
		}
		
	}

	private void loadUnigrams() {
		HashMap<String, Double> unigramFrequency = new HashMap<>();

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

					unigramFrequency.put(unigram, frequency);
				}
			}

		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
			System.out.println("Failed to load " + filePath[0]);
		}

		this.EnglishFrequencies.add(unigramFrequency);
	}

	private void loadBigrams(){
		HashMap<String, Double> bigramFrequency = new HashMap<>();

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

					bigramFrequency.put(bigram, frequency);
				}
			}

		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
			System.out.println("Failed to load " + filePath[1]);
		}

		this.EnglishFrequencies.add(bigramFrequency);
	}

	private void loadTrigrams(){
		HashMap<String, Double> trigramFrequency_eng = new HashMap<>();

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

		this.EnglishFrequencies.add(trigramFrequency_eng);
	}

	public double getFitness(ArrayList<HashMap<String, Double>> frequencies) {
		double fitness = 0;


		for (int i=0; i<frequencies.size(); i++) {
			HashMap<String, Double> f = frequencies.get(i);

			for (String k: f.keySet()) {
				fitness += weight[i] * Math.abs(f.get(k) - EnglishFrequencies.get(i).getOrDefault(k, 0.0));
			}
		}
		
		return fitness;
	}
}
