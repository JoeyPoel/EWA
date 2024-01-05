package teamx.app.backend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.User;
import teamx.app.backend.services.*;
import teamx.app.backend.utils.DTO;
import teamx.app.backend.utils.DTO.UserDTO;

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
    private final WarehouseService warehouseService;
    private final TransactionService transactionService;
    private final InventoryService inventoryService;

    @Autowired
    public EmailController(EmailService emailService, ProjectService projectService,
                           UserService userService, AuthenthicationService authenthicationService, WarehouseService warehouseService, TransactionService transactionService, InventoryService inventoryService) {
        this.emailService = emailService;
        this.projectService = projectService;
        this.userService = userService;
        this.authenthicationService = authenthicationService;
        this.warehouseService = warehouseService;
        this.transactionService = transactionService;
        this.inventoryService = inventoryService;

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
            model.put("name", user.getEmail().split("@")[0]); // Set name as everything before @ on the email address
            model.put("dynamicImageUrl", "https://i.imgur.com/nvh7yZ4.png");

            DTO.MailRequest request = new DTO.MailRequest();
//            request.setTo("Joeywognum@gmail.com"); // FOR TESTING
            request.setTo(user.getEmail());
            request.setName(user.getEmail().split("@")[0]); // Set name as everything before @ on the email address
            request.setSubject(subject);

            String templateFileName = "email-template-password-reset.ftl";
            try {
                emailService.sendEmail(request, model, templateFileName);
            } catch (Exception e) {
                // Log the exception for further analysis or debugging
                logger.error("An error occurred while sending an email: {}", e.getMessage());
            }
            return ResponseEntity.ok("Password Reset Email sent.");
        }
        return ResponseEntity.ok("Found user was null!");
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @PostMapping("/sendProjectEmail")
    public ResponseEntity<String> sendProjectEmail() {
        List<Project> filteredProjects = findAllProjectsThatAreStillInProgress();

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
            model.put("tableRows", tableRows);
            model.put("columnNames", columnNames);
            model.put("content", content);
            model.put("name", admin.getName());
            model.put("dynamicImageUrl", "https://i.imgur.com/nvh7yZ4.png");

            DTO.MailRequest request = new DTO.MailRequest();
            request.setTo("Joeywognum@gmail.com"); // FOR TESTING
    //        request.setTo(admin.getEmail());
            request.setName(admin.getName());
            request.setSubject(subject);

            String templateFileName = "email-template-with-table.ftl";
            try {
                emailService.sendEmail(request, model, templateFileName);
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
        List<DTO.InventoryProductDTO> filteredProducts = findAllProductsThatNeedCare();

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
            model.put("tableRows", tableRows);
            model.put("columnNames", columnNames);
            model.put("content", content);
            model.put("name", admin.getName());
            model.put("dynamicImageUrl", "https://i.imgur.com/nvh7yZ4.png");

            DTO.MailRequest request = new DTO.MailRequest();
            request.setTo("Joeywognum@gmail.com"); // FOR TESTING
//        request.setTo(admin.getEmail());
            request.setName(admin.getName());
            request.setSubject(subject);

            String templateFileName = "email-template-with-table.ftl";
            try {
                emailService.sendEmail(request, model, templateFileName);
            } catch (Exception e) {
                // Log the exception for further analysis or debugging
                logger.error("An error occurred while sending an email: {}", e.getMessage());
            }
        }
        return ResponseEntity.ok("Product reminder emails sent to admins.");
    }

    private List<DTO.InventoryProductDTO> findAllProductsThatNeedCare() {
        List<DTO.InventoryProductDTO> allInventoryProducts = new ArrayList<>();
        List<Long> warehouseIds = warehouseService.findAllIds();

        for (Long warehouseId : warehouseIds) {
            List<DTO.InventoryProductDTO> inventoryProductDTOSPerWarehouse = inventoryService.getByWarehouseId(warehouseId);

            // Update the quantity in each InventoryProductDTO using findProductCurrentStock
            inventoryProductDTOSPerWarehouse
                    .forEach(inventoryProduct->
                            inventoryProduct.setQuantity(
                                    transactionService.findProductCurrentStock(
                                            inventoryProduct.getWarehouseId(), inventoryProduct.getProductId())));

            allInventoryProducts.addAll(inventoryProductDTOSPerWarehouse);
        }

        // Filter products based on current stock level less than minimum stock level
        return allInventoryProducts.stream()
                .filter(inventoryProductDTO -> inventoryProductDTO.getQuantity() <= 10) // TODO REPLACE WITH ACUTAL MINIMUM STOCK LEVEL
                .collect(Collectors.toList());
    }


    private List<Project> findAllProjectsThatAreStillInProgress() {
        java.sql.Date beginningOfTime = java.sql.Date.valueOf("1970-01-01");
        java.sql.Date oneWeekFromNow = java.sql.Date.valueOf(LocalDate.now().plusWeeks(1));

        List<Project> filteredProjects = projectService.findProjectsByStatusAndDateBetween(
                Project.Status.IN_PROGRESS, null, beginningOfTime, oneWeekFromNow
        );
        filteredProjects.sort(Comparator.comparing(Project::getStartDate));

        return filteredProjects; // Return the filtered list
    }
}