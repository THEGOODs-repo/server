package com.umc.thegoods.mapping.ViewSearch;

import com.umc.thegoods.domain.Tag;
import com.umc.thegoods.domain.common.BaseDateTimeEntity;
import com.umc.thegoods.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TagSearch extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TAG_ID", nullable = false)
    private Tag tag;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
}
