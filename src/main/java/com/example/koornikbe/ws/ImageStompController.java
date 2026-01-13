package com.example.koornikbe.ws;

import com.example.koornikbe.dto.PixelColorData;
import com.example.koornikbe.model.Image;
import com.example.koornikbe.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImageStompController {

    private final ImageService service;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/images/{id}/pixels") // /app/images/{id}/pixels
    public void updatePixels(
            @DestinationVariable Long id,
            @Payload List<PixelColorData> pixels
    ) {
        Image updated = service.updateImageFromPixels(id, pixels);

        // broadcast do wszystkich klientów tego obrazka
        messagingTemplate.convertAndSend(
                "/topic/images/" + id,
                pixels
        );
    }
}
