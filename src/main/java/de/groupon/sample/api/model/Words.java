package de.groupon.sample.api.model;

import de.groupon.sample.model.GermanWord;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@XmlRootElement(name = "words")
@XmlType(name = "GermanWordCollection")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Words {

    @XmlElement(name = "word")
    private List<GermanWord> words;

    public Words() {
        words = new ArrayList<GermanWord>();
    }

    public Words(Collection<GermanWord> all) {
        this.words = new ArrayList<GermanWord>(all);
    }

    public List<GermanWord> getWords() {
        return words;
    }

    public void setWords(List<GermanWord> words) {
        this.words = words;
    }
}
