package com.personal.store.common.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ApplicationProfileScreenshotId implements Serializable {
    private Long applicationProfileId;
    private Long applicationImageId;
}
