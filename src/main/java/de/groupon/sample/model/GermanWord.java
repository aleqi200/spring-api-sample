package de.groupon.sample.model;

import de.groupon.sample.model.enumerations.Gender;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "word")
@XmlType(name = "GermanWord")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class GermanWord implements Serializable{

    private static final long serialVersionUID = 4066181595837621664L;

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    private Gender gender;

    @URL
    private String imageUrl;

    public GermanWord(String name) {
        this.name = name;
    }

    public GermanWord() {
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GermanWord that = (GermanWord) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
