package com.cdx.listener;

import com.cdx.common.utils.MailUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;


import java.io.IOException;

public class MailMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {

        // 获取消息
        byte[] body = message.getBody();
        // 将body转化为map集合
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(body);
            // 获取发送的对象
            String to = jsonNode.get("to").asText();
            // 获取主题
            String subject = jsonNode.get("subject").asText();
            // 获取发送的内容
            String content = jsonNode.get("content").asText();
            // 发送邮件
            MailUtil.sendMsg(to, subject, content);
            System.out.println("发送邮件成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
