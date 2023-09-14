package com.yupi.yupao.service;
import java.util.Date;

import com.yupi.yupao.YuPaoApplication;
import com.yupi.yupao.model.domain.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = YuPaoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTest {
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test(){
        //操作对象
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //增
        valueOperations.set("yupiString","dog");
        valueOperations.set("yupiInt",1);
        valueOperations.set("yupiDouble",2.0);
        User user = new User();
        user.setId(1);
        user.setUsername("niniyu");
        valueOperations.set("user",user);
        //查
        Object yupiString = valueOperations.get("yupiString");
        Assertions.assertTrue("dog".equals((String) yupiString));
        Object yupiInt = valueOperations.get("yupiInt");
        Assertions.assertTrue(1==((Integer) yupiInt));
        Object yupiDouble = valueOperations.get("yupiDouble");
        Assertions.assertTrue(2.0 ==((Double) yupiDouble));
        Object niniyu = valueOperations.get("niniyu");
        System.out.println(niniyu);

    }
}
