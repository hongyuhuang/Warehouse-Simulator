package cosc201.a2;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A useless packet manager that just hoards all the packets it receives.
 * 
 * @author Michael Albert
 */
public class PacketHoarder implements PacketManager {

  ArrayList<Packet> hoard = new ArrayList<>();

  public PacketHoarder() {}

  @Override
  public int size() {
    return hoard.size();
  }

  @Override
  public Packet sendPacket() {
    return null;
  }

  @Override
  public void receivePacket(Packet p) {
    hoard.add(p);
  }

  @Override
  public Collection<Packet> packetsHeld() {
    return hoard;
  }
  
}
