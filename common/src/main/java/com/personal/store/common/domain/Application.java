package com.personal.store.common.domain;

import java.util.ArrayList;
import java.util.List;

import com.personal.store.common.domain.abstraction.SimpleEntityAbstraction;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "application")
public class Application extends SimpleEntityAbstraction {
	@OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
	private final List<ApplicationImage> images = new ArrayList<>();
}
