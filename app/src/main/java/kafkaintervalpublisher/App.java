/*
 * This Java source file was generated by the Gradle 'init' task.
 */

package kafkaintervalpublisher;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Date;

import java.text.SimpleDateFormat;

import java.util.Properties;

public class App {
  private static final Logger log = LoggerFactory.getLogger(App.class);
  public String getGreeting() {
    return "Hello World!";
  }
  private static void timedEvent (){
    log.info("I am a Kafka Producer");

    String bootstrapServers = "127.0.0.1:9092";

    // create Producer properties
    Properties properties = new Properties();
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    // create the producer
    KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
    Date now = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    String nowAsString = sdf.format(now);
    System.out.println(now);
    System.out.println(nowAsString);
    // create a producer record
    ProducerRecord<String, String> producerRecord =
            new ProducerRecord<>("demo_java", nowAsString);

    // send data - asynchronous
    producer.send(producerRecord);

    // flush data - synchronous
    producer.flush();
    // flush and close producer
    producer.close();
  }
  public static void main(String[] args) {


    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    Runnable task = new Runnable() {
      public void run() {
        timedEvent();
        System.out.println("Timer event occurred.");
      }
    };

    executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);


  }
}

/*
 * 
            Date now = new Date();
            sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            nowAsString = sdf.format(now);
            System.out.println(now);
            System.out.println(nowAsString);
 */