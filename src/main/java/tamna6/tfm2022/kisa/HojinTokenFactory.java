package tamna6.tfm2022.kisa;

import lombok.extern.slf4j.Slf4j;
import tamna6.tfm2022.dto.TfmAuthDto;

import java.time.LocalDateTime;

@Slf4j
public class HojinTokenFactory {
    public String CreateToken(TfmAuthDto authDto) {

        //1. 토큰 내용 : ID + 로그인시간
        String readyToken = authDto.getID() + authDto.getLastlogin();
        log.info("readyToken : " + readyToken);

        //2. 국산 대칭키 암호 SEED 암호화
        Seed seed = new Seed();
        String hojinToken = seed.seedEncrypt(readyToken);
        log.info("hojinToken : " + hojinToken);
        
        //3. 토큰 반환
        return hojinToken;
    }

    public TfmAuthDto DecryptToken(String token) {
        //1. 국산 대칭키 암호 SEED 복호화
        Seed seed = new Seed();
        String decryptToken = seed.seedDecrypt(token);
        log.info("decryptToken : " + decryptToken);
        log.info("decryptToken token : " + decryptToken.substring(decryptToken.length() -19, decryptToken.length()));

        //2. 복화화값 => ID 및 로그인 시간 추출
        String tokenInnerId = decryptToken.substring(0, decryptToken.length() -19); //ID추출
        LocalDateTime tokenInnerLastlogin = LocalDateTime.parse(decryptToken.substring(decryptToken.length() -19, decryptToken.length())); //로그인시간 추출
        log.info("tokenInnerId : " + tokenInnerId + " tokenInnerLastlogin : " + tokenInnerLastlogin); //토큰 내용 : ID + 로그인시간
        TfmAuthDto abstractionAuthDto = new TfmAuthDto(
                tokenInnerId,
                token,
                tokenInnerLastlogin,
                null,
                null
        );

        //3. 복호화된 DTO 반환
        return abstractionAuthDto;
    }

    //토큰디코딩하는걸로 만들어]]]]]]]]]]]]
//    public String testToekn(String test) {
//
//        //1. 토큰 내용 : ID + 로그인시간
//        String readyToken = test;
//        log.info("readyToken : " + readyToken);
//
//        //2. 국산 대칭키 암호 SEED 암호화
//        Seed seed = new Seed();
//        String hojinToken = seed.seedDecrypt(readyToken);
//        log.info("hojinToken seedDecrypt : " + hojinToken);
//
//        //3. 토큰 반환
//        return hojinToken;
//    }
}
