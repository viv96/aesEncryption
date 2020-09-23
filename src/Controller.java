import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

    // declaring all the components for the layout
    @FXML
    private TextField plainText;
    @FXML
    private TextField secretKey1;
    @FXML
    private TextField encrypted;
    @FXML
    private TextField cypherText;
    @FXML
    private TextField secretKey2;
    @FXML
    private TextField decrypted;

    @FXML
    private void initialize() {
    }

    //handler for encrypt button
    @FXML
    public void encryptButtonHandler(){
        String pText = plainText.getText();
        String key = secretKey1.getText();
        encrypted.setText(Main.encrypt(pText, key));
    }

    //handler for decrypt button
    @FXML
    public void decryptButtonHandler(){
        String cText = cypherText.getText();
        String key = secretKey2.getText();
        decrypted.setText(Main.decrypt(cText, key));
    }
}
