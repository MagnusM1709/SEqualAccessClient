
public class DatabaseClient extends Client{
	
	private DatabaseConnectorClient databaseConnectorClient;



	public DatabaseClient(String pServerIP, int pServerPort, String pDatabase, DatabaseConnectorClient databaseConnectorClient) {
		super(pServerIP, pServerPort);
		this.databaseConnectorClient = databaseConnectorClient;
		send("File:" + pDatabase);
	}

	@Override
	public void processMessage(String pMessage) {
		databaseConnectorClient.setQuerryResult(pMessage);
	}
	

	
	public void execute(String pCommand) {
		send("Cmd:" + pCommand);
	}

}
