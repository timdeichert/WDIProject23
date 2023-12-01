package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;

import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.XMLRecordReader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameXMLLoader {

    public static void main(String[] args) {
        HashedDataSet<Game, Attribute> ds = new HashedDataSet<>();

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


        File sourceFile = new File("data/input/DBpedia_Video_Game(Final).XML");
        String elementPath = "/Games/Game";

        try {
            new GameXMLReader().loadFromXML(sourceFile,elementPath,ds);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }
}
