package teamx.app.backend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.User;
import teamx.app.backend.utils.DTO.UserDTO;
import teamx.app.backend.services.AuthenthicationService;
import teamx.app.backend.services.EmailService;
import teamx.app.backend.services.ProjectService;
import teamx.app.backend.services.UserService;

import java.time.LocalDate;
import java.util.List;
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
            List<Project> allProjects = projectService.findAll();

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
    public void sendProjectEmail() {
        try{
            emailService.sendProjectEmail();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
