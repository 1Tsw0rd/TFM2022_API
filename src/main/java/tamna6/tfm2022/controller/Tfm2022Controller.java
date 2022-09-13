package tamna6.tfm2022.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tamna6.tfm2022.dto.TfmUserDto;
import tamna6.tfm2022.entity.Team;
import tamna6.tfm2022.entity.TfmUser;
import tamna6.tfm2022.repository.PlayerRepository;
import tamna6.tfm2022.repository.TeamRepository;
import tamna6.tfm2022.repository.TfmUserRepository;

import java.util.List;

@Controller
@Slf4j
@Api(value="Swagger Test V1")
public class Tfm2022Controller {

//    @Autowired
//    private TfmUserRepository tfm2022Repository;
//
//    @Autowired
//    private TeamRepository teamRepository;
//    @Autowired
//    private PlayerRepository playerRepository;
//
//    @GetMapping("/")
//    @ApiOperation(value="로그인", notes="localhost:8080")
//    public String tfmLogin(){
//        return "/login";
//    }
//
//    @GetMapping("/signup")
//    @ApiOperation(value="회원가입", notes="localhost:8080/signup")
//    public String tfmSignUp(){
//        return "/signup";
//    }
//
//    @PostMapping("/tfm2022/create")
//    @ApiOperation(value="회원가입 값 전달", notes="웹페이지 없음, 회원가입 성공 시 로그인 화면 이동")
//    public String createTfmAccount(TfmUserDto form){
//
//        //1. DTO -> Entity 변환
//        TfmUser tfmuser = form.toEntity();
//        log.info(form.toString());
//
//        //2. Repository에게 Entity를 DB안에 저장
//        TfmUser saved = tfm2022Repository.save(tfmuser);
//        log.info(saved.toString());
//
//        return "redirect:http://192.168.5.11:5501/";
//    }
//
//    @GetMapping("/main")
//    @ApiOperation(value="TFM2022 메인화면", notes="localhost:8080/main")
//    public String tfmMain(){
//        return "/main";
//    }
//
//    @GetMapping("/detail")
//    @ApiOperation(value="팀 상세보기", notes="localhost:8080/detail")
//    public String tfmDetail(Model model){
//        //ArrayList 사용
//        List<Team> teamEntityList = teamRepository.findAll();
//
//        model.addAttribute("teamList", teamEntityList);
//        return "detail";
//    }

//    @PostMapping("/detail/players")
//    @ApiOperation(value="팀 상세보기 + 선수목록", notes="localhost:8080/detail")
//    public String tfmPlayers(PlayerForm form, Model model){
//
//        Player playerEntity = form.toEntity();
//        log.info("111 : "+ playerEntity);
//        List<Player> PlayerList = playerRepository.findByPlayer(playerEntity.getTno());
//        log.info("222 : "+ PlayerList);
//        model.addAttribute("playerList", PlayerList);
//        log.info("model111 : "+ model);
//        List<Team> teamEntityList = teamRepository.findAll();
//        model.addAttribute("teamList", teamEntityList);
//        log.info("model222 : "+ model);
//
//        return "detail";
//    }

//    @GetMapping("my")
//    @ApiOperation(value="(미구현)My팀", notes="localhost:8080/my")
//    public String tfmMy(){
//        return "my";
//    }
//
//    @GetMapping("play")
//    @ApiOperation(value="(미구현)시뮬레이션", notes="localhost:8080/play")
//    public String tfmPlay(){
//        return "play";
//    }
}
