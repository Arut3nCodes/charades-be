package com.example.koornikbe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CacheToggle {
    @Value("${app.cache.enabled:true}")
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }
}
