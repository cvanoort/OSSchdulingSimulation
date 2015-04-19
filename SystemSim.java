import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Colin Van Oort on 12/2/2014.
 */
public class SystemSim {
    private int timeStep = 0;
    private ProcessQueue processQueue,unCreated;
    private ArrayList<PCB> terminated;
    private int timeQuantum;
    private Schedule schedule;

    public SystemSim(ArrayList<PCB> pq, int timeQuantum, Schedule schedule){
        this.processQueue = new ProcessQueue(5);
        this.unCreated = new ProcessQueue(pq.size());
        this.terminated = new ArrayList<PCB>(pq.size());
        this.timeQuantum = timeQuantum;
        this.schedule = schedule;

        // sort the pseudo-randomly generated processes given into the processQueue or unCreated queue
        for (int i = 0; i < pq.size(); i++) {
            PCB pcb = pq.get(i);
            if(pcb.getCreateTime() == 0){
                pcb.setProcessState(State.READY);
                processQueue.push(pcb);
            }
            else {
                pcb.setProcessState(State.UNCREATED);
                unCreated.push(pcb);
            }
        }

        // sort the unCreated Queue based on the smallest queue entry time first
        unCreated.queueEntrySort();
    }

    private void fifoSchedule(){
        if (!this.processQueue.isEmpty()) {
            // We're using a FIFO algorithm so the first element in the process queue is allowed to run
            PCB pcb = this.processQueue.peek();

            // if the scheduled process isn't running then start it
            if (pcb.getProcessState() != State.RUNNING) {
                pcb.setProcessState(State.RUNNING);
                pcb.setStartTime(this.timeStep);
            }

            // there's a chance that the process will need more cpu time than it originally requested
            if (!(randomInteger(1, 100) == 100)) {
                pcb.setDesiredCPUTime(pcb.getDesiredCPUTime() - 1);
            }
            pcb.setActualCPUTime(pcb.getActualCPUTime() + 1);

            // If the running process doesn't need any more CPU time it can be terminated
            if (pcb.getDesiredCPUTime() == 0) {
                pcb.setProcessState(State.TERMINATED);
                pcb.setFinishTime(this.timeStep);
                pcb.setWaitTime(pcb.getStartTime() - pcb.getQueueAddTime());
                pcb.setTurnAroundTime(pcb.getFinishTime() - pcb.getCreateTime());
                this.processQueue.remove(0);
                this.terminated.add(pcb);
            }
            // Otherwise update the process queue to show the progress
            else {
                this.processQueue.set(0, pcb);
            }
        }
    }

    private void sjfSchedule(){
        if(!this.processQueue.isEmpty()) {
            // organize the processes in the process queue by their desired CPU time in the initial time step
            if (this.timeStep == 0) {
                this.processQueue.sjfSort();
            }

            // The process queue is already ordered by desired CPU time so the first element is the one that should run
            PCB pcb = this.processQueue.peek();

            // If the scheduled process isn't running then start it
            if (pcb.getProcessState() != State.RUNNING) {
                pcb.setProcessState(State.RUNNING);
                pcb.setStartTime(this.timeStep);
            }

            // There's a chance that the process will need more cpu time than it originally requested
            if (!(randomInteger(1, 100) == 100)) {
                pcb.setDesiredCPUTime(pcb.getDesiredCPUTime() - 1);
            }
            pcb.setActualCPUTime(pcb.getActualCPUTime() + 1);

            // If the running process doesn't need any more CPU time it can be terminated
            if (pcb.getDesiredCPUTime() == 0) {
                pcb.setProcessState(State.TERMINATED);
                pcb.setFinishTime(this.timeStep);
                pcb.setWaitTime(pcb.getStartTime() - pcb.getQueueAddTime());
                pcb.setTurnAroundTime(pcb.getFinishTime() - pcb.getCreateTime());
                this.processQueue.remove(0);
                this.terminated.add(pcb);
            }
            // Otherwise update the process queue to reflect the current process state
            else {
                this.processQueue.set(0, pcb);
            }
        }
    }

