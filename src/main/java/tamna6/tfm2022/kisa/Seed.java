package tamna6.tfm2022.kisa;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Seed {
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final byte[]  pbszUserKey = "업프로젝트오이김ㅠ_ㅠㅋ캬캬@@#".getBytes();
    private static final byte[] pbszIV = "1234567891234346".getBytes();

    public static String seedEncrypt(String rawMessage){
        Base64.Encoder encoder = Base64.getEncoder();
        System.out.println('\n' + "<< Seed - 암호화 >>");
        System.out.println("-SEED 암호화 대상 : " + '\n' + rawMessage);

        byte[] message = rawMessage.getBytes(UTF_8);
        byte[] encryptedMessage = KISA_SEED_CBC.SEED_CBC_Encrypt(pbszUserKey,pbszIV, message, 0, message.length);
        return new String(encoder.encode(encryptedMessage), UTF_8);
    }

    public static String seedDecrypt(String encryptedMessage){
        Base64.Decoder decoder = Base64.getDecoder();  //왜 MimeDecoder
        System.out.println('\n' + "<< Seed - 복호화 >>");
        System.out.println("-SEED 복호화 대상 : " + encryptedMessage);

        byte[] message = decoder.decode(encryptedMessage);
        byte[] decryptedMessage = KISA_SEED_CBC.SEED_CBC_Decrypt(pbszUserKey,pbszIV, message, 0, message.length);
        String res = new String(decryptedMessage, UTF_8);
        //  System.out.println("ㅇSEED 복호화 결과 : " + res);
        return res;
    }
}