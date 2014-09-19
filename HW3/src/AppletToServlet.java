import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class AppletToServlet extends HttpServlet {
// Getting a String object from the applet and send it back.
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
try {
response.setContentType("application/x-java-serialized-object");
InputStream inputStream = request.getInputStream();
ObjectInputStream inputFromApplet = new ObjectInputStream(inputStream);
String string = (String) inputFromApplet.readObject();
// getting string value and passing to applet
OutputStream outputStream = response.getOutputStream();
ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
objectOutputStream.writeObject(string);
objectOutputStream.flush();
objectOutputStream.close();
} catch (Exception e) {
e.printStackTrace();
}
}
}