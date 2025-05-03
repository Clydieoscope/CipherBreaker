public abstract class Cipher {

	public Cipher() {}

	public abstract String decrypt(String text, String key);

	public abstract String encrypt(String text, String key);

}