    public void prioritySchedule(){
        if (!this.processQueue.isEmpty()) {
            // Order the process queue by priority in the initial time step
            if (this.timeStep == 0) {
                this.processQueue.prioritySort();
            }

            // Since the queue is ordered by priority then the first element in the queue should run
            PCB pcb = this.processQueue.peek();

            // If the scheduled process isn't running then start it
            if (pcb.getProcessState() != State.RUNNING) {
                pcb.setProcessState(State.RUNNING);
                pcb.setStartTime(this.timeStep);
            }

            // There's a chance that the process will need more cpu time than it originally requested
            if (!(randomInteger(1, 100) == 100)) {
                pcb.setDesiredCPUTime(pcb.getDesiredCPUTime() - 1);
            }
            pcb.setActualCPUTime(pcb.getActualCPUTime() + 1);

            // If the running process doesn't need any more CPU time it can be terminated
            if (pcb.getDesiredCPUTime() == 0) {
                pcb.setProcessState(State.TERMINATED);
                pcb.setFinishTime(this.timeStep);
                pcb.setWaitTime(pcb.getStartTime() - pcb.getQueueAddTime());
                pcb.setTurnAroundTime(pcb.getFinishTime() - pcb.getCreateTime());
                this.processQueue.remove(0);
                this.terminated.add(pcb);
            }
            // Otherwise update the process queue to reflect the current process state
            else {
                this.processQueue.set(0, pcb);
            }
        }
    }

    public void preemptivePrioritySchedule(){
        if (!this.processQueue.isEmpty()) {
            // Order the process queue by priority in the initial time step
            if (timeStep == 0) {
                this.processQueue.prioritySort();
            }

            // Since the queue is ordered by priority then the first element in the queue should run
            PCB pcb = this.processQueue.peek();

            // If the scheduled process isn't running then start it
            if (pcb.getProcessState() != State.RUNNING) {
                pcb.setProcessState(State.RUNNING);
                pcb.setWaitTime(pcb.getWaitTime() + (this.timeStep - pcb.getPreemptTime()));
            }

            // There's a chance that the process will need more cpu time than it originally requested
            if (!(randomInteger(1, 100) == 100)) {
                pcb.setDesiredCPUTime(pcb.getDesiredCPUTime() - 1);
            }
            pcb.setActualCPUTime(pcb.getActualCPUTime() + 1);

            // If the running process doesn't need any more CPU time it can be terminated
            if (pcb.getDesiredCPUTime() == 0) {
                pcb.setProcessState(State.TERMINATED);
                pcb.setFinishTime(this.timeStep);
                pcb.setTurnAroundTime(pcb.getFinishTime() - pcb.getCreateTime());
                this.processQueue.remove(0);
                this.terminated.add(pcb);
            }
            // Otherwise update the process queue to reflect the current process state
            else {
                this.processQueue.set(0, pcb);
            }
        }
    }

    public void roundRobinSchedule(){
        if (!this.processQueue.isEmpty()) {
            // We're using a RoundRobin algorithm so the first element in the process queue is allowed to run for the time quantum
            PCB pcb = this.processQueue.peek();

            // check to see if the process has used up its share of CPU time
            if (pcb.getRobinTime() == this.timeQuantum) {
                // reset the process' time quantum info
                pcb.resetRobinTime();
                pcb.setPreemptTime(this.timeStep);
                pcb.setProcessState(State.READY);

                // move the process to the end of the queue
                this.processQueue.remove(0);
                this.processQueue.push(pcb);

                // Get the next process which will be given the cpu
                pcb = this.processQueue.peek();
            }

            // if the scheduled process isn't running then start it
            if (pcb.getProcessState() != State.RUNNING) {
                pcb.setWaitTime(pcb.getWaitTime() + (this.timeStep - pcb.getPreemptTime()));
                pcb.setProcessState(State.RUNNING);
            }

            // there's a chance that the process will need more cpu time than it originally requested
            if (!(randomInteger(1, 100) == 100)) {
                pcb.setDesiredCPUTime(pcb.getDesiredCPUTime() - 1);
                pcb.incrementRobinTime();
            }
            pcb.setActualCPUTime(pcb.getActualCPUTime() + 1);

            // If the running process doesn't need any more CPU time it can be terminated
            if (pcb.getDesiredCPUTime() == 0) {
                pcb.setProcessState(State.TERMINATED);
                pcb.setFinishTime(this.timeStep);
                pcb.setTurnAroundTime(pcb.getFinishTime() - pcb.getCreateTime());
                this.processQueue.remove(0);
                this.terminated.add(pcb);
            }
            // Otherwise update the process queue to show the progress
            else {
                this.processQueue.set(0, pcb);
            }
        }
    }

