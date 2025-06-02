package org.example.foodee_service.controller;

import lombok.AllArgsConstructor;
import org.example.foodee_service.dto.request.notification_request.MarkAsReadRequest;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.example.foodee_service.service.impl.NotificationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/notify")
@AllArgsConstructor
public class NotificationController {
    private final NotificationServiceImpl notificationService;

    @PostMapping("/markAsRead/v1.0")
    public ResponseEntity<ResultObject<String>> markAsRead(@RequestBody MarkAsReadRequest request) {

        notificationService.markNotificationAsRead(request);

        ResultObject<String> response = ResultObject.success("Xoa thanh cong!");
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

}
