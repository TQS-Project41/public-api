-- MySQL dump 10.13  Distrib 8.0.27, for Linux (x86_64)
--
-- Host: localhost    Database: tqs_41
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `business`
--

DROP TABLE IF EXISTS `business`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business` (
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FKjkjjimeu5p0gv4orhue734xni` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business`
--

LOCK TABLES `business` WRITE;
/*!40000 ALTER TABLE `business` DISABLE KEYS */;
INSERT INTO `business` VALUES (1),(2),(3);
/*!40000 ALTER TABLE `business` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_courier_interactions`
--

DROP TABLE IF EXISTS `business_courier_interactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_courier_interactions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event` int NOT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `business_id` bigint NOT NULL,
  `courier_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpcuqd8pvko1j3604nq11ni3d1` (`business_id`),
  KEY `FKj25vc0c7xoqhgh10t9ulnim0h` (`courier_id`),
  CONSTRAINT `FKj25vc0c7xoqhgh10t9ulnim0h` FOREIGN KEY (`courier_id`) REFERENCES `courier` (`user_id`),
  CONSTRAINT `FKpcuqd8pvko1j3604nq11ni3d1` FOREIGN KEY (`business_id`) REFERENCES `business` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_courier_interactions`
--

LOCK TABLES `business_courier_interactions` WRITE;
/*!40000 ALTER TABLE `business_courier_interactions` DISABLE KEYS */;
INSERT INTO `business_courier_interactions` VALUES (1,0,'2022-06-23 11:07:41.453384',1,4),(2,0,'2022-06-23 11:08:47.782000',2,4),(3,0,'2022-06-23 11:08:50.889721',3,4),(4,2,'2022-06-23 11:11:07.410799',1,4),(5,2,'2022-06-23 11:15:27.504100',2,4),(6,3,'2022-06-23 11:15:32.728549',2,4),(7,2,'2022-06-23 11:16:31.031888',3,4),(8,1,'2022-06-23 11:16:43.274034',3,4);
/*!40000 ALTER TABLE `business_courier_interactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_courier_interactions_event_type`
--

DROP TABLE IF EXISTS `business_courier_interactions_event_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_courier_interactions_event_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_courier_interactions_event_type`
--

LOCK TABLES `business_courier_interactions_event_type` WRITE;
/*!40000 ALTER TABLE `business_courier_interactions_event_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `business_courier_interactions_event_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courier`
--

DROP TABLE IF EXISTS `courier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courier` (
  `user_id` bigint NOT NULL,
  `birthdate` date DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FKdh1i9rf00a0kn9aeo2svr5a4i` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courier`
--

LOCK TABLES `courier` WRITE;
/*!40000 ALTER TABLE `courier` DISABLE KEYS */;
INSERT INTO `courier` VALUES (4,'2000-05-10','Vasco',NULL),(5,'2000-05-10','Alex',NULL),(6,'2000-01-10','Pedro',NULL),(7,'2000-01-10','Leal',NULL),(8,'2000-01-10','Reis',NULL);
/*!40000 ALTER TABLE `courier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery`
--

DROP TABLE IF EXISTS `delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `client_name` varchar(255) NOT NULL,
  `client_phone_number` varchar(255) NOT NULL,
  `delivery_latitude` double NOT NULL,
  `delivery_longitude` double NOT NULL,
  `delivery_timestamp` datetime(6) DEFAULT NULL,
  `status` int NOT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `courier_id` bigint DEFAULT NULL,
  `shop_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkrkpqcnbi2cm3ronmx1e69hkv` (`courier_id`),
  KEY `FKhkx74up5w3yphq1gxm4vl5gvf` (`shop_id`),
  CONSTRAINT `FKhkx74up5w3yphq1gxm4vl5gvf` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`),
  CONSTRAINT `FKkrkpqcnbi2cm3ronmx1e69hkv` FOREIGN KEY (`courier_id`) REFERENCES `courier` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_latitude` double NOT NULL,
  `shop_longitude` double NOT NULL,
  `name` varchar(255) NOT NULL,
  `business_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK16c5e6hxe1waal7sl2h0dpusb` (`business_id`),
  CONSTRAINT `FK16c5e6hxe1waal7sl2h0dpusb` FOREIGN KEY (`business_id`) REFERENCES `business` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'coviran@ua.pt','vascovasco'),(2,'coviran@il.pt','vascovasco'),(3,'coviran@aa.pt','vascovasco'),(4,'courier@xxx.pt','vascovasco'),(5,'courier@aaa.pt','vascovasco'),(6,'courier@ppp.pt','vascovasco'),(7,'courier@lll.pt','vascovasco'),(8,'courier@rrr.pt','vascovasco');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-23 10:21:10
