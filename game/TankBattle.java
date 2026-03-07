/*
 * CLASS: TankBattle
 * DESCRIPTION: Main game class, extends Game. Control center for Tank Battle.
 * AUTHORS: Your Name, Teammate's Name
 */
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TankBattle extends Game {

  static int counter = 0;
  private Tank tank;
  private ArrayList<Enemy> enemies = new ArrayList<>();
  private boolean gameOver = false;
  private int wave = 1;
  private int invincibilityFrames = 0;
  private boolean paused = false;
  private boolean gameStarted = false;

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

    Point[] enemyShape = {
      new Point(0, 0),
      new Point(40, 0),
      new Point(40, 25),
      new Point(0, 25),
    };
    enemies.add(new Enemy(enemyShape, new Point(100, 100), 0));
    enemies.add(new Enemy(enemyShape, new Point(700, 100), 0));
    enemies.add(new Enemy(enemyShape, new Point(400, 500), 0));

    // Anonymous class for pause functionality
    this.addKeyListener(
        new KeyListener() {
          public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_P) paused = !paused;
            if (e.getKeyCode() == KeyEvent.VK_ENTER) gameStarted = true;
          }

          public void keyReleased(KeyEvent e) {}

          public void keyTyped(KeyEvent e) {}
        }
      );

    this.setFocusable(true);
    this.requestFocus();
    this.addKeyListener(tank);
  }

  public void paint(Graphics brush) {
    if (!gameStarted) {
      // Draw castle floor (same as game)
      int tileSize = 50;
      for (int row = 0; row < height; row += tileSize) {
        for (int col = 0; col < width; col += tileSize) {
          if ((row / tileSize + col / tileSize) % 2 == 0) {
            brush.setColor(new Color(105, 105, 105));
          } else {
            brush.setColor(new Color(90, 90, 90));
          }
          brush.fillRect(col, row, tileSize, tileSize);
          brush.setColor(new Color(60, 60, 60));
          brush.drawRect(col, row, tileSize, tileSize);
        }
      }

      // Dark cinematic overlay
      brush.setColor(new Color(0, 0, 0, 160));
      brush.fillRect(0, 0, width, height);

      // Title banner
      brush.setColor(new Color(180, 0, 0));
      brush.fillRect(0, height / 2 - 160, width, 70);
      brush.setColor(new Color(120, 0, 0));
      brush.drawRect(0, height / 2 - 160, width, 70);

      brush.setFont(new Font("Arial", Font.BOLD, 48));
      brush.setColor(Color.white);
      brush.drawString("TANK BATTLE", width / 2 - 130, height / 2 - 105);

      // Divider line
      brush.setColor(new Color(255, 215, 0));
      brush.fillRect(width / 2 - 150, height / 2 - 75, 300, 3);

      // Instructions box
      brush.setColor(new Color(0, 0, 0, 180));
      brush.fillRect(width / 2 - 160, height / 2 - 65, 320, 200);
      brush.setColor(new Color(255, 215, 0));
      brush.drawRect(width / 2 - 160, height / 2 - 65, 320, 200);

      brush.setFont(new Font("Arial", Font.BOLD, 16));
      brush.setColor(new Color(255, 215, 0));
      brush.drawString("CONTROLS", width / 2 - 35, height / 2 - 40);

      brush.setFont(new Font("Arial", Font.PLAIN, 15));
      brush.setColor(Color.white);
      brush.drawString("↑  Move Forward", width / 2 - 80, height / 2 - 15);
      brush.drawString("↓  Move Backward", width / 2 - 80, height / 2 + 10);
      brush.drawString("← →  Rotate Tank", width / 2 - 80, height / 2 + 35);
      brush.drawString("SPACE  Shoot", width / 2 - 80, height / 2 + 60);
      brush.drawString("P  Pause", width / 2 - 80, height / 2 + 85);

      // Pulsing prompt
      brush.setFont(new Font("Arial", Font.BOLD, 20));
      brush.setColor(new Color(255, 215, 0));
      brush.drawString(
        "Press ENTER to Start",
        width / 2 - 100,
        height / 2 + 115
      );

      // Battlements on top and bottom
      brush.setColor(new Color(50, 50, 50));
      for (int x = 0; x < width; x += 30) {
        brush.fillRect(x, 0, 15, 15);
        brush.fillRect(x, height - 15, 15, 15);
      }
      return;
    }
    // Clear screen
    brush.setColor(Color.black);
    brush.fillRect(0, 0, width, height);

    // Draw castle stone floor
    int tileSize = 50;
    for (int row = 0; row < height; row += tileSize) {
      for (int col = 0; col < width; col += tileSize) {
        // Alternate slightly different greys for checkerboard stone effect
        if ((row / tileSize + col / tileSize) % 2 == 0) {
          brush.setColor(new Color(105, 105, 105));
        } else {
          brush.setColor(new Color(90, 90, 90));
        }
        brush.fillRect(col, row, tileSize, tileSize);

        // Grout lines
        brush.setColor(new Color(60, 60, 60));
        brush.drawRect(col, row, tileSize, tileSize);
      }
    }

    // Castle wall border
    brush.setColor(new Color(70, 70, 70));
    brush.fillRect(0, 0, width, 10); // top
    brush.fillRect(0, height - 10, width, 10); // bottom
    brush.fillRect(0, 0, 10, height); // left
    brush.fillRect(width - 10, 0, 10, height); // right

    // Battlements (notches on the border)
    brush.setColor(new Color(50, 50, 50));
    for (int x = 0; x < width; x += 30) {
      brush.fillRect(x, 0, 15, 15);
      brush.fillRect(x, height - 15, 15, 15);
    }
    for (int y = 0; y < height; y += 30) {
      brush.fillRect(0, y, 15, 15);
      brush.fillRect(width - 15, y, 15, 15);
    }

    if (gameOver) {
      brush.setColor(Color.red);
      brush.drawString("GAME OVER", width / 2 - 30, height / 2);
      return;
    }

    // Bullets
    tank.bullets.removeIf(b -> !b.isActive()); // LAMBDA HERE
    for (Bullet b : tank.bullets) {
      b.move();
      b.checkBounds(width, height);
      b.paint(brush);
    }

    for (Enemy e : enemies) {
      e.moveToward(tank);

      // Check bullet hits enemy
      for (Bullet b : tank.bullets) {
        if (b.isActive() && e.isActive() && e.collides(b)) {
          e.destroy();
          b.deactivate();
          scoreTracker.addScore(100); // add this!
        }
      }

      // Check enemy touches player
      if (e.isActive() && e.collides(tank) && invincibilityFrames == 0) {
        scoreTracker.loseLife();
        invincibilityFrames = 100;
        if (scoreTracker.getLives() <= 0) gameOver = true;
      }

      e.paint(brush);
    }

    // Remove dead enemies
    enemies.removeIf(
      e ->
        !e.isActive() &&
        (e.getExplosion() == null || e.getExplosion().isFinished())
    );
    if (enemies.isEmpty()) {
      scoreTracker.addScore(500); // bonus for clearing wave!
      spawnWave();
    }

    // Move and draw tank
    tank.move();
    tank.paint(brush);
    scoreTracker.paint(brush);
    if (invincibilityFrames > 0) invincibilityFrames--;
  }

  private void spawnWave() {
    Point[] enemyShape = {
      new Point(0, 0),
      new Point(40, 0),
      new Point(40, 25),
      new Point(0, 25),
    };
    // Spawn one more enemy per wave
    for (int i = 0; i < wave + 2; i++) {
      // Random spawn on edges so they don't spawn on top of player
      int side = (int) (Math.random() * 4);
      Point spawnPoint;
      if (side == 0) spawnPoint = new Point(Math.random() * width, 20);
      else if (side == 1) spawnPoint = new Point(
        Math.random() * width,
        height - 20
      );
      else if (side == 2) spawnPoint = new Point(20, Math.random() * height);
      else spawnPoint = new Point(width - 20, Math.random() * height);

      enemies.add(new Enemy(enemyShape, spawnPoint, 0));
    }
    wave++;
  }

  // Add as instance variable at top of TankBattle
  private ScoreTracker scoreTracker = new ScoreTracker();

  // Inner class - goes inside TankBattle class
  class ScoreTracker {

    private int score;
    private int lives;

    public ScoreTracker() {
      score = 0;
      lives = 3;
    }

    public void addScore(int points) {
      score += points;
    }

    public void loseLife() {
      lives--;
    }

    public int getLives() {
      return lives;
    }

    public int getScore() {
      return score;
    }

    public void paint(Graphics brush) {
      brush.setColor(Color.white);
      brush.setFont(new Font("Arial", Font.BOLD, 16));
      brush.drawString("Score: " + score, 20, 30);

      // Draw lives as small green squares
      brush.drawString("Lives: ", 20, 55);
      for (int i = 0; i < lives; i++) {
        brush.setColor(new Color(34, 139, 34));
        brush.fillRect(75 + (i * 20), 43, 14, 14);
        brush.setColor(new Color(0, 80, 0));
        brush.drawRect(75 + (i * 20), 43, 14, 14);
      }
    }
  }

  public static void main(String[] args) {
    TankBattle game = new TankBattle();
    game.repaint();
  }
}
