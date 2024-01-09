package teamx.app.backend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.User;
import teamx.app.backend.services.*;
import teamx.app.backend.utils.DTO;
import teamx.app.backend.utils.DTO.UserDTO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for managing email functionalities.
 * Handles endpoints related to sending emails for password reset, project updates, and product alerts.
 *
 * @author Joey van der Poel
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/mail")
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
    private final EmailService emailService;
    private final UserService userService;
    private final AuthenthicationService authenthicationService;
    private final WarehouseService warehouseService;
    @Autowired
    public EmailController(EmailService emailService, UserService userService, AuthenthicationService authenthicationService, WarehouseService warehouseService) {
        this.emailService = emailService;
        this.userService = userService;
        this.authenthicationService = authenthicationService;
        this.warehouseService = warehouseService;
    }

    /**
     * Endpoint for sending a password reset email.
     * @param user UserDTO object containing user email.
     * @return ResponseEntity indicating the success or failure of sending the email.
     */
    @PostMapping("/sendPassResetEmail")
    public ResponseEntity<String> sendPassResetEmail(@RequestBody UserDTO user) {
        UserDTO foundUser = this.authenthicationService.generateResetPassToken(user.getEmail());
        if (foundUser != null) {
            String passwordResetLink = "http://localhost:8080/#/pass-reset:" + foundUser.getJwtToken();
            String subject = "Password reset request";
            String content = "Please click this link underneath to reset your password for the solar sedum website";

            Map<String, Object> model = new HashMap<>();
            model.putAll(Map.of(
                    "content", content,
                    "passwordResetLink", passwordResetLink,
                    "name", user.getEmail().split("@")[0], // Set name as everything before @ on the email address
                    "dynamicImageUrl", "https://i.imgur.com/nvh7yZ4.png"
            ));

            DTO.MailRequest request = new DTO.MailRequest();
            request.setTo(user.getEmail());
            request.setName(user.getEmail().split("@")[0]); // Set name as everything before @ on the email address
            request.setSubject(subject);

            String templateFileName = "email-template-password-reset.ftl";
            try {
                emailService.sendEmail(request, model, templateFileName);
            } catch (Exception e) {
                // Log the exception for further analysis or debugging
                logger.error("An error occurred while sending an email: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
            }
            return ResponseEntity.ok("Password Reset Email sent.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Found user was null!");
    }

    /**
     * Endpoint for sending a password reset email.
     * @param user UserDTO object containing user email.
     * @return ResponseEntity indicating the success or failure of sending the email.
     */
    @PostMapping("/sendPassGenEmail")
    public ResponseEntity<String> sendPassGenEmail(@RequestBody UserDTO user, @RequestParam String generatedPassword) {
        UserDTO foundUser = this.authenthicationService.generateResetPassToken(user.getEmail());
        if (foundUser != null) {
            String passwordResetLink = "http://localhost:8080/#/pass-reset:" + foundUser.getJwtToken();
            String subject = "Password generation request";
            String content = "Your new password is: " + generatedPassword + ". Please click the link below to create your own personal password for the solar sedum website";

            Map<String, Object> model = new HashMap<>();
            model.putAll(Map.of(
                    "content", content,
                    "passwordResetLink", passwordResetLink,
                    "name", user.getEmail().split("@")[0], // Set name as everything before @ on the email address
                    "dynamicImageUrl", "https://i.imgur.com/nvh7yZ4.png"
            ));

            DTO.MailRequest request = new DTO.MailRequest();
            request.setTo(user.getEmail());
            request.setName(user.getEmail().split("@")[0]); // Set name as everything before @ on the email address
            request.setSubject(subject);

            String templateFileName = "email-template-password-reset.ftl";
            try {
                emailService.sendEmail(request, model, templateFileName);
            } catch (Exception e) {
                // Log the exception for further analysis or debugging
                logger.error("An error occurred while sending an email: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
            }
            return ResponseEntity.ok("Password Reset Email sent.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Found user was null!");
    }

    /**
     * Scheduled task to send project update emails daily at 12 PM.
     * Fetches ongoing projects and sends an update to the administrators.
     * @return ResponseEntity indicating the success or failure of sending the email.
     */
    @Scheduled(cron = "0 0 12 * * ?")
    @PostMapping("/sendProjectEmail")
    public ResponseEntity<String> sendProjectEmail() {
        List<Project> filteredProjects = emailService.findAllProjectsThatAreStillInProgress();

        if (filteredProjects.isEmpty()) return ResponseEntity.ok("No projects are still in progress.");

        List<String> tableRows = filteredProjects.stream()
                .map(project -> String.join(",",
                        project.getName(), project.getDescription(), project.getLocation(), project.getClientName(),
                        project.getClientEmail(), project.getClientPhone(), project.getStartDate().toString(), project.getEndDate().toString(),
                        project.getStatus().toString()
                ))
                .collect(Collectors.toList());

        List<String> columnNames = List.of(
            "Project Name", "Description", "Location", "Client Name", "Client Email", "Client Phone", "Start Date", "End Date", "Status"
        );

        List<User> admins = userService.findByRole(User.Role.ADMIN);
        String subject = "Some projects are still in progress";
        String content = "We wanted to update you on the progress of our ongoing projects. The following details highlight the current status:";

        for (User admin : admins) {
            Map<String, Object> model = new HashMap<>();
            model.putAll(Map.of(
                    "tableRows", tableRows,
                    "columnNames", columnNames,
                    "content", content,
                    "name", admin.getName(),
                    "dynamicImageUrl", "https://i.imgur.com/nvh7yZ4.png"
            ));

            DTO.MailRequest request = new DTO.MailRequest();
            request.setTo(admin.getEmail());
            request.setName(admin.getName());
            request.setSubject(subject);

            String templateFileName = "email-template-with-table.ftl";
            try {
                emailService.sendEmail(request, model, templateFileName);
            } catch (Exception e) {
                // Log the exception for further analysis or debugging
                logger.error("Failed to send email to admin: {}. Error: {}", admin.getEmail(), e.getMessage());
            }
        }
        return ResponseEntity.ok("Project reminder emails sent to admins.");
    }

    /**
     * Scheduled task to send product alert emails daily at 12 PM.
     * Fetches products that need care and sends an alert to the administrators.
     * @return ResponseEntity indicating the success or failure of sending the email.
     */
    @Scheduled(cron = "0 0 12 * * ?")
    @PostMapping("/sendProductEmail")
    public ResponseEntity<String> sendProductEmail() {
        List<DTO.InventoryProductDTO> filteredProducts = emailService.findAllProductsThatNeedCare();

        if (filteredProducts.isEmpty()) return ResponseEntity.ok("No products need care.");

        List<String> tableRows = filteredProducts.stream()
                .map(product -> String.join(",",
                        warehouseService.findById(product.getWarehouseId()).getName(),
                        String.valueOf(product.getProductId()), product.getName(),
                        String.valueOf(product.getQuantity()), String.valueOf(product.getPrice())
                ))
                .collect(Collectors.toList());

        List<String> columnNames = List.of(
                "Warehouse", "Id", "Product", "Quantity", "Price"
        );

        List<User> admins = userService.findByRole(User.Role.ADMIN);
        String subject = "Some stock is critically low";
        String content = "We wanted to update you on stock that is critically low, the following products need care:";

        for (User admin : admins) {
            Map<String, Object> model = new HashMap<>();
            model.putAll(Map.of(
                    "tableRows", tableRows,
                    "columnNames", columnNames,
                    "content", content,
                    "name", admin.getName(),
                    "dynamicImageUrl", "https://i.imgur.com/nvh7yZ4.png"
            ));

            DTO.MailRequest request = new DTO.MailRequest();
            request.setTo(admin.getEmail());
            request.setName(admin.getName());
            request.setSubject(subject);

            String templateFileName = "email-template-with-table.ftl";
            try {
                emailService.sendEmail(request, model, templateFileName);
            } catch (Exception e) {
                // Log the exception for further analysis or debugging
                logger.error("Failed to send email to admin: {}. Error: {}", admin.getEmail(), e.getMessage());
            }
        }
        return ResponseEntity.ok("Product reminder emails sent to admins.");
    }
}