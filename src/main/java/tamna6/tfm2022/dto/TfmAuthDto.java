package tamna6.tfm2022.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tamna6.tfm2022.entity.TfmAuth;
import tamna6.tfm2022.entity.TfmUser;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class TfmAuthDto {
    private String ID;
    private String token;
    private LocalDateTime lastlogin;
    private LocalDateTime lastlogout;

    private LocalDateTime lastrequest;

    public static TfmAuthDto authCheckerDto(TfmAuth tfmAuth) { //AuthChecker 전용
        return new TfmAuthDto(
                tfmAuth.getID(),
                tfmAuth.getToken(),
                tfmAuth.getLastlogin(),
                tfmAuth.getLastlogout(),
                tfmAuth.getLastrequest()
        );
    }

    public TfmAuth toEntity() {return  new TfmAuth(ID, token, lastlogin, lastlogout, lastrequest);}

}
