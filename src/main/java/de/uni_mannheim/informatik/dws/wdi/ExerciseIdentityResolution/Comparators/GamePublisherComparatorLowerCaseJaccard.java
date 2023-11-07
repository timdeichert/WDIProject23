package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;

public class GamePublisherComparatorLowerCaseJaccard{

}
/*
public class GamePublisherComparatorLowerCaseJaccard implements Comparator<Game, Attribute> {

    private static final long serialVersionUID = 1L;
    TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();

    private ComparatorLogger comparisonLog;

    @Override
    public double compare(
            Game game1,
            Game game2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {

        String s1 = game1.getPublisher();
        String s2 = game2.getPublisher();

        if (s1 != null) {
            s1 = s1.toLowerCase();
        } else {
            s1 = "";
        }

        if (s2 != null) {
            s2 = s2.toLowerCase();
        } else {
            s2 = "";
        }

        // calculate similarity
        double similarity = sim.calculate(s1, s2);

        // postprocessing
        int postSimilarity = 1;
        if (similarity <= 0.3) {
            postSimilarity = 0;
        }

        postSimilarity *= similarity;

        if(this.comparisonLog != null){
            this.comparisonLog.setComparatorName(getClass().getName());

            this.comparisonLog.setRecord1Value(s1);
            this.comparisonLog.setRecord2Value(s2);

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
} */