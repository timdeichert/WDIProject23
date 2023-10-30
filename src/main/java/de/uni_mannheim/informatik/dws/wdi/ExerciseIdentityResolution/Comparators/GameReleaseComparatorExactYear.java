package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import java.time.LocalDateTime;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.date.YearSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;


public class GameReleaseComparatorExactYear implements Comparator<Game, Attribute> {

    private static final long serialVersionUID = 1L;
    private ComparatorLogger comparisonLog;

    @Override
    public double compare(
            Game record1,
            Game record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {

        int releaseYear1 = extractYear(record1.getRelease());
        int releaseYear2 = extractYear(record2.getRelease());

        double similarity = 0.0;
        if (Math.abs(releaseYear1 - releaseYear2) <= 2) {
            similarity = 1.0;
        }

        if(this.comparisonLog != null){
            this.comparisonLog.setComparatorName(getClass().getName());

            this.comparisonLog.setRecord1Value(record1.getRelease().toString());
            this.comparisonLog.setRecord2Value(record2.getRelease().toString());

            this.comparisonLog.setSimilarity(Double.toString(similarity));
        }
        return similarity;

    }

    private int extractYear(Object releaseDate) {
        if (releaseDate instanceof LocalDateTime) {
            return ((LocalDateTime) releaseDate).getYear();
        } else if (releaseDate instanceof String) {
            String releaseString = (String) releaseDate;
            if (releaseString.contains("-")) {
                return Integer.parseInt(releaseString.split("-")[0]);  // Extract YYYY from YYYY-MM-DD
            }
            return Integer.parseInt(releaseString);  // Directly parse YYYY
        }
        return -1; // Default value for invalid dates
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