/*
 * CLASS: TankBattle
 * DESCRIPTION: Main game class, extends Game. Control center for Tank Battle.
 * AUTHORS: Your Name, Teammate's Name
 */
import java.awt.*;
import java.awt.event.*;

public class TankBattle extends Game {

  static int counter = 0;
  private Tank tank;

  public TankBattle() {
    super("Tank Battle!", 800, 600);
    // Define tank shape (simple triangle pointing east)
    Point[] tankShape = {
      new Point(0, 0),
      new Point(40, 0),
      new Point(40, 25),
      new Point(0, 25),
    };
    tank = new Tank(tankShape, new Point(400, 300), 0);

    this.setFocusable(true);
    this.requestFocus();
    this.addKeyListener(tank);
  }

  public void paint(Graphics brush) {
    // Clear screen
    brush.setColor(Color.black);
    brush.fillRect(0, 0, width, height);

    // Bullets
    tank.bullets.removeIf(b -> !b.isActive()); // LAMBDA HERE
    for (Bullet b : tank.bullets) {
      b.move();
      b.checkBounds(width, height);
      b.paint(brush);
    }

    // Move and draw tank
    tank.move();
    tank.paint(brush);
  }

  public static void main(String[] args) {
    TankBattle game = new TankBattle();
    game.repaint();
  }
}
