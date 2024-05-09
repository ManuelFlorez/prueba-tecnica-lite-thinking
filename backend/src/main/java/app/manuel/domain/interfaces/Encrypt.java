package app.manuel.domain.interfaces;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

public interface Encrypt {
    String encrypt(String property) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;
    String decrypt(String string) throws GeneralSecurityException, IOException;
}
