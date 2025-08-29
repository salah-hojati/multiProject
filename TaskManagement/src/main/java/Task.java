import java.util.List;

public class Task {
    private String taskId;
    private String taskName;
    private String description;
    private List<Dependency> dependencies;
    private List<Decision> decisions;


    public boolean checkDependencies(Task task) {
        for (Dependency dependency : task.getDependencies()) {
            if (!dependency.getDependentTask().isCompleted()) {
                return false; // Dependency is not completed
            }
        }
        return true; // All dependencies are completed
    }
    public void makeDecisions(Task task) {
        for (Decision decision : task.getDecisions()) {
            // Perform some logic based on the decision
            if (decision.getOutcome().equals("success")) {
                // Move to the next step
                System.out.println("Moving to the next step");
            } else {
                // Handle failure scenario
                System.out.println("Failure scenario handled");
            }
        }
    }

    // Constructor, getters, setters


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public List<Decision> getDecisions() {
        return decisions;
    }

    public void setDecisions(List<Decision> decisions) {
        this.decisions = decisions;
    }

    public enum TaskStatus {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED
    }
}
