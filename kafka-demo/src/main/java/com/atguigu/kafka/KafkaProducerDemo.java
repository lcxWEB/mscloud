package com.atguigu.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author: lichunxia
 * @create: 2021-04-13 16:37
 */
public class KafkaProducerDemo implements Runnable {

    private final KafkaProducer<String, String> producer;

    private final String topic;

    public KafkaProducerDemo(String topicName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        this.producer = new KafkaProducer<>(props);
        this.topic = topicName;
    }

    public static void main(String[] args) {
        KafkaProducerDemo producer = new KafkaProducerDemo("topic-demo");
        Thread thread = new Thread(producer);
        thread.start();
    }

    @Override
    public void run() {
        int messageNo = 1;
        try {
            for (; ; ) {
                String messageStr = "你好，这是第" + messageNo + "条数据";
                producer.send(new ProducerRecord<>(topic, "Message", messageStr));
                //生产了100条就打印
                if (messageNo % 100 == 0) {
                    System.out.println("发送的信息:" + messageStr);
                }
                //生产1000条就退出
                if (messageNo % 1000 == 0) {
                    System.out.println("成功发送了" + messageNo + "条");
                    break;
                }
                messageNo++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
