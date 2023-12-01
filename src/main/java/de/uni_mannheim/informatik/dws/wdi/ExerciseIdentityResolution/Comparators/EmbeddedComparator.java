package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import org.nd4j.linalg.api.ndarray.INDArray;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;

import org.nd4j.linalg.ops.transforms.Transforms;
import java.util.stream.Collectors;

public class EmbeddedComparator implements Comparator<Game, Attribute> {
    private ComparatorLogger comparisonLog;

    private WordVectors wordVectors;

    public EmbeddedComparator(String pathToEmbeddings) {
        try {
            File file = new File(pathToEmbeddings);
            wordVectors = WordVectorSerializer.readWord2VecModel(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public INDArray getWordVector(String word) {
        return wordVectors.getWordVectorMatrix(word);
    }


    @Override
    public double compare(
            Game record1,
            Game record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {

        List<String> platformList1 = record1.getGenre();
        List<String> platformList2 = record2.getGenre();


        double similarity = computeEmbeddingSimilarity(platformList1, platformList2);

        if(this.comparisonLog != null){
            this.comparisonLog.setComparatorName(getClass().getName());

            List<String> filteredGenreList1 = filterNullValues(record1.getGenre());
            List<String> filteredGenreList2 = filterNullValues(record2.getGenre());

            this.comparisonLog.setRecord1Value(String.join(" ", filteredGenreList1));
            this.comparisonLog.setRecord2Value(String.join(" ", filteredGenreList2));

            this.comparisonLog.setSimilarity(Double.toString(similarity));
        }
        return similarity;
    }


    public double computeEmbeddingSimilarity(List<String> list1, List<String> list2) {
        if (list1.isEmpty() || list2.isEmpty()) {
            return 0.0;
        }

        INDArray vector1 = averageEmbedding(list1);
        INDArray vector2 = averageEmbedding(list2);

        if (vector1 == null || vector2 == null) {
            return 0.0;
        }

        double cosineSim = Transforms.cosineSim(vector1, vector2);

        return cosineSim;
    }


    private INDArray averageEmbedding(List<String> platforms) {
        INDArray sum = null;
        int count = 0;
        for (String platform : platforms) {
            if (wordVectors.hasWord(platform)) {
                INDArray vector = wordVectors.getWordVectorMatrix(platform);
                if (sum == null) {
                    sum = vector;
                } else {
                    sum.addi(vector);
                }
                count++;
            }
        }
        return count == 0 ? null : sum.divi(count);
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
