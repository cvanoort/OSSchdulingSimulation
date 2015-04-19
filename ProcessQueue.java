import java.util.ArrayList;

/**
 * Created by Colin Van Oort on 11/19/2014.
 */
public class ProcessQueue {
    // Instance Variables *********************************************************************************************
    private ArrayList<PCB> queue;
    private int maxSize;

    // Constructors ***************************************************************************************************
    public ProcessQueue(int maxSize){
        queue = new ArrayList<PCB>();
        this.maxSize = maxSize;
    }

    // Setters ********************************************************************************************************

    public void set(int index, PCB pcb){
        this.queue.set(index, pcb);
    }

    // Getters ********************************************************************************************************
    public int getSize() {
        return this.queue.size();
    }

    public PCB get(int index){
        return this.queue.get(index);
    }

    public ArrayList<PCB> getQueue() {
        return this.queue;
    }

    // Other Methods **************************************************************************************************
    public void push(PCB p){
        this.queue.add(p);
    }

    public PCB pop(){
        PCB p = this.queue.get(0);
        this.queue.remove(p);
        return p;
    }

    public PCB peek(){
        return this.queue.get(0);
    }

    public void remove(int index){
        this.queue.remove(index);
    }

    public boolean isEmpty(){
        return this.queue.isEmpty();
    }

    public boolean isFull(){
        return (this.queue.size() >= this.maxSize);
    }

    @Override
    public String toString() {
        String output = "Process Queue: \n";
        for (int i = 0; i < queue.size(); i++) {
            output = output + queue.get(i).toString() + ", \n";
        }
        output = output + "\n";
        return output;
    }

    public void sjfSort(){
        QuicksortSJF(0, this.queue.size()-1);
    }

    public void addBySJF(PCB pcb){
        if(this.queue.isEmpty()){
            this.queue.add(pcb);
        }
        else {
            for (int i = 0; i <= queue.size(); i++) {
                if (i == queue.size()){
                    this.queue.add(pcb);
                    break;
                }
                // There is no preemption in this SJF algorithm, so we make sure to insert new processes behind the running process only
                if ((pcb.getDesiredCPUTime() < this.queue.get(i).getDesiredCPUTime()) && (this.queue.get(i).getProcessState() != State.RUNNING)) {
                    this.queue.add(i, pcb);
                    break;
                }

                if ((pcb.getDesiredCPUTime() < this.queue.get(i).getDesiredCPUTime()) && (this.queue.get(i).getProcessState() == State.RUNNING)) {
                    this.queue.add(i + 1, pcb);
                    break;
                }
            }
        }
    }

    public void prioritySort(){
        QuicksortPriority(0, this.queue.size()-1);
    }

    public void addByPriority(PCB pcb, boolean preemption, int timeStep){
        if(queue.isEmpty()){
            queue.add(pcb);

        }
        else {
            for (int i = 0; i <= queue.size(); i++) {
                if (i == queue.size()){
                    this.queue.add(pcb);
                    break;
                }

                // There may be preemption in the Priority algorithm, so check to see if this is the case and modify behavior accordingly
                if ((pcb.getPriority() < queue.get(i).getPriority()) && (queue.get(i).getProcessState() != State.RUNNING)) {
                    queue.add(i, pcb);
                    break;
                }

                if ((pcb.getPriority() < queue.get(i).getPriority()) && (queue.get(i).getProcessState() == State.RUNNING)) {
                    if (preemption) {
                        PCB running = queue.get(i);
                        running.setProcessState(State.READY);
                        running.setPreemptTime(timeStep);
                        queue.set(i, running);
                        queue.add(i, pcb);
                        break;
                    } else {
                        queue.add(i+1,pcb);
                        break;
                    }
                }
            }
        }
    }

    public void queueEntrySort(){
        QuickSortQueueEntry(0, this.queue.size()-1);
    }

    public void QuicksortSJF(int min,int max){
        if(min < max){
            int q = PartitionSJF(min, max);
            QuicksortSJF(min,q-1);
            QuicksortSJF(q+1,max);
        }
    }

    public int PartitionSJF(int min,int max){
        PCB x = this.queue.get(max);
        int i = min-1;
        for(int j=min;j<max;j++){
            if(this.queue.get(j).getDesiredCPUTime()<=x.getDesiredCPUTime()){
                i = i+1;
                PCB temp = this.queue.get(i);
                this.queue.set(i,this.queue.get(j));
                this.queue.set(j,temp);
            }
        }
        PCB temp = this.queue.get(i+1);
        this.queue.set(i+1,this.queue.get(max));
        this.queue.set(max,temp);
        return i+1;
    }

    public void QuicksortPriority(int min,int max){
        if(min < max){
            int q = PartitionPriority(min, max);
            QuicksortPriority(min, q - 1);
            QuicksortPriority(q + 1, max);
        }
    }

    public int PartitionPriority(int min,int max){
        PCB x = this.queue.get(max-1);
        int i = min-1;
        for(int j=min;j<max-1;j++){
            if(this.queue.get(j).getPriority()<=x.getPriority()){
                i = i+1;
                PCB temp = this.queue.get(i);
                this.queue.set(i,this.queue.get(j));
                this.queue.set(j,temp);
            }
        }
        PCB temp = this.queue.get(i+1);
        this.queue.set(i+1,this.queue.get(max));
        this.queue.set(max,temp);
        return i+1;
    }

    public void QuickSortQueueEntry(int min,int max){
        if(min < max){
            int q = PartitionQueueEntry(min, max-1);
            QuickSortQueueEntry(min,q-1);
            QuickSortQueueEntry(q+1,max-1);
        }
    }

    public int PartitionQueueEntry(int min,int max){
        PCB x = this.queue.get(max);
        int i = min-1;
        for(int j=min;j<max-1;j++){
            if(this.queue.get(j).getQueueAddTime() <= x.getQueueAddTime()){
                i = i+1;
                PCB temp = this.queue.get(i);
                this.queue.set(i,this.queue.get(j));
                this.queue.set(j,temp);
            }
        }
        PCB temp = this.queue.get(i+1);
        this.queue.set(i+1,this.queue.get(max));
        this.queue.set(max,temp);
        return i+1;
    }
}
