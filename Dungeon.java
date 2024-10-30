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

  }
}
