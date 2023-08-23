package app;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class App {

    /**
     * Die Hauptmethode, die das Programm ausführt.
     *
     * @param args Die Befehlszeilenargumente (hier nicht verwendet).
     * @throws IOException Falls ein Problem mit Ein-/Ausgabe auftritt.
     */
    public static void main(String[] args) throws IOException {
        // Erzeugen einer URL, um eine HTTP-Verbindung zu erstellen
        URL url = new URL("http://192.168.178.87:8080/get?pressure");

        // Öffnen einer HTTP-Verbindung zur angegebenen URL
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        System.out.println("\nSending 'GET' request to URL : " + url);

        // Einrichten eines Scanners, um die Eingabe von der HTTP-Verbindung zu lesen
        Scanner reader = new Scanner(con.getInputStream());
        String inputLine = "";
        
        // Lesen der Eingabezeilen von der HTTP-Verbindung und Anhängen an inputLine
        while (reader.hasNext()) {
            inputLine = inputLine + reader.nextLine();
        }
        reader.close();

        // Ausgabe der empfangenen HTTP-Antwort
        System.out.println("HTTP Response: " + inputLine);
        
        // Erstellen eines JSON-Objekts aus der empfangenen Eingabezeile
        JSONObject obj = new JSONObject(inputLine);
        
        // Extrahieren des Druckwerts aus dem JSON-Objekt und Ausgabe
        double pressureValue = obj.getJSONObject("buffer")
                                 .getJSONObject("pressure")
                                 .getJSONArray("buffer")
                                 .getDouble(0);
        System.out.println("Druck ist: " + pressureValue + " mBAR");
    }
}
