package cosc201.a2;

import java.util.Collection;

/**
 * An implementation of PacketManager represents a particular type of packet
 * manager using some sort of packet handling protocol. It would typically
 * store a collection of packets. See PacketHoarder for a (broken) example.
 * 
 * @author Michael Albert
 */
public interface PacketManager {

  /**
   * The number of packets currently being managed by this manager.
   * @return the number of packets managed
   */
  public int size();

  /**
   * Whether or not this manager has packets.
   * @return true if the manager has packets
   */
  default public boolean isEmpty() {
    return this.size() == 0;
  }

  /**
   * Sends (and removes) a packet from the current set of managed packets. The 
   * packet sent will have an (updated) "current location" which is where it is 
   * being sent to. If the manager has no packets to send (or chooses not to
   * send a packet) then the return value should be null.
   * 
   * @return the packet sent or null if no packet is being sent
   */
  Packet sendPacket();

  /**
   * Receives a single packet and adds it to the packets being managed if it 
   * requires further transfer (i.e., is not at its final destination).
   * @param p the packet received
   */
  void receivePacket(Packet p);

  /**
   * Receives a collection of packets and adds them to the packets being managed
   * by adding them one at a time in the order specified by the iterator of the
   * collection.
   * 
   * @param ps the packets received
   */
  default public void receivePackets(Collection<Packet> ps) {
    for (Packet p : ps) receivePacket(p);
  }
  
  public Collection<Packet> packetsHeld();
  
}
