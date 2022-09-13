package tamna6.tfm2022.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;
import tamna6.tfm2022.entity.Player;
import tamna6.tfm2022.entity.Team;

@AllArgsConstructor
@ToString
public class PlayerDto {
    private Long pno;
    private String pname;
    private String feature;
    private String position;
    private Long att;
    private Long pas;
    private Long def;
    private Long total;
    private Team tno;

    public Player toEntity(){
        return new Player(pno, pname, feature, position, att, pas, def, total, tno);
    }
}
