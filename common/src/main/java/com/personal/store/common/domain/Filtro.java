package com.personal.store.common.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "filtro")
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

    // getters/setters
    public Long getCodFlo() { return codFlo; }
    public void setCodFlo(Long codFlo) { this.codFlo = codFlo; }
}
