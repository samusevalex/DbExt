public final class JobStatusContainer {

    private static JobStatusContainer instance = null;
    private int[] taskStatus;
    private String error="";

    private JobStatusContainer() {

    }

    public static JobStatusContainer getInstance() {
        if (instance == null) instance = new JobStatusContainer();
        return instance;
    }

    public String getStatus() {
        String str = "";
        for (int i = 0; i < taskStatus.length; i++) str += taskStatus[i];
        return str;
    }

    public synchronized void setTaskStatus(int index, int state) {
        // Добавить проверку на index
        taskStatus[index] = state;
    }

    public void setTotal(int total) {
        taskStatus = new int[total];
        for (int i = 0; i < total; i++)
            taskStatus[i] = 0;
    }
    public synchronized void setError(String error){
        this.error = error;

    }
    public String getError() {
        return this.error;
    }
}