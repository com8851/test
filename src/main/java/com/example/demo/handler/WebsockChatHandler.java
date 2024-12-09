package com.example.demo.handler;

import  lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Timer;
import java.util.TimerTask;

@Slf4j
@Component
public class WebsockChatHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
        TextMessage textMessage = new TextMessage("Welcom Chat Server!!!");
        session.sendMessage(textMessage);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 클라이언트가 접속하면 무한 메시지를 보내기 시작
        startSendingMessages(session);
    }

    private void startSendingMessages(WebSocketSession session) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (session.isOpen()) {
                        session.sendMessage(new TextMessage("무한 메시지"));
                    } else {
                        this.cancel();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2000); // 2초마다 메시지 전송
    }
}
