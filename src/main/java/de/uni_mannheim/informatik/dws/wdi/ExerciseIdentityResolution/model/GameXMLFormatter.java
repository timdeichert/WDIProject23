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
        game.appendChild(createTextElementWithProvenance("platform",
                record.getPlatform(),
                record.getMergedAttributeProvenance(Game.PLATFORM), doc));
        game.appendChild(createTextElementWithProvenance("date", record
                .getRelease().toString(), record
                .getMergedAttributeProvenance(Game.RELEASE), doc));
        game.appendChild(createTextElementWithProvenance("genres",
                record.getGenre() != null ? record.getGenre().toString() : "",
                record.getMergedAttributeProvenance(Game.GENRES), doc));
        game.appendChild(createTextElementWithProvenance("global_sales", record
                .getGlobal_Sales().toString(), record
                .getMergedAttributeProvenance(Game.GLOBALSALES), doc));
        game.appendChild(createTextElementWithProvenance("eu_sales", record
                .getEU_Sales().toString(), record
                .getMergedAttributeProvenance(Game.EUSALES), doc));
        game.appendChild(createTextElementWithProvenance("other_sales", record
                .getOther_Sales().toString(), record
                .getMergedAttributeProvenance(Game.OTHERSALES), doc));
        game.appendChild(createTextElementWithProvenance("jp_sales", record
                .getJP_Sales().toString(), record
                .getMergedAttributeProvenance(Game.JPSALES), doc));
        game.appendChild(createTextElementWithProvenance("na_sales", record
                .getNA_Sales().toString(), record
                .getMergedAttributeProvenance(Game.NASALES), doc));
        game.appendChild(createDevelopersElement(record, doc));
        game.appendChild(createModesElement(record, doc));
        game.appendChild(createGenresElement(record, doc));
        game.appendChild(createPublishersElement(record, doc));

        return game;
    }

    protected Element createTextElementWithProvenance(String name,
                                                      String value, String provenance, Document doc) {
        Element elem = createTextElement(name, value, doc);
        elem.setAttribute("provenance", provenance);
        return elem;
    }

    protected Element createDevelopersElement(Game record, Document doc) {
        Element developerRoot = doc.createElement("developers");
        developerRoot.setAttribute("provenance",
                record.getMergedAttributeProvenance(Game.DEVELOPERS));
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


    protected Element createPublishersElement(Game record, Document doc) {
        Element publisherRoot = doc.createElement("publishers");
        publisherRoot.setAttribute("provenance",
                record.getMergedAttributeProvenance(Game.PUBLISHERS));
        if (record.getPublisher() != null) {
            for (String publisher : record.getPublisher()) {
                if (publisher != null && !publisher.isEmpty()) {
                    Element publisherElement = createTextElement("publisher", publisher, doc);
                    publisherRoot.appendChild(publisherElement);
                }
            }
        }
        return publisherRoot;
    }

    protected Element createGenresElement(Game record, Document doc) {
        Element genreRoot = doc.createElement("genres");
        genreRoot.setAttribute("provenance",
                record.getMergedAttributeProvenance(Game.GENRES));
        if (record.getGenre() != null) {
            for (String genre : record.getGenre()) {
                if (genre != null && !genre.isEmpty()) {
                    Element genreElement = createTextElement("genre", genre, doc);
                    genreRoot.appendChild(genreElement);
                }
            }
        }
        return genreRoot;
    }

    protected Element createModesElement(Game record, Document doc) {
        Element modeRoot = doc.createElement("modes");
        modeRoot.setAttribute("provenance",
                record.getMergedAttributeProvenance(Game.MODE));
        if (record.getMode() != null) {
            for (String mode : record.getMode()) {
                if (mode != null && !mode.isEmpty()) {
                    Element modeElement = createTextElement("mode", mode, doc);
                    modeRoot.appendChild(modeElement);
                }
            }
        }
        return modeRoot;
    }
}