package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.*;

import org.w3c.dom.Node;

public class GameXMLReader extends XMLMatchableReader<Game, Attribute> {

    @Override
    public Game createModelFromElement(Node node, String provenanceInfo) {
        // Create a new Game object with provenance information
        Game game = new Game(provenanceInfo);

        // Read data from XML and set attributes
        game.setId(getValueFromChildElement(node, "id"));
        game.setName(getValueFromChildElement(node, "Name"));
        
        // You can continue reading other attributes similarly
        game.setPlatform(getValuesFromChildElements(node, "Platform"));
        game.setGenre(getValuesFromChildElements(node, "Genre"));
        game.setMode(getValuesFromChildElements(node, "Mode"));
        game.setPublisher(getValuesFromChildElements(node, "Publisher"));
        game.setDeveloper(getValuesFromChildElements(node, "Developer"));

        game.setNA_Sales(Float.parseFloat(getValueFromChildElement(node, "NA_Sales")));
        game.setEU_Sales(Float.parseFloat(getValueFromChildElement(node, "EU_Sales")));
        game.setJP_Sales(Float.parseFloat(getValueFromChildElement(node, "JP_Sales")));
        game.setOther_Sales(Float.parseFloat(getValueFromChildElement(node, "Other_Sales")));
        game.setGlobal_Sales(Float.parseFloat(getValueFromChildElement(node, "Global_Sales")));

        game.setCritic_Score(Integer.parseInt(getValueFromChildElement(node, "Critic_Score")));
        game.setCritic_Count(Integer.parseInt(getValueFromChildElement(node, "Critic_Count")));
        game.setUser_Score(Integer.parseInt(getValueFromChildElement(node, "User_Score")));
        
        game.setRating(getValueFromChildElement(node, "Rating"));

        return game;
    }
}
