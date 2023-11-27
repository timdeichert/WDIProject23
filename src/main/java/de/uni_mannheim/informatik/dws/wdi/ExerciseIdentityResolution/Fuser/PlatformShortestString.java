package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fuser;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.ShortestString;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class PlatformShortestString extends
        AttributeValueFuser<String, Game, Attribute> {

    public PlatformShortestString() {
        super(new ShortestString<Game, Attribute>());
    }

    @Override
    public void fuse(RecordGroup<Game, Attribute> group, Game fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<String, Game, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        fusedRecord.setPlatform(fused.getValue());
        fusedRecord.setAttributeProvenance(Game .PLATFORM, fused.getOriginalIds());
    }

    @Override
    public boolean hasValue(Game record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Game.PLATFORM);
    }

    @Override
    public String getValue(Game record, Correspondence<Attribute, Matchable> correspondence) {
        return record.getPlatform();
    }

}
