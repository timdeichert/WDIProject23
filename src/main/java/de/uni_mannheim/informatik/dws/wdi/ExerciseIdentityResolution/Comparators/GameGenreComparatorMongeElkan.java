package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;

public class GameGenreComparatorMongeElkan implements Comparator<Game, Attribute>{

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
            return 0.0;
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
        if (s1 == s2)
            return (float)1.0;
        int len1 = s1.length(),
                len2 = s2.length();

        if (len1 == 0 || len2 == 0)
            return (float)0.0;
        int max_dist = (int)Math.floor(Math.max(len1, len2) / 2) - 1;
        int match = 0;
        int hash_s1[] = new int [s1.length()];
        int hash_s2[] = new int[s2.length()];

        for (int i = 0; i < len1; i++)
        {
            for (int j = Math.max(0, i - max_dist);
                 j < Math.min(len2, i + max_dist + 1); j++)

                if (s1.charAt(i) == s2.charAt(j) &&
                        hash_s2[j] == 0)
                {
                    hash_s1[i] = 1;
                    hash_s2[j] = 1;
                    match++;
                    break;
                }
        }

        if (match == 0)
            return (float)0.0;

        double t = 0;

        int point = 0;

        for (int i = 0; i < len1; i++)
            if (hash_s1[i] == 1)
            {
                while (hash_s2[point] == 0)
                    point++;

                if (s1.charAt(i) != s2.charAt(point++))
                    t++;
            }

        t /= 2;

        return (float)((((double)match) / ((double)len1)
                + ((double)match) / ((double)len2)
                + ((double)match - t) / ((double)match))
                / 3.0);
    }

    public float computeWinklerJaroSimilarity(String s1, String s2)
    {
        double jaro_dist = jaro_distance(s1, s2);

        if (jaro_dist > 0.7)
        {
            int prefix = 0;

            for (int i = 0;
                 i < Math.min(s1.length(), s2.length()); i++)
            {
                if (s1.charAt(i) == s2.charAt(i))
                    prefix++;
                else
                    break;
            }
            prefix = Math.min(4, prefix);

            jaro_dist += 0.1 * prefix * (1 - jaro_dist);
        }
        return (float)jaro_dist;
    }

    public List<String> filterNullValues(List<String> list) {
        if (list == null) {
            return new ArrayList<>();
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