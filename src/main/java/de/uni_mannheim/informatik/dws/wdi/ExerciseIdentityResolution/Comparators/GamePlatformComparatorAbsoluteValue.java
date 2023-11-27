package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class GamePlatformComparatorAbsoluteValue implements Comparator<Game, Attribute> {

    private static final long serialVersionUID = 1L;
    private ComparatorLogger comparisonLog;

    @Override
    public double compare(
            Game record1,
            Game record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {

        // Compare the platform values
        boolean isMatch = record1.getPlatform().equalsIgnoreCase(record2.getPlatform());

        // Log comparison information
        if (this.comparisonLog != null) {
            this.comparisonLog.setComparatorName(getClass().getName());
            this.comparisonLog.setRecord1Value(record1.getPlatform());
            this.comparisonLog.setRecord2Value(record2.getPlatform());

            // Set similarity to 1.0 if it's a match, otherwise set it to 0.0
            double similarity = isMatch ? 1.0 : 0.0;

            this.comparisonLog.setSimilarity(Double.toString(similarity));
            this.comparisonLog.setPostprocessedSimilarity(Double.toString(similarity));
        }

        // Return 1.0 for a match, 0.0 for a non-match
        return isMatch ? 1.0 : 0.0;
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
