package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;
import org.slf4j.Logger;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.RuleLearner;
import de.uni_mannheim.informatik.dws.winter.matching.rules.WekaMatchingRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.*;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.GameXMLReader;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.GameBlockingKeyByTitleGenerator;

public class IR_decisionTree_dbpediaKaggle2 {
    private static final Logger logger = WinterLogManager.activateLogger("traceFile");

    public static void main( String[] args ) throws Exception
    {

        File sourceFile = new File("data/input/DBpedia_Video_Game(Final).XML");
        String elementPath = "/Games/Game"; // Adjust the element path as per your XML structure
        HashedDataSet<Game, Attribute> ds1 = new HashedDataSet<>();
        new GameXMLReader().loadFromXML(sourceFile,elementPath,ds1);

        HashedDataSet<Game, Attribute> ds2 = new HashedDataSet<>();
        new GameXMLReader().loadFromXML(new File("data/input/Finalschema_vgsales.xml"), "/Games/Game", ds2);

        MatchingGoldStandard gsTraining = new MatchingGoldStandard();
        gsTraining.loadFromCSVFile(new File("data/goldstandard/Dbpedia-Kaggle2-train.csv"));

        String options[] = new String[] {}; // add any options for the decision tree here
        String modelType = "J48"; // use a decision tree
        WekaMatchingRule<Game, Attribute> matchingRule = new WekaMatchingRule<>(0.7, modelType, options);
        matchingRule.activateDebugReport("data/output/debugResultsMatchingRule(DBpediaKaggle2_decisionTree).csv", 1000, gsTraining);

        matchingRule.addComparator(new GamePublisherLJaccard());
        matchingRule.addComparator(new GameDevComparatorJaccard());
        matchingRule.addComparator(new GameNameComparatorJaccard());
        matchingRule.addComparator(new GameNameComparatorMongeElkan());
        matchingRule.addComparator(new GameGenreComparatorMongeElkan());
        matchingRule.addComparator(new GamePlatformComparatorJaccard());
        matchingRule.addComparator(new GameNameComparatorLevenshtein());
        matchingRule.addComparator(new GameReleaseComparatorExactYear());
        matchingRule.addComparator(new GamePlatformComparatorMongeElkan());
        matchingRule.addComparator(new GameNameComparatorLowerCaseJaccard());
        matchingRule.addComparator(new GamePublisherComparatorLevenshtein());
        matchingRule.addComparator(new GamePlatformComparatorAbsoluteValue());

        logger.info("*\tLearning matching rule\t*");
        RuleLearner<Game, Attribute> learner = new RuleLearner<>();
        learner.learnMatchingRule(ds1, ds2, null, matchingRule, gsTraining);
        logger.info(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));

        SortedNeighbourhoodBlocker<Game, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new GameBlockingKeyByTitleGenerator(), 50);
        blocker.collectBlockSizeData("data/output/debugResultsBlocking(DBpediaKaggle2_decisionTree).csv", 100);

        MatchingEngine<Game, Attribute> engine = new MatchingEngine<>();

        logger.info("*\tRunning identity resolution\t*");
        Processable<Correspondence<Game, Attribute>> correspondences = engine.runIdentityResolution(
                ds1, ds2, null, matchingRule, blocker);

        new CSVCorrespondenceFormatter().writeCSV(new File("data/output/DBpediaKaggle2_correspondences(decisionTree).csv"), correspondences);

        logger.info("*\tLoading gold standard\t*");
        MatchingGoldStandard gsTest = new MatchingGoldStandard();
        gsTest.loadFromCSVFile(new File(
                "data/goldstandard/Dbpedia-Kaggle2-test.csv"));

        logger.info("*\tEvaluating result\t*");
        MatchingEvaluator<Game, Attribute> evaluator = new MatchingEvaluator<Game, Attribute>();
        Performance perfTest = evaluator.evaluateMatching(correspondences,
                gsTest);

        logger.info("dbpedia1 <-> kaggle2");
        logger.info(String.format(
                "Precision: %.4f",perfTest.getPrecision()));
        logger.info(String.format(
                "Recall: %.4f",	perfTest.getRecall()));
        logger.info(String.format(
                "F1: %.4f",perfTest.getF1()));
    }
}
