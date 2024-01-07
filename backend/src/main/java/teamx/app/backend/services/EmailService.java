package teamx.app.backend.services;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import teamx.app.backend.models.Project;
import teamx.app.backend.utils.DTO;

/**
 * Service handling email operations such as sending emails and retrieving data for email notifications.
 * Manages email sending functionality and data retrieval for product and project alerts.
 * @author Joey van der Poel
 */
@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private Configuration config;

    private final WarehouseService warehouseService;
    private final ProductService productService;
    private final TransactionService transactionService;
    private final InventoryService inventoryService;
    private final ProjectService projectService;
    private final CapacityService capacityService;
    public EmailService(WarehouseService warehouseService, ProductService productService, TransactionService transactionService, InventoryService inventoryService, ProjectService projectService, CapacityService capacityService) {
        this.warehouseService = warehouseService;
        this.productService = productService;
        this.transactionService = transactionService;
        this.inventoryService = inventoryService;
        this.projectService = projectService;
        this.capacityService = capacityService;
    }

    /**
     * Sends an email using the provided details.
     * @param request DTO.MailRequest object containing email details.
     * @param model Map<String, Object> containing data for the email template.
     * @param templateFileName String specifying the name of the email template.
     * @return DTO.MailResponse indicating the status of the email sending process.
     */
    public DTO.MailResponse sendEmail(DTO.MailRequest request, Map<String, Object> model, String templateFileName) {
        DTO.MailResponse response = new DTO.MailResponse();
        MimeMessage message = emailSender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template t = config.getTemplate(templateFileName);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(request.getTo());
            helper.setText(html, true);
            helper.setSubject(request.getSubject());
            helper.setFrom(fromEmail);
            emailSender.send(message);

            response.setMessage("mail send to : " + request.getTo());
            response.setStatus(Boolean.TRUE);

        } catch (MessagingException | IOException | TemplateException e) {
            response.setMessage("Mail Sending failure : "+ e.getMessage());
            response.setStatus(Boolean.FALSE);
        }

        return response;
    }

    /**
     * Retrieves a list of inventory products that require attention due to critically low stock levels.
     * @return List of DTO.InventoryProductDTO containing products needing attention.
     */
    public List<DTO.InventoryProductDTO> findAllProductsThatNeedCare() {
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
                .filter(inventoryProductDTO -> {
                    // Find the corresponding CapacityDTO for the current product and warehouse
                    List<DTO.CapacityDTO> capacities = capacityService.getAllCapacities();
                    DTO.CapacityDTO capacity = capacities.stream()
                            .filter(c -> c.getCategoryId().equals(productService.findById(inventoryProductDTO.getProductId()).getCategory().getId())
                                    && c.getWarehouseId().equals(inventoryProductDTO.getWarehouseId()))
                            .findFirst().orElse(null);

                    // Check if capacity found and compare the quantity with the minimum stock level
                    return capacity != null && inventoryProductDTO.getQuantity() <= capacity.getMinimumStockLevel();
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of projects that are still in progress and due within the next week.
     * @return List of Project containing ongoing projects due within the next week.
     */
    public List<Project> findAllProjectsThatAreStillInProgress() {
        java.sql.Date beginningOfTime = java.sql.Date.valueOf("1970-01-01");
        java.sql.Date oneWeekFromNow = java.sql.Date.valueOf(LocalDate.now().plusWeeks(1));

        List<Project> filteredProjects = projectService.findProjectsByStatusAndDateBetween(
                Project.Status.IN_PROGRESS, null, beginningOfTime, oneWeekFromNow
        );
        filteredProjects.sort(Comparator.comparing(Project::getStartDate));

        return filteredProjects; // Return the filtered list
    }
}

