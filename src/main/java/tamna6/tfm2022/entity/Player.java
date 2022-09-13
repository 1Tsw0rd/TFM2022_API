package tamna6.tfm2022.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;
    @Column
    private String pname;
    @Column
    private String feature;
    @Column
    private String position;
    @Column
    private Long att;
    @Column
    private Long pas;
    @Column
    private Long def;
    @Column
    private Long total;

    @ManyToOne
    @JoinColumn(name="tno")
    private Team tno;
}
