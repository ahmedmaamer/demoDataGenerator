package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Device;
import com.example.demo.service.DeviceService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@CrossOrigin("*")

@NoArgsConstructor
@RestController

public class DeviceController {

    @Autowired
    private DeviceService deviceService ;


    @PostMapping("create_device")
    public ResponseEntity<String> createDevice (@RequestBody Device device){

        try {
            deviceService.saveDevice(device);
            return new ResponseEntity<>("Device saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save device", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("get_devices")
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.getAllDevices();
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete_device/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable UUID id) {
        try {
            deviceService.deleteDevice(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping(value = "/get_device_by_id/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable("id") UUID deviceId) {
        try {
            Device device = deviceService.getDeviceById(deviceId).getBody();
            return ResponseEntity.ok(device);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    }
