package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.GameBlockingKeyByTitleGenerator;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GameNameComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GameNameComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GameNameComparatorLowerCaseJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GameReleaseComparatorExactYear;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GameGenreComparatorTokenContainment;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.GameXMLReader;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.NoBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
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

public class IR_linear {
    private static final Logger logger = WinterLogManager.activateLogger("default");

    public static void main( String[] args ) throws Exception
    {
        // Create first HashedDataSet to store Game objects
        HashedDataSet<Game, Attribute> ds = new HashedDataSet<>();
        File sourceFile = new File("data/input/DBpedia_Video_Game(Final).XML");
        String elementPath = "/Games/Game"; // Adjust the element path as per your XML structure
        new GameXMLReader().loadFromXML(sourceFile,elementPath,ds);

        // Create first HashedDataSet to store Game objects
        HashedDataSet<Game, Attribute> ds2 = new HashedDataSet<>();
        File sourceFile2 = new File("data/input/Kaggle1_Video_Game(Final).XML");
        new GameXMLReader().loadFromXML(sourceFile2,elementPath,ds2);
        System.out.println(ds.size());

        // load the gold standard (test set)
        logger.info("*\tLoading gold standard\t*");
        MatchingGoldStandard gsTest = new MatchingGoldStandard();
        gsTest.loadFromCSVFile(new File(
                "data/goldstandard/DBpedia_Kaggle1_Gold_Standard(test).csv"));

        // create a matching rule
        LinearCombinationMatchingRule<Game, Attribute> matchingRule = new LinearCombinationMatchingRule<>(
                0.7);
        matchingRule.activateDebugReport("data/output/debugResultsMatchingRule.csv", 10000, gsTest);

        // add comparators
        matchingRule.addComparator(new GameReleaseComparatorExactYear(), 0.2);
        matchingRule.addComparator(new GameNameComparatorLowerCaseJaccard(), 0.7);
        matchingRule.addComparator(new GameGenreComparatorTokenContainment(), 0.1);


        // create a blocker (blocking strategy)
        StandardRecordBlocker<Game, Attribute> blocker = new StandardRecordBlocker<Game, Attribute>(new GameBlockingKeyByTitleGenerator());
//       NoBlocker<Game, Attribute> blocker = new NoBlocker<>();
//		SortedNeighbourhoodBlocker<Game, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new GameBlockingKeyByTitleGenerator(), 1);
        blocker.setMeasureBlockSizes(true);
        //Write debug results to file:
        blocker.collectBlockSizeData("data/output/debugResultsBlocking.csv", 10000);

        // Initialize Matching Engine
        MatchingEngine<Game, Attribute> engine = new MatchingEngine<>();

        // Execute the matching
        logger.info("*\tRunning identity resolution\t*");
        Processable<Correspondence<Game, Attribute>> correspondences = engine.runIdentityResolution(
                ds, ds2, null, matchingRule,blocker);

        // write the correspondences to the output file
        new CSVCorrespondenceFormatter().writeCSV(new File("data/output/academy_awards_2_actors_correspondences.csv"), correspondences);

        logger.info("*\tEvaluating result\t*");

        // evaluate your result
        MatchingEvaluator<Game, Attribute> evaluator = new MatchingEvaluator<Game, Attribute>();
        Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);

        // print the evaluation result
        logger.info("GAME <-> GAME");
        logger.info(String.format(
                "Precision: %.4f",perfTest.getPrecision()));
        logger.info(String.format(
                "Recall: %.4f",	perfTest.getRecall()));
        logger.info(String.format(
                "F1: %.4f",perfTest.getF1()));
    }


}