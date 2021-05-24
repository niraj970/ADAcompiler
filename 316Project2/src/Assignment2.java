import java.io.File;

public class Assignment2 {

	public static void main(String[] args) throws Exception {
		LexicalAnalyzer la = new LexicalAnalyzer(args[0]);
		 Parser pa = new Parser(la);
		 pa.START();
	 }
	
}
