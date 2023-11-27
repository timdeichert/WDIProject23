package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fuser;

import java.util.List;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.LongestString;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

import java.util.Collections;
import java.util.Comparator;
/**
 * {@link AttributeValueFuser} for the director of {@link Game}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class PublishersFuserLongestString extends AttributeValueFuser<String, Game, Attribute> {
    
    public PublishersFuserLongestString() {
        super(new LongestString<>());
    }

    @Override
    public boolean hasValue(Game record, Correspondence<Attribute, Matchable> correspondence) {
        List<String> publishers = record.getPublisher();
        return publishers != null && !publishers.isEmpty();
    }

    @Override
    public String getValue(Game record, Correspondence<Attribute, Matchable> correspondence) {
        List<String> publishers = record.getPublisher();
        if (publishers != null && !publishers.isEmpty()) {
            return Collections.max(publishers, Comparator.comparing(String::length));
        } else {
            return null;
        }
    }

    @Override
    public void fuse(RecordGroup<Game, Attribute> group, Game fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<String, Game, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        fusedRecord.setPublisher(fused.getValue() != null ? Collections.singletonList(fused.getValue()) : null);
        fusedRecord.setAttributeProvenance(Game.PUBLISHERS, fused.getOriginalIds());
    }
}

