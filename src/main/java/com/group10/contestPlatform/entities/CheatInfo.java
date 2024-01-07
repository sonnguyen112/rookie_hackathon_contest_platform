package com.group10.contestPlatform.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cheat_info")
public class CheatInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cheat_info_seq")
    @SequenceGenerator(name = "cheat_info_seq", sequenceName = "cheat_info_seq", allocationSize = 1)
    private Long id;
    private String imgUrl;
    @ManyToOne
    @JoinColumn(name = "take_id", nullable = false)
    private Take take;
}
