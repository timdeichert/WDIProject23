package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.*;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import org.w3c.dom.Node;

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
        game.setPlatform(getListFromChildElement(node, "Platform"));
        game.setGenre(getListFromChildElement(node, "Genre"));
        game.setMode(getListFromChildElement(node, "Mode"));
        game.setPublisher(getListFromChildElement(node, "Publisher"));
        game.setDeveloper(getListFromChildElement(node, "Developer"));
        if (getValueFromChildElement(node, "NA_Sales") != null) {
            game.setNA_Sales(Float.parseFloat(getValueFromChildElement(node, "NA_Sales")));
        }
        if (getValueFromChildElement(node, "NA_Sales") != null) {
        game.setEU_Sales(Float.parseFloat(getValueFromChildElement(node, "EU_Sales")));}
        if (getValueFromChildElement(node, "NA_Sales") != null) {
        game.setJP_Sales(Float.parseFloat(getValueFromChildElement(node, "JP_Sales")));}
        if (getValueFromChildElement(node, "NA_Sales") != null) {
        game.setOther_Sales(Float.parseFloat(getValueFromChildElement(node, "Other_Sales")));}
        if (getValueFromChildElement(node, "NA_Sales") != null) {
        game.setGlobal_Sales(Float.parseFloat(getValueFromChildElement(node, "Global_Sales")));}
        if (getValueFromChildElement(node, "NA_Sales") != null) {
        game.setCritic_Score(Integer.parseInt(getValueFromChildElement(node, "Critic_Score")));}
        if (getValueFromChildElement(node, "NA_Sales") != null) {
        game.setCritic_Count(Integer.parseInt(getValueFromChildElement(node, "Critic_Count")));}
        if (getValueFromChildElement(node, "NA_Sales") != null) {
        game.setUser_Score(Integer.parseInt(getValueFromChildElement(node, "User_Score")));}
        
        game.setRating(getValueFromChildElement(node, "Rating"));

        return game;
    }
}
