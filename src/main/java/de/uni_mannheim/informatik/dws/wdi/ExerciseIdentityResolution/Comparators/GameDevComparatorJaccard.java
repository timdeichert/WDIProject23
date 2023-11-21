package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;

import java.util.List;


public class GameDevComparatorJaccard implements Comparator<Game, Attribute> {

    private static final long serialVersionUID = 1L;
    TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();

    private ComparatorLogger comparisonLog;

    @Override
    public double compare(
            Game game1,
            Game game2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {

        List<String> s1 = game1.getDeveloper();
        List<String> s2 = game2.getDeveloper();

        // Check if s1 or s2 is null and handle it
        String te1 = (s1 != null) ? String.join(" ", s1).toLowerCase() : "";
        String te2 = (s2 != null) ? String.join(" ", s2).toLowerCase() : "";

        // calculate similarity
        double similarity = sim.calculate(te1, te2);

        // postprocessing
        double postSimilarity = similarity > 0.3 ? similarity : 0;

        if(this.comparisonLog != null){
            this.comparisonLog.setComparatorName(getClass().getName());
            this.comparisonLog.setRecord1Value(te1);
            this.comparisonLog.setRecord2Value(te2);
            this.comparisonLog.setSimilarity(Double.toString(similarity));
            this.comparisonLog.setPostprocessedSimilarity(Double.toString(postSimilarity));
        }
        return postSimilarity;
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