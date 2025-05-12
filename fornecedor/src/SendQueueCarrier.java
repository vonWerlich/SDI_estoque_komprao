import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.util.ArrayList;
import java.util.List;

public class SendQueueCarrier {
    private final static String QUEUE_NAME = "sdi_queue";
    private final static String HOST = "localhost";

    private Connection connection;
    private Channel channel;

    public SendQueueCarrier() throws Exception{
       
    }

    private String joinMessages(List<String> produtos){
        String res = "";

        int numMessages = produtos.size() / 10;
        int resMessages = produtos.size() % 10; // resto das mensagens a serem enviadas

        int cont = 0;
        String message = "";

        for (int i = 0; i < numMessages; i++) {
            for (int j = 0; j < 10; j++) {
                message += produtos.get(cont);
                cont++;
            }

            res += message;
            message = "";
        }

       
        for (int j = 0; j < resMessages; j++) {
                message += produtos.get(cont);
                cont++;
        }

        res += message;

        return res;
    }

    public void sendCarrier(List<String> produtos) throws Exception{
        List<String> messages = this.joinMessages(produtos);

        this.channelConnect();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, messages.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        this.channelClose();
    }

    private void channelConnect() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.HOST);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
    }

    private void channelClose() throws Exception {
        this.channel.clone();
        this.connection.close();
    }

}