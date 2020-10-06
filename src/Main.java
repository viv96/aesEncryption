import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;


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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //method to encrypt the plaitext
    public static String encrypt(String text, String key){
        try{
            StringBuffer sb = new StringBuffer();
            setKey(key);
            Cipher cipherKey = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipherKey.init(Cipher.ENCRYPT_MODE,secretKey);
            byte[] encrypted = cipherKey.doFinal(text.getBytes());
            for (int i = 0; i < encrypted.length; i++) {
                String hex = Integer.toHexString(encrypted[i] & 0xFF);
                if (hex.length() == 1){
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //method to decrypt the cipher_keytext
    public static String decrypt(String text, String key){
        if (text.length()<1){
            return null;
        }
        byte[] t = new byte[text.length()/2];
        for (int i = 0; i < text.length()/2; i++) {
            int high = Integer.parseInt(text.substring(i*2,i*2+1),16);
            int low = Integer.parseInt(text.substring(i*2+1, i*2+2), 16);
            t[i] = (byte) (high*16+low);
        }
        try{
            setKey(key);
            Cipher cipherKey = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipherKey.init(Cipher.DECRYPT_MODE,secretKey);
            byte[] decrypt = cipherKey.doFinal(t);
            return new String(decrypt);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
