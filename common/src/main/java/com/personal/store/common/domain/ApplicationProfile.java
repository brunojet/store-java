package com.personal.store.common.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.personal.store.common.domain.abstraction.SimpleEntityAbstraction;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_profile_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationProfile extends SimpleEntityAbstraction {
    @Column(name = "review_at")
    private LocalDateTime reviewAt;

    @Column(name = "production_at")
    private LocalDateTime productionAt;

    @Column(name = "stage")
    @Convert(converter = ApplicationStageConverter.class)
    private ApplicationStage stage;

    @JoinColumn(name = "icon_id", referencedColumnName= "id", nullable = false)
    private ApplicationImage icon;

    @OneToMany(mappedBy = "applicationProfile")
    @OrderBy("position ASC")
    private List<ApplicationProfileScreenshot> screenshots = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "application_profile_history_filter",
        joinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "filter_id", referencedColumnName = "id")
    )
    private Set<Filter> filters = new HashSet<>();

    @Column(name = "partner_name", length = 60)
    private String partnerName;

    @Column(name = "application_support_contact_name", length = 60)
    private String applicationSupportContactName;

    @Column(name = "application_support_contact_email", length = 255)
    private String applicationSupportContactEmail;

    @Column(name = "application_support_contact_phone", length = 11)
    private String applicationSupportContactPhone;

    @Column(name = "application_support_contact_site", length = 255)
    private String applicationSupportContactSite;
}
