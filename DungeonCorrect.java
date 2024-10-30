import java.util.Scanner;

public class DungeonCorrect {

  public static void main(String[] args) {
    // CREACIÓN DE PERSONAJE
    Scanner sc = new Scanner(System.in);
    System.out.println("CHARACTER CREATION");
    System.out.println("------------------");

    String name = "";

    while (true) {
      System.out.println("Insert character name:");
      name = sc.next();

      if (!name.trim().isEmpty()) {
        String firstLetter = name.substring(0, 1).toUpperCase();
        String remaining = name.substring(1).toLowerCase();
        name = firstLetter.concat(remaining);
        break;
      } else {
        System.out.println("Name not valid");
      }
    }

    int life = -1;
    while (true) {
      System.out.println("Insert character life points (50-100): ");
      life = sc.nextInt();
      if (life < 50 || life > 100) {
        System.out.println("Life value not valid");
      } else {
        break;
      }
    }

    int food = -1;
    while (true) {
      System.out.println("Insert character food points (30-50): ");
      food = sc.nextInt();
      if (food < 30 || food > 50) {
        System.out.println("Food value not valid");
      } else {
        break;
      }
    }

    System.out.println("----------------------------------------");

    System.out.println("GAME START");
    System.out.println("----------");

    // INIT GAME DATA
    int[][] map = {
        { 0, 0, 0, 3, 0, 0 },
        { 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 2, 0 },
        { 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 4, 0, 1 },
    };

    String[] messages = {
        "A long and dark corridor lies in front of you",
        "You arrived at the Wisdom Altar. A light of hope shines from upward",
        "You arrived at the Red Fountain. The pouring blood poisons you",
        "You arrived at a fountain. When you drink water, it removes poison and flames",
        "A dragon gazes at you. Suddenly, he throws flames at you",
    };

    // INIT vars
    int position = 0;
    String action = "";
    String roomMessage = "";
    int gameState = 0;
    boolean isPoisoned = false;
    boolean isBurning = false;

    // Estados de juego
    final int PLAYING = 0;
    final int NO_FOOD = 1;
    final int NO_LIFE = 2;
    final int SUCCEEDED = 3;
    final int Q_PRESSED = 4;

    while (gameState == PLAYING) { // El juego no ha terminado
      final int ALTAR = 1;
      final int BLOOD_FOUNTAIN = 2;
      final int LIFE_FOUNTAIN = 3;
      final int DRAGON = 4;

      // 1.- LOAD ROOM DATA
      int fila = position / 6;
      int columna = position % 6;

      System.out.println("Position: (" + (fila + 1) + "," + (columna + 1) + ")");

      int mapValue = map[fila][columna];
      roomMessage = messages[mapValue];

      System.out.println(roomMessage);

      // 2.- ROOM EFFECTS / EVENTS
      switch (mapValue) {
        case ALTAR:
          gameState = SUCCEEDED;
          break;

        case BLOOD_FOUNTAIN:
          isPoisoned = true;
          break;

        case LIFE_FOUNTAIN:
          isPoisoned = false;
          isBurning = false;
          break;

        case DRAGON:
          isBurning = true;
          break;

        default:
          break;
      }

      if (gameState == SUCCEEDED)
        break;

      // 3.- ACCIONES
      System.out.println("What do you want to do? (Actions: n,s,e,w,m,p,q)");

      action = sc.next();

      switch (action) {
        case "n":
          if (fila == 0) {
            System.out.println("You cannot go this way");
          } else {
            position -= 6;
            food--;
            if (isPoisoned) {
              life -= 3;
            }
            if (isBurning) {
              life -= 10;
            }
          }
          break;

        case "s":
          if (fila == 4) {
            System.out.println("You cannot go this way");
          } else {
            position += 6;
            food--;
            if (isPoisoned) {
              life -= 3;
            }
            if (isBurning) {
              life -= 10;
            }
          }
          break;

        case "e":
          if (columna == 5) {
            System.out.println("You cannot go this way");
          } else {
            position += 1;
            food--;
            if (isPoisoned) {
              life -= 3;
            }
            if (isBurning) {
              life -= 10;
            }
          }
          break;

        case "w":
          if (columna == 0) {
            System.out.println("You cannot go this way");
          } else {
            position -= 1;
            food--;
            if (isPoisoned) {
              life -= 3;
            }
            if (isBurning) {
              life -= 10;
            }
          }
          break;

        case "p":
          System.out.println("ATTRIBUTES");
          System.out.println("---------");

          System.out.println("Name: " + name);
          System.out.println("Life: " + life);
          System.out.println("Food: " + food);
          System.out.println("---------");
          break;

        case "m":
          System.out.println("DUNGEON MAP");
          System.out.println("-----------");

          for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
              int playerColumna = position % 6;
              int playerFila = position / 6;

              if (playerColumna == j && playerFila == i) {
                System.out.print("* ");
              } else {
                System.out.print(map[i][j] + " ");
              }
            }
            System.out.println();
          }
          System.out.println("-----------------");
          break;

        case "q":
          gameState = Q_PRESSED;
          break;

        default:
          System.out.println("Action not permitted");
          break;
      }

      if (food <= 0) {
        gameState = NO_FOOD;
      } else if (life <= 0) {
        gameState = NO_LIFE;
      }

    }

    // FIN DEL JUEGO
    switch (gameState) {
      case NO_FOOD:
        System.out.println(name + " the heroine is starving");
        break;
      case NO_LIFE:
        System.out.println(name + " the heroine has died");
        break;
      case SUCCEEDED:
        System.out.println(name + " the heroine has found the Wisdom Altar. Well done!!");
        break;
      case Q_PRESSED:
        System.out.println("FAREWELL");
        break;

      default:
        System.out.println("Error. Game State not valid");
    }
  }
}
