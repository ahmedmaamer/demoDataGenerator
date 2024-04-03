package com.example.demo.service ;
import com.example.demo.model.AttributeGenerationRequest;
import com.example.demo.model.Device;
import com.example.demo.model.DeviceData;
import com.example.demo.repository.DeviceDataRepository;
import com.example.demo.repository.DeviceRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class DeviceDataService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceDataRepository deviceDataRepository;

    private final Faker faker;

    public DeviceDataService() {
        this.faker = new Faker();
    }

    public List<DeviceData> generateDeviceData(UUID deviceId, int numberOfDevices, List<AttributeGenerationRequest> attributeGenerationRequests) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        if (deviceOptional.isEmpty()) {
            return Collections.emptyList(); // Device not found
        }

        Device device = deviceOptional.get();
        Map<String, String> attributeTypes = device.getAttributes();
        if (attributeTypes == null || attributeTypes.isEmpty()) {
            return Collections.emptyList(); // No attributes defined for this device
        }

        List<DeviceData> deviceDataList = new ArrayList<>();
        for (int i = 0; i < numberOfDevices; i++) {
            DeviceData deviceData = generateSingleDeviceData(deviceId, attributeGenerationRequests, attributeTypes);
            deviceDataList.add(deviceData);
        }

        return deviceDataList;
    }

    private DeviceData generateSingleDeviceData(UUID deviceId, List<AttributeGenerationRequest> attributeGenerationRequests, Map<String, String> attributeTypes) {
        DeviceData deviceData = new DeviceData();
        deviceData.setDeviceId(UUID.randomUUID());

        Map<String, String> attributeData = generateAttributeData(attributeGenerationRequests, attributeTypes);
        deviceData.setAttributeData(attributeData);

        deviceDataRepository.save(deviceData);
        return deviceData;
    }

    private Map<String, String> generateAttributeData(List<AttributeGenerationRequest> attributeGenerationRequests, Map<String, String> attributeTypes) {
        Map<String, String> attributeData = new HashMap<>();
        Random random = new Random();
        for (AttributeGenerationRequest request : attributeGenerationRequests) {
            String attributeName = request.getAttributeName();
            String attributeType = attributeTypes.get(attributeName);
            if (attributeType != null) {
                String generatedValue = generateValueForAttribute(request, attributeType, random);
                attributeData.put(attributeName, generatedValue);
            }
        }
        return attributeData;
    }
    private String generateValueForAttribute(AttributeGenerationRequest request, String attributeType, Random random) {
        switch (attributeType) {
            case "String":
                return generateStringAttribute(request, random);
            case "Integer":
                return generateIntegerAttribute(request, random);
            case "Boolean":
                return String.valueOf(random.nextBoolean());
            case "Date":
                return generateDateAttribute(request, random);
            default:
                return "";
        }
    }

    private String generateStringAttribute(AttributeGenerationRequest request, Random random) {
        String generationMethod = request.getStringGenerationMethod();
        switch (generationMethod) {
            case "RandomList":
                return generateRandomStringFromList(request.getSpecifiedStringValues(), random);
            case "Pattern":
                return faker.regexify(request.getPattern());
            default:
                return "";
        }
    }

    private String generateRandomStringFromList(List<String> specifiedStringValues, Random random) {
        return specifiedStringValues.isEmpty() ? "" : specifiedStringValues.get(random.nextInt(specifiedStringValues.size()));
    }

    private String generateIntegerAttribute(AttributeGenerationRequest request, Random random) {
        String generationMethod = request.getGenerationMethod();
        switch (generationMethod) {
            case "Random":
                int minValue = request.getMinValue();
                int maxValue = request.getMaxValue();
                return String.valueOf(random.nextInt(maxValue - minValue + 1) + minValue);
            case "RandomList":
                List<Integer> specifiedValues = request.getSpecifiedValues();
                return specifiedValues.isEmpty() ? "" : String.valueOf(specifiedValues.get(random.nextInt(specifiedValues.size())));
            default:
                return "";
        }
    }

    private String generateDateAttribute(AttributeGenerationRequest request, Random random) {
        String dateGenerationMethod = request.getDateGenerationMethod();
        switch (dateGenerationMethod) {
            case "RangeBetweenTwoDates":
                Date firstDate = request.getFirstDate();
                Date lastDate = request.getLastDate();
                return faker.date().between(firstDate, lastDate).toString();
            case "ListOfDates":
                List<String> specifiedDates = request.getSpecifiedDates();
                return specifiedDates.isEmpty() ? "" : specifiedDates.get(random.nextInt(specifiedDates.size()));
            default:
                return "";
        }
    }
}
