package com.example.springboot.email.Controllers;

import com.example.springboot.email.Models.EmailRequest;
import com.example.springboot.email.Models.SenderDetails;
import com.example.springboot.email.Repositories.SenderRepository;
import com.example.springboot.email.Services.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
public class SenderController {

    @Autowired
    private SenderService senderService;

    @Autowired
    private SenderRepository senderRepository;

    @GetMapping("/senders")
    public ResponseEntity<List<SenderDetails>> getAllSenders() {
        List<SenderDetails> senders = senderService.getAllSenders();
        return ResponseEntity.ok(senders);
    }
    //Create or Update
    @PostMapping("/senders")
    public ResponseEntity<SenderDetails> createSender(@RequestBody SenderDetails sender) {
        try {
            SenderDetails updatedSender = senderService.createSender(sender);
            return ResponseEntity.ok(updatedSender);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // You can return a custom error response if needed
        }
    }

    @PatchMapping("/senders")
    public ResponseEntity<SenderDetails> updateSenderFields(@RequestBody Map<String, Object> updates) {
        try {
            SenderDetails updatedSender = senderService.updateSenderFields(1L, updates);
            return ResponseEntity.ok(updatedSender);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // You can return a custom error response if needed
        }
    }


    @DeleteMapping("/senders/{id}")
    public ResponseEntity<String> deleteSender(@PathVariable Long id) {
        try {
            senderService.deleteSender(id);
            return ResponseEntity.ok("Sender details deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete sender details: " + e.getMessage());
        }
    }

}
