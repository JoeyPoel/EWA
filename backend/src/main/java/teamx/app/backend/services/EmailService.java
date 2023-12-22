package teamx.app.backend.services;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.User;
import teamx.app.backend.repositories.CapacityRepository;
import teamx.app.backend.repositories.TransactionRepository;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender emailSender;
    private final ProjectService projectService;
    private final UserService userService;

    public EmailService(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendProjectEmail(){
        java.sql.Date beginningOfTime = java.sql.Date.valueOf("1970-01-01"); // To not miss any projects in the past
        java.sql.Date oneWeekFromNow = java.sql.Date.valueOf(LocalDate.now().plusWeeks(1)); // One week from now in SQL date format

        List<Project> filteredProjects =  projectService.findProjectsByStatusAndDateBetween(
                Project.Status.IN_PROGRESS, null, beginningOfTime, oneWeekFromNow
        );
        // Construct table rows for projects
        List<String> tableRows = filteredProjects.stream()
                .map(project -> String.format(
                        "<td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>",
                        project.getName(), project.getDescription(), project.getLocation(), project.getClientName(),
                        project.getClientEmail(), project.getClientPhone(), project.getStartDate(), project.getEndDate(),
                        project.getStatus()
                ))
                .collect(Collectors.toList());
        // Define column names for the table
        List<String> columnNames = List.of(
                "Project Name", "Description", "Location", "Client Name", "Client Email", "Client Phone", "Start Date", "End Date", "Status"
        );

        List<User> admins = userService.findByRole(User.Role.ADMIN);
        String subject = "Projects that are still pending";

        if (!filteredProjects.isEmpty()) {
            for (User admin : admins) {
                String emailBody = buildEmailBody(admin, "https://placekitten.com/600/200", subject, columnNames, tableRows);
                try {
                    sendEmail("joeywognum@gmail.com", subject, emailBody);
                } catch (Exception e) {
//                    logger.error("Failed to send email to admin: " + admin.getName(), e);
                }
            }
            ResponseEntity.ok("Project reminder emails sent to admins.");
            return;
        }
        ResponseEntity.ok("No projects are still pending in the upcoming week.");
    }


    public String buildEmailBody(User user, String headerImage, String emailSubject, List<String> columnNames, List<String> tableRows) {
        try {
            // Load the template HTML content from the classpath resource
            ClassPathResource resource = new ClassPathResource("/app/backend/EmailTemplate.html");
            byte[] fileData = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String template = new String(fileData);

            // Replace placeholders with actual content
            template = template.replace("HEADER_IMAGE_PLACEHOLDER", headerImage);
            template = template.replace("USER_NAME", user.getName());
            template = template.replace("EMAIL_SUBJECT", emailSubject);

            // Replace table headers placeholder
            StringBuilder headersBuilder = new StringBuilder();
            for (String columnName : columnNames) {
                headersBuilder.append("<th>").append(columnName).append("</th>");
            }
            String tableHeaders = headersBuilder.toString();
            template = template.replace("TABLE_HEADERS_PLACEHOLDER", tableHeaders);

            // Replace table rows placeholder
            StringBuilder rowsBuilder = new StringBuilder();
            for (String row : tableRows) {
                rowsBuilder.append("<tr>").append(row).append("</tr>");
            }
            String tableRowsHtml = rowsBuilder.toString();
            template = template.replace("TABLE_ROWS_PLACEHOLDER", tableRowsHtml);

            return template;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load or process the email template: " + e.getMessage(), e);
        }
    }

    // PRODUCT EMAIL
//    public List<EmailProductDTO> getEmailProducts() {
//        // Fetch transactions and capacities from your data source
//        List<Transaction> transactions = transactionRepository.findAll();
//        List<Capacity> capacities = capacityRepository.findAll();
//
//        // Map transactions and capacities to EmailProductDTO
//        return transactions.stream()
//                .map(transaction -> {
//                    // Find corresponding capacity for the transaction
//                    Capacity capacity = getCapacityForTransaction(transaction, capacities);
//
//                    // Map transaction and capacity to EmailProductDTO
//                    return mapEmailToDTO(transaction, capacity);
//                })
//                .collect(Collectors.toList());
//    }
//
//    private EmailProductDTO mapEmailToDTO(Transaction transaction, Capacity capacity) {
//        EmailProductDTO dto = new EmailProductDTO();
//        dto.setId(transaction.getId());
//        dto.setProductName(transaction.getProduct().getName());
//        dto.setQuantity(transaction.getQuantity());
//
//        // Check if capacity is not null before accessing its properties
//        if (capacity != null) {
//            dto.setMinimumStock(capacity.getMinimumStockLevel());
//        }
//
//        if (transaction.getWarehouse() != null) {
//            dto.setWarehouseName(transaction.getWarehouse().getName());
//        } else {
//            dto.setWarehouseName("Unknown Warehouse");
//        }
//
//        return dto;
//    }
//
//    private Capacity getCapacityForTransaction(Transaction transaction, List<Capacity> capacities) {
//        return capacities.stream()
//                .filter(capacity ->
//                        capacity.getProductCategory().getId().equals(transaction.getProduct().getCategory().getId())
//                                && capacity.getWarehouse() != null
//                                && capacity.getWarehouse().getId().equals(transaction.getWarehouse().getId())
//                                && transaction.getQuantity() < capacity.getMinimumStockLevel()
//                )
//                .findFirst()
//                .orElse(null);
//    }


    // PROJECT EMAIL
//    public List<Project> filterProjects() {
//        try {
////            List<Project> allProjects = projectService.getAllProjects();
//            List<Project> allProjects = null;
//
//            return allProjects.stream()
//                    .filter(project -> {
//                        LocalDate projectDate = project.getStartDate().toLocalDate();
//                        LocalDate dateOneWeekFromNow = LocalDate.now().plusWeeks(1);
//                        return projectDate != null &&
//                                !projectDate.isBefore(dateOneWeekFromNow) &&
//                                project.getStatus() == Project.Status.IN_PROGRESS;
//                    })
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
