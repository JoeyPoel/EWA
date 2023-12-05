package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Task;
import teamx.app.backend.models.dto.TaskDTO;
import teamx.app.backend.repositories.TaskRepository;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ProjectService projectService;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.projectService = projectService;
    }

    public List<Task> findAllToDoTasks(Long userId) {
        return userService.findById(userId).getTodoList();
    }

    public List<Task> findAllInProgressToDoTasks(Long userId) {
        return userService.findById(userId)
                .getTodoList()
                .stream()
                .filter(task -> task.getStatus().equals(Task.Status.IN_PROGRESS))
                .toList();
    }

    public List<Task> findAllDoneToDoTasks(Long userId) {
        return userService.findById(userId)
                .getTodoList()
                .stream()
                .filter(task -> task.getStatus().equals(Task.Status.DONE))
                .toList();
    }

//    public TaskDTO add(TaskDTO taskDTO) {
//        Task task = new Task();
//        task.setName(taskDTO.getName());
//        task.setDescription(taskDTO.getDescription());
//        task.setStatus(Task.Status.valueOf(taskDTO.getStatus()));
//        if (taskDTO.getProjectId() != null) {
//            task.setProject(projectService.findById(taskDTO.getProjectId()));
//        }
//        if (taskDTO.getPersonalTodoListOwnerId() != null) {
//            task.setPersonalTodoListOwner(userService.findById(taskDTO.getPersonalTodoListOwnerId()));
//        }
//        return new taskRepository.save(task);
//    }
}
