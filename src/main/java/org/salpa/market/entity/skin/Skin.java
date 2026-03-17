package org.salpa.market.entity.skin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "skin", name = "skin")
public class Skin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String image;

    @Enumerated(value = EnumType.STRING)
    private Gun gun;

    @Enumerated(value = EnumType.STRING)
    private Rarely rarely;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;
}
