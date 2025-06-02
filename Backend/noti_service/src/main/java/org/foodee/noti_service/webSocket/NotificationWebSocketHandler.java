package org.foodee.noti_service.webSocket;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.foodee.noti_service.service.EmailService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
@RequiredArgsConstructor
public class NotificationWebSocketHandler extends TextWebSocketHandler {

    private final EmailService emailService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Xử lý thông điệp gửi từ client (nếu có)
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

        // Gửi thông báo trả lại cho client
        TextMessage response = new TextMessage("New notification: " + payload);
        session.sendMessage(response);
        // Gửi email cho khách hàng
        String to = "nguyenminhcoang.work@gmail.com";
        String subject = "Hello Thanh Trà";
        String body = payload;

        emailService.sendSimpleEmail(to, subject, body);
    }
}
