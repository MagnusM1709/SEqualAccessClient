import java.util.Arrays;

public class DatabaseConnectorClient extends DatabaseConnector {
	private QueryResult currentQueryResult = null;
	private String message = null;
	private DatabaseClient dc;
	private boolean gotMessage;
	private boolean gotError;
	private long timeOutTime = 10000;

	public DatabaseConnectorClient(String pIP, int pPort, String pDatabase, String pUsername, String pPassword) {
		super("", 0, "", "", "");
		dc = new DatabaseClient(pIP, pPort, pDatabase, this);
	}

	public void executeStatement(String pSQLStatement) {
		// Altes Ergebnis loeschen
		currentQueryResult = null;
		message = null;
		dc.execute(pSQLStatement);
		gotError = false;
		gotMessage = false;
		long startMillis = System.currentTimeMillis();
		while(!(gotMessage && gotError) && (timeOutTime  > (System.currentTimeMillis() - startMillis))) {
			try {
				Thread.sleep(1); // Make it possible for Interupt handler to exit this while loop 
			} catch (InterruptedException e) {}
		}
		try {
			Thread.sleep(20); // need to be , donno why
		} catch (InterruptedException e) {}
	}
	
	public void setQuerryResult(String string) {
		if(string.equals("Ok")) {
			System.out.println("Client connected successfull");
			gotMessage = true;
			gotError = true;
		}else {
			if(string.startsWith("Msg:")){
				currentQueryResult = toQuerryResult(string.substring(4));
				gotMessage = true;
			}
			if(string.startsWith("Err:")) {
				message = string.substring(4).equals("null")?null:string.substring(4);
				gotError = true;
			}
		}

	}

	public QueryResult toQuerryResult(String string) {
		String[] rows = string.split("\f");
		String[] pColumnNames = Arrays.copyOf(rows[0].split("\t"), rows[0].split("\t").length);
		String[] pColumnTypes = Arrays.copyOf(rows[1].split("\t"), rows[1].split("\t").length);
		String[][] pData = new String[rows.length-2][rows[2].split("\t").length];
		for(int i = 0; i < rows.length-2; i++) {
			for(int j = 0; j < rows[i+2].split("\t").length; j++) {
				pData[i][j] = rows[i+2].split("\t")[j];
			}
		}
		
		return new QueryResult(pData, pColumnNames, pColumnTypes);
	}

	/**
	 * Die Anfrage liefert das Ergebnis des letzten mit der Methode executeStatement
	 * an die Datenbank geschickten SQL-Befehls als Ob-jekt vom Typ QueryResult
	 * zurueck. Wurde bisher kein SQL-Befehl abgeschickt oder ergab der letzte
	 * Aufruf von executeStatement keine Ergebnismenge (z.B. bei einem INSERT-Befehl
	 * oder einem Syntaxfehler), so wird null geliefert.
	 */
	public QueryResult getCurrentQueryResult() {
		return currentQueryResult;
	}

	/**
	 * Die Anfrage liefert null oder eine Fehlermeldung, die sich jeweils auf die
	 * letzte zuvor ausgefuehrte Datenbankoperation bezieht.
	 */
	public String getErrorMessage() {
		return message;
	}

	/**
	 * Die Datenbankverbindung wird geschlossen.
	 */
	public void close() {
		try {
			dc.close();
		} catch (Exception e) {
			message = e.getMessage();
		}
	}
}
