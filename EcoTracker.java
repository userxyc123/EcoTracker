// Sakshi Verma
// WildHacks II - 8.6.22
// Project: EcoTracker

import java.util.*;
import java.io.*;
import java.util.Arrays;
import java.io.IOException;

// this class represents an EcoTracker, that motivates people
// to do good things on a daily basis, log those actions,
// and get points! by scoring more points, the user unlocks
// rarer animals (to help spread awareness about endangered species)
public class EcoTracker {
    private int points;
    public static void main(String[] args) throws IOException {
        Scanner console = new Scanner(System.in);
        Scanner input = new Scanner(new File("options"));
        Scanner input2 = new Scanner(new File("result"));
        Scanner input3 = new Scanner(new File("log"));
        
        Map<String, List<String>> map = new TreeMap<>();
        Map<String, List<String>> results = new TreeMap<>();
        
        get(input, map);
        get(input2, results);

        intro(map);
        main(map, console, results);
        
        FileWriter output = new FileWriter("log", true);
        BufferedWriter writeOutput = new BufferedWriter(output);
        
        totaling(console, output, writeOutput);

        reward(input3);
        
        input3.close();
        input3 = new Scanner(new File("log"));
        
        printLog(input3);
    }
    
    // post: gets information from input files, stores into map
    public static void get(Scanner input, Map<String, List<String>> map) {
        while (input.hasNextLine()) {
            String[] line = input.nextLine().split(": ");
            String category = line[0];
            List<String> optionsOfLine = new ArrayList<>();
            String optionsTogether = line[1].trim();
            String[] optionsIsolated = optionsTogether.split(", ");
            
            for (int i = 0; i < optionsIsolated.length; i++) {
                optionsOfLine.add(optionsIsolated[i]);
            }
            map.put(category, optionsOfLine);
        }
    }
    
    // post: prints intro message
    public static void intro(Map<String, List<String>> map) {
        System.out.println("Hi! Welcome to the EcoTracker, where your goal");
        System.out.println("is to level up to new characters by winning points.");
        System.out.println("Score as many points you can each day by logging every");
        System.out.println("(presumably) environmentally friendly habit you do!");
        System.out.println("Keep track of how many points you score, and total");
        System.out.println("them at the end. There's a log that will print out");
        System.out.println("all the points you've logged (previous included).");
        System.out.println();
        System.out.println("Here are the categories: ");
        
        for (String element : map.keySet()) {
            System.out.println("   - " + element);
        }
    }
    
    // post: prompts user to pick a category and action
    public static void main(Map<String, List<String>> map, Scanner console, Map<String, List<String>> results) {
        boolean done = false;
        
        System.out.println();
        System.out.println("Let's get started.");
        
        while (!done) {            
            System.out.println();
            System.out.println("What category would you like");
            System.out.print("to log? (enter \"x\" to quit on the next line) ");
                
            String chosen = console.nextLine();

            if (chosen.equals("x")) {
                done = true;
            }
            else {
                if (!map.containsKey(chosen)) {
                    System.out.println("That is not a category. Please try again.");
                }
                else {
                    System.out.println();
                    System.out.println("Okay, you picked " + chosen + ".");
                    System.out.println();
                    System.out.println("Here are the options: " + map.get(chosen) + ".");
                    System.out.print("What did you do today? (enter option number) ");
                    
                    if (!console.hasNextInt()) {
                        System.out.println("That is not an option listed. Please try again.");
                    }
                    else {
                        int pickedAction = console.nextInt();
                        if (pickedAction < 0) {
                            System.out.println("That is not a valid entry. Please try again.");
                        }
                        else {
                            System.out.println();
                            String action = results.get(chosen).get(pickedAction - 1);
                            System.out.println(action);   
                            System.out.println("You scored " + getPoints(action));  
                        }
                    }
                    console.nextLine();
                }
            } 
        }
    }
    
    // post: totals the points, adds to log
    public static void totaling(Scanner console, FileWriter output, BufferedWriter writeOutput) throws IOException {
        boolean done = false;
        int totalPoints = 0;
        System.out.println();
        while (!done) {
            System.out.println("Please total your points.");
            System.out.println("If you have negative points, please enter a 0.");
            System.out.print("    Total: ");
            if (!console.hasNextInt()) {
                System.out.println("That is not a number, please try again.");
            }
            else {
                totalPoints = console.nextInt();
                done = true;
            }
        }
        writeOutput.write("" + totalPoints);
        writeOutput.newLine();
        writeOutput.close();
    }
    
    // post: prints which characters user has unlocked
    public static void reward(Scanner input3) {
        int logPoints = 0;
        while (input3.hasNextInt()) {
            logPoints += input3.nextInt();
        }
        System.out.println();
        if (logPoints > 100) {
            System.out.println("Congratulations! You've unlocked the tiger with your 100+ points!");
            System.out.println("This species is endangered due to excessive hunting and habitat loss.");
            System.out.println();
            System.out.println("Thank you for showing environmental support. It is important to spread");
            System.out.println("awareness about these endangered species.");
        }
        else if (logPoints > 500) {
            System.out.println("Congratulations! You've unlocked the mountain gorilla with your 500+ points!");
            System.out.println("The mountain gorilla species is endagered due to frequent hunting and habitat loss.");
            System.out.println();
            System.out.println("Thank you for showing environmental support.");
        }
        else if (logPoints > 1000) {
            System.out.println("Congratulations! You've unlocked the Javan Rhino with your 1000+ points!");
            System.out.println("The Javan Rhino species' population is declining at a staggering rate");
            System.out.println("due to severe habitat loss and mass hunting.");
            System.out.println();
            System.out.println("Thank you for your incredible environmental support!");
        }
    }
    
    // post: returns points given the action
    public static String getPoints(String action) {
        String finding = action.substring(action.indexOf("("), action.indexOf(")"));
        finding = finding.substring(1);
        return finding;
    }
    
    // post: prints log
    public static void printLog(Scanner input) {
        System.out.println();
        System.out.println("Here is the log!");
        int lineNum = 1;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            System.out.println("Log " + lineNum + ": " + line + " points");
            lineNum++;
        }
    }
    
}