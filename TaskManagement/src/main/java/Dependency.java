public class Dependency {
    private Task dependentTask;
    private String condition; // Condition for dependency

    // Constructor, getters, setters


    public Task getDependentTask() {
        return dependentTask;
    }

    public void setDependentTask(Task dependentTask) {
        this.dependentTask = dependentTask;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
