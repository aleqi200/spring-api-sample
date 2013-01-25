package de.groupon.sample.service.impl;

import de.groupon.sample.exception.NotFoundException;
import de.groupon.sample.model.GermanWord;
import de.groupon.sample.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RedisWordServiceImpl implements WordService{

    public static final String KEY = GermanWord.class.getSimpleName();

    @Autowired
    private RedisTemplate<String,GermanWord> redisTemplate;

    @Override
    public void save(GermanWord word){
        redisTemplate.opsForHash().put(KEY,word.getName(),word);
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
        return redisTemplate.opsForList().range(KEY,0l,-1l);
    }

}
