package com.example.demo.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Table
public class DynamicData {

    @PrimaryKey
    private UUID id;
    @Column
    private Map<String, String> attributes;

    public DynamicData() {
        this.attributes = new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }

    public void setAttribute(String attributeName, String attributeValue) {
        attributes.put(attributeName, attributeValue);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "DynamicData{" +
                "id='" + id + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
