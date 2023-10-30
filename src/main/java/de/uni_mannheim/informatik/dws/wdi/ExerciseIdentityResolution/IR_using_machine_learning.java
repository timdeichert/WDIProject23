package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.GameXMLReader;
import org.slf4j.Logger;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.MovieBlockingKeyByDecadeGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.MovieBlockingKeyByTitleGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.MovieBlockingKeyByYearGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieDateComparator10Years;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieDateComparator2Years;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieDirectorComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieDirectorComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieDirectorComparatorLowerCaseJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieTitleComparatorEqual;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieTitleComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieTitleComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Movie;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.MovieXMLReader;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.RuleLearner;
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

public class IR_using_machine_learning {
	
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


		// loading our own data
		logger.info("*\tLoading datasets\t*");
		HashedDataSet<Game, Attribute> dbpedia = new HashedDataSet<>();
		new GameXMLReader().loadFromXML(new File("data/input/DBpedia_Video_Game(Final).xml"), "/Games/Game", dbpedia);
		HashedDataSet<Game, Attribute> kaggle2 = new HashedDataSet<>();
		new GameXMLReader().loadFromXML(new File("data/input/Finalschema_vgsales.xml"), "/Games/Game", kaggle2);
		HashedDataSet<Game, Attribute> kaggle1 = new HashedDataSet<>();
		new GameXMLReader().loadFromXML(new File("data/input/Kaggle1_Video_Game(Final).xml"), "/Games/Game", kaggle1);


		// load the training set
		MatchingGoldStandard dbpediaKaggle1Training = new MatchingGoldStandard();
		dbpediaKaggle1Training.loadFromCSVFile(new File("data/goldstandard/DBpedia_Kaggle1_Gold_Standard(train).csv"));
		MatchingGoldStandard kaggle1Kaggle2Training = new MatchingGoldStandard();
		kaggle1Kaggle2Training.loadFromCSVFile(new File("data/goldstandard/Kaggle1_Kaggle2_Gold(train).csv"));


		// create a matching rule
		String options[] = new String[] { "-S" };
		String modelType = "SimpleLogistic"; // use a logistic regression (machine learning)
		WekaMatchingRule<Game, Attribute> matchingRule = new WekaMatchingRule<>(0.7, modelType, options);
		matchingRule.activateDebugReport("data/output/debugResultsMatchingRule.csv", 1000, dbpediaKaggle1Training);

		WekaMatchingRule<Game, Attribute> matchingRule_2 = new WekaMatchingRule<>(0.7, modelType, options);
		matchingRule_2.activateDebugReport("data/output/debugResultsMatchingRule_2.csv", 1000, kaggle1Kaggle2Training);


		// add comparators
		//matchingRule.addComparator(new MovieTitleComparatorEqual());
		matchingRule.addComparator(new MovieDateComparator2Years());
		matchingRule.addComparator(new MovieDateComparator10Years());
		//matchingRule.addComparator(new MovieDirectorComparatorJaccard());
		//matchingRule.addComparator(new MovieDirectorComparatorLevenshtein());
		//matchingRule.addComparator(new MovieDirectorComparatorLowerCaseJaccard());
		//matchingRule.addComparator(new MovieTitleComparatorLevenshtein());
		//matchingRule.addComparator(new MovieTitleComparatorJaccard());
		
		
		// train the matching rule's model
		logger.info("*\tLearning matching rule\t*");
		RuleLearner<Game, Attribute> learner_1 = new RuleLearner<>();
		learner_1.learnMatchingRule(dbpedia, kaggle2, null, matchingRule, dbpediaKaggle1Training);
		logger.info(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));

		// train the matching rule's model
		logger.info("*\tLearning matching rule\t*");
		RuleLearner<Game, Attribute> learner_2 = new RuleLearner<>();
		learner_2.learnMatchingRule(dbpedia, kaggle1, null, matchingRule, kaggle1Kaggle2Training);
		logger.info(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));


		// create a blocker (blocking strategy)
		StandardRecordBlocker<Game, Attribute> blocker = new StandardRecordBlocker<Game, Attribute>(new MovieBlockingKeyByTitleGenerator());
//		SortedNeighbourhoodBlocker<Movie, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new MovieBlockingKeyByDecadeGenerator(), 1);
		blocker.collectBlockSizeData("data/output/debugResultsBlocking.csv", 100);



		// Initialize Matching Engine
		MatchingEngine<Game, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		logger.info("*\tRunning identity resolution\t*");
		Processable<Correspondence<Game, Attribute>> correspondences = engine.runIdentityResolution(
				dbpedia, kaggle2, null, matchingRule,
				blocker);

		// Execute the matching
		logger.info("*\tRunning identity resolution\t*");
		Processable<Correspondence<Game, Attribute>> correspondences_2 = engine.runIdentityResolution(
				dbpedia, kaggle1, null, matchingRule,
				blocker);


		// Execute the matching
		logger.info("*\tRunning identity resolution\t*");
		Processable<Correspondence<Game, Attribute>> correspondences_3 = engine.runIdentityResolution(
				kaggle1, kaggle2, null, matchingRule,
				blocker);


		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/academy_awards_2_actors_correspondences.csv"), correspondences);

		// load the gold standard (test set)
		logger.info("*\tLoading gold standard\t*");
		MatchingGoldStandard dbpediaKaggle1Testing = new MatchingGoldStandard();
		dbpediaKaggle1Testing.loadFromCSVFile(new File(
				"data/goldstandard/DBpedia_Kaggle1_Gold_Standard(test).csv"));
		
		// evaluate your result
		logger.info("*\tEvaluating result\t*");
		MatchingEvaluator<Game, Attribute> evaluator = new MatchingEvaluator<Game, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences,
				dbpediaKaggle1Testing);
		
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
