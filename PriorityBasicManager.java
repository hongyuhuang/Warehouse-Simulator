package cosc201.a2;

import java.util.*;

public class PriorityBasicManager implements PacketManager {
    
    private PriorityQueue<Packet> queue = new PriorityQueue<>(new PacketComparator());
    private Location home;
  
    public PriorityBasicManager(Location home) {
      this.home = home;
    }

    public class PacketComparator implements Comparator<Packet>{
        public int compare(Packet p1, Packet p2) {
            int distanceOfp1 = Math.abs(p1.destination.row - p1.current.row) + Math.abs(p1.destination.col - p1.current.col);
            int distanceOfp2 = Math.abs(p2.destination.row - p2.current.row) + Math.abs(p2.destination.col - p2.current.col);
            if (distanceOfp1 < distanceOfp2){
                return 1;    
            }
            else if (distanceOfp2 > distanceOfp1){
                return -1;
            }
            return 0;
        }
    }
    
    /**
    * The number of packets currently being managed by the PriorityBasicManager.
    * @return the number of packets managed
    */
    @Override
    public int size() {
      return queue.size();
    }
    
    /**
     * Sends (and removes) a packet from the current set of managed packets. The 
     * packet sent will have an (updated) "current location" which is where it is 
     * being sent to. If the manager has no packets to send (or chooses not to
     * send a packet) then the return value should be null.
     * 
     * Packet is moved towards the destination preferring to change the row number if 
     * possible and then the column number (i.e., the route any packet follows will 
     * be up or down followed by left or right)
     * 
     * @return the packet sent or null if no packet is being sent
     */
    @Override
    public Packet sendPacket() {
        Packet p = null;
        if (isEmpty()) return null; 
        p = queue.remove();
        
        // Change the row of the current location
        if(p.current.row < p.destination.row || p.current.row > p.destination.row){
            if(p.current.row < p.destination.row){
                p.current.row++;
            } else {
                p.current.row--;
            } 
        // Change the column of the cuurent location
        } else{
            if(p.current.col < p.destination.col){
                p.current.col++;
            }
            else{
                p.current.col--;
            }
        }        
        return p;
    }

    /**
    * Receives a single packet and adds it to the packets being managed if it 
    * requires further transfer (i.e., is not at its final destination).
    * @param p the packet received
    */
    @Override
    public void receivePacket(Packet p) {
        if (!p.destination.equals(this.home)){
            queue.add(p);
        }
    }
    
    /**
     * The queue of packets currently being managed by the PriorityBasicManager
     * @return the queue of packets managed
     */
    @Override
    public Collection<Packet> packetsHeld() {
        return queue;
    }
}
