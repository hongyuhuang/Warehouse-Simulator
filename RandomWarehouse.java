package cosc201.a2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * A randomly managed warehouse. How many packages are successfully delivered?
 * 
 * One thing to note - to model the synchronous behaviour we collect all the
 * deliveries in a queue and only once we have them all do we deliver to their
 * destination. This ensures that we don't get a package from a cell that has
 * only been delivered there in the current round.
 * 
 */
public class RandomWarehouse {

   final Random R = new Random();

  int rows;
  int cols;
  int totalPackets;
  int totalRounds = 0;
  int maxDistance = 0;
  HashMap<Location, PacketManager> robots = new HashMap<>();
  int delivered = 0;
  Warehouse warehouse = new Warehouse();
  ArrayList<Packet> packets = new ArrayList<Packet>();
  ArrayList<Location> locations = new ArrayList<Location>();

  /**
   * Command line arguments - rows then cols then total number of packages
   */
  public double getEfficeny() {
    rows = 5;
    cols = 5;
    
    totalPackets = rows * cols;
    addRobots();
    addPackets(totalPackets);
    //maxDistance = calcMaxDistance(packets);
    letChaosEnsue();
    maxDistance = calcMaxDistance(packets);
    return calcEfficeny();

  }

  private void addRobots() {
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        // warehouse.managers.put(new Location(r, c), new BasicManager(new Location(r, c)));
        // warehouse.managers.put(new Location(r, c), new LoadAwareManager(new Location(r, c), warehouse));
        // warehouse.managers.put(new Location(r, c), new PriorityManager(new Location(r, c), warehouse));
        warehouse.managers.put(new Location(r, c), new PriorityBasicManager(new Location(r, c)));
      }
    }
  }

  private void addPackets(int n) {
    for (int i = 0; i < n; i++){
      addPacket();
    }
    // for (int r = 0; r < rows; r++) {
    //   for (int c = 0; c < cols; c++) {
    //       // if(R.nextInt(4) == 3){
    //         addPacket(r, c);   
    //       // }
    //       // else{
    //         // totalPackets--;
    //       // }
    //   }
    // }  
}

  // private void addPacket(int row, int col){
  //   Packet p = new Packet();
  //   p.contents = "" + p.ID;
  //   p.current = new Location (row,col);
  //   // p.current = new Location (0,0);
  //   // p.current = randomLocation();
  //   p.destination = randomLocation();
  //   if (p.destination.equals(p.current)) delivered++;
  //   warehouse.managers.get(p.current).receivePacket(p);
  //   packets.add(p);
  // }

  private void addPacket() {
    Packet p = new Packet();
    p.contents = "" + p.ID;
    // p.current = new Location(0, 0);
    p.current = randomLocation();
    p.destination = new Location(rows - 1, cols - 1);
    if (p.destination.equals(p.current)) delivered++;
    warehouse.managers.get(p.current).receivePacket(p);
    packets.add(p);
  }

  private Location randomLocation() {
    Location randomLocation =  new Location(R.nextInt(rows), R.nextInt(cols));
    while(locations.contains(randomLocation)){
      randomLocation =  new Location(R.nextInt(rows), R.nextInt(cols));
    }
    locations.add(randomLocation);
    return randomLocation;
  }

  private void letChaosEnsue() {
    System.out.println("Initial configuration");
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        Location l = new Location(r, c);
        System.out.println(warehouse.managers.get(l).packetsHeld());
      }
    }
    System.out.println("-----------------------");
    int packetsDropped = 0;
    while (packetsDropped + delivered < totalPackets) {
      System.out.println("Starting a round of deliveries");
      ArrayDeque<Packet> deliveryQueue = new ArrayDeque<>();
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
          Packet p = warehouse.managers.get(new Location(r, c)).sendPacket();
          if (p != null) { 
            if (isValidLocation(p.current)) {
              if (p.current.equals(p.destination)) {
                delivered++;
                System.out.println("Packet " + p.ID + " has been delivered!");
              } else {
                System.out.println("Packet " + p.ID + " is moving to " + p.current);
                deliveryQueue.add(p);
              }
            } else {
              System.out.println("Packet " + p.ID + " has been dropped!");
              packetsDropped++;
            }
          }
        }
      }
      for(Packet p : deliveryQueue) {
        warehouse.managers.get(p.current).receivePacket(p);
      }
      totalRounds++;
      System.out.println("Round complete");
      System.out.println("---------");
    }
    System.out.println("Packets delivered = " + delivered);
    System.out.println("Packets dropped = " + packetsDropped);
  }

  private boolean isValidLocation(Location l) {
    return 0 <= l.row && l.row < rows && 0 <= l.col && l.col < cols;
  }

  private int calcMaxDistance(ArrayList<Packet> packets){
    int maxDistance = 0;
    for(int i = 0; i < packets.size(); i++){
      Packet currentPacket = packets.get(i);
      int currentDistance = calcDistance(currentPacket);
      if(currentDistance > maxDistance){
        maxDistance = currentDistance;
      }
    }
    return maxDistance;
  }

  private int calcDistance(Packet p){
    // System.out.println("Current location: (" + p.current.row + ", " + p.current.col + ")");
    // System.out.println("Destination location: (" + p.destination.row + ", " + p.destination.col + ")");   
    return Math.abs(p.destination.row - p.current.row) + Math.abs(p.destination.col - p.current.col);
  }

  private double calcEfficeny(){
    // System.out.println("Max distance: " + maxDistance);
    // System.out.println("Total rounds: " + totalRounds);
    return (double)maxDistance / (double)totalRounds;
  }

}
