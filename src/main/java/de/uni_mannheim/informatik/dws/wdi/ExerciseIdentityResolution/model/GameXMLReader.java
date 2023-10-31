package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.*;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import org.w3c.dom.Node;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
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


        // You can continue reading other attributes similarly
        game.setName(getValueFromChildElement(node, "Name"));
        game.setPlatform(getListFromChildElement(node, "Platform"));
        game.setGenre(getListFromChildElement(node, "Genre"));
        game.setMode(getListFromChildElement(node, "Mode"));
        game.setPublisher(getListFromChildElement(node, "Publisher"));

        try {
            String date = getValueFromChildElement(node, "Release");
            if ("N/A".equals(date)){
                LocalDateTime dt;
                dt = LocalDateTime.of(2023, 1, 1, 0, 0);
            }
            else if (date != null && !date.isEmpty()) {
                LocalDateTime dt;
                if (date.contains("-")) {
                    // If the date is in the format "YYYY-MM-DD"
                    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                            .appendPattern("yyyy-MM-dd")
                            .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
                            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                            .toFormatter(Locale.ENGLISH);
                    dt = LocalDateTime.parse(date, formatter);
                } else {
                    // If the date is just the year (YYYY)
                    dt = LocalDateTime.of(Integer.parseInt(date), 1, 1, 0, 0);
                }
                game.setRelease(dt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        game.setRelease(getValueFromChildElement(node, "Release"));
        game.setDeveloper(getListFromChildElement(node, "Developer"));
        if (getValueFromChildElement(node, "NA_Sales") != null) {
            game.setNA_Sales(Float.parseFloat(getValueFromChildElement(node, "NA_Sales")));
        }
        if (getValueFromChildElement(node, "EU_Sales") != null) {
            game.setEU_Sales(Float.parseFloat(getValueFromChildElement(node, "EU_Sales")));}
        else{
            game.setEU_Sales(1.0F);
        }
        if (getValueFromChildElement(node, "JP_Sales") != null) {
            game.setJP_Sales(Float.parseFloat(getValueFromChildElement(node, "JP_Sales")));}
        else{
            game.setJP_Sales(1.0F);
        }
        if (getValueFromChildElement(node, "Other_Sales") != null) {
            game.setOther_Sales(Float.parseFloat(getValueFromChildElement(node, "Other_Sales")));}
        else{
            game.setOther_Sales(1.0F);
        }
        if (getValueFromChildElement(node, "Global_Sales") != null) {
            game.setGlobal_Sales(Float.parseFloat(getValueFromChildElement(node, "Global_Sales")));}
        else{
            game.setGlobal_Sales(1.0F);
        }
        if (getValueFromChildElement(node, "Critic_Score") != null) {
            game.setCritic_Score(Integer.parseInt(getValueFromChildElement(node, "Critic_Score")));}
        else{
            game.setCritic_Score(1);
        }
        if (getValueFromChildElement(node, "Critic_Count") != null) {
            game.setCritic_Count(Integer.parseInt(getValueFromChildElement(node, "Critic_Count")));}
        else{
            game.setCritic_Count(1);
        }
        if (getValueFromChildElement(node, "User_Score") != null && !getValueFromChildElement(node, "User_Score").equalsIgnoreCase("tbd")) {
            game.setUser_Score(Float.parseFloat(getValueFromChildElement(node, "User_Score")));}
        else{
            game.setUser_Score(1.0F);
        }

        game.setRating(getValueFromChildElement(node, "Rating"));

        return game;
    }
}