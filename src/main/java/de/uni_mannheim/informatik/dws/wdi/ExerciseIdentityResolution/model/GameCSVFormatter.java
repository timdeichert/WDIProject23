package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.util.List;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVDataSetFormatter;

public class GameCSVFormatter extends CSVDataSetFormatter<Game, Attribute> {

    @Override
    public String[] getHeader(List<Attribute> orderedHeader) {
        return new String[] { "ID", "Name", "Developers", "Release", "Genres", "Modes"};
    }

    @Override
    public String[] format(Game record, DataSet<Game, Attribute> dataset, List<Attribute> orderedHeader) {
        return new String[] {
                record.getIdentifier(),
                record.getName(),
                String.valueOf(record.getDeveloper()),
                String.valueOf(record.getMode()),
                String.valueOf(record.getGenre()),
                record.getRelease()==null ? "" : record.getRelease().toString()
        };
    }


}