import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * 
 */

/**
 * @author Administrator
 *
 */
public class TestJava {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			checkFieldForSSRF("http://localhost/tssa-dw");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean checkFieldForSSRF(String hostName) {
		URL url = null;
		if (hostName != null && hostName.startsWith("http")) {
			try {
				url = new URL(hostName);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return false;
			}
			hostName = url.getHost();
			if (!url.getProtocol().startsWith("http")) {
				return false;
			}
		}
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getByName(hostName);

			if (inetAddress.isAnyLocalAddress() || inetAddress.isLoopbackAddress()
					|| inetAddress.isLinkLocalAddress()) {
				return false;
			}
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			return false;
		}
		if (url != null) {
			try {
				HttpURLConnection conn = (HttpURLConnection) (url.openConnection());
				conn.setInstanceFollowRedirects(false);
				conn.connect();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;

	}
}

