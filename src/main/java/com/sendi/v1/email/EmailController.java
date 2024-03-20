package com.sendi.v1.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestParam(value = "file", required = false) MultipartFile[] file,
                                           String to,
                                           String[] cc,
                                           String subject,
                                           String body) {
        log.info("Email is being sent");

        return ResponseEntity.ok(emailService.sendMail(file, to, cc, subject, body));
    }
}
