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


        double similarity = this.computeMongeElkanSimilarity(genreList1, genreList2);

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


    public double computeMongeElkanSimilarity(List<String> list1, List<String> list2) {
        if (list1 == null || list2 == null) {
            return 0.0;  // Return default similarity value if any of the lists is null
        }

        double sumMaxSim = 0;
        for (String s1 : list1) {
            double maxSim = list2.stream()
                    .mapToDouble(s2 -> computeWinklerJaroSimilarity(s1, s2))
                    .max()
                    .orElse(0);
            sumMaxSim += maxSim;
        }

        return sumMaxSim / list1.size();
    }


    public float jaro_distance(String s1, String s2)
    {
        // If the strings are equal
        if (s1 == s2)
            return (float)1.0;

        // Length of two strings
        int len1 = s1.length(),
                len2 = s2.length();

        if (len1 == 0 || len2 == 0)
            return (float)0.0;

        // Maximum distance upto which matching
        // is allowed
        int max_dist = (int)Math.floor(Math.max(len1, len2) / 2) - 1;

        // Count of matches
        int match = 0;

        // Hash for matches
        int hash_s1[] = new int [s1.length()];
        int hash_s2[] = new int[s2.length()];

        // Traverse through the first string
        for (int i = 0; i < len1; i++)
        {

            // Check if there is any matches
            for (int j = Math.max(0, i - max_dist);
                 j < Math.min(len2, i + max_dist + 1); j++)

                // If there is a match
                if (s1.charAt(i) == s2.charAt(j) &&
                        hash_s2[j] == 0)
                {
                    hash_s1[i] = 1;
                    hash_s2[j] = 1;
                    match++;
                    break;
                }
        }

        // If there is no match
        if (match == 0)
            return (float)0.0;

        // Number of transpositions
        double t = 0;

        int point = 0;

        // Count number of occurrences
        // where two characters match but
        // there is a third matched character
        // in between the indices
        for (int i = 0; i < len1; i++)
            if (hash_s1[i] == 1)
            {

                // Find the next matched character
                // in second string
                while (hash_s2[point] == 0)
                    point++;

                if (s1.charAt(i) != s2.charAt(point++))
                    t++;
            }

        t /= 2;

        // Return the Jaro Similarity
        return (float)((((double)match) / ((double)len1)
                + ((double)match) / ((double)len2)
                + ((double)match - t) / ((double)match))
                / 3.0);
    }

    // Jaro Winkler Similarity
    public float computeWinklerJaroSimilarity(String s1, String s2)
    {
        double jaro_dist = jaro_distance(s1, s2);

        // If the jaro Similarity is above a threshold
        if (jaro_dist > 0.7)
        {

            // Find the length of common prefix
            int prefix = 0;

            for (int i = 0;
                 i < Math.min(s1.length(), s2.length()); i++)
            {

                // If the characters match
                if (s1.charAt(i) == s2.charAt(i))
                    prefix++;

                    // Else break
                else
                    break;
            }
            // Maximum of 4 characters are allowed in prefix
            prefix = Math.min(4, prefix);

            // Calculate jaro winkler Similarity
            jaro_dist += 0.1 * prefix * (1 - jaro_dist);
        }
        return (float)jaro_dist;
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
