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

public class EuSalesFuserAverage extends AttributeValueFuser<Double, Game, Attribute> {
    public EuSalesFuserAverage(){
        super(new Average<Game, Attribute>());
    }

    @Override
    public boolean hasValue(Game game, Correspondence<Attribute, Matchable> correspondence) {
        return game.hasValue(Game.EUSALES);
    }

    @Override
    public Double getValue(Game game, Correspondence<Attribute, Matchable> correspondence) {
        return game.getEU_Sales();
    }

    @Override
    public void fuse(RecordGroup<Game, Attribute> group, Game fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<Double, Game, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        fusedRecord.setEU_Sales(fused.getValue());
        fusedRecord.setAttributeProvenance(Game.GLOBALSALES,
                fused.getOriginalIds());

    }
}
