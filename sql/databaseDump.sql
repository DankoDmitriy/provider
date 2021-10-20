-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: provider_base
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account_transactions`
--

DROP TABLE IF EXISTS `account_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_transactions` (
  `transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `sum` decimal(10,2) NOT NULL DEFAULT '0.00',
  `date` timestamp NOT NULL,
  `transaction_type_type_id` int(11) NOT NULL,
  `users_user_id` int(11) NOT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `fk_account_transactions_transaction_type1_idx` (`transaction_type_type_id`),
  KEY `fk_account_transactions_users1_idx` (`users_user_id`),
  CONSTRAINT `fk_account_transactions_transaction_type1` FOREIGN KEY (`transaction_type_type_id`) REFERENCES `transaction_type` (`type_id`),
  CONSTRAINT `fk_account_transactions_users1` FOREIGN KEY (`users_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_transactions`
--

LOCK TABLES `account_transactions` WRITE;
/*!40000 ALTER TABLE `account_transactions` DISABLE KEYS */;
INSERT INTO `account_transactions` VALUES (1,10.00,'2021-08-07 12:11:27',1,3),(2,123.00,'2021-08-07 12:11:27',2,3),(3,45.00,'2021-08-07 12:11:27',3,3),(4,150.00,'2021-08-07 12:11:27',2,3),(5,350.00,'2021-08-07 12:11:27',1,3),(6,1000.00,'2021-08-24 10:43:12',3,3),(7,100.00,'2021-09-06 14:16:54',1,3),(8,50.00,'2021-09-08 13:12:39',1,3),(9,25.00,'2021-09-11 10:26:12',1,3),(10,15.00,'2021-09-14 18:37:22',1,3),(11,15.99,'2021-09-15 15:45:30',2,3),(12,36.00,'2021-09-21 21:00:00',2,28),(13,15.99,'2021-09-28 07:55:39',2,3),(14,15.99,'2021-09-28 07:55:54',2,3),(16,20.00,'2021-09-28 09:11:38',1,3);
/*!40000 ALTER TABLE `account_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `action_type`
--

DROP TABLE IF EXISTS `action_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `action_type` (
  `action_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`action_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action_type`
--

LOCK TABLES `action_type` WRITE;
/*!40000 ALTER TABLE `action_type` DISABLE KEYS */;
INSERT INTO `action_type` VALUES (1,'CHANGE_PASSWORD'),(2,'CARD_ACTIVATE'),(3,'CHANGE_TARIFF'),(4,'CHANGE_STATUS'),(5,'CHANGE_ROLE');
/*!40000 ALTER TABLE `action_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actions`
--

DROP TABLE IF EXISTS `actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actions` (
  `action_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` timestamp NOT NULL,
  `action_type_type_id` int(11) NOT NULL,
  `users_user_id` int(11) NOT NULL,
  `tariffs_tariff_id` int(11) NOT NULL,
  PRIMARY KEY (`action_id`),
  KEY `fk_actions_action_type1_idx` (`action_type_type_id`),
  KEY `fk_actions_users1_idx` (`users_user_id`),
  KEY `fk_actions_tariffs1_idx` (`tariffs_tariff_id`),
  CONSTRAINT `fk_actions_action_type1` FOREIGN KEY (`action_type_type_id`) REFERENCES `action_type` (`action_type_id`),
  CONSTRAINT `fk_actions_tariffs1` FOREIGN KEY (`tariffs_tariff_id`) REFERENCES `tariffs` (`tariff_id`),
  CONSTRAINT `fk_actions_users1` FOREIGN KEY (`users_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actions`
--

LOCK TABLES `actions` WRITE;
/*!40000 ALTER TABLE `actions` DISABLE KEYS */;
INSERT INTO `actions` VALUES (1,'2021-08-07 12:09:13',3,3,2),(2,'2021-09-02 13:32:49',3,3,2),(3,'2021-09-03 12:00:27',1,3,2),(4,'2021-09-03 12:06:40',3,3,3),(5,'2021-09-06 14:16:54',2,3,3),(6,'2021-09-08 13:12:39',2,3,3),(7,'2021-09-11 10:26:12',2,3,3),(8,'2021-09-14 18:37:22',2,3,3),(9,'2021-09-14 18:39:48',3,3,5),(10,'2021-09-15 15:45:30',3,3,4),(11,'2021-09-17 11:18:48',4,6,2),(12,'2021-09-17 11:30:30',4,6,2),(13,'2021-09-17 11:30:36',4,6,2),(14,'2021-09-17 11:55:43',4,6,2),(15,'2021-09-17 11:55:46',4,6,2),(16,'2021-09-17 12:57:24',5,3,4),(17,'2021-09-17 12:57:34',5,3,4),(18,'2021-09-17 13:58:34',4,3,4),(19,'2021-09-17 14:16:29',4,3,4),(24,'2021-09-21 15:18:50',4,26,4),(25,'2021-09-21 21:00:00',3,28,3),(26,'2021-09-28 07:55:39',3,3,2),(27,'2021-09-28 07:55:54',3,3,4),(29,'2021-09-28 09:11:38',2,3,4),(30,'2021-09-29 16:12:56',4,26,4),(31,'2021-09-29 16:13:02',4,26,4),(32,'2021-10-05 07:16:46',4,28,3),(33,'2021-10-05 07:45:55',5,28,3),(34,'2021-10-05 07:48:32',5,28,3),(35,'2021-10-05 08:46:10',5,28,3),(36,'2021-10-05 08:46:11',5,28,3),(37,'2021-10-05 08:46:13',4,28,3),(38,'2021-10-05 08:46:14',4,28,3),(39,'2021-10-05 08:46:14',4,28,3);
/*!40000 ALTER TABLE `actions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `card_status`
--

DROP TABLE IF EXISTS `card_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `card_status` (
  `card_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`card_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card_status`
--

LOCK TABLES `card_status` WRITE;
/*!40000 ALTER TABLE `card_status` DISABLE KEYS */;
INSERT INTO `card_status` VALUES (1,'USED'),(2,'NOT_USED');
/*!40000 ALTER TABLE `card_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `express_payment_cards`
--

DROP TABLE IF EXISTS `express_payment_cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `express_payment_cards` (
  `card_id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `card_number` varchar(45) DEFAULT NULL,
  `card_pin` varchar(45) DEFAULT NULL,
  `activation_date` timestamp NULL DEFAULT NULL,
  `card_status_card_status_id` int(11) NOT NULL,
  `users_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`card_id`),
  UNIQUE KEY `card_number_UNIQUE` (`card_number`),
  KEY `fk_express_payment_cards_card_status1_idx` (`card_status_card_status_id`),
  KEY `fk_express_payment_cards_users1_idx` (`users_user_id`),
  CONSTRAINT `fk_express_payment_cards_card_status1` FOREIGN KEY (`card_status_card_status_id`) REFERENCES `card_status` (`card_status_id`),
  CONSTRAINT `fk_express_payment_cards_users1` FOREIGN KEY (`users_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `express_payment_cards`
--

LOCK TABLES `express_payment_cards` WRITE;
/*!40000 ALTER TABLE `express_payment_cards` DISABLE KEYS */;
INSERT INTO `express_payment_cards` VALUES (1,100.00,'4fc711301f3c784d66955d98d399afb','c4ca4238a0b923820dcc509a6f75849b','2021-09-06 14:16:54',1,3),(2,50.00,'768c1c687efe184ae6dd2420710b8799','c4ca4238a0b923820dcc509a6f75849b','2021-09-08 13:12:39',1,3),(3,25.00,'f7a5c99c58103f6b65c451efd0f81826','c4ca4238a0b923820dcc509a6f75849b','2021-09-11 10:26:12',1,3),(4,15.00,'75891c215fa472036c240d83dddd8b74','c4ca4238a0b923820dcc509a6f75849b','2021-09-14 18:37:22',1,3),(5,10.00,'b69b712f7bd6757ddcda59959c89a2b1','c4ca4238a0b923820dcc509a6f75849b','1999-12-31 21:00:00',2,NULL),(6,5.00,'58b2c53441a9db19e159bec686d685d8','c4ca4238a0b923820dcc509a6f75849b','1999-12-31 21:00:00',2,NULL),(43,20.00,'acf257b6c717a754935868292759d6e9','fc866afd8178a05a370ed6770a95f91f','2021-09-28 09:11:38',1,3),(44,20.00,'8517fe548d76112cc7a6ef838a5b2513','adaf11d5de62b2fc32faec381cbfc879','2022-09-28 21:00:00',2,NULL),(45,20.00,'8f979c03eb4956d1c0aedab5f0296cd3','1a82100e1778277ce635b76a9b21cd01','2022-09-28 21:00:00',2,NULL),(46,20.00,'9cb15ac2aa5756a0ca5c39a5d37daff1','470c0dbc9caced87b0760ad19822a91d','2022-09-28 21:00:00',2,NULL),(47,20.00,'183070e629166d8307dcaff516fa014e','37b37d463dbb1e70d896c4dad1b648f0','2022-09-28 21:00:00',2,NULL),(48,20.00,'3f7b2cacbdb7b42829bbd66516f5377a','43281af62b62ed40e8b9d272830a20dd','2022-09-28 21:00:00',2,NULL),(49,20.00,'7ef887ef76fc5e79b352a754e7f7a1aa','b76c82ec41b8adcdf5216e4a6a24fc61','2022-09-28 21:00:00',2,NULL),(50,20.00,'82adb32877fe39d3930af5cc4aa3e5a8','31c193d459695de6535f0a31e70d0313','2022-09-28 21:00:00',2,NULL),(51,20.00,'aff5ac480b4cfcbf8eff734ff937f8ec','7819675a7f0a3b8cd8b47f77ab30224b','2022-09-28 21:00:00',2,NULL),(52,20.00,'d01952cc842edfa503da31a5de7fea21','6a8262a5cb641a19fe2d2e9ed9d02e4a','2022-09-28 21:00:00',2,NULL);
/*!40000 ALTER TABLE `express_payment_cards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `express_payment_cards_series`
--

DROP TABLE IF EXISTS `express_payment_cards_series`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `express_payment_cards_series` (
  `series_id` int(11) NOT NULL AUTO_INCREMENT,
  `series` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`series_id`),
  UNIQUE KEY `series_UNIQUE` (`series`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `express_payment_cards_series`
--

LOCK TABLES `express_payment_cards_series` WRITE;
/*!40000 ALTER TABLE `express_payment_cards_series` DISABLE KEYS */;
INSERT INTO `express_payment_cards_series` VALUES (7,'e1faffb3e614e6c2fba74296962386b7');
/*!40000 ALTER TABLE `express_payment_cards_series` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periodicity_write_off`
--

DROP TABLE IF EXISTS `periodicity_write_off`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `periodicity_write_off` (
  `write_off_id` int(11) NOT NULL AUTO_INCREMENT,
  `period` varchar(45) NOT NULL,
  PRIMARY KEY (`write_off_id`),
  UNIQUE KEY `period_UNIQUE` (`period`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodicity_write_off`
--

LOCK TABLES `periodicity_write_off` WRITE;
/*!40000 ALTER TABLE `periodicity_write_off` DISABLE KEYS */;
INSERT INTO `periodicity_write_off` VALUES (1,'DAY'),(2,'MONTH'),(3,'NEVER');
/*!40000 ALTER TABLE `periodicity_write_off` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariff_statuses`
--

DROP TABLE IF EXISTS `tariff_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tariff_statuses` (
  `status_id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`status_id`),
  UNIQUE KEY `status_UNIQUE` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariff_statuses`
--

LOCK TABLES `tariff_statuses` WRITE;
/*!40000 ALTER TABLE `tariff_statuses` DISABLE KEYS */;
INSERT INTO `tariff_statuses` VALUES (2,'ACTIVE'),(1,'BASE'),(3,'BLOCK');
/*!40000 ALTER TABLE `tariff_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariffs`
--

DROP TABLE IF EXISTS `tariffs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tariffs` (
  `tariff_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '---',
  `max_speed` int(11) NOT NULL DEFAULT '0',
  `min_speed` int(11) NOT NULL DEFAULT '0',
  `traffic` decimal(10,2) NOT NULL DEFAULT '0.00',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `tariff_statuses_status_id` int(11) NOT NULL,
  `periodicity_write_off_write_off_id` int(11) NOT NULL,
  PRIMARY KEY (`tariff_id`),
  KEY `fk_tariffs_tariff_statuses1_idx` (`tariff_statuses_status_id`),
  KEY `fk_tariffs_periodicity_write_off1_idx` (`periodicity_write_off_write_off_id`),
  CONSTRAINT `fk_tariffs_periodicity_write_off1` FOREIGN KEY (`periodicity_write_off_write_off_id`) REFERENCES `periodicity_write_off` (`write_off_id`),
  CONSTRAINT `fk_tariffs_tariff_statuses1` FOREIGN KEY (`tariff_statuses_status_id`) REFERENCES `tariff_statuses` (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariffs`
--

LOCK TABLES `tariffs` WRITE;
/*!40000 ALTER TABLE `tariffs` DISABLE KEYS */;
INSERT INTO `tariffs` VALUES (2,'Base 1',50,25,10240.00,15.99,2,2),(3,'Base 2',10,5,10240.00,36.00,2,2),(4,'Base 3',80,40,102400.00,15.99,2,2),(5,'Base 4',17,17,10240.00,11.99,3,1),(6,'Base 55555',555,555,5555555.00,55.55,3,2);
/*!40000 ALTER TABLE `tariffs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_type`
--

DROP TABLE IF EXISTS `transaction_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) COLLATE utf8_bin NOT NULL,
  `description` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `type_UNIQUE` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_type`
--

LOCK TABLES `transaction_type` WRITE;
/*!40000 ALTER TABLE `transaction_type` DISABLE KEYS */;
INSERT INTO `transaction_type` VALUES (1,'REFILL',NULL),(2,'WRITE_OFF',NULL),(3,'ADJUSTMENT',NULL);
/*!40000 ALTER TABLE `transaction_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_UNIQUE` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'ADMIN'),(3,'GUEST'),(2,'USER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_statuses`
--

DROP TABLE IF EXISTS `user_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_statuses` (
  `status_id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`status_id`),
  UNIQUE KEY `userStatusescol_UNIQUE` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_statuses`
--

LOCK TABLES `user_statuses` WRITE;
/*!40000 ALTER TABLE `user_statuses` DISABLE KEYS */;
INSERT INTO `user_statuses` VALUES (1,'ACTIVE'),(2,'BLOCK'),(3,'WAIT_ACTIVATE');
/*!40000 ALTER TABLE `user_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(55) COLLATE utf8_bin DEFAULT NULL,
  `last_name` varchar(55) COLLATE utf8_bin DEFAULT NULL,
  `patronymic` varchar(55) COLLATE utf8_bin DEFAULT NULL,
  `contract_number` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `contract_date` timestamp NULL DEFAULT NULL,
  `balance` decimal(10,2) DEFAULT '0.00',
  `name` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `password` char(64) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `activation_code` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `activation_code_used` int(11) NOT NULL DEFAULT '0',
  `traffic` decimal(10,2) DEFAULT '0.00',
  `user_roles_role_id` int(11) NOT NULL,
  `user_statuses_status_id` int(11) NOT NULL,
  `tariffs_tariff_id` int(11) DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `contract_number_UNIQUE` (`contract_number`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fk_users_user_roles_idx` (`user_roles_role_id`),
  KEY `fk_users_user_statuses1_idx` (`user_statuses_status_id`),
  KEY `fk_users_tariffs1_idx` (`tariffs_tariff_id`),
  CONSTRAINT `fk_users_tariffs1` FOREIGN KEY (`tariffs_tariff_id`) REFERENCES `tariffs` (`tariff_id`),
  CONSTRAINT `fk_users_user_roles` FOREIGN KEY (`user_roles_role_id`) REFERENCES `user_roles` (`role_id`),
  CONSTRAINT `fk_users_user_statuses` FOREIGN KEY (`user_statuses_status_id`) REFERENCES `user_statuses` (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'Иван','Иванов','Иванович','123contract','2020-10-12 21:00:00',0.00,'100000000000001','3fc0a7acf087f549ac2b266baf94b8b1','admin@local.site','a9040ab0-a82a-45b5-b237-caf90b1dea79',0,1024.00,1,1,3),(3,'Петр','Петров','Петрович','1.2Unlim_2','2021-08-07 12:09:13',162.03,'202100000000003','3fc0a7acf087f549ac2b266baf94b8b1','danko.dima@gmail.com','1d1da45e-8c45-4d79-a26d-3e19d007116b',1,1331200.00,2,1,4),(6,'Mike','Broun','White','1.2Unlim_2fghj','2021-08-07 12:11:27',0.00,'202100000000006','3fc0a7acf087f549ac2b266baf94b8b1','test_user@site.local','50c6f0df-cde2-4e7d-ac6f-04aca96d5a99',0,0.00,2,3,2),(8,'Kölen','Gerbov','Nikü','12333','2021-08-07 12:30:02',0.00,'202100000000008','3fc0a7acf087f549ac2b266baf94b8b1','test_user@site.local','28cae249-9b92-476d-a311-e1e7fe6028bb',0,1024.00,2,3,2),(9,'Diana','Land','Adam','1233sadas3','2021-08-26 10:51:00',0.00,'202100000000009','3fc0a7acf087f549ac2b266baf94b8b1','teddst_user@site.local','1234567898asd76543234567',1,1024.00,2,1,2),(22,'Tom','Lui','Jeck','202100000000022','2021-09-13 21:00:00',0.00,'202100000000022','3fc0a7acf087f549ac2b266baf94b8b1b','dasdfads@gmail.com','c6d05411-ff7e-4345-9190-47f704311df6',0,10240.00,2,3,2),(23,'Lui','First','King','202100000000023','2021-09-12 21:00:00',0.00,'202100000000023','3fc0a7acf087f549ac2b266baf94b8b1','kinglui@mail.ru','139ba17e-3b01-4fcc-b814-c8bcfd2098c7',0,10240.00,2,3,2),(24,'Juli','Robertson','Jim','202100000000024','2021-09-12 21:00:00',0.00,'202100000000024','3fc0a7acf087f549ac2b266baf94b8b1','asdaksdbhk@gmail.com','d4c9efd2-916e-471c-9bf5-c8ee5c3078a0',0,10240.00,2,3,2),(26,'Alex','Frank','David','202100000000026','2021-09-20 21:00:00',0.00,'202100000000026','3fc0a7acf087f549ac2b266baf94b8b1','asdasdasdasdads@gmail.com','2a1fcf9d-c984-488d-9874-c1a47fdc83a6',0,102400.00,2,2,4),(27,'Marusia','Krasa','Kosa','202100000000027','2021-09-21 21:00:00',0.00,'202100000000027','3fc0a7acf087f549ac2b266baf94b8b1','marusia@gmail.com','38e84b32-4d45-4f77-929a-799724b2d05c',0,10240.00,2,3,3),(28,'Виталий','Киркоров','Олегович','202100000000028','2021-09-21 21:00:00',-36.00,'202100000000028','3fc0a7acf087f549ac2b266baf94b8b1','sadfsfsf@mail.ru','6bb899df-630d-492d-951c-92df5ca5bbe5',0,10240.00,2,3,3);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-20 14:06:53
