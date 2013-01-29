package de.groupon.sample.service.impl;

import de.groupon.sample.config.profile.AppConfig;
import de.groupon.sample.model.GermanWord;
import de.groupon.sample.model.enumerations.Gender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles({"dev","test"})
public class RedisWordServiceImplTest {

    @Autowired
    RedisWordServiceImpl redisWordService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRedisFlow() throws Exception {
        final GermanWord test = new GermanWord("test");
        test.setGender(Gender.DAS);
        redisWordService.save(test);
        final GermanWord germanWord = redisWordService.getWord("test");
        assertNotNull(germanWord);
        assertEquals(germanWord,test);
        final Collection<GermanWord> all = redisWordService.getAll();
        assertNotNull(all);
        assertTrue(all.contains(germanWord));
        redisWordService.delete(test);
        final Collection<GermanWord> collectionAfterDelete = redisWordService.getAll();
        assertNotNull(collectionAfterDelete);
        assertFalse(collectionAfterDelete.contains(germanWord));

    }
}
