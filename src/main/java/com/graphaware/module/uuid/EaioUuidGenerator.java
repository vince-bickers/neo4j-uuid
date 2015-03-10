package com.graphaware.module.uuid;

import com.eaio.uuid.UUID;

public class EaioUuidGenerator implements UuidGenerator {

    @Override
    public String generateUuid() {
        UUID uuid = new UUID();
        return uuid.toString();
    }
}
