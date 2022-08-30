package cosc201.a2;

import java.util.HashMap;

public class Warehouse {

  HashMap<Location, PacketManager> managers = new HashMap<>();

  public Warehouse() {};

  /**
   * Set the manager at a given location. Note this overwrites any existing manager (and their
   * packets) at that location, so use with care (typically only in initialising a warehouse)
   * 
   * @param l A location
   * @param m A manager for that location
   */
  public void setManager(Location l, PacketManager m) {
    managers.put(l, m);
  }

  /**
   * Returns the manager associated with a location (or null if there isn't one)
   * @param l A location
   * @return The manager at that location.
   */
  public PacketManager getManager(Location l) {
    return managers.get(l);
  }

  
}
