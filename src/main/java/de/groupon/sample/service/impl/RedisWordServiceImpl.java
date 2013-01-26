package de.groupon.sample.service.impl;

import de.groupon.sample.exception.NotFoundException;
import de.groupon.sample.model.GermanWord;
import de.groupon.sample.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class RedisWordServiceImpl implements WordService{

    public static final String KEY = GermanWord.class.getSimpleName();

    @Autowired
    private RedisTemplate<String,GermanWord> redisTemplate;

    @Override
    public void save(GermanWord word){
        redisTemplate.opsForHash().put(KEY, word.getName(), word);
    }

    @Override
    public GermanWord getWord(String value) throws NotFoundException{
        final GermanWord germanWord = (GermanWord) redisTemplate.opsForHash().get(KEY, value);
        if(germanWord == null){
            throw new NotFoundException("Word "+ value + "not found");
        }
        return germanWord;
    }

    @Override
    public void delete(GermanWord value){
       redisTemplate.opsForHash().delete(KEY,value.getName());
    }

    @Override
    public Collection<GermanWord> getAll(){
        List<GermanWord> vals = new ArrayList<GermanWord>();
        final List<Object> values = redisTemplate.opsForHash().values(KEY);
        for (Object value : values) {
            if(value instanceof  GermanWord){
                vals.add((GermanWord) value);
            }
        }
        return vals;
    }

}
