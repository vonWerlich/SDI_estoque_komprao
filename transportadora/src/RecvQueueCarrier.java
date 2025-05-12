package transportadora.src;

import java.io.IOException;
import java.nio.channels.Channel;
import java.sql.Connection;
import java.util.function.Consumer;

import com.rabbitmq.client.*;

public class RecvQueueCarrier {
    private final static String QUEUE_NAME = "sdi_queue";
    private final static String HOST = "localhost";

    private Connection connection;
    private Channel channel;

    public RecvQueueCarrier() {}

    public void printMessage(byte[] body){
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received:\n '\t" + message + "'");
    }

    public void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.QUEUE_NAME);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body){
                this.printMessage(body);
             }
        };
    }
}
