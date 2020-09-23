import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

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

    @FXML
    public void encryptButtonHandler(){
        String pText = plainText.getText();
        String key = secretKey1.getText();
        encrypted.setText(Main.encrypt(pText, key));
    }

    @FXML
    public void decryptButtonHandler(){
        String cText = cypherText.getText();
        String key = secretKey2.getText();
        decrypted.setText(Main.decrypt(cText, key));
    }
}
