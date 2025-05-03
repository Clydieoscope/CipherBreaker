public class SearchingContext {
    private SearchingStrategy searchingStrategy;
    private final Tester tester;
    private final Analyzer analyzer;

    public SearchingContext(SearchingStrategy searchingStrategy) {
        this.searchingStrategy = searchingStrategy;
        this.tester = new Tester();
        this.analyzer = new Analyzer();
    }

    public void setSearchingStrategy(SearchingStrategy searchingStrategy) {
        this.searchingStrategy = searchingStrategy;
    }

    public String searchKey(String cipherText) {
        return searchingStrategy.searchKey(cipherText, tester, analyzer);
    }
}
