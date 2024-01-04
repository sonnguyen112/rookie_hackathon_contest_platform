package com.group10.contestPlatform.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    @GeneratedValue
    private Long id;
    private String imgUrl;
    @ManyToOne
    @JoinColumn(name = "take_id", nullable = false)
    private Take take;
}
