package de.groupon.sample.api.resource;


import de.groupon.sample.exception.AlreadyExistsException;
import de.groupon.sample.exception.NotFoundException;
import de.groupon.sample.model.GermanWord;
import de.groupon.sample.model.enumerations.Gender;
import de.groupon.sample.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public Collection<GermanWord> getAllWords() {
        return wordService.getAll();
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
    public ResponseEntity<GermanWord> createWord(@RequestBody GermanWord data, HttpServletRequest request)  {
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

        final GermanWord word = new GermanWord();
        word.setGender(gender);
        word.setName(name);
        try{
            if(wordService.getWord(name) != null){
                throw new AlreadyExistsException("Conflict");
            }
        }catch (NotFoundException ex){
            //ok
        }

        wordService.save(word);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", request.getRequestURL().toString() + "/"+word.getName());
        return new ResponseEntity<GermanWord>(null, responseHeaders, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/client/{clientId}", method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public GermanWord updateWord(@PathVariable("clientId") String clientId, @RequestBody GermanWord info) throws NotFoundException {
        wordService.getWord(info.getName());
        wordService.save(info);
        return info;
    }

}
