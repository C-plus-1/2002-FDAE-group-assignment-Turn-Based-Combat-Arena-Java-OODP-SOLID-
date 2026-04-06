package Boundary;



public interface InputHandler {
	
	int getChoice(String prompt, int min, int max);
	
	String getText(String prompt);
	
	
	void close();
	
}
