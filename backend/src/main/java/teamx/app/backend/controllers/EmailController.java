package teamx.app.backend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.User;
import teamx.app.backend.models.dto.InventoryProjectDTO;
import teamx.app.backend.models.dto.MailRequest;
import teamx.app.backend.utils.DTO.UserDTO;
import teamx.app.backend.services.AuthenthicationService;
import teamx.app.backend.services.EmailService;
import teamx.app.backend.services.ProjectService;
import teamx.app.backend.services.UserService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for email
 *
 * @author Joey van der Poel
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/mail")
public class EmailController {

    //    TODO Emails over: Status project, Wachtwoord vergeten, Product negatief stock
    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
    private final EmailService emailService;
    private final ProjectService projectService;
    private final UserService userService;
    private final AuthenthicationService authenthicationService;


    public EmailController(EmailService emailService, ProjectService projectService,
                           UserService userService, AuthenthicationService authenthicationService) {
        this.emailService = emailService;
        this.projectService = projectService;
        this.userService = userService;
        this.authenthicationService = authenthicationService;
    }

    @PostMapping("/sendPassResetEmail")
    public ResponseEntity<String> sendPassResetEmail(@RequestBody UserDTO user) {
        UserDTO foundUser = this.authenthicationService.generateResetPassToken(user.getEmail());
        if (foundUser != null) {
            String passwordResetLink = "http://localhost:8080/#/pass-reset:" + foundUser.getJwtToken();

            String subject = "Password reset request";
            String content = "Please click this link underneath to reset your password for the solar sedum website";

            Map<String, Object> model = new HashMap<>();
            model.put("content", content);
            model.put("passwordResetLink", passwordResetLink);
            model.put("name", user.getName());
            model.put("dynamicImageUrl", "https://i.imgur.com/nvh7yZ4.png");

            MailRequest request = new MailRequest();
//            request.setTo("Joeywognum@gmail.com"); // FOR TESTING
            request.setTo(user.getEmail());
            request.setName(user.getName());
            request.setSubject(subject);

            try {
                emailService.sendEmail(request, model);
            } catch (Exception e) {
                // Log the exception for further analysis or debugging
                logger.error("An error occurred while sending an email: {}", e.getMessage());
            }
        }
        return ResponseEntity.ok("Password Reset Email sent.");
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @PostMapping("/sendProjectEmail")
    public ResponseEntity<String> sendProjectEmail() {
        java.sql.Date beginningOfTime = java.sql.Date.valueOf("1970-01-01");
        java.sql.Date oneWeekFromNow = java.sql.Date.valueOf(LocalDate.now().plusWeeks(1));

        List<Project> filteredProjects = projectService.findProjectsByStatusAndDateBetween(
                Project.Status.IN_PROGRESS, null, beginningOfTime, oneWeekFromNow
        );
        filteredProjects.sort(Comparator.comparing(Project::getStartDate));

        if (filteredProjects.isEmpty()) {
            return ResponseEntity.ok("No projects are still in progress.");
        }

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
            model.put("tableRows", tableRows);
            model.put("columnNames", columnNames);
            model.put("content", content);
            model.put("name", admin.getName());
            model.put("dynamicImageUrl", "https://i.imgur.com/nvh7yZ4.png");

            MailRequest request = new MailRequest();
            request.setTo("Joeywognum@gmail.com"); // FOR TESTING
//        request.setTo(admin.getEmail());
            request.setName(admin.getName());
            request.setSubject(subject);

            try {
                emailService.sendEmail(request, model);
            } catch (Exception e) {
                // Log the exception for further analysis or debugging
                logger.error("An error occurred while sending an email: {}", e.getMessage());
            }
        }
        return ResponseEntity.ok("Project reminder emails sent to admins.");
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @PostMapping("/sendProductEmail")
    public ResponseEntity<String> sendProductEmail() {
        // TODO Get all products that need care
        //TESTING
        // Create a sample product using a constructor
        InventoryProjectDTO productThatNeedsCare = new InventoryProjectDTO(
                123L, 5, "Sample Product", "Warehouse A", "Inbound", new Date()
        );
        // Add the sample product to the list of filtered products
        List<InventoryProjectDTO> filteredProducts = new ArrayList<>();
        filteredProducts.add(productThatNeedsCare);

        if (filteredProducts.isEmpty()) {
            return ResponseEntity.ok("No products need care.");
        }

        List<String> tableRows = filteredProducts.stream()
                .map(product -> String.join(",",
                        product.getWarehouseName(), String.valueOf(product.getId()), product.getProductName(), String.valueOf(product.getQuantity())
                ))
                .collect(Collectors.toList());



        List<String> columnNames = List.of(
                "Warehouse", "Id", "Product", "Quantity"
        );

        List<User> admins = userService.findByRole(User.Role.ADMIN);
        String subject = "Some stock is critically low";
        String content = "We wanted to update you on stock that is critically low, the following products need care:";

        for (User admin : admins) {
            Map<String, Object> model = new HashMap<>();
            model.put("tableRows", tableRows);
            model.put("columnNames", columnNames);
            model.put("content", content);
            model.put("name", admin.getName());
            model.put("dynamicImageUrl", "https://i.imgur.com/nvh7yZ4.png");

            MailRequest request = new MailRequest();
            request.setTo("Joeywognum@gmail.com"); // FOR TESTING
//        request.setTo(admin.getEmail());
            request.setName(admin.getName());
            request.setSubject(subject);

            try {
                emailService.sendEmail(request, model);
            } catch (Exception e) {
                // Log the exception for further analysis or debugging
                logger.error("An error occurred while sending an email: {}", e.getMessage());
            }
        }
        return ResponseEntity.ok("Product reminder emails sent to admins.");
    }
}
