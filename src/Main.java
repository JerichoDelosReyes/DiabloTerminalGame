import java.util.Scanner;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jerZer0
 * @author NoroñTuron
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain;

        do {
            System.out.println("WELCOME TO DIABLO");
            System.out.print("You are a young adventurer seeking glory. \nEnter your name: ");
            String playerName = scanner.nextLine();

            System.out.println("\nHello, " + playerName + "! Choose your class to begin your journey:");
            System.out.println("1. Mage");
            System.out.println("2. Warrior");
            System.out.println("3. Tank");
            System.out.println("4. Duelist");

            int choice = scanner.nextInt();
            scanner.nextLine(); 
            Character player;

            switch (choice) {
                case 1 -> player = new Mage(playerName);
                case 2 -> player = new Warrior(playerName);
                case 3 -> player = new Tank(playerName);
                case 4 -> player = new Duelist(playerName);
                default -> {
                    System.out.println("Invalid choice! Defaulting to Mage.");
                    player = new Mage(playerName);
                }
            }

            System.out.println("\nYou have chosen: " + player.name + " the " + player.getClass().getSimpleName() + "!");
            System.out.println("\nYour journey begins in the Dark Forest...");
            System.out.println("\nChoose your enemy:");
            System.out.println("1. Bandit Leader (Warrior)");
            System.out.println("2. Forest Beast (Tank)");
            System.out.println("3. Dark Sorcerer (Mage)");

            int enemyChoice = scanner.nextInt();
            scanner.nextLine();
            Character enemy;

            switch (enemyChoice) {
                case 1 -> enemy = new BanditLeader("Bandit Leader");
                case 2 -> enemy = new ForestBeast("Forest Beast");
                case 3 -> enemy = new DarkSorcerer("Dark Sorcerer");
                default -> {
                    System.out.println("Invalid choice! Defaulting to Bandit Leader.");
                    enemy = new BanditLeader("Bandit Leader");
                }
            }

            System.out.println("\nA wild " + enemy.name + " appears!");
            battle(player, enemy);

            if (!player.isAlive()) {
                System.out.println("\nYour journey ends here. Better luck next time!");
            } else {
                System.out.println("\nCongratulations! You have defeated " + enemy.name + " and proven yourself a true hero!");
            }

            System.out.print("\nPlay again? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            playAgain = response.equals("yes");

        } while (playAgain);

        System.out.println("\nThank you for playing DIABLO! Goodbye!");
        scanner.close();
    }

    public static void battle(Character player, Character enemy) {
        Scanner scanner = new Scanner(System.in);
        int currentTurn = 0;

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\nYour Health: " + player.health + ", Mana: " + player.mana);
            System.out.println(enemy.name + " Health: " + enemy.health);

            System.out.println("\nChoose your action:");
            System.out.println("1. Attack");
            System.out.println("2. Use Special Move");
            System.out.println("3. Defend and Regen Mana (+10 Mana, halve enemy damage)");

            int action = scanner.nextInt();
            boolean playerMadeAction = false;

            switch (action) {
                case 1 -> {
                    System.out.println("\nChoose your attack:");
                    System.out.println("1. First Skill (5 mana)");
                    System.out.println("2. Second Skill (10 mana)");
                    System.out.println("3. Third Skill (15 mana)");
                    System.out.println("4. Go back");

                    int skillChoice = scanner.nextInt();

                    if (skillChoice == 4) {
                        break;
                    } else {
                        player.useSkill(skillChoice, enemy);
                        playerMadeAction = true;
                    }
                }
                case 2 -> {
                    try {
                        player.specialMove(enemy, currentTurn);
                        playerMadeAction = true;
                    } catch (SpecialMoveCooldownException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    player.defendAndRegenMana();
                    playerMadeAction = true;
                }
                default -> System.out.println("\nInvalid action! You lose your turn.");
            }

            if (playerMadeAction && enemy.isAlive()) {
                enemy.attack(player);
                currentTurn++; 
            }
        }

        if (player.isAlive()) {
            System.out.println("\nYou have defeated " + enemy.name + "!\n");
        } else {
            System.out.println("\n" + player.name + " has fallen. Game Over.");
        }
    }
}
