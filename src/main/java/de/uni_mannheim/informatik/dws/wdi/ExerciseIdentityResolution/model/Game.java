package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.time.LocalDateTime;
import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;

public class Game implements Matchable {

    protected String id;
    protected String provenance;

    private String Name;
    private LocalDateTime Release;

    private List<String> Platform;
    private List<String> Genre;
    private List<String> Mode;
    private List<String> Publisher;
    private List<String> Developer;
    private Float NA_Sales;
    private Float EU_Sales;
    private Float JP_Sales;
    private Float Other_Sales;
    private Float Global_Sales;
    private Integer Critic_Score;
    private Integer Critic_Count;
    private Float User_Score;
    private String Rating;

    public Game(String identifier,String provenance) {
        id = identifier;
        this.provenance = provenance;;
    }

    public LocalDateTime getRelease() {
        if (Release == null)
        {
            return LocalDateTime.of(2023, 1, 1, 0, 0);
        }
        return Release;
    }

    public void setRelease(LocalDateTime release) {
        this.Release = release;
    }
    public String getName() {
        if (Name == null)
        {
            return "TESTER";
        }
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<String> getPlatform() {
        return Platform;
    }

    public void setPlatform(List<String> platform) {
        Platform = platform;
    }

    public List<String> getGenre() {
        return Genre;
    }

    public void setGenre(List<String> genre) {
        Genre = genre;
    }



    public void setId(String id) {
        this.id = id;
    }

    public List<String> getMode() {
        return Mode;
    }

    public void setMode(List<String> mode) {
        Mode = mode;
    }

    public List<String> getPublisher() {
        return Publisher;
    }

    public void setPublisher(List<String> publisher) {
        Publisher = publisher;
    }

    public List<String> getDeveloper() {
        return Developer;
    }

    public void setDeveloper(List<String> developer) {
        Developer = developer;
    }

    public Float getNA_Sales() {
        return NA_Sales;
    }

    public void setNA_Sales(Float NA_Sales) {
        this.NA_Sales = NA_Sales;
    }

    public Float getEU_Sales() {
        return EU_Sales;
    }

    public void setEU_Sales(Float EU_Sales) {
        this.EU_Sales = EU_Sales;
    }

    public Float getJP_Sales() {
        return JP_Sales;
    }

    public void setJP_Sales(Float JP_Sales) {
        this.JP_Sales = JP_Sales;
    }

    public Float getOther_Sales() {
        return Other_Sales;
    }

    public void setOther_Sales(Float other_Sales) {
        Other_Sales = other_Sales;
    }

    public Float getGlobal_Sales() {
        return Global_Sales;
    }

    public void setGlobal_Sales(Float global_Sales) {
        Global_Sales = global_Sales;
    }

    public Integer getCritic_Score() {
        return Critic_Score;
    }

    public void setCritic_Score(Integer critic_Score) {
        Critic_Score = critic_Score;
    }

    public Integer getCritic_Count() {
        return Critic_Count;
    }

    public void setCritic_Count(Integer critic_Count) {
        Critic_Count = critic_Count;
    }

    public Float getUser_Score() {
        return User_Score;
    }

    public void setUser_Score(Float user_Score) {
        User_Score = user_Score;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    @Override
    public String getIdentifier() {
        return id;
    }

    @Override
    public String getProvenance() {
        return null;
    }
}
