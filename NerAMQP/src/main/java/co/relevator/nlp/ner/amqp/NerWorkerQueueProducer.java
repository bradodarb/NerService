package co.relevator.nlp.ner.amqp;

import co.relevator.nlp.ner.NerParser;
import co.relevator.nlp.ner.models.NerResult;
import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.apache.xpath.operations.Bool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by brad on 3/30/14.
 */
public class NerWorkerQueueProducer {

    private static NerAmqpServiceSettings _settings;
    private static Gson gson = new Gson();

    public static void main(String[] argv)
            throws java.io.IOException,
            java.lang.InterruptedException {

        checkInit();
        NerParser.getInstance().init(_settings);

        String exchangeName = _settings.getExchangeName();
        String queueName = _settings.getQueueName();
        String host = _settings.getHostName();
        Integer qos = _settings.getQOS();
        Boolean durable = _settings.getDurable();
        Boolean exclusive = _settings.getExclusive();
        Boolean autoDelete = _settings.getAutoDelete();
        Boolean autoAck = false;


        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, "direct");
        channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);

        channel.basicQos(qos);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, autoAck, consumer);


        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            if (delivery != null) {
                AMQP.BasicProperties props = delivery.getProperties();

                AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
                        .correlationId(props.getCorrelationId())
                        .build();

                String q = new String(delivery.getBody());

                NerResult result;
                try {

                    result = NerParser.getInstance().parse(q);

                } catch (Exception ex) {

                    result = new NerResult(q, null, ex.getMessage());
                }

                String response = gson.toJson(result);
                channel.basicPublish(exchangeName, props.getReplyTo(), replyProps, response.getBytes());
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        }
    }


    private static void checkInit() {
//        if(_settings == null)
//        {
//            try {
//                String path = context.getInitParameter("propsFile");
//                FileInputStream inputStream = null;
//                inputStream = new FileInputStream(new File(context.getRealPath(path)));
//                if (inputStream != null) {
//                    final Gson gson = new Gson();
//                    final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//                    _settings = gson.fromJson(reader, NerAmqpServiceSettings.class);
//                }
//            } catch (final Exception e) {
//                //log
//            }
//        }
    }
}