    public void step(){
        this.createProcesses();

        System.out.println("Time Step: " + this.timeStep + "\n Before Execution: \n" );
        System.out.println(this.processQueue.toString());

        if (this.schedule == Schedule.FIFO){
            this.fifoSchedule();
        }

        else if (this.schedule == Schedule.SJF){
            this.sjfSchedule();
        }

        else if (this.schedule == Schedule.RR){
            this.roundRobinSchedule();
        }

        else if (this.schedule == Schedule.PRIORITY){
            this.prioritySchedule();
        }

        else {
            this.preemptivePrioritySchedule();
        }

        System.out.println("Time Step: " + this.timeStep + "\n After Execution: \n" );
        System.out.println(this.processQueue.toString());

        this.timeStep++;
    }

    public void createProcesses() {
        // Check to see if the process queue isn't full, add created processes to it
        if (!this.processQueue.isFull()) {
            // Add as many processes to the queue as possible
            while (!this.unCreated.isEmpty() && this.unCreated.peek().getCreateTime() <= this.timeStep && !this.processQueue.isFull()) {
                PCB pcb = this.unCreated.pop();
                pcb.setProcessState(State.READY);
                pcb.setQueueAddTime(this.timeStep);
                pcb.setPreemptTime(this.timeStep);

                if ((this.schedule == Schedule.FIFO) || (this.schedule == Schedule.RR)) {
                    this.processQueue.push(pcb);
                }
                else if (this.schedule == Schedule.PRIORITY) {
                    this.processQueue.addByPriority(pcb, false, this.timeStep);
                }
                else if (this.schedule == Schedule.PREEMPTIVE_PRIORITY) {
                    this.processQueue.addByPriority(pcb, true, this.timeStep);
                }
                else {
                    this.processQueue.addBySJF(pcb);
                }
            }
        }

        // If the process queue is full, or couldn't hold all of the new processes,
        // mark excess created processes as new and leave them in the uncreated queue
        for (int i = 0; i < this.unCreated.getSize(); i++) {
            PCB pcb = this.unCreated.get(i);
            if (pcb.getCreateTime() == this.timeStep) {
                pcb.setProcessState(State.NEW);
                this.unCreated.set(i, pcb);
            }
            if (pcb.getCreateTime() > this.timeStep) {
                break;
            }
        }

    }

    public double calcAvgWait(){
        int avgWait = 0;
        for (int i = 0; i < terminated.size(); i++) {
            avgWait = (avgWait + terminated.get(i).getWaitTime() );
        }
        if (terminated.size() == 0){
            return avgWait;
        }
        else {
            return (avgWait / terminated.size());
        }
    }

    public double calcAvgTurn(){
        int avgTurn = 0;
        for (int i = 0; i < terminated.size(); i++) {
            avgTurn = (avgTurn + terminated.get(i).getTurnAroundTime() );
        }
        if (terminated.size() == 0){
            return avgTurn;
        }
        else {
            return (avgTurn / terminated.size());
        }
    }

    public int randomInteger(int min, int max){
        Random random = new Random();
        return (random.nextInt((max - min) + 1) + min);
    }

    public boolean isEmpty(){
        return (this.processQueue.isEmpty() && this.unCreated.isEmpty());
    }

}
