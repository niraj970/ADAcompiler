


public class Parser {
	private Errors error;
	private Token nexttoken;
	private LexicalAnalyzer lexeme;
	
	public Parser(LexicalAnalyzer lexeme) {
		this.lexeme = lexeme;
		error = new Errors();
		nexttoken = lexeme.getToken();	
	}

	public void START() {
		if(nexttoken.getSym() == Symbol.PROCSYM) {
			nexttoken = lexeme.getToken();
			if(nexttoken.getSym() == Symbol.IDENT) {
				nexttoken = lexeme.getToken();
				if(nexttoken.getSym() == Symbol.ISSYM) {
					nexttoken = lexeme.getToken();
					DECPART();
					if(nexttoken.getSym() == Symbol.BEGINSYM) {
						nexttoken = lexeme.getToken();
						if(nexttoken.getSym() == Symbol.ENDSYM) {
							error.error(5, lexeme.getCurrCharPosNum());
						}
						SEQOFSTMT();
						if(nexttoken.getSym() == Symbol.ENDSYM) {
							nexttoken = lexeme.getToken();
							if(nexttoken.getSym() == Symbol.SEMICOLON) {
								nexttoken = lexeme.getToken();
								if(nexttoken.getSym() == Symbol.EOI) {
									System.out.println("The program is syntactically correct.");
								}else {
									error.error(8, lexeme.getCurrCharPosNum());
								}
							}else {
								error.error(9, lexeme.getCurrCharPosNum());
							}
						}else {
							System.out.println(nexttoken.toString());
							error.error(7, lexeme.getCurrCharPosNum());
						}
					}else{
						error.error(4, lexeme.getCurrCharPosNum());
					}
				}else {
					error.error(2, lexeme.getCurrCharPosNum());
				}
			}else {
				error.error(1, lexeme.getCurrCharPosNum());
			}
		}else {
			error.error(0, lexeme.getCurrCharPosNum());
		}
	}
	
	private void DECPART() {
		while(nexttoken.getSym() == Symbol.IDENT) {
			OBJECTDEC();
	}
		}
	
	private void OBJECTDEC() {
		nexttoken = lexeme.getToken();
		while(nexttoken.getSym() == Symbol.COMMA) {
			nexttoken = lexeme.getToken();
			if(nexttoken.getSym() == Symbol.IDENT) {
				nexttoken = lexeme.getToken();
			}else {
				error.error(1, lexeme.getCurrCharPosNum());;
			}
		}
		if(nexttoken.getSym() == Symbol.COLON) {
			nexttoken = lexeme.getToken();
			if(nexttoken.getSym() == Symbol.BOOLSYM ||nexttoken.getSym() == Symbol.INTSYM) {
				nexttoken = lexeme.getToken();
				if(nexttoken.getSym() == Symbol.SEMICOLON) {
					nexttoken = lexeme.getToken();
			}else {
				error.error(9, lexeme.getCurrCharPosNum());
			}
		}else {
			error.error(3, lexeme.getCurrCharPosNum());
		}
	}else {
		error.error(10, lexeme.getCurrCharPosNum());
	}
}
	
	
	private void SEQOFSTMT() {
		while(nexttoken.getSym() == Symbol.NULLSYM || nexttoken.getSym() ==  Symbol.IDENT || 
				nexttoken.getSym() == Symbol.IFSYM || nexttoken.getSym() == Symbol.WHILESYM || 
				nexttoken.getSym() == Symbol.GETSYM || nexttoken.getSym() == Symbol.PUTSYM ||
				nexttoken.getSym() == Symbol.NEWLINE) {
					STATEMENT();
		}
			
		
	}
	
