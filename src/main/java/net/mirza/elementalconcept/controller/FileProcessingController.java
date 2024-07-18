package net.mirza.elementalconcept.controller;

import lombok.RequiredArgsConstructor;
import net.mirza.elementalconcept.model.User;
import net.mirza.elementalconcept.service.FileProcessingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileProcessingController {

    private final FileProcessingService fileProcessingService;

    @PostMapping("/process")
    public List<User> processFile(@RequestBody MultipartFile file) throws IOException {
        return fileProcessingService.processFile(file);
    }

}
