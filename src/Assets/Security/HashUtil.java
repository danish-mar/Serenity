package Assets.Security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static String hashString(String input) {
        try {
            // Get an instance of the SHA-256 MessageDigest
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Perform the hash computation
            byte[] encodedhash = digest.digest(input.getBytes());

            // Convert byte array into a hexadecimal string
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Helper method to convert byte array to a hexadecimal string
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static boolean checkHash(String hash, String input) {
        String inputHash = hashString(input);
        return hash.equals(inputHash);
    }
}
