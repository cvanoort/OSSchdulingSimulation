import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Colin Van Oort on 12/4/2014.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the CS 201 Programming assignment by: Colin Van Oort.");
        System.out.println("You will now be prompted to input variables to create a basic system simulation");
        System.out.println("How many random processes would you like the simulated system to contain? (Integer)");

        int processes = 0;
        try {
            processes = Integer.parseInt(br.readLine());
        }catch (NumberFormatException nfe){
            System.err.println("Invalid Format");
        }

        System.out.println("What time quantum would you like used in a Round Robin scheduling algorithm? (Integer)");
        int timeQuantum = 0;
        try {
            timeQuantum = Integer.parseInt(br.readLine());
        }catch (NumberFormatException nfe){
            System.err.println("Invalid Format");
        }

        // create the list of random PCB's which will be used by the system simulations
        ArrayList<PCB> pcbList = new ArrayList<PCB>();
        ArrayList<PCB> pcbList2 = new ArrayList<PCB>();
        ArrayList<PCB> pcbList3 = new ArrayList<PCB>();
        ArrayList<PCB> pcbList4 = new ArrayList<PCB>();
        ArrayList<PCB> pcbList5 = new ArrayList<PCB>();
        for (int i = 0; i < processes; i++) {
            PCB pcb = new PCB();
            pcbList.add(pcb.clone());
            pcbList2.add(pcb.clone());
            pcbList3.add(pcb.clone());
            pcbList4.add(pcb.clone());
            pcbList5.add(pcb.clone());
        }


        String input = "";

        // Fifo Scheduled system ***************************************************************************************
        System.out.println("Would you like to simulate a system with a FIFO scheduler? (Y/N)");

        try {
            input = br.readLine();
        }
        catch (IOException ioe){
            System.out.println("Invalid Input, please input either Y or N");
        }

        if (input.toUpperCase().equals("Y") || input.toUpperCase().equals("YES") || input.toUpperCase().equals("YEAH") || input.toUpperCase().equals("YA") || input.toUpperCase().equals("YEA")){
            SystemSim fifoSim = new SystemSim(pcbList,timeQuantum,Schedule.FIFO);
            while(!fifoSim.isEmpty()){
                fifoSim.step();
            }
            System.out.println("The average wait time for the system was: " + fifoSim.calcAvgWait() + "\n"
                    + "The average Turn-around time for the system was: " + fifoSim.calcAvgTurn() + "\n");
        }



        // SJF Scheduled system ****************************************************************************************
        System.out.println("Would you like to simulate a system with a SJF scheduler? (Y/N)");
        try {
            input = br.readLine();
        }
        catch (IOException ioe){
            System.out.println("Invalid Input, please input either Y or N");
        }

        if (input.toUpperCase().equals("Y") || input.toUpperCase().equals("YES") || input.toUpperCase().equals("YEAH") || input.toUpperCase().equals("YA") || input.toUpperCase().equals("YEA")){
            SystemSim sjfSim = new SystemSim(pcbList2,timeQuantum,Schedule.SJF);
            while(!sjfSim.isEmpty()){
                sjfSim.step();
            }
            System.out.println("The average wait time for the system was:" + sjfSim.calcAvgWait() + "\n"
                    + "The average Turn-around time for the system was: " + sjfSim.calcAvgTurn() + "\n");
        }



        // Priority Scheduled system ***********************************************************************************
        System.out.println("Would you like to simulate a system with a Priority scheduler? (Y/N)");
        try {
            input = br.readLine();
        }
        catch (IOException ioe){
            System.out.println("Invalid Input, please input either Y or N");
        }

        if (input.toUpperCase().equals("Y") || input.toUpperCase().equals("YES") || input.toUpperCase().equals("YEAH") || input.toUpperCase().equals("YA") || input.toUpperCase().equals("YEA")){
            SystemSim prioritySim = new SystemSim(pcbList3,timeQuantum,Schedule.PRIORITY);
            while(!prioritySim.isEmpty()){
                prioritySim.step();
            }
            System.out.println("The average wait time for the system was:" + prioritySim.calcAvgWait() + "\n"
                    + "The average Turn-around time for the system was: " + prioritySim.calcAvgTurn() + "\n");
        }



        // Preemptive Priority Scheduled System ************************************************************************
        System.out.println("Would you like to simulate a system with a Preemptive Priority scheduler? (Y/N)");
        try {
            input = br.readLine();
        }
        catch (IOException ioe){
            System.out.println("Invalid Input, please input either Y or N");
        }

        if (input.toUpperCase().equals("Y") || input.toUpperCase().equals("YES") || input.toUpperCase().equals("YEAH") || input.toUpperCase().equals("YA") || input.toUpperCase().equals("YEA")){
            SystemSim preemptivePrioritySim = new SystemSim(pcbList4,timeQuantum,Schedule.PREEMPTIVE_PRIORITY);
            while(!preemptivePrioritySim.isEmpty()){
                preemptivePrioritySim.step();
            }
            System.out.println("The average wait time for the system was:" + preemptivePrioritySim.calcAvgWait() + "\n"
                    + "The average Turn-around time for the system was: " + preemptivePrioritySim.calcAvgTurn() + "\n");
        }


        // Round Robin Scheduled system ********************************************************************************
        System.out.println("Would you like to simulate a system with a Round Robin scheduler? (Y/N)");
        try {
            input = br.readLine();
        }
        catch (IOException ioe){
            System.out.println("Invalid Input, please input either Y or N");
        }

        if (input.toUpperCase().equals("Y") || input.toUpperCase().equals("YES") || input.toUpperCase().equals("YEAH") || input.toUpperCase().equals("YA") || input.toUpperCase().equals("YEA")){
            SystemSim roundRobinSim = new SystemSim(pcbList5,timeQuantum,Schedule.RR);
            while(!roundRobinSim.isEmpty()){
                roundRobinSim.step();
            }
            System.out.println("The average wait time for the system was:" + roundRobinSim.calcAvgWait() + "\n"
                    + "The average Turn-around time for the system was: " + roundRobinSim.calcAvgTurn() + "\n");
        }

    }
}
