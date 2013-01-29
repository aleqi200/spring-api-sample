package de.groupon.sample.api.resource;


import de.groupon.sample.api.model.Words;
import de.groupon.sample.exception.AlreadyExistsException;
import de.groupon.sample.exception.InvalidParamException;
import de.groupon.sample.exception.NotFoundException;
import de.groupon.sample.model.GermanWord;
import de.groupon.sample.model.enumerations.Gender;
import de.groupon.sample.service.WordService;
import org.hibernate.validator.internal.constraintvalidators.URLValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/api/word")
public class WordResource {

    @Autowired
    private WordService wordService;


    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public Words getAllWords() {
        return new Words(wordService.getAll());
    }

    @ResponseBody
    @RequestMapping(value = "/{wordId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public GermanWord getOneSingleWord(@PathVariable("wordId") String wordId) throws NotFoundException {
        return wordService.getWord(wordId);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<GermanWord> createWord(@Valid @RequestBody(required = true) GermanWord data, HttpServletRequest request) throws InvalidParamException {
        wordService.save(data);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", request.getRequestURL().toString() + "/"+data.getName());
        return new ResponseEntity<GermanWord>(null, responseHeaders, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<GermanWord> createWordForm(@RequestParam("name") String name,
                                                     @RequestParam("gender") Gender gender,
                                                     HttpServletRequest request) throws AlreadyExistsException {

        try{
            if(wordService.getWord(name) != null){
                throw new AlreadyExistsException("Conflict");
            }
        }catch (NotFoundException ex){
            //ok
        }

        final GermanWord word = new GermanWord(name);
        word.setGender(gender);

        wordService.save(word);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", request.getRequestURL().toString() + "/"+word.getName());
        return new ResponseEntity<GermanWord>(null, responseHeaders, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/{wordId}", method = {RequestMethod.PUT},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public GermanWord updateWord(@PathVariable("wordId")  String wordId, @RequestBody(required = true) @Valid GermanWord requestWord)
            throws NotFoundException, InvalidParamException {
        final GermanWord dbWord = wordService.getWord(wordId);

        if(!dbWord.getName().equals(requestWord.getName())){
            throw new InvalidParamException("Operation not allowed. You can't change a word name");
        }
        wordService.save(requestWord);
        return requestWord;
    }

    @ResponseBody
    @RequestMapping(value = "/{wordId}", method = {RequestMethod.PATCH},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public GermanWord updateWordSingleValues(@PathVariable("wordId")  String wordId, @RequestBody(required = true) GermanWord requestWord)
            throws NotFoundException, InvalidParamException {
        final GermanWord dbWord = wordService.getWord(wordId);
        if(!dbWord.getGender().equals(requestWord.getGender()) && requestWord.getGender() != null){
            dbWord.setGender(requestWord.getGender());
        }

        if(dbWord.getImageUrl() == null
                || dbWord.getImageUrl().equals(requestWord.getImageUrl())
                && StringUtils.hasText(requestWord.getImageUrl())){
           dbWord.setImageUrl(requestWord.getImageUrl());
        }
        wordService.save(dbWord);
        return wordService.getWord(wordId);
    }


    @ResponseBody
    @RequestMapping(value = "/{wordId}", method = {RequestMethod.DELETE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public GermanWord delete(@PathVariable("wordId") String wordId) throws NotFoundException {
        wordService.delete(wordService.getWord(wordId));
        return null;
    }

}
