package com.personal.store.common.domain;

import com.personal.store.common.domain.abstraction.SimpleEntityAbstraction;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "application")
public class Application extends SimpleEntityAbstraction {
}
