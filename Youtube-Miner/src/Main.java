import java.util.ArrayList;
import java.util.Random;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("Hello World");
		String prefix = generatePrefix(5);
		ArrayList<String> videoIds = new Search().prefixSearch(prefix);
		return;
	}
	
	public static String generatePrefix(int size){
		Random rand = new Random();
		
		
		char[] charSet = {'0','1','2','3','4','5','6','7','8','9','-','_','a','b','c','d','e','f','g','h','i','j','k',
				'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K',
				'L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}; //character set for possible characters in id
		
		
		char[] prefix = new char[size];  //random prefix
		
		for(int i = 0; i < size; i++){ //fill random prefix
			int randomIndex = rand.nextInt(64); // 0-9.
			prefix[i] = charSet[randomIndex];
		}
		
		String prefixString = new String(prefix); //convert to string
		return prefixString;
		
	}

}
