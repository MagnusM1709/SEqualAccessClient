/**
 * <p>
 * Materialien zu den zentralen NRW-Abiturpruefungen im Fach Informatik ab 2018.
 * </p>
 * <p>
 * Klasse QueryResult
 * </p>
 *
 * <p>
 * Ein Objekt der Klasse QueryResult stellt die Ergebnistabelle einer Datenbankanfrage mit Hilfe 
 * der Klasse DatabaseConnector dar. Objekte dieser Klasse werden nur von der Klasse DatabaseConnector erstellt. 
 * Die Klasse verfuegt ueber keinen oeffentlichen Konstruktor.
 * </p>
 *
 * <p>
 * Erweitert um toString-Funktion
 * </p>
 * @author Qualitaets- und UnterstuetzungsAgentur - Landesinstitut fuer Schule
 * @author Magnus MÃ¼ller 
 * @version 2015-01-31
 */
public class QueryResult{
  private String[][] data;
  private String[] columnNames;
  private String[] columnTypes;

  /**
   * Paketinterner Konstruktor.
   */
  QueryResult(String[][] pData, String[] pColumnNames, String[] pColumnTypes){
    data = pData;
    columnNames = pColumnNames;   
    columnTypes = pColumnTypes;
  }

  /**
   * Die Anfrage liefert die Eintraege der Ergebnistabelle als zweidimensionales Feld
   * vom Typ String. Der erste Index des Feldes stellt die Zeile und der zweite die 
   * Spalte dar (d.h. Object[zeile][spalte]).
   */
  public String[][] getData(){
    return data;
  }

  /**
   * Die Anfrage liefert die Bezeichner der Spalten der Ergebnistabelle als Feld vom 
   * Typ String zurueck.
   */
  public String[] getColumnNames(){
    return columnNames;
  }

  /**
   * Die Anfrage liefert die Typenbezeichnung der Spalten der Ergebnistabelle als Feld 
   * vom Typ String zurueck. Die Bezeichnungen entsprechen den Angaben in der MySQL-Datenbank.
   */
  public String[] getColumnTypes(){
    return columnTypes;
  }

  /**
   * Die Anfrage liefert die Anzahl der Zeilen der Ergebnistabelle als Integer.
   */
  public int getRowCount(){
    if (data != null )
      return data.length;
    else 
      return 0;
  }

  /**
   * Die Anfrage liefert die Anzahl der Spalten der Ergebnistabelle als Integer.
   */
  public int getColumnCount(){
    if (data != null && data.length > 0 && data[0] != null)
      return data[0].length;
    else
      return 0;
  }
  /**
   * Gibt die Anfrage als Formatierten String in Tabellenform zurueck
   */
  public String toString() {
	  return toString(8);
  }
  /**
   * Gibt die Anfrage als Formatierten String in Tabellenform zurueck
   * @param tabWidth die Maximale Breite einer Spalte
   */
  public String toString(int tabWidth) {
	  String returnString = "";
	  for (String name: columnNames) {
		  returnString += reduce(name,tabWidth)  + "\t";
	  }
	  returnString += "\n";
	  
	  for (String type: columnTypes) {
		  returnString +=  reduce(type,tabWidth) + "\t";
	  }
	  returnString += "\n";
	  returnString += "\n";
	  
	  for (String date2[]: data) {
		  for (String date1: date2) {
			  returnString +=  reduce(date1,tabWidth) + "\t";
		  }
		  returnString += "\n";
	  }
	  
	  
	  return returnString;
  }

  public String toUnformattedString() {
	 String returnString = "";
	  for (String name: columnNames) {
		  returnString += name + "\t";
	  }
	  //System.out.println(returnString);
	  returnString += "\f";
	  
	  for (String type: columnTypes) {
		  returnString += type + "\t";
	  }
	  returnString += "\f";
	  
	  for (String date2[]: data) {
		  for (String date1: date2) {
			  returnString += date1 + "\t";
		  }
		  returnString += "\f";
	  }
	  //System.out.println(returnString);
	  
	  return returnString;
  }
  
  private String reduce(String string, int length){
	  return string.length() >= length?string.substring(0,length-1):string;
  }

}
