import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Main extends Application {

    private static SecretKeySpec secretKey;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        loginStage(stage);
    }

    //starting the scene
    private void loginStage(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View.fxml"));
            Scene loginScene = new Scene(root, 430, 530);
            stage.setTitle("AES");
            stage.setScene(loginScene);
            stage.show();
        } catch (IOException e) {
            System.out.print("Failed to load View.fxml file.");
        }
    }

    //expanding and dividing the key
    public static void setKey(String skey){

        try {
            byte[] key = skey.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //method to encrypt the plaitext
    public static String encrypt(String text, String key){
        try{
            setKey(key);
            Cipher cipherKey = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipherKey.init(Cipher.ENCRYPT_MODE,secretKey);
            return Base64.getEncoder().encodeToString(cipherKey.doFinal(text.getBytes("UTF-8")));
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //method to decrypt the cipher_keytext
    public static String decrypt(String text, String key){
        try{
            setKey(key);
            Cipher cipherKey = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipherKey.init(Cipher.DECRYPT_MODE,secretKey);
            return new String(cipherKey.doFinal(Base64.getDecoder().decode(text)));
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
