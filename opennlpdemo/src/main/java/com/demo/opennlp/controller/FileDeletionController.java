package com.demo.opennlp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileDeletionController {

    // Endpoint to delete the file by name
    @DeleteMapping("/delete-model/{fileName}")
    public ResponseEntity<String> deleteModel(@PathVariable String fileName) {
        // Path to the file to be deleted
        String filePath = Paths.get("src", "main", "resources", "trainmodel", fileName).toString();
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            return ResponseEntity.status(404).body("File not found: " + filePath);
        }
        // Attempt to delete the file
        boolean deleted = file.delete();

        // Return appropriate response based on deletion result
        if (deleted) {
            return ResponseEntity.ok("File successfully deleted: " + filePath);
        } else {
            return ResponseEntity.status(500).body("Failed to delete the file: " + filePath);
        }
    }

    @DeleteMapping("/delete-file/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        // Path to the file to be deleted
        String filePath = Paths.get("src", "main", "resources", "filename", fileName).toString();
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            return ResponseEntity.status(404).body("File not found: " + filePath);
        }
        // Attempt to delete the file
        boolean deleted = file.delete();

        // Return appropriate response based on deletion result
        if (deleted) {
            return ResponseEntity.ok("File successfully deleted: " + filePath);
        } else {
            return ResponseEntity.status(500).body("Failed to delete the file: " + filePath);
        }
    }
}