package cosc201.a2;

public class RandomWarehouseApp {
        
    public static void main(String[] args) {
        double totalEfficency = 0;
        //for(int i = 0; i < 100; i++){
        // Number of simulations can be altered.
        for(int i = 0; i < 100; i++){
            RandomWarehouse r = new RandomWarehouse();
            totalEfficency += r.getEfficeny();
        }
        System.out.println("Average Efficency: " + totalEfficency / 100);    
    }
}
