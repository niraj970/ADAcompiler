
public class Errors {

	public void error(int code, int position)
	{
		if(code == 0)
		{
			printBlanks(position);
			System.out.print	("PROCSYM not found");
			System.exit(0);
		}

		if(code == 1)
		{
			printBlanks(position);
			System.out.print("Missing IDENT");
			System.exit(0);
		}

		if(code == 2)
		{
			printBlanks(position);
			System.out.print("Missing ISSYM");
			System.exit(0);
		}

		if(code == 3)
		{
			printBlanks(position);
			System.out.print(" BOOLSYM or INTSYM required");
			System.exit(0);
		}

		if(code == 4)
		{
			printBlanks(position);
			System.out.print("Missing BEGINSYM");
			System.exit(0);
		}

		if(code == 5)
		{
			printBlanks(position);
			System.out.print(" SEQ OF STMT required");
			System.exit(0);
		}

		if(code == 6)
		{
			printBlanks(position);
			System.out.print("Missing RPAREN");
			System.exit(0);
		}

		if(code == 7)
		{
			printBlanks(position);
			System.out.print("Missing ENDSYM");
			System.exit(0);
		}

		if(code == 8)
		{
			printBlanks(position);
			System.out.print("Did not reach EOI");
			System.exit(0);
		}

		if(code == 9)
		{
			printBlanks(position);
			System.out.print("Missing SEMICOLON");
			System.exit(0);
		}

		if(code == 10)
		{
			printBlanks(position);
			System.out.print("Missing COLON");
			System.exit(0);
		}

		if(code == 11)
		{
			printBlanks(position);
			System.out.print("Becomes required");
			System.exit(0);
		}

		if(code == 12)
		{
			printBlanks(position);
			System.out.print("Expected PRIMARY");
			System.exit(0);
		}
		if(code == 13)
		{
			printBlanks(position);
			System.out.print("THENSYM required after IFSYM");
			System.exit(0);
		}
		if(code == 14)
		{
			printBlanks(position);
			System.out.println("missing IFSYM");
			System.exit(0);
		}
		if(code == 15)
		{
			printBlanks(position);
			System.out.print("LOOP missing");
			System.exit(0);
	}
		if(code == 16)
		{
			printBlanks(position);
			System.out.print("LPAREN missing");
			System.exit(0);
		}
		
	}

	private void printBlanks(int position)
	{
		for(int i=0; i<position; i++)
		{
			System.out.print(" ");
		}
		System.out.print("^ ");
	}
}



