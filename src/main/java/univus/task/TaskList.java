package univus.task;

import java.util.ArrayList;

import univus.exceptions.DuplicatesException;
/**
 * Represents a list of tasks in the Univus application.
 */
public class TaskList {
    private ArrayList<Task> taskList;

    /**
     * Constructs a new instance of the TaskList class with an empty task list.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added to the list.
     */
    public void add(Task task) throws DuplicatesException {
        for (Task existingTask : taskList) {
            if (existingTask.getDescription().equals(task.getDescription())) {
                throw new DuplicatesException("OOPS!!! Task with the same description already exists.");
            }
        }
        this.taskList.add(task);
    }

    /**
     * Removes and returns the task at the specified index from the task list.
     *
     * @param index The index of the task to be removed.
     * @return The task that was removed from the list.
     */
    public Task remove(int index) {
        return this.taskList.remove(index - 1);
    }

    /**
     * Retrieves and returns the task at the specified index from the task list.
     *
     * @param index The index of the task to be retrieved.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        return this.taskList.get(index - 1);
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The number of tasks in the list.
     */
    public int size() {
        return this.taskList.size();
    }

    /**
     * Returns the entire task list.
     *
     * @return An ArrayList containing all the tasks in the list.
     */
    public ArrayList<Task> getTaskList() {
        return this.taskList;
    }

    /**
     * Sets the task list for the current instance.
     *
     * @param taskList An ArrayList of Task objects representing the new task list.
     *                 If null is passed, the task list will be set to an empty ArrayList.
     */
    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }


    /**
     * @param keyword The keyword to find the sentence containing this keyword.
     * @return The sentences contain this keyword.
     */
    public TaskList find(String keyword) {
        TaskList searchingResult = new TaskList();
        ArrayList<Task> results = new ArrayList<>();
        for (Task task: taskList) {
            String description = task.getDescription();
            if (description.contains(keyword)) {
                results.add(task);
            }
        }
        searchingResult.setTaskList(results);
        return searchingResult;
    }

    /**
     * Lists and prints all tasks in the task list with their respective indices.
     */
    public String list() {
        StringBuilder allList = new StringBuilder();
        int index = 1;
        for (Task msg : taskList) {
            allList.append(index + "." + msg.toString()).append("\n");
            index++;
        }
        return allList.toString();
    }
}
