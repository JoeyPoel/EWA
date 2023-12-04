package teamx.app.backend.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.User;
import teamx.app.backend.models.dto.UserDTO;
import teamx.app.backend.services.EmailService;
import teamx.app.backend.services.ProjectService;
import teamx.app.backend.services.UserService;
import teamx.app.backend.services.AuthenthicationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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


    public EmailController(EmailService emailService, ProjectService projectService, UserService userService,
                           AuthenthicationService authenthicationService) {
        this.emailService = emailService;
        this.projectService = projectService;
        this.userService = userService;
        this.authenthicationService = authenthicationService;
    }

    @GetMapping("/filteredProjects")
    public List<Project> filterProjects() {
        try {
            List<Project> allProjects = projectService.getAllProjects();

            return allProjects.stream()
                    .filter(project -> {
                        LocalDate projectDate = project.getStartDate().toLocalDate();
                        LocalDate dateOneWeekFromNow = LocalDate.now().plusWeeks(1);
                        return projectDate != null &&
                                !projectDate.isBefore(dateOneWeekFromNow) &&
                                project.getStatus() == Project.Status.IN_PROGRESS;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/sendPassResetEmail")
    public void sendPassResetEmail(@RequestBody UserDTO user) {
        UserDTO foundUser = this.authenthicationService.generateResetPassToken(user.getEmail());
        if (foundUser != null) {
            String link = "http://localhost:8080/#/pass-reset:" + foundUser.getJwtToken();

            String subject = "Password reset request";
            String content = "Please click this link underneath to reset your password for the solar sedum website";

            // TODO change to Dear username
            StringBuilder emailBody = new StringBuilder();
            emailBody.append("<h2 style=\"margin-bottom: 15px;\">Dear ").append("user").append(",</h2>");
            emailBody.append("<h3 style=\"margin-bottom: 20px;\">").append(content).append("</h3>");
            emailBody.append("<h4 style=\"margin-bottom: 20px;\">").append(link).append("</h4>");

            try {
                emailService.sendEmail(user.getEmail(), subject, emailBody.toString());
            } catch (Exception e) {
                logger.error("Failed to send email to user", e);
            }
        } else {
            // TODO make a better error return
            logger.error("Failed to send email to user");
        }
    }

    @PostMapping("/sendProjectEmail")
    @Scheduled(cron = "0 0 12 * * ?")
    public void sendProjectEmail() {
        List<User> admins = userService.findByRole(User.Role.ADMIN);
        String subject = "Projects that are still pending";

        // Define column names for the table
        List<String> columnNames = List.of(
                "Project Name", "Description", "Location", "Client Name", "Client Email", "Client Phone", "Start Date", "End Date", "Status"
        );

        // Construct table rows for projects
        List<String> tableRows = filterProjects().stream()
                .map(project -> String.format(
                        "<td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>",
                        project.getName(), project.getDescription(), project.getLocation(), project.getClientName(),
                        project.getClientEmail(), project.getClientPhone(), project.getStartDate(), project.getEndDate(),
                        project.getStatus()
                ))
                .collect(Collectors.toList());

        if (!filterProjects().isEmpty()) {
            for (User admin : admins) {
                String emailBody = generateEmailWithTable(admin.getName(), "https://placekitten.com/600/200", subject, columnNames , tableRows);
                try {
                    emailService.sendEmail("joeywognum@gmail.com", subject, emailBody);
                } catch (Exception e) {
                    logger.error("Failed to send email to admin: " + admin.getName(), e);
                }
            }
            ResponseEntity.ok("Project reminder emails sent to admins.");
            return;
        }
        ResponseEntity.ok("No projects are still pending in the upcoming week.");
    }

    @GetMapping("/productsThatNeedCare")
    public List<Product> getProductsThatNeedCare() {
//        // TODO GET ALL PRODUCTS THAT NEED CARE
//        try {
//            // TODO INITIALIZE LIST OF PRODUCTS
//            List<Product> allProducts = null;
//            // TODO FILTER PRODUCTS
//            return allProducts.stream()
//                    .filter(product -> {
//                        product.get
//                    })
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }
    @PostMapping("/sendProductEmail")
    @Scheduled(cron = "0 0 12 * * ?")
    public void sendProductEmail() {
        List<User> admins = userService.findByRole(User.Role.ADMIN);
        String subject = "Products that need care";

        // Define column names for the table
        List<String> columnNames = List.of(
                "Product Name", "Current stock level", "Minimum stock level", "Warehouse Name");

        // Construct table rows for projects
        List<String> tableRows = getProductsThatNeedCare().stream()
                .map(product -> String.format(
                        "<td>%s</td><td>%s</td><td>%s</td><td>%s</td>",
                        product.getName()
                ))
                .collect(Collectors.toList());

        if (!filterProjects().isEmpty()) {
            for (User admin : admins) {
                String emailBody = generateEmailWithTable(admin.getName(), "https://placekitten.com/600/200", subject, columnNames , tableRows);
                try {
                    emailService.sendEmail("joeywognum@gmail.com", subject, emailBody);
                } catch (Exception e) {
                    logger.error("Failed to send email to admin: " + admin.getName(), e);
                }
            }
            ResponseEntity.ok("Product care emails sent to admins.");
            return;
        }
        ResponseEntity.ok("No products need care.");
    }


    private String generateEmailWithTable(String recipientName, String headerImage, String emailSubject, List<String> columnNames, List<String> tableRows) {
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<html><head><style>");

        // Add CSS styling
        emailBody.append("body { font-family: Arial, sans-serif; }");
        emailBody.append("h2, h4 { color: #333; }");
        emailBody.append("table { border-collapse: collapse; width: 100%; }");
        emailBody.append("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        emailBody.append("th { background-color: #f2f2f2; }");
        emailBody.append("tr:nth-child(even) { background-color: #f9f9f9; }");

        emailBody.append("</style></head><body>");
        emailBody.append("<div class=\"container\">");
        emailBody.append("<div style=\"text-align: center;\">");
        emailBody.append("<img src=\"").append(headerImage).append("\" alt=\"Header Image\" style=\"max-width: 100%; height: auto; margin-bottom: 20px;\">");
        emailBody.append("</div>");
        emailBody.append("<h2 style=\"margin-bottom: 15px;\">Dear ").append(recipientName).append(",</h2>");
        emailBody.append("<h4 style=\"margin-bottom: 20px;\">").append(emailSubject).append("</h4>");

        // Construct table for project details in HTML
        emailBody.append("<table>");
        emailBody.append("<thead><tr>");
        for (String columnName : columnNames) {
            emailBody.append("<th>").append(columnName).append("</th>");
        }
        emailBody.append("</tr></thead><tbody>");
        for (String row : tableRows) {
            emailBody.append("<tr>").append(row).append("</tr>");
        }
        emailBody.append("</tbody></table>");

        emailBody.append("<h4>Thank you.</h4>");
        emailBody.append("</div></body></html>");

        return emailBody.toString();
    }

}
