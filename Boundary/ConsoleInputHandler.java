package Boundary;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {
 
	private Scanner sc;
	
	public ConsoleInputHandler() {
		this.sc = new Scanner(System.in);
		
	}
	
	@Override
	
	public int getChoice(String prompt, int min, int max) {
		while (true) {
			System.out.print(prompt);
			String text = sc.nextLine().trim();
			
			try {
				int choice = Integer.parseInt(text);
				
				if (choice >= min && choice <= max) {
					return choice;
				}
				
				System.out.println("Please enter a number between" + min + "and" + max + ".");
				
				
				
			} 
			catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a number.");
				
			}
		}
	}
	
	@Override
    public String getText(String prompt) {
        String line = "";
        while (line.isEmpty()) {
            System.out.print(prompt);
            line = sc.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Input cannot be empty.");
            }
        }
        return line;
    }
 
   
    
 
    @Override
    public void close() {
        sc.close();
    }
    }
