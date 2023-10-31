import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity; // Use Levenshtein similarity

public class GamePublisherComparatorLevenshtein implements Comparator<Game, Attribute> {

    private static final long serialVersionUID = 1L;
    private LevenshteinSimilarity sim = new LevenshteinSimilarity(); // Use Levenshtein similarity

    private ComparatorLogger comparisonLog;

    @Override
    public double compare(
            Game record1,
            Game record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {

        String publisher1 = record1.getPublisher();
        String publisher2 = record2.getPublisher();

        double similarity = sim.calculate(publisher1, publisher2); // Use Levenshtein similarity

        if (this.comparisonLog != null) {
            this.comparisonLog.setComparatorName(getClass().getName());

            this.comparisonLog.setRecord1Value(publisher1);
            this.comparisonLog.setRecord2Value(publisher2);

            this.comparisonLog.setSimilarity(Double.toString(similarity));
        }
        return similarity;
    }

    @Override
    public ComparatorLogger getComparisonLog() {
        return this.comparisonLog;
    }

    @Override
    public void setComparisonLog(ComparatorLogger comparatorLog) {
        this.comparisonLog = comparatorLog;
    }
}