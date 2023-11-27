package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fuser;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

import java.util.ArrayList;
import java.util.Collection;

public class GlobalSalesFuser extends AttributeValueFuser<Double, Game, Attribute> {
    public GlobalSalesFuser(){
        super(null); // No predefined strategy
    }

    @Override
    public boolean hasValue(Game game, Correspondence<Attribute, Matchable> correspondence) {
        return game.hasValue(Game.GLOBALSALES);
    }

    @Override
    public Double getValue(Game game, Correspondence<Attribute, Matchable> correspondence) {
        return game.getGlobal_Sales();
    }

    @Override
    public void fuse(RecordGroup<Game, Attribute> group, Game fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        double maxSales = 0.0;
        Collection<String> recordIdsWithMaxValue = new ArrayList<>(); // To keep track of provenance

        for(Game game : group.getRecords()) {
            if(game.hasValue(Game.GLOBALSALES)) {
                double sales = game.getGlobal_Sales();
                if (sales > maxSales) {
                    maxSales = sales;
                    recordIdsWithMaxValue.clear(); // Clear previous records as a new max is found
                    recordIdsWithMaxValue.add(game.getIdentifier()); // Track provenance
                } else if (sales == maxSales) {
                    // If another record has the same max value, add its identifier too
                    recordIdsWithMaxValue.add(game.getIdentifier());
                }
            }
        }
        fusedRecord.setGlobal_Sales(maxSales);
        // Set the provenance information
        fusedRecord.setAttributeProvenance(Game.GLOBALSALES, recordIdsWithMaxValue);
    }
}
