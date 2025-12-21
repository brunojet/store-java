package com.personal.store.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filtro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filtro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codFlo;

    @Column(name = "cod_flo_cto", length = 3)
    private String codFloCto;

    @Column(name = "nom_flo", length = 255, nullable = false)
    private String nomFlo;

    @Column(name = "cod_tip_flo")
    private Long codTipFlo;

    @Column(name = "cod_flo_pai_id")
    private Long parentId;
}
