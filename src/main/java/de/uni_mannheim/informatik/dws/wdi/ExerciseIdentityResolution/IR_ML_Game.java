package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.GameBlockingKeyByDecadeGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.GameBlockingKeyByTitleGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.GameBlockingKeyByYearGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.*;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.GameXMLReader;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.GameXMLReader;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.RuleLearner;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.NoBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.WekaMatchingRule;
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

public class IR_ML_Game {

    /*
     * Logging Options:
     * 		default: 	level INFO	- console
     * 		trace:		level TRACE     - console
     * 		infoFile:	level INFO	- console/file
     * 		traceFile:	level TRACE	- console/file
     *
     * To set the log level to trace and write the log to winter.log and console,
     * activate the "traceFile" logger as follows:
     *     private static final Logger logger = WinterLogManager.activateLogger("traceFile");
     *
     */

    private static final Logger logger = WinterLogManager.activateLogger("default");

    public static void main( String[] args ) throws Exception
    {


        // Exercise loading data
        /*
        logger.info("*\tLoading datasets\t*");
        HashedDataSet<Game, Attribute> ds1 = new HashedDataSet<>();
        new GameXMLReader().loadFromXML(new File("data/input/academy_awards.xml"), "/Games/Game", ds1);
        HashedDataSet<Game, Attribute> ds2 = new HashedDataSet<>();
        new GameXMLReader().loadFromXML(new File("data/input/actors.xml"), "/Games/Game", ds2);
*/

        // Own Game data loading
        File sourceFile = new File("data/input/DBpedia_Video_Game(Final).XML");
        String elementPath = "/Games/Game"; // Adjust the element path as per your XML structure
        HashedDataSet<Game, Attribute> ds1 = new HashedDataSet<>();
        new GameXMLReader().loadFromXML(sourceFile,elementPath,ds1);

        HashedDataSet<Game, Attribute> ds2 = new HashedDataSet<>();
        new GameXMLReader().loadFromXML(new File("data/input/Kaggle1_Video_Game(Final).xml"), "/Games/Game", ds2);

        // load the training set
        MatchingGoldStandard gsTraining = new MatchingGoldStandard();
        gsTraining.loadFromCSVFile(new File("data/goldstandard/DBpedia_Kaggle1_Gold_Standard_Training.csv"));


        // create a matching rule using logistic regression
        String options[] = new String[] { "-S" };
        String modelType = "SimpleLogistic"; // use a logistic regression
        WekaMatchingRule<Game, Attribute> matchingRule = new WekaMatchingRule<>(0.7, modelType, options);
        matchingRule.activateDebugReport("data/output/debugResultsMatchingRule.csv", 1000, gsTraining);

/*
		// create a matching rule using a decision tree
		String options[] = new String[] {}; // add any options for the decision tree here
		String modelType = "J48"; // use a decision tree
		WekaMatchingRule<Game, Attribute> matchingRule = new WekaMatchingRule<>(0.7, modelType, options);
		matchingRule.activateDebugReport("data/output/debugResultsMatchingRule.csv", 1000, gsTraining);
*/


        // add comparators
        matchingRule.addComparator(new GameDevComparatorJaccard());
        matchingRule.addComparator(new GameGenreComparatorMongeElkan());
        matchingRule.addComparator(new GameNameComparatorLowerCaseJaccard());
        matchingRule.addComparator(new GameNameComparatorLevenshtein());
        matchingRule.addComparator(new GameNameComparatorJaccard());
        matchingRule.addComparator(new GamePlatformComparatorMongeElkan());
        matchingRule.addComparator(new GamePublisherComparatorLevenshtein());
        matchingRule.addComparator(new GamePublisherLJaccard());
        matchingRule.addComparator(new GameReleaseComparatorExactYear());

/*        // train the matching rule's model
        logger.info("*\tLearning matching rule\t*");
        RuleLearner<Game, Attribute> learner = new RuleLearner<>();
        learner.learnMatchingRule(ds1, ds2, null, matchingRule, gsTraining);
        logger.info(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));
*/

		// train the matching rule's model using the decision tree
		logger.info("*\tLearning matching rule with decision tree\t*");
		RuleLearner<Game, Attribute> learner = new RuleLearner<>();
		learner.learnMatchingRule(ds1, ds2, null, matchingRule, gsTraining);
		logger.info(String.format("Matching rule with decision tree is:\n%s", matchingRule.getModelDescription()));


        // create a blocker (blocking strategy)
//        StandardRecordBlocker<Game, Attribute> blocker = new StandardRecordBlocker<Game, Attribute>(new GameBlockingKeyByTitleGenerator());
        NoBlocker<Game, Attribute> NoBlocker = new NoBlocker<>();
		SortedNeighbourhoodBlocker<Game, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new GameBlockingKeyByTitleGenerator(), 10);
        blocker.collectBlockSizeData("data/output/debugResultsBlocking.csv", 100);

        // Initialize Matching Engine
        MatchingEngine<Game, Attribute> engine = new MatchingEngine<>();

        // Execute the matching
        logger.info("*\tRunning identity resolution\t*");
        Processable<Correspondence<Game, Attribute>> correspondences = engine.runIdentityResolution(
                ds1, ds2, null, matchingRule,
                blocker);

        // write the correspondences to the output file
        new CSVCorrespondenceFormatter().writeCSV(new File("data/output/DBpedia_Kaggle1_correspondences.csv"), correspondences);

        // load the gold standard (test set)
        logger.info("*\tLoading gold standard\t*");
        MatchingGoldStandard gsTest = new MatchingGoldStandard();
        gsTest.loadFromCSVFile(new File(
                "data/goldstandard/DBpedia_Kaggle1_Gold_Standard.csv"));

        // evaluate your result
        logger.info("*\tEvaluating result\t*");
        MatchingEvaluator<Game, Attribute> evaluator = new MatchingEvaluator<Game, Attribute>();
        Performance perfTest = evaluator.evaluateMatching(correspondences,
                gsTest);

        // print the evaluation result
        logger.info("Academy Awards <-> Actors");
        logger.info(String.format(
                "Precision: %.4f",perfTest.getPrecision()));
        logger.info(String.format(
                "Recall: %.4f",	perfTest.getRecall()));
        logger.info(String.format(
                "F1: %.4f",perfTest.getF1()));
    }
}
