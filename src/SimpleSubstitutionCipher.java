
public class SimpleSubstitutionCipher extends Cipher{

	public SimpleSubstitutionCipher() {}

	@Override
	public String decrypt(String cipherText, String key) {
		StringBuilder sb = new StringBuilder();
		key = key.toUpperCase();
		char[] k = new char[26];

		for (int i = 0; i < 26; i++) {
			k[key.charAt(i) - 'A'] = (char) ('A' + i);
		}

		for (int i = 0; i < cipherText.length(); i++) {
			char c = cipherText.charAt(i);

			if (c >= 'A' && c <= 'Z') {
				sb.append(k[c - 'A']);
			} else if (c >= 'a' && c <= 'z') {
				sb.append(Character.toLowerCase(k[c - 'a']));
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	@Override
	public String encrypt(String text, String key) {
		StringBuilder sb = new StringBuilder();
		char[] k = key.toUpperCase().toCharArray();

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if (c >= 'A' && c <= 'Z') {
				sb.append(k[c - 'A']);
			} else if (c >= 'a' && c <= 'z') {
				sb.append(Character.toLowerCase(k[c - 'a']));
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}
}
