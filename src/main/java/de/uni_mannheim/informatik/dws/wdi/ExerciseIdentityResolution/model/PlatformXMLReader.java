package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;


public class PlatformXMLReader extends XMLMatchableReader<Platforms, Attribute> {

    @Override
    public Platforms createModelFromElement(Node node, String provenanceInfo) {
        String id = getValueFromChildElement(node, "ID");

        // create the object with id and provenance information
        Platforms platforms = new Platforms(id, provenanceInfo);

        // fill the attributes
        platforms.setName(getValueFromChildElement(node, "Platform"));
        return platforms;
    }
}