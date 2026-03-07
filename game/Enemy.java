/*
 * CLASS: Enemy
 * DESCRIPTION: An enemy tank that moves toward the player.
 * AUTHORS: Your Name, Teammate's Name
 */
import java.awt.*;

public class Enemy extends Polygon implements Collidable {

  private double speed = .5;
  private boolean active;
  // Add as instance variable in Enemy
  private Explosion explosion = null;

  // Inner class - goes inside Enemy class
  class Explosion {

    private int frame = 0;
    private int x, y;
    private final int MAX_FRAMES = 20;

    public Explosion(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public boolean isFinished() {
      return frame >= MAX_FRAMES;
    }

    public void paint(Graphics brush) {
      int radius = frame * 3;
      int alpha = 255 - (int) (255 * ((double) frame / MAX_FRAMES));
      brush.setColor(new Color(255, 100, 0, alpha));
      brush.fillOval(x - radius, y - radius, radius * 2, radius * 2);
      brush.setColor(new Color(255, 255, 0, alpha));
      brush.fillOval(x - radius / 2, y - radius / 2, radius, radius);
      frame++;
    }
  }

  public Enemy(Point[] shape, Point position, double rotation) {
    super(shape, position, rotation);
    this.active = true;
  }

  public boolean isActive() {
    return active;
  }

  public Explosion getExplosion() {
    return explosion;
  }

  public void destroy() {
    active = false;
    int cx = (int) position.x + 20;
    int cy = (int) position.y + 12;
    explosion = new Explosion(cx, cy);
  }

  public void moveToward(Tank tank) {
    // Calculate angle toward player
    double dx = tank.position.x - position.x;
    double dy = tank.position.y - position.y;
    double angle = Math.toDegrees(Math.atan2(dy, dx));
    rotation = angle;

    // Move toward player
    position.x += speed * Math.cos(Math.toRadians(rotation));
    position.y += speed * Math.sin(Math.toRadians(rotation));
  }

  public void paint(Graphics brush) {
    if (!active && explosion != null && !explosion.isFinished()) {
      explosion.paint(brush);
      return;
    }
    Point[] points = getPoints();
    int[] xPoints = new int[points.length];
    int[] yPoints = new int[points.length];
    for (int i = 0; i < points.length; i++) {
      xPoints[i] = (int) points[i].x;
      yPoints[i] = (int) points[i].y;
    }

    // Enemy body
    brush.setColor(new Color(139, 0, 0)); // dark red
    brush.fillPolygon(xPoints, yPoints, points.length);
    brush.setColor(new Color(80, 0, 0));
    brush.drawPolygon(xPoints, yPoints, points.length);

    // Turret
    int cx = 0, cy = 0;
    for (int i = 0; i < points.length; i++) {
      cx += xPoints[i];
      cy += yPoints[i];
    }
    cx /= points.length;
    cy /= points.length;

    brush.setColor(new Color(100, 0, 0));
    brush.fillOval(cx - 8, cy - 8, 16, 16);
    brush.setColor(new Color(60, 0, 0));
    brush.drawOval(cx - 8, cy - 8, 16, 16);

    // Barrel
    int barrelEndX = (int) (cx + 20 * Math.cos(Math.toRadians(rotation)));
    int barrelEndY = (int) (cy + 20 * Math.sin(Math.toRadians(rotation)));
    Graphics2D brush2d = (Graphics2D) brush;
    brush2d.setStroke(new BasicStroke(3));
    brush2d.setColor(new Color(60, 0, 0));
    brush2d.drawLine(cx, cy, barrelEndX, barrelEndY);
    brush2d.setStroke(new BasicStroke(1));
  }
}
