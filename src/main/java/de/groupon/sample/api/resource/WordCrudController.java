package de.groupon.sample.api.resource;


import de.groupon.sample.exception.NotFoundException;
import de.groupon.sample.model.GermanWord;
import de.groupon.sample.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
@RequestMapping("/api/word")
public class WordCrudController {

    @Autowired
    private WordService wordService;


    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Collection<GermanWord> getAllWords() {
        return wordService.getAll();
    }

    @ResponseBody
    @RequestMapping(value = "/{wordId}", method = RequestMethod.GET)
    public GermanWord getOneSingleWord(@PathVariable("wordId") String wordId) throws NotFoundException {
        return wordService.getWord(wordId);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GermanWord> createWord(@RequestBody GermanWord data, HttpServletRequest request)  {
        wordService.save(data);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", request.getRequestURL().toString() + "/"+data.getName());
        return new ResponseEntity<GermanWord>(null, responseHeaders, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/client/{clientId}", method = RequestMethod.PUT)
    public void updateWord(@PathVariable("clientId") String clientId, @RequestBody GermanWord info){

    }

}
