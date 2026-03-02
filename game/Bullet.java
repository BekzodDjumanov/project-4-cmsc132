/*
 * CLASS: Bullet
 * DESCRIPTION: A projectile fired by the tank, travels in the direction
 *              the tank is facing when fired.
 * AUTHORS: Your Name, Teammate's Name
 */
import java.awt.*;

public class Bullet extends Polygon {

  private double speed = 8.0;
  private boolean active;
  public double direction;

  public Bullet(Point position, double direction) {
    super(
      new Point[] { new Point(0, 0), new Point(6, 2), new Point(6, -2) },
      position,
      direction
    );
    this.direction = direction;
    this.active = true;
  }

  public boolean isActive() {
    return active;
  }

  public void move() {
    position.x += speed * Math.cos(Math.toRadians(direction));
    position.y += speed * Math.sin(Math.toRadians(direction));
  }

  public void checkBounds(int width, int height) {
    if (
      position.x < 0 ||
      position.x > width ||
      position.y < 0 ||
      position.y > height
    ) {
      active = false;
    }
  }

  public void paint(Graphics brush) {
    Point[] points = getPoints();
    int[] xPoints = new int[points.length];
    int[] yPoints = new int[points.length];
    for (int i = 0; i < points.length; i++) {
      xPoints[i] = (int) points[i].x;
      yPoints[i] = (int) points[i].y;
    }
    brush.setColor(Color.gray);
    brush.fillPolygon(xPoints, yPoints, points.length);
  }
}
