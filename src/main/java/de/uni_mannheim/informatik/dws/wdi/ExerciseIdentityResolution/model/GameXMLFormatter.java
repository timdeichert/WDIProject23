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

        game.appendChild(createDeveloperElement(record, doc));

        return game;
    }

    protected Element createTextElementWithProvenance(String name,
                                                      String value, String provenance, Document doc) {
        Element elem = createTextElement(name, value, doc);
        elem.setAttribute("provenance", provenance);
        return elem;
    }

/*
    protected Element createActorsElement(Movie record, Document doc) {
        Element actorRoot = actorFormatter.createRootElement(doc);
        actorRoot.setAttribute("provenance",
                record.getMergedAttributeProvenance(Movie.ACTORS));
        for (Actor a : record.getActors()) {
            actorRoot.appendChild(actorFormatter
                    .createElementFromRecord(a, doc));
        }
        return actorRoot;
    }
    public class ActorXMLFormatter extends XMLFormatter<Actor> {
        @Override
        public Element createRootElement(Document doc) {
            return doc.createElement("actors");
        }
        @Override
        public Element createElementFromRecord(Actor record, Document doc) {
            Element actor = doc.createElement("actor");

            actor.appendChild(createTextElement("name", record.getName(), doc));
            if(record.getBirthplace()!=null) {
                actor.appendChild(createTextElement("birthplace", record.getBirthplace(), doc));
            }
            if(record.getBirthday()!=null) {
                actor.appendChild(createTextElement("birthday", record.getBirthday().toString(), doc));
            }
            return actor;
        }
 */

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
}