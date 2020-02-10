package com.cdx.controller.Utils.MailProducer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MailProducerImpl implements MailProducer {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * convertAndSend讲传输的数据通过类型转化为json对象
     * @param map
     */
    public void send(Map map){
        rabbitTemplate.convertAndSend("sendRegirectOKEmail",map);
    }
}
