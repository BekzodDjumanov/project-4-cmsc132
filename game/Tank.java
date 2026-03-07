import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Tank extends Polygon implements KeyListener, Collidable {

  private boolean forward, left, right, backward;
  private int cx, cy;
  private double speed = 3.0;
  public ArrayList<Bullet> bullets = new ArrayList<>();
  private boolean active = true;

  public Tank(Point[] shape, Point position, double rotation) {
    super(shape, position, rotation);
    forward = false;
    left = false;
    right = false;
    backward = false;
  }

  public void move() {
    if (forward) {
      position.x += speed * Math.cos(Math.toRadians(rotation));
      position.y += speed * Math.sin(Math.toRadians(rotation));
    }
    if (backward) {
      position.x -= speed * Math.cos(Math.toRadians(rotation));
      position.y -= speed * Math.sin(Math.toRadians(rotation));
    }
    if (left) rotate(-5);
    if (right) rotate(5);
  }

  public void paint(Graphics brush) {
    Point[] points = getPoints();
    int[] xPoints = new int[points.length];
    int[] yPoints = new int[points.length];
    for (int i = 0; i < points.length; i++) {
      xPoints[i] = (int) points[i].x;
      yPoints[i] = (int) points[i].y;
    }

    // tank body
    brush.setColor(new Color(34, 139, 34));
    brush.fillPolygon(xPoints, yPoints, points.length);

    // dark outline
    brush.setColor(new Color(0, 80, 0));
    brush.drawPolygon(xPoints, yPoints, points.length);

    // turret
    cx = 0;
    cy = 0;
    for (int i = 0; i < points.length; i++) {
      cx += xPoints[i];
      cy += yPoints[i];
    }
    cx /= points.length;
    cy /= points.length;

    brush.setColor(new Color(20, 100, 20));
    brush.fillOval(cx - 8, cy - 8, 16, 16);
    brush.setColor(new Color(0, 60, 0));
    brush.drawOval(cx - 8, cy - 8, 16, 16);

    int barrelEndX = (int) (cx + 20 * Math.cos(Math.toRadians(rotation)));
    int barrelEndY = (int) (cy + 20 * Math.sin(Math.toRadians(rotation)));
    brush.setColor(new Color(0, 60, 0));
    brush.drawLine(cx, cy, barrelEndX, barrelEndY);
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP) forward = true;
    if (e.getKeyCode() == KeyEvent.VK_DOWN) backward = true;
    if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;

    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      double angle = Math.toRadians(rotation);
      Point bulletStart = new Point(
        cx + 22 * Math.cos(angle),
        cy + 22 * Math.sin(angle)
      );
      bullets.add(new Bullet(bulletStart, rotation));
    }
  }

  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP) forward = false;
    if (e.getKeyCode() == KeyEvent.VK_DOWN) backward = false;
    if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
  }

  public boolean isActive() {
    return active;
  }

  public void keyTyped(KeyEvent e) {}
}
