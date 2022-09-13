package tamna6.tfm2022.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TfmAuth {
    @Id
    @Column(length = 20)
    private String ID;
    @Column(unique=true, length = 80)  //길이 변경가능
    private String token;
    @Column
    private LocalDateTime lastlogin;
    @Column
    private LocalDateTime lastlogout;

}
