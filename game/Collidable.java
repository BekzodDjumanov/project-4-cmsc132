interface Collidable {
  /*
   * INTERFACE: Collidable
   * DESCRIPTION: Implemented by game elements that can collide with each other.
   * AUTHORS: Your Name, Teammate's Name
   */

  boolean isActive();
  boolean collides(Polygon other);
}
