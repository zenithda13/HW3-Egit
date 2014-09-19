import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class AppletToServletExample extends Applet {
  private TextField inputField = new TextField(10);
  private TextField resultField = new TextField(10);

  public void init() {
    // add input label, field and send button
    add(new Label("Input Your Name", Label.RIGHT));
    add(inputField);
    Button sendButton = new Button("Send");
    add(sendButton);
    sendButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        SendData();
      }
    });
    // add output label as a non editable field
    add(new Label("Output:", Label.RIGHT));
    add(resultField);
    resultField.setEditable(false);
  }

  // Get a connection to the servlet.
  private URLConnection getServletConnection() throws MalformedURLException,
      IOException {
    URL urlServlet = new URL(getCodeBase(), "applettoservlet");
    URLConnection con = urlServlet.openConnection();
    con.setDoInput(true);
    con.setDoOutput(true);
    con.setUseCaches(false);
    con.setRequestProperty("Content-Type",
        "application/x-java-serialized-object");
    return con;
  }

  // Send the inputField data to the servlet and show the result in the
  // outputField.
  private void SendData() {
    try {
      String input = inputField.getText();
      // send data to the servlet
      URLConnection con = getServletConnection();
      OutputStream outputStream = con.getOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(outputStream);
      oos.writeObject(input);
      oos.flush();
      oos.close();
      // receive result from servlet
      InputStream inputStream = con.getInputStream();
      ObjectInputStream inputFromServlet = new ObjectInputStream(
          inputStream);
      String result = (String) inputFromServlet.readObject();
      inputFromServlet.close();
      inputStream.close();
      // show result
      resultField.setText(result);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}