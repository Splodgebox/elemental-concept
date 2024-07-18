package net.mirza.elementalconcept.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mirza.elementalconcept.config.FeatureFlagConfig;
import net.mirza.elementalconcept.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileProcessingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileProcessingService.class.getName());

    private final FeatureFlagConfig featureFlagConfig;
    private final ObjectMapper objectMapper;

    public FileProcessingService(FeatureFlagConfig featureFlagConfig, ObjectMapper objectMapper) {
        this.featureFlagConfig = featureFlagConfig;
        this.objectMapper = objectMapper;
    }

    public List<User> processFile(MultipartFile file) throws IOException {
        List<User> users = readUsersFromFile(file);
        File outputFile = new File("OutcomeFile.json");
        exportFile(users, outputFile);

        return users;
    }

    public List<User> readUsersFromFile(MultipartFile file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        List<User> users = new ArrayList<>();

        reader.lines().forEach(line -> {
            String[] fields = line.split("\\|");

            if (featureFlagConfig.isValidationEnabled() && fields.length < 7) {
                LOGGER.warn("Not enough arguments to convert data into user from line: {}", line);
                return;
            }

            User user = new User(fields[2], fields[4], Double.parseDouble(fields[6]));
            users.add(user);
            LOGGER.info("Successfully added user: {}", user);
        });

        return users;
    }

    public void exportFile(List<User> users, File outputFile) throws IOException {
        objectMapper.writeValue(outputFile, users);
    }

}
