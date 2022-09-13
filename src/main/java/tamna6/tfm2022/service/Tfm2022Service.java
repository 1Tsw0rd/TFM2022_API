package tamna6.tfm2022.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tamna6.tfm2022.dto.TfmAuthDto;
import tamna6.tfm2022.dto.TfmUserDto;
import tamna6.tfm2022.entity.Player;
import tamna6.tfm2022.entity.Team;
import tamna6.tfm2022.entity.TfmAuth;
import tamna6.tfm2022.entity.TfmUser;
import tamna6.tfm2022.kisa.HojinTokenFactory;
import tamna6.tfm2022.repository.PlayerRepository;
import tamna6.tfm2022.repository.TeamRepository;
import tamna6.tfm2022.repository.TfmAuthRepository;
import tamna6.tfm2022.repository.TfmUserRepository;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class Tfm2022Service {
    @Autowired
    private TfmUserRepository tfmUserRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TfmAuthRepository tfmAuthRepository;

    public List<Team> showTeamList() {
        return teamRepository.findAll();
    }

    public List<Player> showTeamDetail(Long tno) {
        List<Player> players = playerRepository.findByPlayer(tno);

        System.out.println("zidh" + players.toString());
        return players;
    }

    @Transactional
    public String signup(TfmUserDto dto) {

        //ID중복 검사
        TfmUser tfmUser = tfmUserRepository.findById(dto.getId()).orElseGet(() -> null);
        log.info("비번 : " + dto.getPw());

        dto.setPw("{noop}" + dto.getPw());
        log.info("dto : " + dto);

        LocalDateTime nowTime = LocalDateTime.now(); //가입시간 tfm_user createdate
        nowTime = nowTime.withNano(0); //0으로 설정하면 나노초 9자 모두 제거.. 0이 많으면 짤리는 현상 있이서 사용
        log.info("nowTime : " + nowTime);

        dto.setCreatedate(nowTime);
        log.info("date작업한 dto" + dto);

        //log.info("tfmUser : " + String.valueOf(tfmUser));

        String res = "";
        if (tfmUser != null) {  //null이라면 중복된 계정명이 있다는 것
            //log.info("noo:" + tfmUser);
            res = "Fail";
        } else if (tfmUser == null) { //null이라면 계정등록해도 괜찮
            //log.info("ok : " + tfmUser);
            tfmUser = dto.toEntity();
            //log.info("ok2 : " + tfmUser);
            tfmUser = tfmUserRepository.save(tfmUser);
            res = "OK";
        }

        return res;
    }

    //1차 검증 - ID/PW 조작 여부 체크
    public String checkIdAndPw(TfmUserDto dto) {
        String result = "";

        if (dto.getId() != null && dto.getPw() != null) {
            //ID 체크
            String selectId = tfmUserRepository.findByUserId(dto.getId()); //id 조회값
            //PW 체크
            String noopPw = "{noop}" + dto.getPw(); //예진님 양념, 해킹 어렵게
            String selectPw = tfmUserRepository.findByPw(selectId, noopPw); //pw 조회값

//            log.info("selectId : " + selectId);
//            log.info("dto.getId(): " + dto.getId());
//            log.info("noopPw : " + noopPw.length());
//            log.info("selectPw : " + selectPw.length());

            //ID 및 PW 입력값과 DB값 비교
            if (selectId.equals(dto.getId()) && selectPw.equals(noopPw)) {  //=는 주소값을 비교하고, equals 메소드는 값 자체를 비교
                log.info("이상없음");
                result = selectId;
            } else {
                log.info("이상있음111");
                result = "Error1";
            }
        } else {
            log.info("이상있음222");
            result = "Error1";
        }

        return result;
    }

    @Transactional
    public String checkLogin(TfmUserDto dto) {
        String hojinToken = "";
        LocalDateTime nowTime = LocalDateTime.now(); //tfm_auth lastlogin 저장용
        nowTime = nowTime.withNano(0);//0으로 설정하면 나노초 9자 모두 제거.. 0이 많으면 짤리는 현상 있이서 사용
        HojinTokenFactory hojinTokenFactory = new HojinTokenFactory(); //토큰생성용

        TfmAuth CheckAuthId = tfmAuthRepository.findById(dto.getId()).orElse(null);
        log.info("CheckAuthId : " + CheckAuthId);

        //1. 토큰 생성 후 저장 - 최초 로그인(tfm_auth 안에 값 없는 경우)
        if (CheckAuthId == null) {
            //1-1. 저장할 Dto 준비
            TfmAuthDto authDto = new TfmAuthDto(
                    dto.getId(),
                    null,
                    nowTime,
                    null,
                    nowTime
            );

            log.info("CheckAuthId null authDto 1 : " + authDto);
            //1-2. 토큰 생성
            hojinToken = hojinTokenFactory.CreateToken(authDto);
            //1-3. 토큰 세팅
            authDto.setToken(hojinToken);
            //1-4. authDto -> Entity변환
            TfmAuth tfmAuth = authDto.toEntity();
            //1-5. tfm_auth DB저장
            tfmAuthRepository.save(tfmAuth);
        } else if (CheckAuthId != null) {
            //A-0. 로그인 이력 있는 경우(CheckAuthId != null)

            //]]]]]]]]최초 로그인 후 로그아웃 안하는 경우 lastlogout 값 null 이므로 오류가 나므로 아래처럼 처리...이거 authchecker로 해결하자

            //조건1. 현재시간(로그인하려는) > 최근로그아웃시간 => nowTime.isAfter(CheckAuthId.getLastlogout())
              if(nowTime.isAfter(CheckAuthId.getLastlogout())){
                //조건2. DB 로그인시간 < 로그아웃시간 => CheckAuthId.getLastlogin().isBefore(CheckAuthId.getLastlogout())
                if (CheckAuthId.getLastlogin().isBefore(CheckAuthId.getLastlogout())){
                    //조건3. token 값 null이어야 함
                    if(CheckAuthId.getToken() == null){
                        //A-1. 저장할 Dto 준비
                        TfmAuthDto authDto = new TfmAuthDto(
                                dto.getId(),
                                null,
                                nowTime,
                                CheckAuthId.getLastlogout(),
                                nowTime
                        );
                        log.info("CheckAuthId not null authDto 1 : " + authDto);

                        //A-2. 토큰 생성
                        hojinToken = hojinTokenFactory.CreateToken(authDto);
                        //A-3. 토큰 세팅
                        authDto.setToken(hojinToken);
                        //A-4. authDto -> Entity변환
                        TfmAuth tfmAuth = authDto.toEntity();
                        //A-5. tfm_auth DB저장
                        tfmAuthRepository.save(tfmAuth);
                    } else log.info("조건3 미충족 Error - token null"); //end 조건3
                } else log.info("조건2 미충족 Error - DB속 최근로그인시간 < 최근로그아웃시간"); //end 조건2
            } else log.info("조건3 미충족 Error - 현재시간 > 최근로그아웃시간"); //end 조건1
        } else {
            //가-1. 이상이 있는 경우
            log.info("로그인시간 > 로그아웃 시간 및 토큰 null 검증 실패");
            hojinToken = null;
        }

        log.info("hojinToken : " + hojinToken);
        return hojinToken;
    }

    @Transactional
    public TfmAuthDto authCheck(TfmAuthDto dto) {
        //1. 토큰 복호화 => id, lastlogin추출, 사용자가 보낸 token 활용
        HojinTokenFactory hojinTokenFactory = new HojinTokenFactory(); //토큰생성용
        TfmAuthDto abstractionAuthDto = hojinTokenFactory.DecryptToken(dto.getToken());

        //2-1. 검증 준비 : id로 DB Table tfm_auth의 token, lastlogin 추출
        TfmAuth DBtfmAuth = abstractionAuthDto.toEntity();

        DBtfmAuth = tfmAuthRepository.findById(abstractionAuthDto.getID()).orElse(null);
        log.info("추출값 : " + abstractionAuthDto);
        log.info("DB값 : " + DBtfmAuth);

        //2-2. 1차 검증 : 넘어온 token vs DB token
        if(abstractionAuthDto.getToken().equals(DBtfmAuth.getToken())){
            log.info("토큰값 비교 성공");

            //2-3. 2차 검증 : 복호화한 lastlogin vs DB lastlogin
            if(abstractionAuthDto.getLastlogin().equals(DBtfmAuth.getLastlogin())){
                log.info("로그인 일자 비교 성공");

                //2-4. 3차 검증 : 복호화한 lastlogin(최근로그인) > DB lastlogout(최근 로그아웃)
                //DB lastlogout null 인 경우 대체용 변수
                LocalDateTime date1 = LocalDateTime.parse("1945-08-15 16:30:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                if (DBtfmAuth.getLastlogout() != null) {
                    log.info("DB lastlogout 값 있는걸로 들어옴");
                    date1 = DBtfmAuth.getLastlogout();
                    log.info("date1 : " + date1);
                }

                log.info("date1 : " + date1);
                //본격적으로 로그인일자 > 로그아웃일자 비교
                if(abstractionAuthDto.getLastlogin().isAfter(date1)){
                    log.info("로그인일자 > 로그아웃일자 조건 달성");

                    //2-5. tfm_auth lastrequest 시간 갱신
                    LocalDateTime nowTime = LocalDateTime.now();
                    nowTime = nowTime.withNano(0);

                    log.info("lastrequest 시간 갱신 : " + nowTime + " id : " + abstractionAuthDto.getID());

                    tfmAuthRepository.updateLastrequest(nowTime, abstractionAuthDto.getID());

                } else {
                    log.info("로그인일자 > 로그아웃일자 조건 부적합");
                    abstractionAuthDto = null;
                } //3차검증 닫기

            } else {
                log.info("로그인 일자 비교 실패");
                abstractionAuthDto = null;
            }//2차검증 닫기

        } else {
            log.info("토큰값 비교 실패");
            abstractionAuthDto = null;
        }//1차검증 닫기

    return abstractionAuthDto;
    }

    @Transactional
    public String checkLogout(String token) {

        log.info("서비스 logout 들어옴 " + token);

        //1. 토큰 복호화 => id, lastlogin추출, 사용자가 보낸 token 활용
        HojinTokenFactory hojinTokenFactory = new HojinTokenFactory(); //토큰작업장
        TfmAuthDto abstractionAuthDto = hojinTokenFactory.DecryptToken(token);

        //2. DB에 저장할 값 준비
        log.info("복호화 토큰  : " + abstractionAuthDto);

        LocalDateTime nowTime = LocalDateTime.now(); //tfm_auth에 저장될 lastlogout
        nowTime = nowTime.withNano(0); //0으로 설정하면 나노초 9자 모두 제거.. 0이 많으면 짤리는 현상 있이서 사용
        log.info("nowTime : " + nowTime);

        abstractionAuthDto.setToken(null); //로그아웃 시 토큰은 삭제함
        abstractionAuthDto.setLastlogout(nowTime); //현재시간 로그아웃시간으로 저장 준비
        abstractionAuthDto.setLastrequest(null);

        log.info("수정하기 전 " + abstractionAuthDto);

        //3. Dto -> Entity 변환
        TfmAuth authDto = abstractionAuthDto.toEntity();
        authDto = tfmAuthRepository.save(authDto);
        log.info("authDto : " + authDto);

        if(authDto.getToken() == null && authDto.getLastlogout().equals(nowTime))
            return "OK";
        else
            return "Fail";
    }
}