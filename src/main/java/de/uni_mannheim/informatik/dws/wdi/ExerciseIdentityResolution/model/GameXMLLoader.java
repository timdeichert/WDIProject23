package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;

import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GameXMLLoader {

    public static void main(String[] args) {
        // Create a HashedDataSet to store Game objects
        HashedDataSet<Game, Attribute> ds = new HashedDataSet<>();

        // Map the XML element names to attribute names in the data set
        Map<String, Attribute> nodeMapping = new HashMap<>();
        nodeMapping.put("Name", new Attribute("Name"));
        nodeMapping.put("Platform", new Attribute("Platform"));
        nodeMapping.put("Genre", new Attribute("Genre"));
        nodeMapping.put("Mode", new Attribute("Mode"));
        nodeMapping.put("Publisher", new Attribute("Publisher"));
        nodeMapping.put("Developer", new Attribute("Developer"));
        nodeMapping.put("NA_Sales", new Attribute("NA_Sales"));
        nodeMapping.put("EU_Sales", new Attribute("EU_Sales"));
        nodeMapping.put("JP_Sales", new Attribute("JP_Sales"));
        nodeMapping.put("Other_Sales", new Attribute("Other_Sales"));
        nodeMapping.put("Global_Sales", new Attribute("Global_Sales"));
        nodeMapping.put("Critic_Score", new Attribute("Critic_Score"));
        nodeMapping.put("Critic_Count", new Attribute("Critic_Count"));
        nodeMapping.put("User_Score", new Attribute("User_Score"));
        nodeMapping.put("Rating", new Attribute("Rating"));

        // Adjust the path to the XML file as necessary with the below lines

        //File sourceFile = new File("your-xml-file.xml");
        //String elementPath = "/games/game"; // Adjust the element path as per your XML structure

        // Load data from the XML file into the dataset
        //new XMLRecordReader("id", nodeMapping).loadFromXML(sourceFile, elementPath, ds);

        //'ds' contains the Game objects with data from the XML file
    }
}
