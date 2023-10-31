package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import java.util.stream.Collectors;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;


public class GameGenreComparatorTokenContainment implements Comparator<Game, Attribute>{

    private ComparatorLogger comparisonLog;

    @Override
    public double compare(
            Game record1,
            Game record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {

        List<String> genreList1 = record1.getGenre();
        List<String> genreList2 = record2.getGenre();


        double similarity = this.computeMOTCSimilarity(genreList1, genreList2);

        if(this.comparisonLog != null){
            this.comparisonLog.setComparatorName(getClass().getName());

            List<String> filteredGenreList1 = filterNullValues(record1.getGenre());
            List<String> filteredGenreList2 = filterNullValues(record2.getGenre());

            this.comparisonLog.setRecord1Value(String.join(", ", filteredGenreList1));
            this.comparisonLog.setRecord2Value(String.join(", ", filteredGenreList2));

            this.comparisonLog.setSimilarity(Double.toString(similarity));
        }
        return similarity;

    }

    public double computeMOTCSimilarity(List<String> list1, List<String> list2) {
        if (list1 == null || list2 == null) {
            return 0.0;  // Return default similarity value if any of the lists is null
        }
        Set<String> set1 = new HashSet<>(list1);
        Set<String> set2 = new HashSet<>(list2);

        // Count the number of shared tokens
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        int sharedTokens = intersection.size();

        // Return the ratio of shared tokens to the smallest token set size
        return (double) sharedTokens / Math.min(set1.size(), set2.size());
    }


    public List<String> filterNullValues(List<String> list) {
        if (list == null) {
            return new ArrayList<>();  // Return an empty list if the input list is null
        }
        return list.stream().filter(item -> item != null).collect(Collectors.toList());
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
