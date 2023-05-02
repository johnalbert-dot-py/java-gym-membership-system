CREATE DATABASE  IF NOT EXISTS `gymmembershipsystem` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gymmembershipsystem`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: gymmembershipsystem
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_member_user_idx` (`user_id`),
  CONSTRAINT `fk_member_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (2,3,'2023-05-03 02:01:40','2023-05-03 02:01:40');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memberprogram`
--

DROP TABLE IF EXISTS `memberprogram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `memberprogram` (
  `id` int NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `payment_status` varchar(255) NOT NULL,
  `payment_type` varchar(255) NOT NULL,
  `program_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `program_id` (`program_id`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `memberprogram_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `memberprogram_ibfk_2` FOREIGN KEY (`program_id`) REFERENCES `program` (`id`),
  CONSTRAINT `memberprogram_ibfk_3` FOREIGN KEY (`program_id`) REFERENCES `program` (`id`) ON DELETE CASCADE,
  CONSTRAINT `memberprogram_ibfk_4` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memberprogram`
--

LOCK TABLES `memberprogram` WRITE;
/*!40000 ALTER TABLE `memberprogram` DISABLE KEYS */;
INSERT INTO `memberprogram` VALUES (21,2,'2023-05-03','2023-05-31','PENDING','CASH',1);
/*!40000 ALTER TABLE `memberprogram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program`
--

DROP TABLE IF EXISTS `program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `amount` float DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program`
--

LOCK TABLES `program` WRITE;
/*!40000 ALTER TABLE `program` DISABLE KEYS */;
INSERT INTO `program` VALUES (1,'Beginners Gym Program','A 3-month program for those who are new to gym training. Includes personalized workout plan and one-on-one coaching sessions.\nAmount: 150.00',300,'2023-05-03 02:44:38','2023-05-03 02:44:38'),(2,'Cardio Fitness Program','A 6-month program designed to improve cardiovascular endurance and overall fitness. Includes group cardio classes and access to all gym facilities.',400,'2023-05-03 02:44:52','2023-05-03 02:44:52'),(3,'Strength Training Program','A 12-month program for those who want to build strength and muscle mass. Includes personalized workout plan and one-on-one coaching sessions.',600,'2023-05-03 02:45:07','2023-05-03 02:45:07'),(4,'Yoga and Meditation Program','A 6-month program for those who want to improve flexibility, balance, and mental wellness. Includes yoga and meditation classes.',400,'2023-05-03 02:45:18','2023-05-03 02:45:18');
/*!40000 ALTER TABLE `program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programsession`
--

DROP TABLE IF EXISTS `programsession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programsession` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `program_id` int NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_program_session_program_idx` (`program_id`),
  CONSTRAINT `fk_program_session_program` FOREIGN KEY (`program_id`) REFERENCES `program` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programsession`
--

LOCK TABLES `programsession` WRITE;
/*!40000 ALTER TABLE `programsession` DISABLE KEYS */;
INSERT INTO `programsession` VALUES (1,'Introduction to Gym Equipment',1,'2023-05-03 02:46:19','2023-05-03 02:46:19'),(2,'Cardiovascular Training Basics',1,'2023-05-03 02:47:12','2023-05-03 02:47:12'),(3,'Strength Training Basics ',1,'2023-05-03 02:47:12','2023-05-03 02:47:12'),(4,'Resistance Machines and Free Weights ',1,'2023-05-03 02:47:12','2023-05-03 02:47:12'),(5,'Stretching and Flexibility Training',1,'2023-05-03 02:47:12','2023-05-03 02:47:12'),(6,'Goal Setting and Progress Tracking ',1,'2023-05-03 02:47:12','2023-05-03 02:47:12'),(7,'Personalized Workout Plan Design ',1,'2023-05-03 02:47:12','2023-05-03 02:47:12'),(8,'Nutrition and Healthy Eating Habits',1,'2023-05-03 02:47:12','2023-05-03 02:47:12'),(9,'One-on-One Coaching Session 1 ',1,'2023-05-03 02:47:12','2023-05-03 02:47:12'),(10,'One-on-One Coaching Session 2 ',1,'2023-05-03 02:47:12','2023-05-03 02:47:12'),(11,' Introduction to Cardio Equipment (treadmills, ellipticals, stationary bikes, etc.)',2,'2023-05-03 02:48:36','2023-05-03 02:48:36'),(12,'Cardiovascular Training Basics (heart rate zones, target heart rate, etc.) ',2,'2023-05-03 02:48:36','2023-05-03 02:48:36'),(13,'HIIT (High-Intensity Interval Training) Workout',2,'2023-05-03 02:48:36','2023-05-03 02:48:36'),(14,' Group Cardio Class 1 (e.g. Zumba, Spinning, Step Aerobics)',2,'2023-05-03 02:48:36','2023-05-03 02:48:36'),(15,'Endurance Training (long distance running, rowing, etc.)',2,'2023-05-03 02:48:36','2023-05-03 02:48:36'),(16,'Group Cardio Class 2',2,'2023-05-03 02:48:36','2023-05-03 02:48:36'),(17,'Strength Training for Cardiovascular Endurance (using resistance machines and free weights to improve stamina)',2,'2023-05-03 02:48:36','2023-05-03 02:48:36'),(18,'Personalized Cardio Workout Plan Design',2,'2023-05-03 02:48:36','2023-05-03 02:48:36'),(19,'Nutrition and Hydration for Cardiovascular Health',2,'2023-05-03 02:48:36','2023-05-03 02:48:36'),(20,' Progress Tracking and Goal Setting',2,'2023-05-03 02:48:36','2023-05-03 02:48:36'),(21,'Introduction to Strength Training Equipment (barbells, dumbbells, resistance machines, etc.)',3,'2023-05-03 02:50:17','2023-05-03 02:50:17'),(22,' Compound Lifts (e.g. squat, deadlift, bench press)',3,'2023-05-03 02:50:17','2023-05-03 02:50:17'),(23,' Isolation Lifts (e.g. bicep curls, tricep extensions, calf raises)',3,'2023-05-03 02:50:17','2023-05-03 02:50:17'),(24,'Progressive Overload (increasing weight and resistance to build strength and muscle)',3,'2023-05-03 02:50:17','2023-05-03 02:50:17'),(25,'Strength Training for Upper Body ',3,'2023-05-03 02:50:17','2023-05-03 02:50:17'),(26,' Strength Training for Lower Body',3,'2023-05-03 02:50:17','2023-05-03 02:50:17'),(27,'Core and Stability Training',3,'2023-05-03 02:50:17','2023-05-03 02:50:17'),(28,'Personalized Strength Training Plan Design',3,'2023-05-03 02:50:17','2023-05-03 02:50:17'),(29,'Nutrition and Supplements for Strength Training',3,'2023-05-03 02:50:17','2023-05-03 02:50:17'),(30,'Progress Tracking and Goal Setting',3,'2023-05-03 02:50:17','2023-05-03 02:50:17'),(31,'Introduction to Yoga and Its Benefits',4,'2023-05-03 02:51:36','2023-05-03 02:51:36'),(32,'Sun Salutations and Basic Asanas (poses)',4,'2023-05-03 02:51:36','2023-05-03 02:51:36'),(33,'Pranayama (breathing techniques)',4,'2023-05-03 02:51:36','2023-05-03 02:51:36'),(34,'Restorative Yoga and Yin Yoga',4,'2023-05-03 02:51:36','2023-05-03 02:51:36'),(35,'Hatha Yoga and Vinyasa Flow',4,'2023-05-03 02:51:36','2023-05-03 02:51:36'),(36,'Meditation Techniques and Mindfulness Practice',4,'2023-05-03 02:51:36','2023-05-03 02:51:36'),(37,'Chakra Balancing and Energy Healing',4,'2023-05-03 02:51:36','2023-05-03 02:51:36'),(38,'Personalized Yoga and Meditation Plan Design\n',4,'2023-05-03 02:51:36','2023-05-03 02:51:36'),(39,'Ayurveda and Holistic Health Practices',4,'2023-05-03 02:51:36','2023-05-03 02:51:36'),(40,'Ayurveda and Holistic Health Practices',4,'2023-05-03 02:51:36','2023-05-03 02:51:36');
/*!40000 ALTER TABLE `programsession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_staff_user_idx` (`user_id`),
  CONSTRAINT `fk_staff_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,4,'2023-05-03 06:15:33','2023-05-03 06:15:33');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `weight_in_kg` float DEFAULT '0',
  `height_in_ft` float DEFAULT '0',
  `birthdate` varchar(255) DEFAULT NULL,
  `gender` varchar(255) NOT NULL,
  `role` int NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,'Johns','Does','Midman',50,5.3,'2005-05-05','Male',1,'test','9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08','2023-05-03 02:01:40','2023-05-03 07:31:30'),(4,'staff','staff','staff',0,0,'2006-05-11','Male',0,'staff','1562206543da764123c21bd524674f0a8aaf49c8a89744c97352fe677f7e4006','2023-05-03 06:15:33','2023-05-03 06:15:33');
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

-- Dump completed on 2023-05-03  7:47:23