	private void STATEMENT() {
		if(nexttoken.getSym() == Symbol.NULLSYM) {
			nexttoken = lexeme.getToken();
			CHECKSYM(Symbol.SEMICOLON, 9);
		}else if(nexttoken.getSym() == Symbol.IDENT){
			nexttoken = lexeme.getToken();
			CHECKSYM(Symbol.BECOMES , 11);
			EXPRESSION();
			CHECKSYM(Symbol.SEMICOLON, 9);
		}else if(nexttoken.getSym() == Symbol.IFSYM) {
			nexttoken = lexeme.getToken();
			EXPRESSION();
		    CHECKSYM(Symbol.THENSYM, 13);
			SEQOFSTMT();
			if(nexttoken.getSym() == Symbol.ELSESYM) {
				nexttoken = lexeme.getToken();
				SEQOFSTMT();
			}
			CHECKSYM(Symbol.ENDSYM, 7);
			CHECKSYM(Symbol.IFSYM, 14);
			CHECKSYM(Symbol.SEMICOLON, 9);
		}else if(nexttoken.getSym() == Symbol.WHILESYM) {
			nexttoken = lexeme.getToken();
			EXPRESSION();
			CHECKSYM(Symbol.LOOPSYM, 15);
			SEQOFSTMT();
			CHECKSYM(Symbol.ENDSYM, 7);
			CHECKSYM(Symbol.LOOPSYM, 15);
			CHECKSYM(Symbol.SEMICOLON, 9);
		}else if(nexttoken.getSym() == Symbol.GETSYM) {
				nexttoken = lexeme.getToken();
				CHECKSYM(Symbol.LPAREN, 16);
				if(nexttoken.getSym() == Symbol.IDENT) {
					nexttoken = lexeme.getToken();
					while(nexttoken.getSym() == Symbol.COMMA) {
						nexttoken = lexeme.getToken();
						if(nexttoken.getSym() == Symbol.IDENT) {
							nexttoken = lexeme.getToken();
						}else {
							error.error(1, lexeme.getCurrCharPosNum());
						}
					}
				}
				CHECKSYM(Symbol.RPAREN, 6);
				CHECKSYM(Symbol.SEMICOLON, 9);
		}else if(nexttoken.getSym() == Symbol.PUTSYM){
			nexttoken = lexeme.getToken();
			CHECKSYM(Symbol.LPAREN, 16);
			if(nexttoken.getSym() == Symbol.IDENT) {
				nexttoken = lexeme.getToken();
				while(nexttoken.getSym() == Symbol.COMMA) {
					nexttoken = lexeme.getToken();
					if(nexttoken.getSym() == Symbol.IDENT) {
						nexttoken = lexeme.getToken();
					}else {
						error.error(1, lexeme.getCurrCharPosNum());
					}
				}
			}
			CHECKSYM(Symbol.RPAREN, 6);
			CHECKSYM(Symbol.SEMICOLON, 9);
		}else if(nexttoken.getSym() == Symbol.NEWLINE) {
			nexttoken = lexeme.getToken();
			CHECKSYM(Symbol.SEMICOLON, 9);
		}
	
	}
	
	private void EXPRESSION() {
		SIMPEXPR();
		if (nexttoken.getSym() == Symbol.EQL || nexttoken.getSym() == Symbol.NEQ || nexttoken.getSym() == Symbol.LSS
				|| nexttoken.getSym() == Symbol.LEQ || nexttoken.getSym() == Symbol.GTR
				|| nexttoken.getSym() == Symbol.GEQ) {
			nexttoken = lexeme.getToken();
			SIMPEXPR();
		}
	}

	private void SIMPEXPR() {
		TERM();
		while (nexttoken.getSym() == Symbol.PLUS || nexttoken.getSym() == Symbol.MINUS) {
			nexttoken = lexeme.getToken();
			TERM();
		}
	}

	private void TERM() {
		PRIMARY();
		while (nexttoken.getSym() == Symbol.TIMES || nexttoken.getSym() == Symbol.SLASH
				|| nexttoken.getSym() == Symbol.REMSYM) {
			nexttoken= lexeme.getToken();
			PRIMARY();
		}
	}

	private void PRIMARY() {
		if (nexttoken.getSym() == Symbol.LPAREN) {
			nexttoken= lexeme.getToken();
		    EXPRESSION();
		    CHECKSYM(Symbol.RPAREN,7);
		}
		else if(nexttoken.getSym() == Symbol.IDENT)
			nexttoken = lexeme.getToken();

		else if (nexttoken.getSym() == Symbol.NUMLIT)
			nexttoken = lexeme.getToken();

		else if (nexttoken.getSym() == Symbol.TRUESYM)
			nexttoken = lexeme.getToken();

		else if (nexttoken.getSym() == Symbol.FALSESYM)
			nexttoken = lexeme.getToken();

		 else {
			error.error(13, lexeme.getCurrCharPosNum());
		
	}
		}
	
	private void CHECKSYM(Symbol sym, int errorcode) {
		if(nexttoken.getSym() == sym) {
			nexttoken = lexeme.getToken();
		}else {
			error.error(errorcode,  lexeme.getCurrCharPosNum());
		}
	}
	
	}	

