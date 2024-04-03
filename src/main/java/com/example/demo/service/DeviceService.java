package com.example.demo.service;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.metadata.schema.TableMetadata;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.example.demo.model.Device;
import com.example.demo.repository.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraPersistentEntity;
import org.springframework.data.cassandra.core.mapping.CassandraPersistentProperty;
import org.springframework.data.mapping.PropertyHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);
    private final CqlSession cqlSession;
    private final CassandraMappingContext cassandraMappingContext;

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(CqlSession cqlSession, DeviceRepository deviceRepository , CassandraMappingContext cassandraMappingContext) {
        this.cqlSession = cqlSession;
        this.deviceRepository = deviceRepository;
        this.cassandraMappingContext = cassandraMappingContext ;

    }

    public void saveDevice(Device device) {
        try {
            device.setDeviceId(UUID.randomUUID());
            deviceRepository.save(device);

            // Dynamically create a table for the device
            createTableForDevice(device.getDeviceName(), Device.class);
        } catch (Exception e) {
            // Log the exception
            logger.error("Error creating device and table", e);
            // You might want to throw a custom exception or handle the error according to your application's logic
            throw new RuntimeException("Error creating device and table", e);
        }
    }
    public void createTableForDevice(String deviceName, Class<?> clazz) {
        Optional<TableMetadata> existingTable = cqlSession.getMetadata().getKeyspace(cqlSession.getKeyspace().get())
                .flatMap(keyspaceMetadata -> Optional.ofNullable(keyspaceMetadata.getTable(deviceName)))
                .flatMap(table -> table);

        if (existingTable.isPresent()) {
            // Table already exists, no need to create it
            return;
        }

        Optional<CassandraPersistentEntity<?>> persistentEntityOptional = cqlSession.getKeyspace()
                .map(keyspace -> cassandraMappingContext.getRequiredPersistentEntity(clazz));

        CassandraPersistentEntity<?> persistentEntity = persistentEntityOptional.orElseThrow(() ->
                new RuntimeException("Failed to get persistent entity for class " + clazz.getName()));

        CqlIdentifier tableName = CqlIdentifier.fromCql(deviceName);
        StringBuilder createTableCql = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(tableName.asCql(true)).append(" (")
                .append("id UUID PRIMARY KEY");

        persistentEntity.doWithProperties((PropertyHandler<CassandraPersistentProperty>) property -> {
            DataType dataType = DataTypes.TEXT; // Example: Using TEXT as default data type
            String dataTypeCql = dataType.asCql(false, true); // Use appropriate method to get CQL representation

            // Add more columns as needed
            createTableCql.append(", ")
                    .append(property.getName()).append(" ").append(dataTypeCql);
        });

        createTableCql.append(");");

        cqlSession.execute(createTableCql.toString());
    }


    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public void deleteDevice(UUID id) {
        deviceRepository.deleteById(id);
    }

    public ResponseEntity<Device> getDeviceById(UUID id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found with id: " + id));
        return new ResponseEntity<>(device, HttpStatus.OK);
    }
}
