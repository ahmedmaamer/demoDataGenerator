package com.example.demo.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Map;
import java.util.UUID;

@Table
public class DeviceData {
    @PrimaryKey
    private UUID deviceId;
    @Column
    private Map<String, String> attributeData; // Map<AttributeName, AttributeValue>

    // Constructors, Getters, and Setters

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public Map<String, String> getAttributeData() {
        return attributeData;
    }

    public void setAttributeData(Map<String, String> attributeData) {
        this.attributeData = attributeData;
    }
}

