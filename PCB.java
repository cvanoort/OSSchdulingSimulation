import java.util.Random;

/**
 * Created by Colin Van Oort on 11/19/2014.
 */
public class PCB {
    // Class Variables
    private static int availableID = 0;

    // Instance Variables
    private State processState;
    private int processID, priority, desiredCPUTime, actualCPUTime,startTime,finishTime,queueAddTime,createTime,preemptTime,waitTime,turnAroundTime,robinTime;

    // Constructors ***************************************************************************************************
    public PCB(){
        this.processID = availableID;
        availableID++;
        this.processState = State.NEW;
        this.priority = randomInteger(0,10);
        this.desiredCPUTime = randomInteger(1,20);
        this.createTime = randomInteger(0,10);
        this.actualCPUTime = 0;
        this.robinTime = 0;
        this.waitTime = 0;
    }

    // Setters ********************************************************************************************************

    public void setProcessState(State processState) {
        this.processState = processState;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDesiredCPUTime(int desiredCPUTime) {
        if (desiredCPUTime >= 0 ) {
            this.desiredCPUTime = desiredCPUTime;
        }
        else{this.desiredCPUTime = 0;}
    }

    public void setActualCPUTime(int actualCPUTime) {
        this.actualCPUTime = actualCPUTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public void setQueueAddTime(int queueAddTime) {
        this.queueAddTime = queueAddTime;
    }

    public void setPreemptTime(int preemptTime) {
        this.preemptTime = preemptTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    // Getters ********************************************************************************************************


    public int getProcessID() {
        return processID;
    }

    public State getProcessState() {
        return processState;
    }

    public int getPriority() {
        return priority;
    }

    public int getDesiredCPUTime() {
        return desiredCPUTime;
    }

    public int getActualCPUTime() {
        return actualCPUTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public int getQueueAddTime() {
        return queueAddTime;
    }

    public int getCreateTime() {
        return createTime;
    }

    public int getPreemptTime() {
        return preemptTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public int getRobinTime() {
        return robinTime;
    }

    // Other Methods **************************************************************************************************
    @Override
    public String toString() {
        return "Process ID: " + this.processID + ", Actual CPU Time: " + this.actualCPUTime + ", Desired CPU Time: " + this.desiredCPUTime + ", Priority: " + this.priority + ", State: " + this.processState;
    }

    public int randomInteger(int min, int max){
        Random random = new Random();
        return (random.nextInt((max - min) + 1) + min);
    }

    public void incrementRobinTime(){
        this.robinTime++;
    }

    public void resetRobinTime(){
        this.robinTime = 0;
    }

    public PCB clone() {
        PCB pcb = new PCB();
        pcb.setProcessState(this.processState);
        pcb.processID = this.processID;
        pcb.priority = this.priority;
        pcb.desiredCPUTime = this.desiredCPUTime;
        pcb.actualCPUTime = this.actualCPUTime;
        pcb.startTime = this.startTime;
        pcb.finishTime = this.finishTime;
        pcb.queueAddTime = this.queueAddTime;
        pcb.createTime = this.createTime;
        pcb.preemptTime = this.preemptTime;
        pcb.waitTime = this.waitTime;
        pcb.turnAroundTime = this.turnAroundTime;
        pcb.robinTime = this.robinTime;

        return pcb;
    }

}
