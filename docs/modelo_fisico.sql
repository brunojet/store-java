-- store.application definition

CREATE TABLE `application` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  `name` varchar(191) NOT NULL,
  `description` longtext,
  `active` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_application_created_at` (`created_at`),
  KEY `idx_application_updated_at` (`updated_at`),
  KEY `idx_application_name` (`name`),
  KEY `idx_application_active` (`active`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.application_contact definition

CREATE TABLE `application_contact` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  `name` varchar(191) NOT NULL,
  `description` longtext,
  `active` tinyint(1) DEFAULT '0',
  `site` longtext NOT NULL,
  `email` longtext NOT NULL,
  `phone` longtext NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_application_contact_created_at` (`created_at`),
  KEY `idx_application_contact_updated_at` (`updated_at`),
  KEY `idx_application_contact_name` (`name`),
  KEY `idx_application_contact_active` (`active`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.application_detail definition

CREATE TABLE `application_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_application_detail_created_at` (`created_at`),
  KEY `idx_application_detail_updated_at` (`updated_at`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.category_type definition

CREATE TABLE `category_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  `name` varchar(191) NOT NULL,
  `description` longtext,
  `active` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_category_type_created_at` (`created_at`),
  KEY `idx_category_type_updated_at` (`updated_at`),
  KEY `idx_category_type_name` (`name`),
  KEY `idx_category_type_active` (`active`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.integration_type definition

CREATE TABLE `integration_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  `name` varchar(191) NOT NULL,
  `description` longtext,
  `active` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_integration_type_created_at` (`created_at`),
  KEY `idx_integration_type_updated_at` (`updated_at`),
  KEY `idx_integration_type_name` (`name`),
  KEY `idx_integration_type_active` (`active`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.storage_object definition

CREATE TABLE `storage_object` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  `path` char(40) NOT NULL,
  `name` varchar(40) NOT NULL,
  `mime_type` varchar(100) NOT NULL,
  `status` smallint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_storage_object_path` (`path`),
  KEY `idx_storage_object_created_at` (`created_at`),
  KEY `idx_storage_object_updated_at` (`updated_at`),
  KEY `idx_storage_object_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.terminal_model definition

CREATE TABLE `terminal_model` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  `name` varchar(191) NOT NULL,
  `description` longtext,
  `active` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_terminal_model_created_at` (`created_at`),
  KEY `idx_terminal_model_updated_at` (`updated_at`),
  KEY `idx_terminal_model_name` (`name`),
  KEY `idx_terminal_model_active` (`active`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.application_configuration definition

CREATE TABLE `application_configuration` (
  `application_id` bigint NOT NULL,
  `integration_type_id` bigint NOT NULL,
  `terminal_model_id` bigint NOT NULL,
  PRIMARY KEY (`application_id`,`integration_type_id`,`terminal_model_id`),
  UNIQUE KEY `uk_cfg_pk` (`application_id`,`integration_type_id`,`terminal_model_id`),
  KEY `uk_cfg_rev` (`terminal_model_id`,`integration_type_id`,`application_id`),
  KEY `fk_application_configuration_integration_type` (`integration_type_id`),
  CONSTRAINT `fk_application_application_configurations` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`),
  CONSTRAINT `fk_application_configuration_integration_type` FOREIGN KEY (`integration_type_id`) REFERENCES `integration_type` (`id`),
  CONSTRAINT `fk_application_configuration_terminal_model` FOREIGN KEY (`terminal_model_id`) REFERENCES `terminal_model` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.application_profile_history definition

CREATE TABLE `application_profile_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  `application_contact_id` bigint NOT NULL,
  `application_detail_id` bigint NOT NULL,
  `review_at` datetime(3) DEFAULT NULL,
  `production_at` datetime(3) DEFAULT NULL,
  `deactivated_at` datetime(3) DEFAULT NULL,
  `deactivation_cause` longtext,
  PRIMARY KEY (`id`),
  KEY `idx_application_profile_history_created_at` (`created_at`),
  KEY `idx_application_profile_history_updated_at` (`updated_at`),
  KEY `fk_application_profile_history_application_contact` (`application_contact_id`),
  KEY `fk_application_profile_history_application_detail` (`application_detail_id`),
  CONSTRAINT `fk_application_profile_history_application_contact` FOREIGN KEY (`application_contact_id`) REFERENCES `application_contact` (`id`),
  CONSTRAINT `fk_application_profile_history_application_detail` FOREIGN KEY (`application_detail_id`) REFERENCES `application_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.application_profile_history_configuration definition

CREATE TABLE `application_profile_history_configuration` (
  `profile_id` bigint NOT NULL,
  `application_id` bigint NOT NULL,
  `integration_type_id` bigint NOT NULL,
  `terminal_model_id` bigint NOT NULL,
  PRIMARY KEY (`profile_id`,`application_id`,`integration_type_id`,`terminal_model_id`),
  KEY `fk_application_profile_history_configuration_application4ce2ca07` (`application_id`,`integration_type_id`,`terminal_model_id`),
  CONSTRAINT `fk_application_profile_history_configuration_application4ce2ca07` FOREIGN KEY (`application_id`, `integration_type_id`, `terminal_model_id`) REFERENCES `application_configuration` (`application_id`, `integration_type_id`, `terminal_model_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_application_profile_history_configuration_applicationa0a00bb5` FOREIGN KEY (`profile_id`) REFERENCES `application_profile_history` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.category definition

CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  `name` varchar(191) NOT NULL,
  `description` longtext,
  `active` tinyint(1) DEFAULT '0',
  `category_type_id` bigint NOT NULL,
  `parent_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_category_created_at` (`created_at`),
  KEY `idx_category_updated_at` (`updated_at`),
  KEY `idx_category_name` (`name`),
  KEY `idx_category_active` (`active`),
  KEY `idx_category_category_type_id` (`category_type_id`),
  KEY `idx_category_parent_id` (`parent_id`),
  CONSTRAINT `fk_category_category_type` FOREIGN KEY (`category_type_id`) REFERENCES `category_type` (`id`),
  CONSTRAINT `fk_category_parent` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.image definition

CREATE TABLE `image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  `storage_object_id` bigint NOT NULL,
  `image_type` smallint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_image_storage_object_id` (`storage_object_id`),
  KEY `idx_image_created_at` (`created_at`),
  KEY `idx_image_updated_at` (`updated_at`),
  KEY `idx_image_image_type` (`image_type`),
  CONSTRAINT `fk_image_storage_object` FOREIGN KEY (`storage_object_id`) REFERENCES `storage_object` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `chk_image_image_type` CHECK ((`image_type` in (0,10,20)))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.tipo_filtro definition

CREATE TABLE `tipo_filtro` (
  `cod_tip_flo` bigint NOT NULL AUTO_INCREMENT,
  `cod_tip_flo_cto` varchar(3) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `description` text,
  `cod_tip_flo_pai_id` bigint DEFAULT NULL,
  PRIMARY KEY (`cod_tip_flo`),
  KEY `fk_tipo_filtro_children` (`cod_tip_flo_pai_id`),
  CONSTRAINT `fk_tipo_filtro_children` FOREIGN KEY (`cod_tip_flo_pai_id`) REFERENCES `tipo_filtro` (`cod_tip_flo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.video definition

CREATE TABLE `video` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  `storage_object_id` bigint NOT NULL,
  `video_type` smallint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_video_storage_object_id` (`storage_object_id`),
  KEY `idx_video_created_at` (`created_at`),
  KEY `idx_video_updated_at` (`updated_at`),
  CONSTRAINT `fk_video_storage_object` FOREIGN KEY (`storage_object_id`) REFERENCES `storage_object` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `chk_video_video_type` CHECK ((`video_type` in (0,10)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.application_detail_screenshots definition

CREATE TABLE `application_detail_screenshots` (
  `application_detail_id` bigint NOT NULL,
  `image_id` bigint NOT NULL,
  PRIMARY KEY (`application_detail_id`,`image_id`),
  KEY `fk_application_detail_screenshots_image` (`image_id`),
  CONSTRAINT `fk_application_detail_screenshots_application_detail` FOREIGN KEY (`application_detail_id`) REFERENCES `application_detail` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_application_detail_screenshots_image` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.application_profile_history_category definition

CREATE TABLE `application_profile_history_category` (
  `profile_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`profile_id`,`category_id`),
  KEY `fk_application_profile_history_category_category` (`category_id`),
  CONSTRAINT `fk_application_profile_history_category_application_profe8eb6048` FOREIGN KEY (`profile_id`) REFERENCES `application_profile_history` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_application_profile_history_category_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.application_version definition

CREATE TABLE `application_version` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  `name` varchar(191) NOT NULL,
  `description` longtext,
  `active` tinyint(1) DEFAULT '0',
  `application_id` bigint NOT NULL,
  `integration_type_id` bigint NOT NULL,
  `terminal_model_id` bigint NOT NULL,
  `version_name` varchar(100) NOT NULL,
  `version_code` bigint NOT NULL,
  `size` bigint NOT NULL,
  `image_id` bigint DEFAULT NULL,
  `pilot_at` datetime(3) DEFAULT NULL,
  `production_at` datetime(3) DEFAULT NULL,
  `deactivated_at` datetime(3) DEFAULT NULL,
  `deactivation_cause` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`application_id`,`integration_type_id`,`terminal_model_id`),
  KEY `idx_application_version_created_at` (`created_at`),
  KEY `idx_application_version_updated_at` (`updated_at`),
  KEY `idx_application_version_name` (`name`),
  KEY `idx_application_version_active` (`active`),
  KEY `idx_application_version_image_id` (`image_id`),
  KEY `idx_application_version_pilot_at` (`pilot_at`),
  KEY `idx_application_version_production_at` (`production_at`),
  KEY `idx_application_version_deactivated_at` (`deactivated_at`),
  KEY `idx_application_version_deactivation_cause` (`deactivation_cause`),
  KEY `fk_application_configuration_application_versions` (`application_id`,`integration_type_id`,`terminal_model_id`),
  CONSTRAINT `fk_application_configuration_application_versions` FOREIGN KEY (`application_id`, `integration_type_id`, `terminal_model_id`) REFERENCES `application_configuration` (`application_id`, `integration_type_id`, `terminal_model_id`),
  CONSTRAINT `fk_application_version_image` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.filtro definition

CREATE TABLE `filtro` (
  `cod_flo` bigint NOT NULL AUTO_INCREMENT,
  `cod_flo_cto` varchar(3) DEFAULT NULL,
  `nom_flo` varchar(255) NOT NULL,
  `cod_tip_flo` bigint NOT NULL,
  `cod_flo_pai_id` bigint DEFAULT NULL,
  PRIMARY KEY (`cod_flo`),
  KEY `fk_tipo_filtro_filters` (`cod_tip_flo`),
  KEY `fk_filtro_children` (`cod_flo_pai_id`),
  CONSTRAINT `fk_filtro_children` FOREIGN KEY (`cod_flo_pai_id`) REFERENCES `filtro` (`cod_flo`),
  CONSTRAINT `fk_tipo_filtro_filters` FOREIGN KEY (`cod_tip_flo`) REFERENCES `tipo_filtro` (`cod_tip_flo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- store.application_catalog definition

CREATE TABLE `application_catalog` (
  `integration_type_id` bigint NOT NULL,
  `terminal_model_id` bigint NOT NULL,
  `stage` smallint NOT NULL,
  `application_id` bigint NOT NULL,
  `application_profile_id` bigint NOT NULL,
  `application_version_id` bigint NOT NULL,
  `active` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`integration_type_id`,`terminal_model_id`,`stage`,`application_id`),
  KEY `ak_app_ctlg_u0` (`application_id`,`stage`,`integration_type_id`,`terminal_model_id`),
  KEY `idx_ac_application_profile` (`application_profile_id`),
  KEY `idx_ac_application_version` (`application_version_id`),
  KEY `idx_application_catalog_active` (`active`),
  KEY `fk_application_version_application_catalogs` (`application_version_id`,`application_id`,`integration_type_id`,`terminal_model_id`),
  KEY `fk_application_configuration_application_catalogs` (`application_id`,`integration_type_id`,`terminal_model_id`),
  CONSTRAINT `fk_application_catalog_application_version` FOREIGN KEY (`application_version_id`) REFERENCES `application_version` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_application_configuration_application_catalogs` FOREIGN KEY (`application_id`, `integration_type_id`, `terminal_model_id`) REFERENCES `application_configuration` (`application_id`, `integration_type_id`, `terminal_model_id`),
  CONSTRAINT `fk_application_profile_history_application_catalogs` FOREIGN KEY (`application_profile_id`) REFERENCES `application_profile_history` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_application_version_application_catalogs` FOREIGN KEY (`application_version_id`, `application_id`, `integration_type_id`, `terminal_model_id`) REFERENCES `application_version` (`id`, `application_id`, `integration_type_id`, `terminal_model_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `chk_application_catalog_stage` CHECK ((`stage` in (10,20,30,40)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;