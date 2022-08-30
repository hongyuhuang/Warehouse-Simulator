package cosc201.a2;
/**
 * A basic container class to represent locations in a grid (Java does not 
 * have a class with this intended use - the provided classes are intended
 * for key-value pairs).
 * 
 * @author Michael Albert
 */
public class Location {

  int row;
  int col;

  public Location(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Creates a copy of a location
   * @param other the location to be copied
   */
  public Location(Location other) {
    this(other.row, other.col);
  }

  /**
   * Modification of the auto-provided method (1001267 is a prime).
   */  
  @Override
  public int hashCode() {
    return 1001267*row + col;
  }

  /**
   * Auto-provided by Visual Studio code (right click in editor window
   * and choose "Source action" to get the option to add equals and hashcode)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Location other = (Location) obj;
    if (col != other.col)
      return false;
    if (row != other.row)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "(" + row + ", " + col + ")";
  } 

}
