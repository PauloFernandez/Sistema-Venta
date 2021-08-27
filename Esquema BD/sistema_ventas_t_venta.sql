-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sistema_ventas
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `t_venta`
--

DROP TABLE IF EXISTS `t_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_venta` (
  `id_venta` int(11) NOT NULL AUTO_INCREMENT,
  `cliente` varchar(200) NOT NULL,
  `vendedor` varchar(200) NOT NULL,
  `total` decimal(10,0) NOT NULL,
  `fecha` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_venta`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_venta`
--

LOCK TABLES `t_venta` WRITE;
/*!40000 ALTER TABLE `t_venta` DISABLE KEYS */;
INSERT INTO `t_venta` VALUES (13,'Modificar','jLabel34',30,'2021-08-06 11:30:49'),(14,'Kary Makup','jLabel34',420,'2021-08-12 20:57:36'),(15,'Kary Makup','Paulo Fernandez',150,'2021-08-13 17:27:11'),(16,'TikToquero','Paulo Fernandez',500,'2021-08-13 17:28:44'),(17,'Modificar','Paulo Fernandez',100,'2021-08-13 17:32:33'),(18,'Prueva','jLabel34',343,'2021-08-20 10:05:31'),(19,'TikToquero','jLabel34',330,'2021-08-20 10:09:57'),(20,'Modificar','jLabel34',137,'2021-08-20 10:13:45'),(21,'Modificar','jLabel34',80,'2021-08-20 10:15:37'),(22,'Modificar','jLabel34',107,'2021-08-20 10:17:42'),(23,'TikToquero','jLabel34',213,'2021-08-20 11:02:01'),(24,'Modificar','jLabel34',107,'2021-08-20 11:05:09'),(25,'Modificar','jLabel34',107,'2021-08-20 11:11:43'),(26,'Modificar','jLabel34',107,'2021-08-20 11:22:27');
/*!40000 ALTER TABLE `t_venta` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-24  3:31:48
