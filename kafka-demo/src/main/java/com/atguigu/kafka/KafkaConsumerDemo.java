package com.atguigu.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author: lichunxia
 * @create: 2021-04-13 16:42
 */
public class KafkaConsumerDemo implements Runnable {

    private static final String GROUPID = "groupA";
    private final KafkaConsumer<String, String> consumer;
    private final String topic;
    private ConsumerRecords<String, String> msgList;

    public KafkaConsumerDemo(String topicName) {
        Properties props = new Properties();
        // props.put("bootstrap.servers", "master:9092,slave1:9092,slave2:9092");
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", GROUPID);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        this.consumer = new KafkaConsumer<>(props);
        this.topic = topicName;
        this.consumer.subscribe(Arrays.asList(topic));
    }

    public static void main(String args[]) {
        KafkaConsumerDemo test1 = new KafkaConsumerDemo("topic-demo");
        Thread thread1 = new Thread(test1);
        thread1.start();
    }

    @Override
    public void run() {
        int messageNo = 1;
        System.out.println("---------开始消费---------");
        try {
            for (; ; ) {
                msgList = consumer.poll(Duration.ofSeconds(1));
                if (null != msgList && msgList.count() > 0) {
                    for (ConsumerRecord<String, String> record : msgList) {
                        //消费100条就打印, 但打印的数据不一定是这个规律的
                        if (messageNo % 100 == 0) {
                            System.out.println(messageNo + "=======receive: key = " + record.key()
                                    + ", value = " + record.value()
                                    + " offset===" + record.offset());
                        }
                        //当消费了1000条就退出
                        if (messageNo % 1000 == 0) {
                            break;
                        }
                        messageNo++;
                    }
                } else {
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

}
