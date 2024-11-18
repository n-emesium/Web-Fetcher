//AA-00-04-00-XX-YY address format, change layers later;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class Fetcher {
    private URL url;    
    private HttpURLConnection connection;
    public Fetcher(String s) {
        try {
            if (!s.startsWith("http://") && !s.startsWith("https://")) {
                s = "https://" + s;
            }
            url = new URL(s);
            connection = (HttpURLConnection) url.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String fetch() {
        try {
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                content.append(line);
            }
            in.close();
            
            Pattern pattern = Pattern.compile("<div id=\"random_word\">(.*?)</div>");
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                String randomWord = matcher.group(1);
                System.out.println("Random word: " + randomWord);
                return randomWord;
            } else {
                System.out.println("Word not found. ");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
