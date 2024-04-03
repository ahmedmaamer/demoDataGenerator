package com.example.demo.service ;
import com.example.demo.model.DynamicData;
import com.example.demo.repository.DynamicDataRepository;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class DynamicDataService {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataService.class);

    @Autowired
    private DynamicDataRepository dynamicDataRepository;

    private final Faker faker = new Faker();



    public void saveDynamicDataFromCsv(InputStream inputStream, int numberOfObjects) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> attributeNames = Arrays.asList(br.readLine().split(","));
            logger.info("Attribute names parsed from CSV: {}", attributeNames);

            for (int i = 0; i < numberOfObjects; i++) {
                DynamicData dynamicData = new DynamicData();
                dynamicData.setId(UUID.randomUUID());
                attributeNames.forEach(attributeName ->
                        dynamicData.setAttribute(attributeName, String.valueOf(faker.random().nextLong())));
                dynamicDataRepository.save(dynamicData);
            }

        } catch (IOException e) {
            logger.error("Error reading CSV file: {}", e.getMessage());
        }
    }
}
