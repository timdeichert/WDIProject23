package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.GameBlockingKeyByTitleGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.*;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.GameXMLReader;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import org.slf4j.Logger;
import java.io.File;

public class IR_linear_dbpediaKaggle1_SortedNeighbourhoodBlocker {
    private static final Logger logger = WinterLogManager.activateLogger("traceFile");

    public static void main( String[] args ) throws Exception
    {
        HashedDataSet<Game, Attribute> ds = new HashedDataSet<>();
        File sourceFile = new File("data/input/DBpedia_Video_Game(Final).XML");
        String elementPath = "/Games/Game";
        new GameXMLReader().loadFromXML(sourceFile,elementPath,ds);

        HashedDataSet<Game, Attribute> ds2 = new HashedDataSet<>();
        File sourceFile2 = new File("data/input/Kaggle1_Video_Game(Final).XML");
        new GameXMLReader().loadFromXML(sourceFile2,elementPath,ds2);

        logger.info("*\tLoading gold standard\t*");
        MatchingGoldStandard gsTest = new MatchingGoldStandard();
        gsTest.loadFromCSVFile(new File(
                "data/goldstandard/DBpedia_Kaggle1_Gold_Standard(Testing).csv"));

        LinearCombinationMatchingRule<Game, Attribute> matchingRule = new LinearCombinationMatchingRule<>(
                0.7);
        matchingRule.activateDebugReport("data/output/debugResultsMatchingRule(DBpediaKaggle1_Linear_SNblocker).csv", 10000, gsTest);

        matchingRule.addComparator(new GamePlatformComparatorAbsoluteValue(), 0.3);
        matchingRule.addComparator(new GameReleaseComparatorExactYear(), 0.1);
        matchingRule.addComparator(new GameNameComparatorLowerCaseJaccard(), 0.5);
        matchingRule.addComparator(new GameGenreComparatorMongeElkan(), 0.05);
        matchingRule.addComparator(new GamePublisherLJaccard(), 0.025);
        matchingRule.addComparator(new GameDevComparatorJaccard(), 0.025);
//       matchingRule.addComparator(new EmbeddedComparator("src/main/java/de/uni_mannheim/informatik/dws/wdi/ExerciseIdentityResolution/cc.en.300.vec"), 0.1);

        SortedNeighbourhoodBlocker<Game, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new GameBlockingKeyByTitleGenerator(), 50);
        blocker.setMeasureBlockSizes(true);
        blocker.collectBlockSizeData("data/output/debugResultsBlocking(DBpediaKaggle1_Linear_SNblocker).csv", 10000);

        MatchingEngine<Game, Attribute> engine = new MatchingEngine<>();

        logger.info("*\tRunning identity resolution\t*");
        Processable<Correspondence<Game, Attribute>> correspondences = engine.runIdentityResolution(
                ds, ds2, null, matchingRule,blocker);

        new CSVCorrespondenceFormatter().writeCSV(new File("data/output/dbpedia_kaggle1_linear_SNblocker_correspondences.csv"), correspondences);

        logger.info("*\tEvaluating result\t*");

        MatchingEvaluator<Game, Attribute> evaluator = new MatchingEvaluator<Game, Attribute>();
        Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);

        logger.info("DBpedia <-> Kaggle 1");
        logger.info(String.format(
                "Precision: %.4f",perfTest.getPrecision()));
        logger.info(String.format(
                "Recall: %.4f",	perfTest.getRecall()));
        logger.info(String.format(
                "F1: %.4f",perfTest.getF1()));
    }
}
