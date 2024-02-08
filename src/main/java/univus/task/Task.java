package univus.task;
public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void mark() {
        this.isDone = true;
    }

    public boolean isDone() {
        return isDone;
    }

    public void unMark() {
        this.isDone = false;
    }
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getDescription() {
        return this.description;
    }
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + description;
    }
}