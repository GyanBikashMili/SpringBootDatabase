package com.example.springboot.email.Controllers;


import com.example.springboot.email.Models.EmailRequest;
import com.example.springboot.email.Models.SenderDetails;
import com.example.springboot.email.Repositories.SenderRepository;
import com.example.springboot.email.Services.SenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ReceiverController {

    @Autowired
    private SenderService senderService;

    @Autowired
    private SenderRepository senderRepository;

    // Send Email
    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        List<SenderDetails> senderDetailsList = senderRepository.findAll();

        if (senderDetailsList.isEmpty()) {
            throw new RuntimeException("Sender details not found in the database.");
        }

        try {
            senderService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
            log.info("Email sent successfully!");
            return ResponseEntity.ok("Email sent successfully!");
        } catch (Exception e) {
            log.error("Failed to send email: {}", e.getMessage());
            log.debug("Debugging information for the failed email sending", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email: " + e.getMessage());
        }
    }
}
