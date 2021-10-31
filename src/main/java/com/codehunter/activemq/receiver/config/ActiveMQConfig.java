package com.codehunter.activemq.receiver.config;

import com.codehunter.activemq.receiver.service.CheckingAccountService;
import com.codehunter.activemq.sdo.ICheckingAccountService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * @author codehunter
 */
@Configuration
@EnableJms
public class ActiveMQConfig {
    public static final String QUEUE_NAME = "order.queue";

    @Value("${spring.activemq.broker-url}")
    String brokerUrl;

    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setMessageConverter(messageConverter());
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public ActiveMQQueue getQueue() {
        return new ActiveMQQueue("queue.remote");
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerUrl);
        ActiveMQPrefetchPolicy qPrefetchPolicy = new ActiveMQPrefetchPolicy();
        qPrefetchPolicy.setQueuePrefetch(0);
        qPrefetchPolicy.setMaximumPendingMessageLimit(0);
        factory.setPrefetchPolicy(qPrefetchPolicy);
        factory.setTrustAllPackages(true);
        return factory;
    }

    @Bean
    public JmsInvokerServiceExporter checkingAccountService() {
        JmsInvokerServiceExporter jmsInvokerServiceExporter = new JmsInvokerServiceExporter();
        jmsInvokerServiceExporter.setServiceInterface(ICheckingAccountService.class);
        jmsInvokerServiceExporter.setService(getCheckingAccountService());
        return jmsInvokerServiceExporter;
    }

    @Bean
    public ICheckingAccountService getCheckingAccountService() {
        return new CheckingAccountService();
    }

//    @Bean
//    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
//        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
//        simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
//        simpleMessageListenerContainer.setDestination(getQueue());
//        simpleMessageListenerContainer.setConcurrentConsumers(3);
//        simpleMessageListenerContainer.setMessageListener(checkingAccountService());
//        return simpleMessageListenerContainer;
//    }

    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainer() {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory());
        listenerContainer.setDestination(getQueue());
        listenerContainer.setConcurrentConsumers(5);
        listenerContainer.setMaxConcurrentConsumers(5);
        listenerContainer.setMessageListener(checkingAccountService());
        return listenerContainer;
    }
}
