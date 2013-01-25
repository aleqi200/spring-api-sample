package de.groupon.sample.service;

import de.groupon.sample.exception.NotFoundException;
import de.groupon.sample.model.GermanWord;

import java.util.Collection;

public interface WordService {
    Collection<GermanWord> getAll();

    void delete(GermanWord value);

    GermanWord getWord(String value) throws NotFoundException;

    void save(GermanWord word);
}
