package tamna6.tfm2022.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tamna6.tfm2022.dto.TeamDto;
import tamna6.tfm2022.dto.TfmAuthDto;
import tamna6.tfm2022.dto.TfmUserDto;
import tamna6.tfm2022.entity.Player;
import tamna6.tfm2022.entity.Team;
import tamna6.tfm2022.service.Tfm2022Service;

import java.util.List;

@RestController
@Slf4j
@Api(value = "TFM2022 API Controller")
public class Tfm2022ApiController {
    @Autowired
    private Tfm2022Service tfm2022Service;

    @GetMapping("/api/teamlist")
    @ApiOperation(value="모든 팀 조회", notes="localhost:8080/api/teamlist")
    public List<Team> showTeamList() {
        return tfm2022Service.showTeamList();
    }

    @PostMapping("/api/teamdetail")
    @ApiOperation(value = "팀 상세보기(소속선수 포함)", notes = "localhost:8080/api/teamdetail")
    public ResponseEntity<List<Player>> showTeamDetail(@RequestBody TeamDto teamDto) {
        Long tno = teamDto.getTno();
        List<Player> players = tfm2022Service.showTeamDetail(tno);

        return ResponseEntity.status(HttpStatus.OK).body(players);
    }


    @PostMapping("/api/logincheck")
    @ApiOperation(value = "로그인 검사", notes = "localhost:8080/api/logincheck")
    public ResponseEntity<JSONObject> checkLogin(@RequestBody TfmUserDto dto) {
        String CheckLogin = "";

        //1. 1차검증 - ID 및 PW 검사
        String CheckId = tfm2022Service.checkIdAndPw(dto);
        log.info("CheckId : " + CheckId);

        //2. 2차검증 - 1차검증 이상 없을 경우 진행]]]]]]]]]]]]]]]]
        if (CheckId.contains("Error") != true) { //1차검증 후 Error문자열이 없으면 2차검증 진행
            log.info("2차검증 들어옴");
            CheckLogin = tfm2022Service.checkLogin(dto);
        } //else 도 만들자]]]]]]]]]]]]]]]]]

        JSONObject json = new JSONObject();
        //json.put("idp", dto.getId()); //(보안)혹시 모를 사태에 대비해 DB값 전달하지 ㅇ낳음
        //json.put("pwo", dto.getPw());
        json.put("HojinToken", CheckLogin);

        log.info("json : " + json);

//        "{\"test\":\"wow\"}"
        return (CheckId.contains("Error") != true) ? //CheckId가 Error문자열 포함하지 않다면
                ResponseEntity.status(HttpStatus.OK).body(json) : //참이면 여기 코드 실행
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //거짓이면 여기 코드 실행
    }

    @PostMapping("/api/signup")
    @ApiOperation(value = "회원가입", notes = "localhost:8080/api/signup")
    public ResponseEntity<String> signup(@RequestBody TfmUserDto dto) {

        //웨이터가 주방장에 주문내용 전달
        String result = tfm2022Service.signup(dto);
        log.info("test : " + result);
        return (result == "OK") ? //created가 null이 아니라면
                ResponseEntity.status(HttpStatus.OK).body(result) : //참이면 여기 코드 실행
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //거짓이면 여기 코드 실행

    }

    @PostMapping("/api/authcheck")
    @ApiOperation(value = "인증 검사", notes = "localhost:8080/api/authCheck")
    public ResponseEntity<JSONObject> checkAuth(@RequestBody TfmAuthDto dto) {
    // 로그아웃 => 로그아웃시간 갱신, 토큰값 삭제, login.html로 이동

        log.info("getToekn : " + dto.getToken());

        TfmAuthDto tfmauthDto = tfm2022Service.authCheck(dto);

        log.info("마지막 tfmauthDto : " + tfmauthDto);
        JSONObject json = new JSONObject();
        json.put("hojinToken", tfmauthDto.getToken());
        log.info("json : " + json);
        return ResponseEntity.status(HttpStatus.OK).body(json);

    }

    @PatchMapping("/api/logoutcheck")
    @ApiOperation(value = "로그아웃", notes = "localhost:8080/api/logoutCheck")
    public ResponseEntity<String> checkLogout(@RequestBody TfmAuthDto dto){

        log.info("로그아웃 테스트 : " + dto);
        String result = tfm2022Service.checkLogout(dto.getToken());

        return (result == "OK") ?
                ResponseEntity.status(HttpStatus.OK).body("Good bye~"):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
    }
}
