public class Task {
    private String description;
    private String Time;
    private boolean isCompleted;

    public Task(String description) {
        this.description = description;
        this.isCompleted = false;
    }
    public void setDescription(String newDescription){
        description = newDescription;
    }
    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return description + (isCompleted ? " (Completed)" : "");
    }
}