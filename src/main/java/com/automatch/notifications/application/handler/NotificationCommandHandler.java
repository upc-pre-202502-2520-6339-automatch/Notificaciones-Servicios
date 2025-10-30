package com.automatch.notifications.application.handler;

import com.automatch.notifications.application.dto.NotificationRequestDTO;
import com.automatch.notifications.application.service.NotificationApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationCommandHandler {

    private final NotificationApplicationService applicationService;

    public void handle(NotificationRequestDTO dto) {
        applicationService.send(dto);
    }
}