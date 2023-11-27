package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fuser;

import java.time.LocalDateTime;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * {@link AttributeValueFuser} for the release date of {@link Game}s.
 * 
 * @author Your Name
 */
public class ReleaseDateFuserFavourSource extends AttributeValueFuser<LocalDateTime, Game, Attribute> {

    public ReleaseDateFuserFavourSource() {
        super(new FavourSources<LocalDateTime, Game, Attribute>());
    }

    @Override
    public boolean hasValue(Game record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Game.RELEASE);
    }

    @Override
    public LocalDateTime getValue(Game record, Correspondence<Attribute, Matchable> correspondence) {
        return record.getRelease();
    }

    @Override
    public void fuse(RecordGroup<Game, Attribute> group, Game fusedRecord,
                     Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<LocalDateTime, Game, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        fusedRecord.setRelease(fused.getValue());
        fusedRecord.setAttributeProvenance(Game.RELEASE, fused.getOriginalIds());
    }
}
