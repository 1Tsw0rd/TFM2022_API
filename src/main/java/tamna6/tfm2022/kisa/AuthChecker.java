package tamna6.tfm2022.kisa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tamna6.tfm2022.dto.TfmAuthDto;
import tamna6.tfm2022.entity.TfmAuth;
import tamna6.tfm2022.repository.TfmAuthRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class AuthChecker extends Exception {

    @Autowired
    TfmAuthRepository tfmAuthRepository;

    @Scheduled(cron = "0 0/1 * * * *") //1분마다 동작
    public void AbnormalLogout(){
        LocalDateTime nowTime = LocalDateTime.now(); //tfm_auth에 저장될 lastlogout
        nowTime = nowTime.withNano(0); //0으로 설정하면 나노초 9자 모두 제거.. 0이 많으면 짤리는 현상 있이서 사용
        //log.info("nowTime test : " + nowTime);

        //1. tfm_auth 내 모든 row 조회
        List<TfmAuth> tfmAuthList = tfmAuthRepository.findAbnormalAuth();

        //2-1. 비정상 종료 없는 경우
        if(tfmAuthList.isEmpty()){
            //log.info("empty!!!!!!!!!!!!!!!!!!" + tfmAuthList.isEmpty());
            //log.info("테스트 시작 tfmAuthDtoList.size() : " + tfmAuthList.size());
            log.info("tfm_auth 점검완료 - 이상없음");
        } else if(tfmAuthList.size() > 0){ //2-2. 비정상 종료 있는 경우
            log.info("tfmAuthList.size() > 0 : " + tfmAuthList);
            for(int i = 0; i < tfmAuthList.size(); i++){
                log.info(" i : " + i);
                TfmAuth authClearEntity = new TfmAuth(
                        tfmAuthList.get(i).getID(),
                        null,
                        tfmAuthList.get(i).getLastlogin(),
                        nowTime,
                        null
                        );
                log.info("authClearEntity: " + authClearEntity);
                tfmAuthRepository.save(authClearEntity);
            }
            log.info("tfmAuthList 정리완료");
        }//end else if
    }//end AbnormalLogout
}//end class