public class DecryptionResult {
    public String plainText, cipherText, key, cipher;
    public double fitness;

    public DecryptionResult(String cipherText, String plainText, String key, double fitness, String cipher) {
        this.plainText = plainText;
        this.cipherText = cipherText;
        this.key = key;
        this.fitness = fitness;
        this.cipher = cipher;
    }

    public DecryptionResult(DecryptionResult that) {
        this(that.cipherText, that.plainText, that.key, that.fitness, that.cipher);
    }

    @Override
    public String toString() {
        return key + " : " + fitness;
    }
}
