package com.yupi.yupao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yupao.model.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class RedissonTest {
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;
    //重点用户
    private List<Long> mainUserList = Arrays.asList(1L);

    @Test
    public void test(){
        //list存在本地JVM内存中
        List<String> list = new ArrayList<>();
        list.add("yupi");
        //rList存在于redis内存中
        /*RList<Object> rList = redissonClient.getList("test-list");
        rList.add("lili2");
        rList.get(0);*/
        Map<String,Integer> map = new HashMap<>();
        map.put("map",1);
        map.get("map");
        RMap<Object, Object> rMap = redissonClient.getMap("test-map");
        rMap.put("map",1);
        rMap.get("map");
        // map.remove("map");
       // rList.remove(0);
        //System.out.println(map);
    }
    @Test
    public void test2(){
        RLock lock = redissonClient.getLock("yupao:precachejob:docache:lock");
        try {
            //只有一个线程可以获取锁
            if(lock.tryLock(0,-1, TimeUnit.MILLISECONDS)){
                System.out.println("getLock"+ Thread.currentThread().getId());
                Thread.sleep(10000);
                for (Long userId : mainUserList){
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    Page<User> userPage = userService.page(new Page<>(1,20),queryWrapper);
                    String redisKey = String.format("yupao:user:recommend:%s",userId);
                    ValueOperations valueOperations = redisTemplate.opsForValue();
                    try {
                        valueOperations.set(redisKey,userPage,50000, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        log.error("redis set key error");
                    }
                }
            }
        } catch (InterruptedException e) {
            log.error("error");
        }finally {
            //只能释放自己的锁
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
}
