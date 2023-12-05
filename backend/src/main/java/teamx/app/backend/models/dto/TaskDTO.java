package teamx.app.backend.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamx.app.backend.models.Task;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private int order;
    private String name;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String deadline;
    private String status;
    private Long projectId;
    private Long personalTodoListOwnerId;

    public TaskDTO(Task task){
        this.id = task.getId();
        this.order = task.getOrder();
        this.name = task.getName();
        this.description = task.getDescription();
        this.deadline = task.getDeadline().toString();
        this.status = String.valueOf(task.getStatus());
        this.projectId = task.getProject().getId();
        this.personalTodoListOwnerId = task.getPersonalTodoListOwner().getId();
    }
}
