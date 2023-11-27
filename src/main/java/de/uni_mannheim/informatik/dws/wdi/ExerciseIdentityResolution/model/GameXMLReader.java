package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.*;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

import java.util.Map;

public class GameXMLReader extends XMLMatchableReader<Game, Attribute>implements
        FusibleFactory<Game, Attribute> {

    @Override
    protected void initialiseDataset(DataSet<Game, Attribute> dataset) {
        super.initialiseDataset(dataset);
        dataset.addAttribute(Game.NAME);
        dataset.addAttribute(Game.DEVELOPERS);
        dataset.addAttribute(Game.RELEASE);
        dataset.addAttribute(Game.GENRES);
        dataset.addAttribute(Game.OTHERSALES);
        dataset.addAttribute(Game.EUSALES);
        dataset.addAttribute(Game.GLOBALSALES);
        dataset.addAttribute(Game.PUBLISHERS);
        dataset.addAttribute(Game.MODE);
        dataset.addAttribute(Game.PLATFORM);
        dataset.addAttribute(Game.JPSALES);
        dataset.addAttribute(Game.NASALES);
    }

        @Override
        public Game createModelFromElement(Node node, String provenanceInfo) {
            String id =  getValueFromChildElement(node,"ID");
            Game game = new Game(id ,provenanceInfo);

            game.setName(getValueFromChildElement(node, "Name"));
            List<String> genres = getListFromChildElement(node, "Genres");
            game.setGenre(genres);
            List<String> developers = getListFromChildElement(node, "Developers");
            game.setDeveloper(developers);
            List<String> modes = getListFromChildElement(node, "modes");
            game.setMode(modes);
            List<String> publishers = getListFromChildElement(node, "publishers");
            game.setPublisher(publishers);

            try {
                String date = getValueFromChildElement(node, "Release");
                if ("N/A".equals(date)){
                    LocalDateTime dt;
                    dt = LocalDateTime.of(2023, 1, 1, 0, 0);
                }
                else if (date != null && !date.isEmpty()) {
                    LocalDateTime dt;
                    if (date.contains("-")) {
                        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                                .appendPattern("yyyy-MM-dd")
                                .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
                                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                                .toFormatter(Locale.ENGLISH);
                        dt = LocalDateTime.parse(date, formatter);
                    } else {
                        dt = LocalDateTime.of(Integer.parseInt(date), 1, 1, 0, 0);
                    }
                    game.setRelease(dt);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (getValueFromChildElement(node, "Platform") != null) {
                String platform = getValueFromChildElement(node, "Platform");
//                game.setPlatform(platform);

              if(platform.equals("PS")) {
                    game.setPlatform("PlayStation (console)");
                }
                else if(platform.equals("PS2")) {
                    game.setPlatform("PlayStation 2");
                }
                else if(platform.equals("PS3")) {
                    game.setPlatform("PlayStation 3");
                }
                else if(platform.equals("PS4")) {
                    game.setPlatform("PlayStation 4");
                }
              else if(platform.equals("PS5")) {
                  game.setPlatform("PlayStation 5");
              }
                else if(platform.equals("PSP")){
                    game.setPlatform("PlayStation Portable");
              }
                else if(platform.equals("PSV")){
                    game.setPlatform("PlayStation Vita");
              }
                else if(platform.equals("GBA")){
                    game.setPlatform("Game Boy Advance");
              }
                else if (platform.equals("GB")){
                    game.setPlatform("Game Boy");
              }
                else if(platform.equals("XONE")) {
                    game.setPlatform("Xbox One");
                }
                else if(platform.equals("X360")) {
                    game.setPlatform("Xbox 360");
                }
                else if (platform.equals("XB")){
                    game.setPlatform("Xbox (console)");
              }
                else if(platform.equals("SNES")){
                    game.setPlatform("Super Nintendo Entertainment System");
              }
              else if(platform.equals("NES")){
                  game.setPlatform("Nintendo Entertainment System");
              }
                else if(platform.equals("DS")){
                    game.setPlatform("Nintendo DS");
              }
                else if(platform.equals("3DS")){
                    game.setPlatform("Nintendo 3DS");
              }
                else if (platform.equals("N64")){
                    game.setPlatform("Nintendo 64");
              }
                else if (platform.equals("GC")){
                    game.setPlatform("GameCube");
              }
                else if (platform.equals("WiiU")){
                    game.setPlatform("Wii U");
              }
                else if(platform.equals("SAT")){
                    game.setPlatform("Sega Saturn");
              }
                else {
                    game.setPlatform(platform);
                }
            }

            if (getValueFromChildElement(node, "NA_Sales") != null) {
                game.setNA_Sales(Double.parseDouble(getValueFromChildElement(node, "NA_Sales")));
            }else{
                game.setNA_Sales((double) 0);
            }
            if (getValueFromChildElement(node, "EU_Sales") != null) {
                game.setEU_Sales(Double.parseDouble(getValueFromChildElement(node, "EU_Sales")));}
            else{
                game.setEU_Sales((double) 0);
            }
            if (getValueFromChildElement(node, "JP_Sales") != null) {
                game.setJP_Sales(Double.parseDouble(getValueFromChildElement(node, "JP_Sales")));}
            else{
                game.setJP_Sales((double) 0);
            }
            if (getValueFromChildElement(node, "Other_Sales") != null) {
                game.setOther_Sales(Double.parseDouble(getValueFromChildElement(node, "Other_Sales")));}
            else{
                game.setOther_Sales((double) 0);
            }
            if (getValueFromChildElement(node, "Global_Sales") != null) {
                game.setGlobal_Sales(Double.parseDouble(getValueFromChildElement(node, "Global_Sales")));}
            else{
                game.setGlobal_Sales((double) 0);
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
                game.setUser_Score(Double.parseDouble(getValueFromChildElement(node, "User_Score")));}
            else{
                game.setUser_Score((double) 1);
            }

            game.setRating(getValueFromChildElement(node, "Rating"));

            return game;
        }

    @Override
    public Game createInstanceForFusion(RecordGroup<Game, Attribute> cluster) {
        List<String> ids = new LinkedList<>();

        for (Game m : cluster.getRecords()) {
            ids.add(m.getIdentifier());
        }
        Collections.sort(ids);
        String mergedId = StringUtils.join(ids, '+');
        return new Game(mergedId, "fused");
    }
}
