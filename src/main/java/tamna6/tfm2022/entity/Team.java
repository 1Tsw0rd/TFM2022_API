package tamna6.tfm2022.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 id를 자동 생성해줌
    private Long tno;

    @Column
    private String tname;
    @Column
    private String logo;
    @Column
    private String history;
    @Column
    private String alias;
    @Column
    private String stadium;
    @Column
    private String trophy;
    
    //@OneToMany
    //@JoinColumn(name = "tno")
    //private List<Player> playerList = new ArrayList<>();
}
