package cosc201.a2;

public class Packet{
  private static int NEXT_ID = 0;
  
  Location current;
  Location destination;
  String contents;
  final int ID;

  public Packet() {
    this.ID = NEXT_ID++;
  }

  public Packet(String contents) {
    this();
    this.contents = contents;
  }

  void setCurrent(Location current) {
    this.current = new Location(current);
  }

  void setDestination(Location destination) {
    this.destination = new Location(destination);
  }

  @Override
  public int hashCode() {
    return ID;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Packet other = (Packet) obj;
    if (ID != other.ID)
      return false;
    return true;
  }

  public String toString() {
    return "[" + ID + " " + current + " -> " + destination + "]";
  }

}