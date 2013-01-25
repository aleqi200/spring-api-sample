package de.groupon.sample.model;

import de.groupon.sample.model.enumerations.Gender;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "word")
@XmlType(name = "GermanWord")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class GermanWord implements Serializable{

    private String name;
    private Gender gender;

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
}
