import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The JAVAWebClient class implements the web client.
 * 
 * @author	Eow Doo Nee
 * @version	1.0
 * */
public class JAVAWebClient {

	private final String CRLF = "\r\n";
	//private final String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)";
	private final String USER_AGENT = "NETWORKPROTOCOL";
	private final int TIMEOUT_DURATION = 30000;
	
	public static void main(String[] args) throws Exception {
		System.out.println("Setting Up Web Client");
		JAVAWebClient webClient = new JAVAWebClient();
		
		System.out.println("\n==============================\n");

		System.out.println("Send HTTP GET request");
		System.out.println("Enter the URL to send the GET request to: ");
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String GETrequestURL = input.readLine();
		System.out.println("The URL you have entered is: " + GETrequestURL);
		System.out.println("Calling sendGETrequest...\n");
		System.out.println(webClient.sendGETrequest(GETrequestURL));

		System.out.println("Enter the Answer to send: ");
		String answer = input.readLine();
		System.out.println("Enter the URL to send this answer to: ");
		String answerURL = input.readLine();
		System.out.println("Calling sendPOSTrequest...\n");
		System.out.println(webClient.sendPOSTrequest(answerURL, answer));

	}

	/**
	 * Forms a GET request to send to a specified URL
	 * 
	 * @param	urlString	- URL to send the GET request to
	 * @return	Response from web server
	 * */
	private String sendGETrequest(String urlString) throws Exception {
		// URL Sanity Check
		if (urlString == null || urlString.length() == 0) return null;
		
		// URL Input Check
		urlString = ( urlString.startsWith("http://") || urlString.startsWith("https://") ) ? urlString : ("http://" + urlString).intern();
		
		// Creating URL Object
		URL url = new URL(urlString);
		HttpURLConnection URLconnection = (HttpURLConnection) url.openConnection();

		// Set Request Method (Optional default is GET)
		URLconnection.setRequestMethod("GET");
		
		// Set Header Field: User-Agent
		URLconnection.setRequestProperty("User-Agent", USER_AGENT);
		
		// Set Header Field: Accept
		URLconnection.setRequestProperty("Accept", "text/html");
		
		// Set Header Field: Timeout
		URLconnection.setConnectTimeout(TIMEOUT_DURATION);
		
		System.out.println("Sending GET request to specified URL : " + urlString + "\n");
		
		// Get Response Code
		int responseCode = URLconnection.getResponseCode();
		System.out.println("Response Code: " + responseCode + "\n");
		
		// Return exception error if Response Code is not 200 (OK)
		try {
			if (URLconnection.getResponseCode() != HttpURLConnection.HTTP_OK) return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		// Send GET Request
		BufferedReader inputStream = new BufferedReader(new InputStreamReader(URLconnection.getInputStream()));
		String inputLine = null;
		StringBuffer stringBuffer = new StringBuffer();

		while ((inputLine = inputStream.readLine()) != null) {
			stringBuffer.append(inputLine).append(CRLF);
		}
		
		// Close streams and URLconnection
		if (inputStream != null) inputStream.close();
		if (URLconnection != null) URLconnection.disconnect();

		return stringBuffer.toString();
	}
	
	/**
	 * Forms a POST request (with data) to send to a specified URL
	 * 
	 * @param	urlString	- URL to send the POST request to
	 * @param	data		- Data to be included in the POST request
	 * @return	Response from web server
	 * */
	private String sendPOSTrequest(String urlString, String data) throws Exception {
		// URL Sanity Check
		if (urlString == null || urlString.length() == 0) return null;

		// URL Input Check
		urlString = ( urlString.startsWith("http://") || urlString.startsWith("https://") ) ? urlString : ("http://" + urlString).intern();

		// Creating URL Object
		URL url = new URL(urlString);
		HttpURLConnection URLconnection = (HttpURLConnection) url.openConnection();

		// Set Request Method (Optional default is GET)
		URLconnection.setRequestMethod("POST");

		// Set Header Field: User-Agent
		URLconnection.setRequestProperty("User-Agent", USER_AGENT);

		// Set Header Field: Accept
		URLconnection.setRequestProperty("Accept", "text/html");

		// Set Header Field: Timeout
		URLconnection.setConnectTimeout(TIMEOUT_DURATION);
		URLconnection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(URLconnection.getOutputStream());
		wr.writeBytes(data);
		wr.flush();
		wr.close();
		System.out.println("Sending POST request to specified URL : " + urlString + "\n");

		// Get Response Code
		int responseCode = URLconnection.getResponseCode();
		System.out.println("Response Code: " + responseCode + "\n");

		// Return exception error if Response Code is not 200 (OK)
		try {
			if (URLconnection.getResponseCode() != HttpURLConnection.HTTP_OK) return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// Send GET Request
		BufferedReader inputStream = new BufferedReader(new InputStreamReader(URLconnection.getInputStream()));
		String inputLine = null;
		StringBuffer stringBuffer = new StringBuffer();

		while ((inputLine = inputStream.readLine()) != null) {
			stringBuffer.append(inputLine).append(CRLF);
		}

		// Close streams and URLconnection
		if (inputStream != null) inputStream.close();
		if (URLconnection != null) URLconnection.disconnect();

		return stringBuffer.toString();

	}
}
