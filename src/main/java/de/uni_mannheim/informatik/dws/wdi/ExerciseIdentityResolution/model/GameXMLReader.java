package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.*;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Map;

public class GameXMLReader extends XMLMatchableReader<Game, Attribute>  {

    @Override
    protected void initialiseDataset(DataSet<Game, Attribute> dataset) {
        super.initialiseDataset(dataset);
    }

    @Override
    public Game createModelFromElement(Node node, String provenanceInfo) {
        // Create a new Game object with provenance information
        String id =  getValueFromChildElement(node,"ID");
        Game game = new Game(id ,provenanceInfo);

        // Read data from XML and set attributes
        game.setId(getValueFromChildElement(node, "id"));
        game.setName(getValueFromChildElement(node, "Name"));


        // You can continue reading other attributes similarly
        String releaseDateStr = getValueFromChildElement(node, "Release");
        if (releaseDateStr != null && !releaseDateStr.isEmpty() && !releaseDateStr.equalsIgnoreCase("N/A")) {
            LocalDateTime dateTime;
            try {
                if (releaseDateStr.length() == 4) {
                    // If only the year is provided
                    dateTime = LocalDate.of(Integer.parseInt(releaseDateStr), 1, 1).atStartOfDay();
                } else {
                    // If full date is provided
                    LocalDate releaseDate = LocalDate.parse(releaseDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    dateTime = releaseDate.atStartOfDay();
                }
                game.setRelease(dateTime);
            } catch (DateTimeParseException e) {
                System.err.println("Failed to parse release date: " + releaseDateStr);
            }
        }

        // load the list of platforms
        List<Platforms> platforms = getObjectListFromChildElement(node, "Platforms",
                "Platform", new PlatformXMLReader(), provenanceInfo);
        game.setPlatforms(platforms);


        game.setGenre(getListFromChildElement(node, "Genre"));
        game.setMode(getListFromChildElement(node, "Mode"));
        game.setPublisher(getListFromChildElement(node, "Publisher"));
        game.setDeveloper(getListFromChildElement(node, "Developer"));

        game.setNA_Sales(getFloatValueFromChildElement(node, "NA_Sales"));
        game.setEU_Sales(getFloatValueFromChildElement(node, "EU_Sales"));
        game.setJP_Sales(getFloatValueFromChildElement(node, "JP_Sales"));
        game.setOther_Sales(getFloatValueFromChildElement(node, "Other_Sales"));
        game.setGlobal_Sales(getFloatValueFromChildElement(node, "Global_Sales"));
        game.setCritic_Score(getIntValueFromChildElement(node, "Critic_Score"));
        game.setCritic_Count(getIntValueFromChildElement(node, "Critic_Count"));

        String userScoreValue = getValueFromChildElement(node, "User_Score");
        if (userScoreValue != null && !userScoreValue.equalsIgnoreCase("tbd")) {
            game.setUser_Score(Float.parseFloat(userScoreValue));
        } else {
            game.setUser_Score(null);  // or set a default value if needed
        }
        game.setRating(getValueFromChildElement(node, "Rating"));

        return game;
    }




    private Float getFloatValueFromChildElement(Node node, String childName) {
        String valueStr = getValueFromChildElement(node, childName);
        if(valueStr != null && !valueStr.isEmpty()) {
            return Float.parseFloat(valueStr);
        } else {
            return null; // or some default value like 0.0f
        }
    }

    private Integer getIntValueFromChildElement(Node node, String childName) {
        String valueStr = getValueFromChildElement(node, childName);
        if(valueStr != null && !valueStr.isEmpty()) {
            return Integer.parseInt(valueStr);
        } else {
            return null; // or some default value like 0
        }
    }


}
