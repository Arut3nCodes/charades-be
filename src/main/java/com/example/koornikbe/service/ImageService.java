package com.example.koornikbe.service;

import com.example.koornikbe.dto.PixelColorData;
import com.example.koornikbe.model.Image;
import com.example.koornikbe.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository repository;

    public List<Image> getAllImages() {
        return repository.findAll();
    }

    public Image getImageById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found: " + id));
    }

    public Image addImage(Image entity) {
        entity.setImageid(null);
        return repository.save(entity);
    }

    public Image updateImage(Long id, Image entity) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found: " + id);
        }
        entity.setImageid(id);
        return repository.save(entity);
    }

    public void deleteImage(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found: " + id);
        }
        repository.deleteById(id);
    }

    public Image createWhiteImage(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid image resolution");
        }

        BufferedImage bufferedImage =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = bufferedImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.dispose();

        byte[] imageBytes = toPng(bufferedImage);

        Image image = new Image();
        image.setWidth(width);
        image.setHeight(height);
        image.setContent(imageBytes);
        image.setFormat("PNG");

        return addImage(image);
    }

    @Transactional
    public Image updateImageFromPixels(Long imageId, List<PixelColorData> pixelDataList) {
        // Load existing image entity
        Image image = repository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // Convert byte[] to BufferedImage
        BufferedImage bufferedImage;
        try (var in = new java.io.ByteArrayInputStream(image.getContent())) {
            bufferedImage = ImageIO.read(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read existing image", e);
        }

        // Update pixels
        for (PixelColorData data : pixelDataList) {
            Color color = Color.decode(data.getColor());
            for (int[] pixel : data.getPixels()) {
                int x = pixel[0];
                int y = pixel[1];
                if (x >= 0 && x < bufferedImage.getWidth() && y >= 0 && y < bufferedImage.getHeight()) {
                    bufferedImage.setRGB(x, y, color.getRGB());
                }
            }
        }

        // Convert back to byte[]
        byte[] updatedBytes = toPng(bufferedImage);
        image.setContent(updatedBytes);

        // Persist changes
        return repository.save(image);
    }

    private byte[] toPng(BufferedImage image) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate image", e);
        }
    }
}
