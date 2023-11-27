package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fuser;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

import java.util.List;

public class ModeFuserFavourSource extends AttributeValueFuser<List<String>, Game, Attribute> {
    public ModeFuserFavourSource(){
        super(new FavourSources<List<String>, Game, Attribute>());
    }

    @Override
    public boolean hasValue(Game game, Correspondence<Attribute, Matchable> correspondence) {
        return game.hasValue(Game.MODE);
    }

    @Override
    public List<String> getValue(Game game, Correspondence<Attribute, Matchable> correspondence) {
        return game.getMode();
    }

    @Override
    public void fuse(RecordGroup<Game, Attribute> group, Game fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<List<String>, Game, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        fusedRecord.setMode(fused.getValue());
        fusedRecord.setAttributeProvenance(Game.MODE, fused.getOriginalIds());
    }
}
