package co.relevator.nlp.ner.amqp;

import co.relevator.nlp.ner.NerSettings;
import org.apache.xpath.operations.Bool;

/**
 * Created by brad on 3/29/14.
 */
public class NerAmqpServiceSettings extends NerSettings {

    protected String hostName;
    protected String exchangeName;
    protected String queueName;
    protected Integer QOS;
    protected Boolean durable;
    protected Boolean exclusive;
    protected Boolean autoDelete;
    protected String registerQueue;

    protected String logQueue;
    protected String logExchange;

    public String getRegisterQueue(){
        return registerQueue;
    }

    public String getExchangeName(){
        return exchangeName;
    }

    public String getQueueName(){
        return queueName;
    }

    public String getHostName() {
        return hostName;
    }

    public Integer getQOS() {
        return QOS;
    }

    public Boolean getDurable() {
        return durable;
    }

    public Boolean getExclusive() {
        return exclusive;
    }

    public Boolean getAutoDelete() {
        return autoDelete;
    }

    public String getLogQueue() {
        return logQueue;
    }

    public String getLogExchange() {
        return logExchange;
    }
}
