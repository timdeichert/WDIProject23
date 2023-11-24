package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;


public class Game extends AbstractRecord<Attribute> implements Serializable {

    private String Name;
    private LocalDateTime Release;

    private String Platform;
    private List<String> Genre;
    private List<String> Mode;
    private List<String> Publisher;
    private List<String> Developer;
    private Float NA_Sales;
    private Float EU_Sales;
    private Float JP_Sales;
    private Float Other_Sales;
    private Double Global_Sales;
    private Integer Critic_Score;
    private Integer Critic_Count;
    private Float User_Score;
    private String Rating;

    private static final long serialVersionUID = 1L;

    public Game(String identifier,String provenance) {
        super(identifier, provenance);
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
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPlatform() {
        return Platform;
    }

    public void setPlatform(String platform) {
        this.Platform = platform;
    }

    public List<String> getGenre() {
        return Genre;
    }

    public void setGenre(List<String> genre) {
        this.Genre = genre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getMode() {
        return Mode;
    }

    public void setMode(List<String> mode) {
        this.Mode = mode;
    }

    public List<String> getPublisher() {
        return Publisher;
    }

    public void setPublisher(List<String> publisher) {
        this.Publisher = publisher;
    }

    public List<String> getDeveloper() {
        return Developer;
    }

    public void setDeveloper(List<String> developer) {
        this.Developer = developer;
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
        this.Other_Sales = other_Sales;
    }

    public Double getGlobal_Sales() {
        return Global_Sales;
    }

    public void setGlobal_Sales(Double global_Sales) {
        this.Global_Sales = global_Sales;
    }

    public Integer getCritic_Score() {
        return Critic_Score;
    }

    public void setCritic_Score(Integer critic_Score) {
        this.Critic_Score = critic_Score;
    }

    public Integer getCritic_Count() {
        return Critic_Count;
    }

    public void setCritic_Count(Integer critic_Count) {
        this.Critic_Count = critic_Count;
    }

    public Float getUser_Score() {
        return User_Score;
    }

    public void setUser_Score(Float user_Score) {
        this.User_Score = user_Score;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        this.Rating = rating;
    }



    private Map<Attribute, Collection<String>> provenance = new HashMap<>();
    private Collection<String> recordProvenance;

    public void setRecordProvenance(Collection<String> provenance) {
        recordProvenance = provenance;
    }

    public Collection<String> getRecordProvenance() {
        return recordProvenance;
    }

    public void setAttributeProvenance(Attribute attribute,
                                       Collection<String> provenance) {
        this.provenance.put(attribute, provenance);
    }

    public Collection<String> getAttributeProvenance(String attribute) {
        return provenance.get(attribute);
    }

    public String getMergedAttributeProvenance(Attribute attribute) {
        Collection<String> prov = provenance.get(attribute);

        if (prov != null) {
            return StringUtils.join(prov, "+");
        } else {
            return "";
        }
    }





    @Override
    public String toString() {
        return String.format("[Game %s: %s / %s / %s]", getIdentifier(), getName(),
                getDeveloper(), getRelease().toString());
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Game){
            return this.getIdentifier().equals(((Game) obj).getIdentifier());
        }else
            return false;
    }


    public static final Attribute NAME = new Attribute("Name");
    public static final Attribute DEVELOPERS = new Attribute("Developers");
    public static final Attribute RELEASE = new Attribute("Release");
    public static final Attribute GENRES = new Attribute("Genres");
    public static final Attribute GLOBALSALES = new Attribute("Global_Sales");

    @Override
    public boolean hasValue(Attribute attribute) {
        if(attribute==NAME)
            return getName() != null && !getName().isEmpty();
        else if(attribute==DEVELOPERS)
            return getDeveloper() != null && !getDeveloper().isEmpty();
        else if(attribute==RELEASE)
            return getRelease() != null;
        else if(attribute==GENRES)
            return getGenre() != null && getGenre().size() > 0;
        else if(attribute==GLOBALSALES)
            return getGlobal_Sales() != null && !getGlobal_Sales().isNaN();
        else
            return false;
    }

    @Override
    public int hashCode() {
        return getIdentifier().hashCode();
    }




}
