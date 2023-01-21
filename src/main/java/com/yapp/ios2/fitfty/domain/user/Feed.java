package com.yapp.ios2.fitfty.domain.user;


import com.yapp.ios2.fitfty.domain.AbstractEntity;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "`feed`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feed extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long pictureId;
    private String pictureToken;
}
