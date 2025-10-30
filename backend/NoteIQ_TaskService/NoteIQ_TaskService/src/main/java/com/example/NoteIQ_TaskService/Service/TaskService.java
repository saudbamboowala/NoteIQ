package com.example.NoteIQ_TaskService.Service;

import com.example.NoteIQ_TaskService.Domain.Task;
import com.example.NoteIQ_TaskService.Exception.TaskNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    Task saveTask(Task task);
    List<Task> fetchByCategory(String tCategory);
    List<Task> fetchByStatus(String tStatus);
    List<Task> fetchByDueDate(LocalDate tDueDate);
    Task archiveTask(String taskId) throws TaskNotFoundException;  // Changed from int to String
    Task updateTask(Task updatedTask) throws TaskNotFoundException;
    List<Task> fetchArchivedTasks(String userId);
    List<Task> fetchTaskByUserId(String userId) throws TaskNotFoundException;
    void deleteTask(String taskId) throws TaskNotFoundException;  // Changed from int to String
    Task getTaskById(String taskId) throws TaskNotFoundException;
}
