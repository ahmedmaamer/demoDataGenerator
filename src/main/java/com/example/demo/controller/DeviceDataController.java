package com.example.demo.controller ;

import com.example.demo.model.AttributeGenerationRequest;
import com.example.demo.model.DeviceData;
import com.example.demo.service.DeviceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceDataController {

    @Autowired
    private DeviceDataService deviceDataService;

    @PostMapping("/{deviceId}/generate")
    public ResponseEntity<List<DeviceData>> generateDeviceData(@PathVariable UUID deviceId,
                                                               @RequestParam int numberOfDevices,
                                                               @RequestBody List<AttributeGenerationRequest> attributeGenerationRequests) {
        List<DeviceData> generatedDeviceData = deviceDataService.generateDeviceData(deviceId, numberOfDevices, attributeGenerationRequests);
        if (generatedDeviceData.isEmpty()) {
            return ResponseEntity.notFound().build(); // Device not found or no attributes defined
        }
        return ResponseEntity.ok(generatedDeviceData);
    }
}
