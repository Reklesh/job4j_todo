package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.task.TaskService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public String getAll(@RequestParam(name = "filter", defaultValue = "all") String filter, Model model) {
        try {
            model.addAttribute("tasks", taskService.findAll(filter));
            return "tasks/list";
        } catch (IllegalStateException e) {
            log.error("Ошибка при фильтрации задач: {}", filter, e);
            model.addAttribute(
                    "message", "Не удалось загрузить список задач. Попробуйте позже.");
            return "errors/404";
        }
    }

    @GetMapping("/create")
    public String getCreationPage() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task) {
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping({"/{id}", "/{id}/update"})
    public String getById(@PathVariable int id, Model model, HttpServletRequest request) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return request.getRequestURI().contains("/update") ? "tasks/edit" : "tasks/details";
    }

    @PostMapping("/{id}/complete")
    public String executeTask(@PathVariable int id, @RequestParam boolean currentStatus, Model model) {
        boolean newStatus = !currentStatus;
        var isTaskCompleted = taskService.updateDone(id, newStatus);
        if (!isTaskCompleted) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/tasks/" + id;
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message",
                    "Задача с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/tasks";
    }
}
