package com.bjsxt.sorm.core;

import com.bjsxt.po.Flower;
import com.bjsxt.sorm.pool.DBConnPool;
import com.bjsxt.vo.StudentVO;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by TianXiang on 2019/4/6.
 */
public class MySqlQueryTest {

    @Test
    public void test01() {
        Flower t = new Flower();
        t.setId(4);
        Query query = new MySqlQuery();
        query.delete(t);
    }

    @Test
    public void test02() {
        Flower f = new Flower();
        f.setName("蒲公英1");
        f.setPrice(15.23F);
        f.setProduction("贵州");
        Query query = new MySqlQuery();
        query.insert(f);
    }

    @Test
    public void test03() {
        Flower f = new Flower();
        f.setId(6);
        f.setName("蒲公英888");
//        f.setPrice(666D);
        f.setProduction("广西8");
        Query query = new MySqlQuery();
//        query.update(f,new String[]{"name","price"});
        query.update(f);
    }

    @Test
    public void test04() {
        List list = new MySqlQuery().queryRows("select id,name,production,price from flower where id > ? and price>?", Flower.class, new Object[]{1,5F});
        System.out.println(((Flower)list.get(0)).getName());
    }

    @Test
    public void test05() {
        List list = new MySqlQuery().queryRows("SELECT s.id id,s.name `name`,s.age age,t.name teaName from student s JOIN teacher t WHERE s.tid = t.id", StudentVO.class, null);
        System.out.println(list);
    }

    @Test
    public void test06() {
        Object o = new MySqlQuery().queryValue("select count(*) from teacher", null);
        System.out.println("o = " + o);
        o = new MySqlQuery().queryNumber("select count(*) from flower where price>?", new Object[]{4F});
        System.out.println("o = " + o);
    }

    @Test
    public void test07() {
        Object o = QueryFactory.createQuery().queryValue("select count(*) from teacher", null);
        System.out.println("o = " + o);
        o = QueryFactory.createQuery().queryNumber("select count(*) from flower where price>?", new Object[]{4F});
        System.out.println("o = " + o);
        Flower f = (Flower)QueryFactory.createQuery().queryById(Flower.class, 6);
        System.out.println(f.getName()+" "+f.getProduction()+" "+f.getPrice());
    }

    //使用连接池1417
    //不使用连接池5833
    @Test
    public void test08() throws InterruptedException {
        long start = System.currentTimeMillis();
        int taskNum = 1000;
        CountDownLatch latch = new CountDownLatch(taskNum);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                Object o = QueryFactory.createQuery().queryValue("select count(*) from teacher", null);
                System.out.println("o = " + o);
                o = QueryFactory.createQuery().queryNumber("select count(*) from flower where price>?", new Object[]{4F});
                System.out.println("o = " + o);
                latch.countDown();
            }
        };

        ExecutorService exec = Executors.newFixedThreadPool(30);

        for (int i = 0; i < taskNum; i++) {
            exec.submit(task);
        }
        latch.await();
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(DBConnPool.pool.size());//没有实现多长时间没用，回收连接池的代码
    }

}