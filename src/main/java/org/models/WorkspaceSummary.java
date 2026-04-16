package org.models;

public class WorkspaceSummary {
    private String workspaceName;
    private int totalTasks;
    private int pending;
    private int inProgress;
    private int completed;
    private int cancelled;

    public WorkspaceSummary(String workspaceName, int totalTasks,
                            int pending, int inProgress, int completed, int cancelled) {
        this.workspaceName = workspaceName;
        this.totalTasks    = totalTasks;
        this.pending       = pending;
        this.inProgress    = inProgress;
        this.completed     = completed;
        this.cancelled     = cancelled;
    }

    public String getWorkspaceName() { return workspaceName; }
    public int getTotalTasks()       { return totalTasks; }
    public int getPending()          { return pending; }
    public int getInProgress()       { return inProgress; }
    public int getCompleted()        { return completed; }
    public int getCancelled()        { return cancelled; }
}