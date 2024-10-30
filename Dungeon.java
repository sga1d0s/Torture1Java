import java.util.Scanner;

public class Dungeon {

  public static void main(String[] args) {
    // CREACIÓN DE PERSONAJE
    Scanner sc = new Scanner(System.in);
    System.out.println("CHARACTER CREATION");
    System.out.println("------------------");

    String name = "";

    while (true) {
      System.out.println("Insert character name:");
      name = sc.next();

      if (!name.equals("") && !name.equals(" ")) {
        String firstLetter = name.substring(0, 1).toUpperCase();
        String remaining = name.substring(1, name.length()).toLowerCase();

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
    // --------------------------------
    int[][] map = {
        { 0, 0, 0, 3, 0, 0 },
        { 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 2, 0 },
        { 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 4, 0, 1 },
    };

    String[] messages = {
        "A long and dark corridor lies in front of you",
        "You arrived at the Wistom Altar. A light of hope shines from upward",
        "You arrived at the Red Fountain, Ther pouring bood poisons you",
        "You arrived at fountain. When you drink water, it removes poison and flames",
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
      // Mostrar descripcion de la sala
      int fila = position / 10;
      int columna = position & 10;

      System.out.println("Position: (" + (fila + 1) + "," + (columna + 1) + ")");

      int mapValue = map[fila][columna];
      roomMessage = messages[mapValue];

      System.out.println(roomMessage);

      // 2.- ROOM EFFECTS / EVENTS
      switch (mapValue) {
        // Sala del altar
        case ALTAR:
          gameState = SUCCEEDED;
          break;

        // Sala Blood fountain
        case BLOOD_FOUNTAIN:
          isPoisoned = true;
          break;

        // Sala life fountan
        case LIFE_FOUNTAIN:
          isPoisoned = false;
          isBurning = false;
          break;

        // Sala dragon
        case DRAGON:
          isBurning = true;
          break;

        // Corridor
        default:
          break;
      }

      if (gameState == SUCCEEDED)
        break;

      // 3.- ACCIONES
      // Mostrar acciones disponibles
      System.out.println("What do you want to do? (Actions: n,s,e,w,m,p,q)");

      // Órdenes
      action = sc.next();

      switch (action) {
        case "n":
          // Si estoy en la fila 0, no me puedo mover
          if (fila == 0) {
            System.out.println("You cannot go this way");
          } else {
            position -= 10;
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
            position -= 10;
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
          System.out.println("ATRIBUTES");
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
              int playerColumna = position % 10; // Columna
              int playerFila = position / 10; // Fila

              if (playerColumna == j && playerFila == i) {
                System.out.println("*");
              } else {
                System.out.println(map[i][j]);
              }
            }
            System.out.println("");
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

      // Comprobamos si el juego ha terminado de alguna de las formas
      if (food <= 0) {
        gameState = NO_FOOD;
      }
      else if (life <= 0){
        gameState = NO_LIFE;
      }

    } // End of GAME LOOP (While)

    //

  }
}
