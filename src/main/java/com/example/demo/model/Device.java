package com.example.demo.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Map;
import java.util.UUID;

@Table
public class Device {
    @PrimaryKey
    private UUID deviceId;
    @Column
    private String deviceName;
    @Column
    private String deviceType ;
    @Column
    private Boolean deviceStatus ;

    public Boolean getDeviceStaus() {
        return deviceStatus;
    }

    public void setDeviceStaus(Boolean deviceStaus) {
        this.deviceStatus = deviceStaus;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Column
    private Map<String, String> attributes; // Map<AttributeName, AttributeType>

    // Constructors, Getters, and Setters


    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
