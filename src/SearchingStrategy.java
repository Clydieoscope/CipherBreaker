public interface SearchingStrategy {
    DecryptionResult searchKey(String cipherText, Tester tester, Analyzer analyzer);
}

