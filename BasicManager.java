package cosc201.a2;

import java.util.ArrayDeque;
import java.util.Collection;

public class BasicManager implements PacketManager {
    
    private ArrayDeque<Packet> queue = new ArrayDeque<>();
    private Location home;
  
    public BasicManager(Location home) {
      this.home = home;
    }
    
    /**
    * The number of packets currently being managed by the BasicManager.
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
        while (!this.isEmpty()) {
            p = queue.remove();
            if (!p.destination.equals(this.home)) break; 
            // We have found a packet that needs to move
        }
        if (p == null || p.destination.equals(this.home)) return null; 
        // No packets to move
        
        // Change the row of the current location
        // if(p.current.row < p.destination.row || p.current.row > p.destination.row){
        if(p.current.row != p.destination.row){
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
     * Receives a single packet and adds it to the packets being managed.
     * @param p the packet received
     */
    @Override
    public void receivePacket(Packet p) {
        queue.add(p);
    }
    
    /**
     * The queue of packets currently being managed by the BasicManager
     * @return the queue of packets managed
     */
    @Override
    public Collection<Packet> packetsHeld() {
        return queue;
    }
}
