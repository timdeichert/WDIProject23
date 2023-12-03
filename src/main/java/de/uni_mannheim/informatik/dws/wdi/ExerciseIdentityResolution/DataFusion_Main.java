package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fuser.*;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation.*;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.GameXMLFormatter;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.GameXMLReader;
import de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEvaluator;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.dws.winter.model.*;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import org.slf4j.Logger;

public class DataFusion_Main
{
    private static final Logger logger = WinterLogManager.activateLogger("trace");

    public static void main( String[] args ) throws Exception
    {
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

        ds1.setScore(3.0);
        ds2.setScore(1.0);
        ds3.setScore(2.0);

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter(Locale.ENGLISH);

        ds1.setDate(LocalDateTime.parse("2012-01-01", formatter));
        ds2.setDate(LocalDateTime.parse("2010-01-01", formatter));
        ds3.setDate(LocalDateTime.parse("2008-01-01", formatter));

        logger.info("*\tLoading correspondences\t*");
        CorrespondenceSet<Game, Attribute> correspondences = new CorrespondenceSet<>();
        correspondences.loadCorrespondences(new File("data/output/DBpedia_Kaggle1_correspondences(DBpediaKaggle1_decisionTree).csv"),ds1, ds3);
        correspondences.loadCorrespondences(new File("data/output/DBpedia_Kaggle1_correspondences(Kaggle1Kaggle2_decisionTree).csv"),ds3, ds2);

        correspondences.printGroupSizeDistribution();

        logger.info("*\tEvaluating results\t*");
        DataSet<Game, Attribute> gs = new FusibleHashedDataSet<>();
        new GameXMLReader().loadFromXML(new File("data/goldstandard/Fuser_GoldStandard.xml"), "/Games/Game", gs);

        for(Game m : gs.get()) {
            logger.info(String.format("gs: %s", m.getIdentifier()));
        }

        DataFusionStrategy<Game, Attribute> strategy = new DataFusionStrategy<>(new GameXMLReader());
        strategy.activateDebugReport("data/output/debugResultsDatafusion.csv", -1, gs);

        strategy.addAttributeFuser(Game.NAME, new TitleFuserFavourSource(),new GameTitleEvaluationRule());
        strategy.addAttributeFuser(Game.RELEASE,new ReleaseDateFuserFavourSource(), new ReleaseEvaluation());
        strategy.addAttributeFuser(Game.DEVELOPERS, new DeveloperFuser(),new DeveloperEvaluation());
        strategy.addAttributeFuser(Game.GLOBALSALES, new GlobalSalesFuser(),new GlobalSalesEvaluation());
        strategy.addAttributeFuser(Game.EUSALES, new EuSalesFuserMax(),new EuSalesEvaluation());
        strategy.addAttributeFuser(Game.GENRES, new GenresFuserFavourSource(),new GenresEvaluation());
        strategy.addAttributeFuser(Game.PUBLISHERS, new PublisherFuserFavourSource(),new PublisherEvaluationRule());
        strategy.addAttributeFuser(Game.PLATFORM, new PlatformShortestString(),new PlatformEvaluation());
        strategy.addAttributeFuser(Game.JPSALES, new JpSalesFuserMax(),new JpSalesEvaluation());
        strategy.addAttributeFuser(Game.NASALES, new NaSalesFuserMax(),new NaSalesEvaluation());
        strategy.addAttributeFuser(Game.OTHERSALES, new OtherSalesFuser(),new OtherSalesEvaluation());
        strategy.addAttributeFuser(Game.MODE,new ModeFuserFavourSource(),new ModeEvaluation());

        DataFusionEngine<Game, Attribute> engine = new DataFusionEngine<>(strategy);

        engine.printClusterConsistencyReport(correspondences, null);

        engine.writeRecordGroupsByConsistency(new File("data/output/recordGroupConsistencies.csv"), correspondences, null);

        logger.info("*\tRunning data fusion\t*");
        FusibleDataSet<Game, Attribute> fusedDataSet = engine.run(correspondences, null);

        new GameXMLFormatter().writeXML(new File("data/output/fused.xml"), fusedDataSet);

        DataFusionEvaluator<Game, Attribute> evaluator = new DataFusionEvaluator<>(strategy, new RecordGroupFactory<Game, Attribute>());

        double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

        logger.info(String.format("*\tAccuracy: %.2f", accuracy));
        fusedDataSet.printDataSetDensityReport();
    }
}