package cosc201.a2;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Random;

/**
 * A random manager that maintains a queue of packets and just moves the first
 * that's not at its destination to a random adjoining cell (without checking 
 * that) the cell is in fact valid.
 * 
 * @author Michael Albert
 */
public class RandomManager implements PacketManager {
  
  static final int UP = 0;
  static final int DOWN = 1;
  static final int LEFT = 2;
  static final int RIGHT = 3;
  static final Random R = new Random();
  
  private ArrayDeque<Packet> queue = new ArrayDeque<>();
  private Location home;

  public RandomManager(Location home) {
    this.home = home;
  }

  @Override
  public int size() {
    return queue.size();
  }

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
    int dir = R.nextInt(4);
    switch (dir) {
      case UP:
        p.current.row -= 1; break;
      case DOWN:
        p.current.row += 1; break;
      case LEFT:
        p.current.col -= 1; break;
      case RIGHT:
        p.current.col += 1; break;
    }
    return p;
  }

  @Override
  public void receivePacket(Packet p) {
    queue.add(p);
  }

  @Override
  public Collection<Packet> packetsHeld() {
    return queue;
  }
  
}
