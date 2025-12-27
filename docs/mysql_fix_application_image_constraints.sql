-- Fix MySQL CHECK constraints for application_image to match enum codes used by the application.
--
-- Problem:
--  - StorageObjectStatus uses explicit codes: 0, 10, 20, 30, 40
--  - Some schemas were created with CHECK(status in (0..n)) (enum ordinals), causing inserts to fail.
--
-- Run this against your MySQL schema (MySQL 8.0+).

ALTER TABLE application_image
  DROP CHECK application_image_chk_1;

ALTER TABLE application_image
  ADD CONSTRAINT application_image_chk_1 CHECK (status in (0,10,20,30,40));
