package com.eshop.inventory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author: lichunxia
 * @create: 2021-02-25 13:01
 */
@SpringBootApplication
@MapperScan("com.eshop.inventory.mapper")
public class EshopInventoryMainApp {

    public static void main(String[] args) {
        SpringApplication.run(EshopInventoryMainApp.class, args);

        // HashMap<String, String> map = new HashMap<>(63);
        // map.put("test", "kkkk");
        // String test = map.get("test");
        // map.remove("test");
        // Set<Map.Entry<String, String>> entries = map.entrySet();
        // for (Map.Entry<String, String> entry : entries) {
        //     String key = entry.getKey();
        //     String value = entry.getValue();
        // }
        // Set<String> keySet = map.keySet();
        // Collection<String> values = map.values();
        //
        // int i = 4;
        // System.out.println(++i == 5);
        // System.out.println(i);
        // System.out.println(map.size());
        //
        // int[] array = new int[10];
        // array[5] = 10;
        //
        // System.out.println(array[i++]);
        // System.out.println(i);

        // ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        //
        // new Thread(() -> {
        //     for (int j = 0; j < 10; j++) {
        //         concurrentHashMap.put("test" + j, j + 1);
        //         System.out.println(Thread.currentThread().getName() + " : " + j);
        //         try {
        //             TimeUnit.SECONDS.sleep(1);
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }, "Thread1").start();
        //
        //
        // ConcurrentHashMap.KeySetView<String, Integer> set = concurrentHashMap.keySet();
        // for (String key : set) {
        //
        // }
        //
        // new Thread(() -> {
        //     Set<Map.Entry<String, Integer>> entrySet = concurrentHashMap.entrySet();
        //     for (Map.Entry<String, Integer> entry : entrySet) {
        //         System.out.println(Thread.currentThread().getName() + " " + entry.getKey() + ":" + entry.getValue());
        //         try {
        //             TimeUnit.SECONDS.sleep(1);
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }, "Thread2").start();
        //
        //
        // ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<>();
        // // freqs.computeIfAbsent(v -> new LongAdder()).increment();


    }
}
