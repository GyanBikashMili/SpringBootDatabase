package com.example.springboot.email.Services;

import com.example.springboot.email.Models.SenderDetails;
import com.example.springboot.email.Repositories.SenderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SenderService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SenderRepository senderRepository;

//    @Value("${keystore.path}")
//    private String keystorePath;
//
//    @Value("${keystore.password}")
//    private String keystorePassword;

    //Get Mapping
    public List<SenderDetails> getAllSenders() {
        return senderRepository.findAll();
    }


    //Create or Update database
    public SenderDetails createSender(SenderDetails sender) {


        // Check if a sender record with id 1 exists
        SenderDetails existingSender = senderRepository.findById(1L).orElse(null);

        if (existingSender != null) {
            existingSender.setHost(sender.getHost());
            existingSender.setPort(sender.getPort());
            existingSender.setUsername(sender.getUsername());
            existingSender.setPassword(sender.getPassword());

            // Save the updated sender details
            return senderRepository.save(existingSender);
        } else {
            // Save the new sender details with id 1
            sender.setId(1L);
            return senderRepository.save(sender);
        }
    }


    //Sent Email
    public void sendEmail(String to, String subject, String body) {
        List<SenderDetails> senderDetailsList = senderRepository.findAll();

        if (senderDetailsList.isEmpty()) {
            throw new RuntimeException("Sender details not found in the database.");
        }

        SenderDetails senderDetails = senderDetailsList.get(0);

        //update properties
        JavaMailSenderImpl dynamicMailSender = new JavaMailSenderImpl();
        dynamicMailSender.setJavaMailProperties(((JavaMailSenderImpl) mailSender).getJavaMailProperties());
        dynamicMailSender.setHost(senderDetails.getHost());
        dynamicMailSender.setPort(senderDetails.getPort());
        dynamicMailSender.setUsername(senderDetails.getUsername());
        dynamicMailSender.setPassword(senderDetails.getPassword());
        mailSender = dynamicMailSender;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderDetails.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        System.out.println("Email is sent....");
    }
   //Update
   public SenderDetails updateSenderFields(Long id, Map<String, Object> updates) {
       SenderDetails sender = senderRepository.findById(id).orElse(null);

       if (sender == null) {
           throw new RuntimeException("Sender details not found in the database.");
       }

       // Apply the updates to the sender object
       if (updates.containsKey("host")) {
           sender.setHost((String) updates.get("host"));
       }
       if (updates.containsKey("port")) {
           sender.setPort((int) updates.get("port"));
       }
       if (updates.containsKey("username")) {
           sender.setUsername((String) updates.get("username"));
       }
       if (updates.containsKey("password")) {
           sender.setPassword((String) updates.get("password"));
       }

       // Save the updated sender details
       senderRepository.save(sender);
       return sender;
   }

    public void deleteSender(Long id) {
        senderRepository.deleteById(id);
    }
}
