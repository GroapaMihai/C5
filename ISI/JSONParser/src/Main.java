import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	public static String getQuery(String str) {
		return "UPDATE address SET fk_country = (SELECT country.id FROM country WHERE country.country_shortname = '" + str + "') WHERE address.country_shortname = '" + str + "';\n";
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\Countries.txt"), "UTF8"));

		String str;

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\Countries_FK.txt"), "UTF-8"));

		while ((str = in.readLine()) != null) {			
			out.write(getQuery(str));
		}

        in.close();
        out.close();
	}
}
