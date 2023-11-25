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
        Element game = doc.createElement("game");

        game.appendChild(createTextElement("id", record.getIdentifier(), doc));

        game.appendChild(createTextElementWithProvenance("name",
                record.getName(),
                record.getMergedAttributeProvenance(Game.NAME), doc));
        game.appendChild(createTextElementWithProvenance("developers",
                record.getDeveloper() != null ? record.getDeveloper().toString() : "",
                record.getMergedAttributeProvenance(Game.DEVELOPERS), doc));
        game.appendChild(createTextElementWithProvenance("date", record
                .getRelease().toString(), record
                .getMergedAttributeProvenance(Game.RELEASE), doc));
        game.appendChild(createTextElementWithProvenance("genres",
                record.getGenre() != null ? record.getGenre().toString() : "",
                record.getMergedAttributeProvenance(Game.GENRES), doc));
        return game;
    }

    protected Element createTextElementWithProvenance(String name,
                                                      String value, String provenance, Document doc) {
        Element elem = createTextElement(name, value, doc);
        elem.setAttribute("provenance", provenance);
        return elem;
    }


}