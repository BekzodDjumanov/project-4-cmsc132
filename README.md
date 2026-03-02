# 🪖 Tank Battle Game

A 2D tank battle game built in Java as part of a university programming project. Fight enemy tanks, dodge bullets, and survive as long as possible!

## 🎮 Gameplay

- Control your tank using the keyboard
- Shoot bullets to destroy enemy tanks
- Avoid enemy fire — taking a hit ends the game
- Score points for every enemy destroyed

## 🕹️ Controls

| Key      | Action       |
| -------- | ------------ |
| ↑ Arrow  | Move forward |
| ← Arrow  | Rotate left  |
| → Arrow  | Rotate right |
| Spacebar | Shoot        |

## 🛠️ Built With

- Java (AWT / Graphics)
- Custom 2D polygon-based game engine

## 🏗️ Project Structure

```
src/
├── TankBattle.java       # Main game class (extends Game)
├── Tank.java             # Player tank element
├── Enemy.java            # Enemy tank element
├── Bullet.java           # Projectile element
├── Polygon.java          # Base polygon/shape class
├── Point.java            # 2D point utility class
└── Game.java             # Abstract game engine base class
```

## ✅ Java Features Demonstrated

- Inheritance & abstract classes
- Interfaces (`Collidable`)
- Inner classes
- Anonymous classes
- Lambda expressions
- 2D array manipulation
- Keyboard event handling (`KeyListener`)
- Collision detection

## 👥 Authors

- Bekzod Djumanov
- Avhaan Narang

## 📋 Course Info

CMSC132 — University of Maryland  
Project 4: Game Implementation  
Spring 2026
