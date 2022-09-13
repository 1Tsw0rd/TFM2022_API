package tamna6.tfm2022.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TfmUser {

    @Id
    @Column(length = 20)
    private String id;
    @Column(nullable = false, length = 30)
    //@JsonIgnore //json으로 반환할 때 포함하지 않는다.
    private String pw;
    @Column(nullable = false, length = 20)
    private String nick;
    @Column(nullable = false, length = 20)
    private String uname;
    @Column(nullable = false, length = 10)
    private String tamid;
    @Column(nullable = false, length = 2)
    private int gisu;
    @Column(nullable = false, length = 50)
    private String mail;
    @Column(length = 20)
    private LocalDateTime createdate;

}
