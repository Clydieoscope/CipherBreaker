public class SearchingContext {
    private SearchingStrategy searchingStrategy;
    private final Tester tester;
    private final Analyzer analyzer;

    public SearchingContext(SearchingStrategy searchingStrategy) {
        this.searchingStrategy = searchingStrategy;
        this.tester = new Tester();
        this.analyzer = new Analyzer();
    }

    public SearchingContext() {
        this.tester = new Tester();
        this.analyzer = new Analyzer();
    }

    public void setSearchingStrategy(SearchingStrategy searchingStrategy) {
        this.searchingStrategy = searchingStrategy;
    }

    public DecryptionResult searchKey(String cipherText) {
        try {
            if (searchingStrategy != null) {
                return searchingStrategy.searchKey(cipherText, tester, analyzer);
            } else {
                throw new IllegalStateException("Searching strategy is null.");
            }
        } catch (IllegalStateException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return null;
    }
}
