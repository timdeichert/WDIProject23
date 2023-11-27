package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

import java.util.List;


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
        game.appendChild(createTextElementWithProvenance("date", record
                .getRelease().toString(), record
                .getMergedAttributeProvenance(Game.RELEASE), doc));
        game.appendChild(createTextElementWithProvenance("genres",
                record.getGenre() != null ? record.getGenre().toString() : "",
                record.getMergedAttributeProvenance(Game.GENRES), doc));

        game.appendChild(createTextElementWithProvenance("global_sales", record
                .getGlobal_Sales().toString(), record
                .getMergedAttributeProvenance(Game.GLOBALSALES), doc));

        game.appendChild(createDeveloperElement(record, doc));
        game.appendChild(createModesElement(record, doc));

        return game;
    }

    protected Element createTextElementWithProvenance(String name,
                                                      String value, String provenance, Document doc) {
        Element elem = createTextElement(name, value, doc);
        elem.setAttribute("provenance", provenance);
        return elem;
    }

    protected Element createDeveloperElement(Game record, Document doc) {
        Element developerRoot = doc.createElement("developers");
        developerRoot.setAttribute("provenance",
                record.getMergedAttributeProvenance(Game.DEVELOPERS));

        // Check if the developer list is not null before iterating
        if (record.getDeveloper() != null) {
            for (String developer : record.getDeveloper()) {
                if (developer != null && !developer.isEmpty()) {
                    Element developerElement = createTextElement("developer", developer, doc);
                    developerRoot.appendChild(developerElement);
                }
            }
        }
        return developerRoot;
    }

    protected Element createModesElement(Game record, Document doc) {
        Element developerRoot = doc.createElement("Modes");
        developerRoot.setAttribute("provenance",
                record.getMergedAttributeProvenance(Game.MODE));

        // Check if the developer list is not null before iterating
        if (record.getMode() != null) {
            for (String mode : record.getMode()) {
                if (mode != null && !mode.isEmpty()) {
                    Element developerElement = createTextElement("Mode", mode, doc);
                    developerRoot.appendChild(developerElement);
                }
            }
        }
        return developerRoot;
    }
}