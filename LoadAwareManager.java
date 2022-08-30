package cosc201.a2;

import java.util.ArrayDeque;
import java.util.Collection;

public class LoadAwareManager implements PacketManager {
    
    private ArrayDeque<Packet> queue = new ArrayDeque<>();
    private Location home;
    private Warehouse w;
  
    public LoadAwareManager(Location home, Warehouse warehouse) {
      this.home = home;
      this.w = warehouse;
    }
    
    /**
    * The number of packets currently being managed by the LoadAwareManager.
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
     * If the destination of packets is in a straight line from the current location, it just 
     * sends it one step in the appropriate direction. If it has two reasonable choices, 
     * it checks to see how many packets are held at those locations and sends to the smaller 
     * of the two. If the two are equal it prefers to change the row number 
     * i.e., prefers vertical moves over horizontal ones.
     * 
     * @return the packet sent or null if no packet is being sent
     */
    @Override
    public Packet sendPacket() {
        Packet p = null;
        if (isEmpty()) return null; 
        p = queue.remove();

        // Destination is a straight line from the current location
        if(p.current.row == p.destination.row || p.current.col == p.destination.col)
            // Change the column number if the destination is in the same row as the current location
            if(p.current.row == p.destination.row){
                if(p.current.col < p.destination.col){
                    p.current.col++;
                } else {
                   p.current.col--;
                } 
            // Change the row number if the destination is in the same column as the current location
            } else {
                if(p.current.row < p.destination.row){
                    p.current.row++;
                } else {
                    p.current.row--;
                }
        // The current location has two reasonble choices to travel to get to the destination
        } else { 
            if(p.current.row > p.destination.row) {
                // The destination is north-west from the current location
                int packetsNorth = w.getManager(new Location (p.current.row - 1, p.current.col)).size();
                if(p.current.col > p.destination.col){
                    if(packetsNorth > w.getManager(new Location (p.current.row, p.current.col - 1)).size()){
                        p.current.col--;
                    } else {
                        p.current.row--;
                    }
                // The destination is north-east from the current location
                } else {  
                    if(packetsNorth > w.getManager(new Location (p.current.row, p.current.col + 1)).size()){
                        p.current.col++;
                    } else {
                        p.current.row--;
                    }
                }
            } else {
                // The destination is south-west from the current location
                int packetsSouth = w.getManager(new Location (p.current.row + 1, p.current.col)).size();
                if(p.current.col > p.destination.col){       
                    if(packetsSouth > w.getManager(new Location (p.current.row, p.current.col - 1)).size()){
                        p.current.col--;
                    } else {
                        p.current.row++;
                    }  
                // The destination is south-east from the current location
                } else {
                    if(packetsSouth > w.getManager(new Location (p.current.row, p.current.col + 1)).size()){
                        p.current.col++;
                    } else {
                        p.current.row++;
                    }    
                }
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
     * The queue of packets currently being managed by the LoadAwareManager
     * @return the queue of packets managed
     */
    @Override
    public Collection<Packet> packetsHeld() {
        return queue;
    }
}

