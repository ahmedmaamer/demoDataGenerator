package com.example.demo.controller ;
import com.example.demo.service.DynamicDataService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@NoArgsConstructor
@RestController
public class DynamicDataController {

    @Autowired
    private DynamicDataService dynamicDataService;

    @PostMapping("/upload-csv")
    public String uploadCsv(@RequestParam("file") MultipartFile file,
                            @RequestParam("numberOfObjects") int numberOfObjects) {
        if (file.isEmpty()) {
            return "Please select a CSV file to upload.";
        }
        try {
            dynamicDataService.saveDynamicDataFromCsv(file.getInputStream(), numberOfObjects);
            return "CSV file uploaded successfully. Generated " + numberOfObjects + " objects.";
        } catch (Exception e) {
            return "Failed to upload CSV file: " + e.getMessage();
        }
    }
}
