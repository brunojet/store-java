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
@Table(name = "tipo_filtro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoFiltro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codTipFlo;

    @Column(name = "cod_tip_flo_cto", length = 3)
    private String codTipFloCto;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "cod_tip_flo_pai_id")
    private Long parentId;
}
