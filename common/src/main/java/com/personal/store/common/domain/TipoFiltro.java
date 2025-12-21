package com.personal.store.common.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_filtro")
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

    // getters/setters
    public Long getCodTipFlo() { return codTipFlo; }
    public void setCodTipFlo(Long codTipFlo) { this.codTipFlo = codTipFlo; }
}
