package tamna6.tfm2022.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tamna6.tfm2022.entity.TfmUser;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class TfmUserDto {
    private String id;
    private String pw;
    private String nick;
    private String uname;
    private String tamid;
    private int gisu;
    private String mail;
    private LocalDateTime createdate;
    public TfmUser toEntity(){
        return new TfmUser(id, pw, nick, uname, tamid, gisu, mail, createdate);
    }
}
