package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;


public class GameXMLFormatter extends XMLFormatter<Game>{
    @Override
    public Element createRootElement(Document doc) {
        return doc.createElement("games");
    }

    @Override
    public Element createElementFromRecord(Game record, Document doc) {
        Element movie = doc.createElement("game");

        movie.appendChild(createTextElement("id", record.getIdentifier(), doc));

        movie.appendChild(createTextElementWithProvenance("name",
                record.getName(),
                record.getMergedAttributeProvenance(Game.NAME), doc));
        movie.appendChild(createTextElementWithProvenance("developers",
                record.getDeveloper().toString(),
                record.getMergedAttributeProvenance(Game.DEVELOPERS), doc));
        movie.appendChild(createTextElementWithProvenance("date", record
                .getRelease().toString(), record
                .getMergedAttributeProvenance(Game.RELEASE), doc));
        movie.appendChild(createTextElementWithProvenance("genres", record
                .getGenre().toString(), record
                .getMergedAttributeProvenance(Game.GENRES), doc));
        return movie;
    }

    protected Element createTextElementWithProvenance(String name,
                                                      String value, String provenance, Document doc) {
        Element elem = createTextElement(name, value, doc);
        elem.setAttribute("provenance", provenance);
        return elem;
    }


}
