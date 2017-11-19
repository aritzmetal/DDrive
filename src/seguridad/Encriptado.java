package seguridad;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;

public class Encriptado {

	private final static String algoritmo = "AES";
	private final static String cifrado = "AES/CBC/PKCS5Padding";

	public static String encriptar(String clave, String iv, String texto) throws Exception {
		
		Cipher cipher = Cipher.getInstance(cifrado);
		SecretKeySpec skeySpec = new SecretKeySpec(clave.getBytes(), algoritmo);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
		byte[] encriptado = cipher.doFinal(texto.getBytes());
		return new String(encodeBase64(encriptado));
		
	}

	public static String desencriptar(String key, String iv, String encriptado) throws Exception {
		Cipher cipher = Cipher.getInstance(cifrado);
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), algoritmo);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
		byte[] enc = decodeBase64(encriptado);
		
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
		byte[] desencriptado = cipher.doFinal(enc);
		return new String(desencriptado);
	}
}
