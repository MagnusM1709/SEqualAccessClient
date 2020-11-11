# SEqualAccessClient

Needs SEqualAccessServer running:
https://github.com/MagnusM1709/SEqualAccessServer

Use is the same as in DatabaseConnector from Standardsicherung NRW exept 
DatabaseConnector variableName = new DatabaseConnector(...);
it is 
DatabaseConnector variableName = new DatabaseConnectorClient(...);

pIp has to be the IP of the Computer running SEqualAccessServer or localhost if the same Computer
pPort has to be 50009 
pDatabase the absolute (!) path to the access DB
pUsername and pPassword are ignored
