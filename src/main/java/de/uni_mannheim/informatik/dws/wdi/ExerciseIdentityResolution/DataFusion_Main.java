package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

/*
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation.ActorsEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation.DateEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation.DirectorEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation.TitleEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.ActorsFuserUnion;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.DateFuserFavourSource;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.DateFuserVoting;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.DirectorFuserLongestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.TitleFuserShortestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.FusibleMovieFactory;
*/


import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.GameXMLReader;
import de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEvaluator;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroupFactory;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import org.slf4j.Logger;

public class DataFusion_Main
{
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

    private static final Logger logger = WinterLogManager.activateLogger("trace");

    public static void main( String[] args ) throws Exception
    {
        // Load the Data into FusibleDataSet
        logger.info("*\tLoading datasets\t*");

        String elementPath = "/Games/Game"; // Adjust the element path as per your XML structure

        FusibleDataSet<Game, Attribute> ds1 = new FusibleHashedDataSet<>();
        new GameXMLReader().loadFromXML(new File("data/input/DBpedia_Video_Game(Final).XML"), elementPath, ds1);
        ds1.printDataSetDensityReport();

        FusibleDataSet<Game, Attribute> ds2 = new FusibleHashedDataSet<>();
        new GameXMLReader().loadFromXML(new File("data/input/Finalschema_vgsales.XML"), elementPath, ds2);
        ds2.printDataSetDensityReport();

        FusibleDataSet<Game, Attribute> ds3 = new FusibleHashedDataSet<>();
        new GameXMLReader().loadFromXML(new File("data/input/Kaggle1_Video_Game(Final).XML"), elementPath, ds3);
        ds3.printDataSetDensityReport();

        // Maintain Provenance
        // Scores (e.g. from rating)
        ds1.setScore(1.0);
        ds2.setScore(2.0);
        ds3.setScore(3.0);

        // Date (e.g. last update)
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter(Locale.ENGLISH);

        ds1.setDate(LocalDateTime.parse("2012-01-01", formatter));
        ds2.setDate(LocalDateTime.parse("2010-01-01", formatter));
        ds3.setDate(LocalDateTime.parse("2008-01-01", formatter));

        // load correspondences
        logger.info("*\tLoading correspondences\t*");
        CorrespondenceSet<Game, Attribute> correspondences = new CorrespondenceSet<>();
        correspondences.loadCorrespondences(new File("/data/output/academy_awards_2_actors_correspondences.csv"),ds1, ds2);
        correspondences.loadCorrespondences(new File("data/output/actors_2_golden_globes_correspondences.csv"),ds2, ds3);

        // write group size distribution
        correspondences.printGroupSizeDistribution();

        // load the gold standard
        logger.info("*\tEvaluating results\t*");
        DataSet<Game, Attribute> gs = new FusibleHashedDataSet<>();
        new GameXMLReader().loadFromXML(new File("data/goldstandard/gold.xml"), "/movies/movie", gs);

        for(Game m : gs.get()) {
            logger.info(String.format("gs: %s", m.getIdentifier()));
        }

        // define the fusion strategy
        DataFusionStrategy<Game, Attribute> strategy = new DataFusionStrategy<>(new GameXMLReader());
        // write debug results to file
        strategy.activateDebugReport("data/output/debugResultsDatafusion.csv", -1, gs);

        // add attribute fusers
//        strategy.addAttributeFuser(Game.NAME, new TitleFuserShortestString(),new TitleEvaluationRule());
//       strategy.addAttributeFuser(Game.DEVELOPERS,new DirectorFuserLongestString(), new DirectorEvaluationRule());
//        strategy.addAttributeFuser(Game.RELEASE, new DateFuserFavourSource(),new DateEvaluationRule());
//        strategy.addAttributeFuser(Game.GENRES,new ActorsFuserUnion(),new ActorsEvaluationRule());

        // create the fusion engine
        DataFusionEngine<Game, Attribute> engine = new DataFusionEngine<>(strategy);

        // print consistency report
        engine.printClusterConsistencyReport(correspondences, null);

        // print record groups sorted by consistency
        engine.writeRecordGroupsByConsistency(new File("data/output/recordGroupConsistencies.csv"), correspondences, null);

        // run the fusion
        logger.info("*\tRunning data fusion\t*");
        FusibleDataSet<Game, Attribute> fusedDataSet = engine.run(correspondences, null);

        // write the result
        new GameXMLFormatter().writeXML(new File("data/output/fused.xml"), fusedDataSet);

        // evaluate
        DataFusionEvaluator<Game, Attribute> evaluator = new DataFusionEvaluator<>(strategy, new RecordGroupFactory<Game, Attribute>());

        double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

        logger.info(String.format("*\tAccuracy: %.2f", accuracy));
    }
}