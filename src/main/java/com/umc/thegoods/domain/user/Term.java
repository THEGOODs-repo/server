package com.umc.thegoods.domain.user;

import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.mapping.user.UserTerm;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Term extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(columnDefinition = "VARCHAR(30)")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Column(columnDefinition = "VARCHAR(1)")
    private String essential;


    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
    private List<UserTerm> userTermList = new ArrayList<>();
}
