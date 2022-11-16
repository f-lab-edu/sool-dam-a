DROP TABLE IF EXISTS `subscription` CASCADE;
DROP TABLE IF EXISTS `shipping_information` CASCADE;
DROP TABLE IF EXISTS `subscription_product_box_product` CASCADE;
DROP TABLE IF EXISTS `subscription_product_box` CASCADE;
DROP TABLE IF EXISTS `review` CASCADE;
DROP TABLE IF EXISTS `product` CASCADE;
DROP TABLE IF EXISTS `product_category` CASCADE;
DROP TABLE IF EXISTS `user` CASCADE;

CREATE TABLE `user` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `email` varchar(255) UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `nickname` varchar(255),
  `is_adult` boolean NOT NULL DEFAULT false,
  `created_at` datetime NOT NULL DEFAULT (now()),
  `updated_at` datetime,
  `deleted_at` datetime
);

CREATE TABLE `shipping_information` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `detail_address` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `message` varchar(255),
  `is_default` boolean NOT NULL DEFAULT false,
  `created_at` datetime NOT NULL DEFAULT (now()),
  `updated_at` datetime,
  `deleted_at` datetime
);

CREATE TABLE `subscription_product_box` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` int NOT NULL,
  `image_url` varchar(2000) NOT NULL,
  `description` varchar(2000) NOT NULL,
  `payment_date` datetime NOT NULL,
  `arrive_date` datetime NOT NULL
);

CREATE TABLE `subscription` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `subscription_product_box_id` bigint NOT NULL,
  `shipping_information_id` bigint NOT NULL,
  `started_at` datetime NOT NULL,
  `quantity` int NOT NULL,
  `total_price` int NOT NULL,
  `canceled_at` datetime,
  `end_date` datetime,
  `created_at` datetime NOT NULL DEFAULT (now()),
  `updated_at` datetime
);

CREATE TABLE `subscription_product_box_product` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `subscription_product_box_id` bigint NOT NULL,
  `product_id` bigint NOT NULL
);

CREATE TABLE `product` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `product_category_id` bigint NOT NULL,
  `name` varchar(255) UNIQUE NOT NULL,
  `price` int NOT NULL,
  `image_url` varchar(2000) NOT NULL,
  `description` varchar(2000) NOT NULL,
  `abv` double NOT NULL,
  `capacity` int NOT NULL,
  `created_at` datetime NOT NULL DEFAULT (now()),
  `updated_at` datetime,
  `deleted_at` datetime
);

CREATE TABLE `product_category` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(2000) NOT NULL,
  `image_url` varchar(2000) NOT NULL
);

CREATE TABLE `review` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `content` varchar(2000) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT (now()),
  `updated_at` datetime,
  `deleted_at` datetime
);

ALTER TABLE `shipping_information` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `subscription` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `subscription` ADD FOREIGN KEY (`subscription_product_box_id`) REFERENCES `subscription_product_box` (`id`);
ALTER TABLE `subscription` ADD FOREIGN KEY (`shipping_information_id`) REFERENCES `shipping_information` (`id`);
ALTER TABLE `review` ADD FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);
ALTER TABLE `review` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `product` ADD FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`id`);
ALTER TABLE `subscription_product_box_product` ADD FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);
ALTER TABLE `subscription_product_box_product` ADD FOREIGN KEY (`subscription_product_box_id`) REFERENCES `subscription_product_box` (`id`);
