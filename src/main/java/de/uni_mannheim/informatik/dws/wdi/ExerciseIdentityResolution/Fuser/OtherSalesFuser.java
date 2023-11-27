package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fuser;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.numeric.Average;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.processing.aggregators.MaxAggregator;
import org.apache.jena.sparql.function.library.max;

import java.util.ArrayList;
import java.util.Collection;

public class OtherSalesFuser extends AttributeValueFuser<Double, Game, Attribute> {
    public OtherSalesFuser(){
        super(null);
    }

    @Override
    public boolean hasValue(Game game, Correspondence<Attribute, Matchable> correspondence) {
        return game.hasValue(Game.OTHERSALES);
    }

    @Override
    public Double getValue(Game game, Correspondence<Attribute, Matchable> correspondence) {
        return game.getOther_Sales();
    }

    @Override
    public void fuse(RecordGroup<Game, Attribute> group, Game fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        double maxSales = 0.0;
        Collection<String> recordIdsWithMaxValue = new ArrayList<>();
        for(Game game : group.getRecords()) {
            if(game.hasValue(Game.OTHERSALES)) {
                double sales = game.getOther_Sales();
                if (sales > maxSales) {
                    maxSales = sales;
                    recordIdsWithMaxValue.clear();
                    recordIdsWithMaxValue.add(game.getIdentifier());
                } else if (sales == maxSales) {
                    recordIdsWithMaxValue.add(game.getIdentifier());
                }
            }
        }
        fusedRecord.setOther_Sales(maxSales);
        fusedRecord.setAttributeProvenance(Game.OTHERSALES, recordIdsWithMaxValue);
    }
}
