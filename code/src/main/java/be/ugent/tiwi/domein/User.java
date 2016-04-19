package be.ugent.tiwi.domein;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

/**
 * Created by Jeroen on 19/04/16.
 */
public class User {
    private String username;
    private String password;

    /**
     * Constructor van de klasse
     */
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = toMD5hash(password);
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = toMD5hash(password);
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    /**
     * MD5 hasher
     */
    private String toMD5hash(String unencrypted){
        String encrypted = "";
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(unencrypted.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            String hashtekst = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while(hashtekst.length() < 32 ){
                hashtekst = "0"+hashtekst;
            }
            encrypted = hashtekst;
        }
        catch(NoSuchAlgorithmException exceptie){
            System.out.println("Password encryptor error: " + exceptie.toString());
        }
        return encrypted;
    }

    /**
     * tostring
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{Username=");
        sb.append(this.username);
        sb.append(", password=");
        sb.append(this.password);
        sb.append("}");
        return sb.toString();
    }
}
