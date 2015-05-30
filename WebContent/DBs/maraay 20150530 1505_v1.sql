-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.6.19-0ubuntu0.14.04.1


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema maraay
--

CREATE DATABASE IF NOT EXISTS maraay;
USE maraay;

--
-- Definition of table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `mobile` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `label` varchar(300) DEFAULT NULL,
  `direction` int(10) unsigned DEFAULT NULL,
  `customer_type` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_customer_1` (`direction`),
  KEY `FK_customer_2` (`customer_type`),
  CONSTRAINT `FK_customer_1` FOREIGN KEY (`direction`) REFERENCES `direction` (`id`),
  CONSTRAINT `FK_customer_2` FOREIGN KEY (`customer_type`) REFERENCES `customer_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customer`
--

/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`id`,`name`,`address`,`mobile`,`phone`,`label`,`direction`,`customer_type`) VALUES 
 (9,'ماركت أبو عبده','بركة السبع','(131) 1131-3311','(213) 213-1313','خلف الكوبرى',4,2),
 (10,'محمد البقال','بركة السبع الجديدة','(123) 3211-1132','(313) 211-1231','سيبيسم',4,2),
 (11,'رر','ررر','(124) 1241-2412','(124) 124-1241','لسلس',5,2);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


--
-- Definition of table `customer_day`
--

DROP TABLE IF EXISTS `customer_day`;
CREATE TABLE `customer_day` (
  `customer_id` int(10) unsigned DEFAULT NULL,
  `day_id` int(10) unsigned DEFAULT NULL,
  KEY `FK_customer_day_1` (`customer_id`),
  KEY `FK_customer_day_2` (`day_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customer_day`
--

/*!40000 ALTER TABLE `customer_day` DISABLE KEYS */;
INSERT INTO `customer_day` (`customer_id`,`day_id`) VALUES 
 (9,1),
 (9,2),
 (9,3),
 (9,4),
 (9,5),
 (9,6),
 (9,7),
 (10,3),
 (10,4),
 (11,6);
/*!40000 ALTER TABLE `customer_day` ENABLE KEYS */;


--
-- Definition of table `customer_type`
--

DROP TABLE IF EXISTS `customer_type`;
CREATE TABLE `customer_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customer_type`
--

/*!40000 ALTER TABLE `customer_type` DISABLE KEYS */;
INSERT INTO `customer_type` (`id`,`type`) VALUES 
 (1,'جملة'),
 (2,'تجزئة'),
 (3,'كبار عملاء');
/*!40000 ALTER TABLE `customer_type` ENABLE KEYS */;


--
-- Definition of table `day_week`
--

DROP TABLE IF EXISTS `day_week`;
CREATE TABLE `day_week` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `day_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `day_week`
--

/*!40000 ALTER TABLE `day_week` DISABLE KEYS */;
INSERT INTO `day_week` (`id`,`day_name`) VALUES 
 (1,'السبت'),
 (2,'اﻷحد'),
 (3,'اﻹتنين'),
 (4,'الثلاثاء'),
 (5,'اﻷربع'),
 (6,'الخميس'),
 (7,'الجمعة');
/*!40000 ALTER TABLE `day_week` ENABLE KEYS */;


--
-- Definition of table `direction`
--

DROP TABLE IF EXISTS `direction`;
CREATE TABLE `direction` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `representative` int(10) unsigned DEFAULT NULL,
  `driver` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_direction_1` (`representative`),
  KEY `FK_direction_2` (`driver`),
  CONSTRAINT `FK_direction_1` FOREIGN KEY (`representative`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_direction_2` FOREIGN KEY (`driver`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `direction`
--

/*!40000 ALTER TABLE `direction` DISABLE KEYS */;
INSERT INTO `direction` (`id`,`name`,`representative`,`driver`) VALUES 
 (4,'بركة السبع',7,8),
 (5,'طنطا',7,8);
/*!40000 ALTER TABLE `direction` ENABLE KEYS */;


--
-- Definition of table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(300) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `basic_salary` double DEFAULT NULL,
  `national_id` varchar(100) DEFAULT NULL,
  `employment_date` date DEFAULT NULL,
  `departure_date` date DEFAULT NULL,
  `employee_type` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_employee_1` (`employee_type`),
  CONSTRAINT `FK_employee_1` FOREIGN KEY (`employee_type`) REFERENCES `employee_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `employee`
--

/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`id`,`name`,`address`,`mobile`,`basic_salary`,`national_id`,`employment_date`,`departure_date`,`employee_type`) VALUES 
 (6,'إبراهيم السيد','قويسنا','(012) 2282-5626',500,'3453453453453','2008-03-05',NULL,1),
 (7,'مسعد السيد','قويسنا','(325) 2352-3532',300,'8888888','2015-01-07',NULL,2),
 (8,'إبراهيم عباس','بنها','(446) 5464-6646',100,'6454654654','2008-03-07',NULL,3);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;


--
-- Definition of table `employee_type`
--

DROP TABLE IF EXISTS `employee_type`;
CREATE TABLE `employee_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `job` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `employee_type`
--

/*!40000 ALTER TABLE `employee_type` DISABLE KEYS */;
INSERT INTO `employee_type` (`id`,`job`) VALUES 
 (1,'محاسب'),
 (2,'مندوب'),
 (3,'سواق');
/*!40000 ALTER TABLE `employee_type` ENABLE KEYS */;


--
-- Definition of table `pay_type`
--

DROP TABLE IF EXISTS `pay_type`;
CREATE TABLE `pay_type` (
  `pay_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pay_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`pay_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pay_type`
--

/*!40000 ALTER TABLE `pay_type` DISABLE KEYS */;
INSERT INTO `pay_type` (`pay_type_id`,`pay_type`) VALUES 
 (1,'نقدى'),
 (2,'إيداع'),
 (3,'تسوية');
/*!40000 ALTER TABLE `pay_type` ENABLE KEYS */;


--
-- Definition of table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `barcode` varchar(255) DEFAULT NULL,
  `min_unit_price` double DEFAULT NULL,
  `max_unit_price` double DEFAULT NULL,
  `max_min_count` int(10) unsigned DEFAULT NULL,
  `rep_max_un_price` double DEFAULT NULL,
  `rep_min_un_price` double DEFAULT NULL,
  `adding_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `max_unit` int(10) unsigned DEFAULT NULL,
  `min_unit` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_product_1` (`max_unit`),
  KEY `FK_product_2` (`min_unit`),
  CONSTRAINT `FK_product_1` FOREIGN KEY (`max_unit`) REFERENCES `unit` (`id`),
  CONSTRAINT `FK_product_2` FOREIGN KEY (`min_unit`) REFERENCES `unit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product`
--

/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`id`,`name`,`barcode`,`min_unit_price`,`max_unit_price`,`max_min_count`,`rep_max_un_price`,`rep_min_un_price`,`adding_date`,`max_unit`,`min_unit`) VALUES 
 (6,'فراولة','32432',2,40,20,35,1.75,'2015-03-28 15:18:27',1,2),
 (7,'مانجو','324235',2,20,10,16,1.6,'2015-03-28 15:19:33',1,2),
 (8,'ا','kjjkkljkl',2,20,10,15,1.5,'2015-04-18 18:29:06',1,2);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;


--
-- Definition of table `product_customer`
--

DROP TABLE IF EXISTS `product_customer`;
CREATE TABLE `product_customer` (
  `product_order` int(11) NOT NULL,
  `customer_id` int(10) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product_customer`
--

/*!40000 ALTER TABLE `product_customer` DISABLE KEYS */;
INSERT INTO `product_customer` (`product_order`,`customer_id`,`product_id`) VALUES 
 (0,9,6),
 (1,9,7),
 (0,10,6),
 (1,10,7),
 (0,11,6);
/*!40000 ALTER TABLE `product_customer` ENABLE KEYS */;


--
-- Definition of table `tbl_com_blending_date`
--

DROP TABLE IF EXISTS `tbl_com_blending_date`;
CREATE TABLE `tbl_com_blending_date` (
  `blending_date_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `total_before` double DEFAULT NULL,
  `total_after` double DEFAULT NULL,
  `incoming_blending_rate_total` double DEFAULT NULL,
  `discount_value` double DEFAULT NULL,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  `incoming_total_after` double DEFAULT NULL,
  PRIMARY KEY (`blending_date_id`),
  KEY `FK_tbl_com_blending_date_1` (`by_user_id`),
  CONSTRAINT `FK_tbl_com_blending_date_1` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_com_blending_date`
--

/*!40000 ALTER TABLE `tbl_com_blending_date` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_com_blending_date` ENABLE KEYS */;


--
-- Definition of table `tbl_com_blending_value`
--

DROP TABLE IF EXISTS `tbl_com_blending_value`;
CREATE TABLE `tbl_com_blending_value` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `max_mount` int(10) unsigned DEFAULT NULL,
  `old_max_mount` int(10) unsigned DEFAULT NULL,
  `max_mount_price` double DEFAULT NULL,
  `old_max_mount_price` double DEFAULT NULL,
  `min_mount` int(10) unsigned DEFAULT NULL,
  `old_min_mount` int(10) unsigned DEFAULT NULL,
  `min_mount_price` double DEFAULT NULL,
  `old_min_mount_price` double DEFAULT NULL,
  `total_mount` double DEFAULT NULL,
  `old_total_mount` double DEFAULT NULL,
  `showen_mount` varchar(45) DEFAULT NULL,
  `total_before` double DEFAULT NULL,
  `total_after` double DEFAULT NULL,
  `incoming_blending_rate` double DEFAULT NULL,
  `is_edited` tinyint(3) unsigned DEFAULT NULL,
  `blending_date_id` bigint(20) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tbl_com_blending_value_1` (`blending_date_id`),
  KEY `FK_tbl_com_blending_value_2` (`product_id`),
  CONSTRAINT `FK_tbl_com_blending_value_1` FOREIGN KEY (`blending_date_id`) REFERENCES `tbl_com_blending_date` (`blending_date_id`),
  CONSTRAINT `FK_tbl_com_blending_value_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_com_blending_value`
--

/*!40000 ALTER TABLE `tbl_com_blending_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_com_blending_value` ENABLE KEYS */;


--
-- Definition of table `tbl_com_calculation_equation`
--

DROP TABLE IF EXISTS `tbl_com_calculation_equation`;
CREATE TABLE `tbl_com_calculation_equation` (
  `calc_equ_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `total_before` double DEFAULT NULL,
  `total_after` double DEFAULT NULL,
  `total_supply_value` double DEFAULT NULL,
  `old_supply_value` double DEFAULT NULL,
  `min_plus` double DEFAULT NULL,
  `discount_value` double DEFAULT NULL,
  PRIMARY KEY (`calc_equ_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_com_calculation_equation`
--

/*!40000 ALTER TABLE `tbl_com_calculation_equation` DISABLE KEYS */;
INSERT INTO `tbl_com_calculation_equation` (`calc_equ_id`,`date`,`total_before`,`total_after`,`total_supply_value`,`old_supply_value`,`min_plus`,`discount_value`) VALUES 
 (9,'2015-03-01',300,270,250,NULL,-20,30),
 (10,'2015-05-01',172,154.8,100,NULL,-54.80000000000001,17.19999999999999);
/*!40000 ALTER TABLE `tbl_com_calculation_equation` ENABLE KEYS */;


--
-- Definition of table `tbl_com_defects_date`
--

DROP TABLE IF EXISTS `tbl_com_defects_date`;
CREATE TABLE `tbl_com_defects_date` (
  `defects_date_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `total_before` double DEFAULT NULL,
  `total_after` double DEFAULT NULL,
  `discount_value` double DEFAULT NULL,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`defects_date_id`),
  KEY `FK_tbl_com_defects_date_1` (`by_user_id`),
  CONSTRAINT `FK_tbl_com_defects_date_1` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_com_defects_date`
--

/*!40000 ALTER TABLE `tbl_com_defects_date` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_com_defects_date` ENABLE KEYS */;


--
-- Definition of table `tbl_com_defects_value`
--

DROP TABLE IF EXISTS `tbl_com_defects_value`;
CREATE TABLE `tbl_com_defects_value` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `max_mount` int(10) unsigned DEFAULT NULL,
  `old_max_mount` int(10) unsigned DEFAULT NULL,
  `max_mount_price` double DEFAULT NULL,
  `min_mount` int(10) unsigned DEFAULT NULL,
  `old_min_mount` int(10) unsigned DEFAULT NULL,
  `min_mount_price` double DEFAULT NULL,
  `old_min_mount_price` double DEFAULT NULL,
  `old_max_mount_price` double DEFAULT NULL,
  `total_mount` double DEFAULT NULL,
  `old_total_mount` double DEFAULT NULL,
  `showen_mount` varchar(45) DEFAULT NULL,
  `total_before` double DEFAULT NULL,
  `total_after` double DEFAULT NULL,
  `is_edited` tinyint(3) unsigned DEFAULT NULL,
  `defects_date_id` bigint(20) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tbl_com_defects_value_1` (`defects_date_id`),
  KEY `FK_tbl_com_defects_value_2` (`product_id`),
  CONSTRAINT `FK_tbl_com_defects_value_1` FOREIGN KEY (`defects_date_id`) REFERENCES `tbl_com_defects_date` (`defects_date_id`),
  CONSTRAINT `FK_tbl_com_defects_value_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_com_defects_value`
--

/*!40000 ALTER TABLE `tbl_com_defects_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_com_defects_value` ENABLE KEYS */;


--
-- Definition of table `tbl_com_discount_date`
--

DROP TABLE IF EXISTS `tbl_com_discount_date`;
CREATE TABLE `tbl_com_discount_date` (
  `date_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `by_user` int(10) unsigned DEFAULT NULL,
  `is_completed` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`date_id`),
  KEY `FK_tbl_com_discount_date_1` (`by_user`),
  CONSTRAINT `FK_tbl_com_discount_date_1` FOREIGN KEY (`by_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_com_discount_date`
--

/*!40000 ALTER TABLE `tbl_com_discount_date` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_com_discount_date` ENABLE KEYS */;


--
-- Definition of table `tbl_com_discount_value`
--

DROP TABLE IF EXISTS `tbl_com_discount_value`;
CREATE TABLE `tbl_com_discount_value` (
  `discount_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `discount_value` double DEFAULT NULL,
  `old_value` double DEFAULT NULL,
  `is_edited` tinyint(3) unsigned DEFAULT NULL,
  `discount_date` bigint(20) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`discount_id`),
  KEY `FK_tbl_com_discount_value_1` (`discount_date`),
  KEY `FK_tbl_com_discount_value_2` (`product_id`),
  CONSTRAINT `FK_tbl_com_discount_value_1` FOREIGN KEY (`discount_date`) REFERENCES `tbl_com_discount_date` (`date_id`),
  CONSTRAINT `FK_tbl_com_discount_value_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_com_discount_value`
--

/*!40000 ALTER TABLE `tbl_com_discount_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_com_discount_value` ENABLE KEYS */;


--
-- Definition of table `tbl_com_incoming_date`
--

DROP TABLE IF EXISTS `tbl_com_incoming_date`;
CREATE TABLE `tbl_com_incoming_date` (
  `incoming_date_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `total_before` double DEFAULT NULL,
  `total_after` double DEFAULT NULL,
  `discount_value` double DEFAULT NULL,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`incoming_date_id`),
  KEY `FK_tbl_com_incoming_date_1` (`by_user_id`),
  CONSTRAINT `FK_tbl_com_incoming_date_1` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_com_incoming_date`
--

/*!40000 ALTER TABLE `tbl_com_incoming_date` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_com_incoming_date` ENABLE KEYS */;


--
-- Definition of table `tbl_com_incoming_value`
--

DROP TABLE IF EXISTS `tbl_com_incoming_value`;
CREATE TABLE `tbl_com_incoming_value` (
  `incoming_value_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `max_mount` int(10) unsigned DEFAULT NULL,
  `old_max_mount` int(10) unsigned DEFAULT NULL,
  `max_mount_price` double DEFAULT NULL,
  `old_max_mount_price` double DEFAULT NULL,
  `min_mount` int(10) unsigned DEFAULT NULL,
  `old_min_mount` int(10) unsigned DEFAULT NULL,
  `min_mount_price` double DEFAULT NULL,
  `old_min_mount_price` double DEFAULT NULL,
  `total_mount` double DEFAULT NULL,
  `old_total_mount` double DEFAULT NULL,
  `showen_mount` varchar(45) DEFAULT NULL,
  `total_before` double DEFAULT NULL,
  `total_after` double DEFAULT NULL,
  `is_edited` tinyint(3) unsigned DEFAULT NULL,
  `incoming_date_id` bigint(20) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`incoming_value_id`),
  KEY `FK_tbl_com_incoming_value_1` (`incoming_date_id`),
  KEY `FK_tbl_com_incoming_value_2` (`product_id`),
  CONSTRAINT `FK_tbl_com_incoming_value_1` FOREIGN KEY (`incoming_date_id`) REFERENCES `tbl_com_incoming_date` (`incoming_date_id`),
  CONSTRAINT `FK_tbl_com_incoming_value_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_com_incoming_value`
--

/*!40000 ALTER TABLE `tbl_com_incoming_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_com_incoming_value` ENABLE KEYS */;


--
-- Definition of table `tbl_com_offer_date`
--

DROP TABLE IF EXISTS `tbl_com_offer_date`;
CREATE TABLE `tbl_com_offer_date` (
  `offer_date_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `total_before` double DEFAULT NULL,
  `total_after` double DEFAULT NULL,
  `discount_value` double DEFAULT NULL,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`offer_date_id`),
  KEY `FK_tbl_com_offer_date_1` (`by_user_id`),
  CONSTRAINT `FK_tbl_com_offer_date_1` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_com_offer_date`
--

/*!40000 ALTER TABLE `tbl_com_offer_date` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_com_offer_date` ENABLE KEYS */;


--
-- Definition of table `tbl_com_offer_value`
--

DROP TABLE IF EXISTS `tbl_com_offer_value`;
CREATE TABLE `tbl_com_offer_value` (
  `offer_value_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `max_mount` int(10) unsigned DEFAULT NULL,
  `old_max_mount` int(10) unsigned DEFAULT NULL,
  `max_mount_price` double DEFAULT NULL,
  `old_max_mount_price` double DEFAULT NULL,
  `min_mount` int(10) unsigned DEFAULT NULL,
  `old_min_mount` int(10) unsigned DEFAULT NULL,
  `min_mount_price` double DEFAULT NULL,
  `old_min_mount_price` double DEFAULT NULL,
  `total_mount` double DEFAULT NULL,
  `old_total_mount` double DEFAULT NULL,
  `showen_mount` varchar(45) DEFAULT NULL,
  `total_before` double DEFAULT NULL,
  `total_after` double DEFAULT NULL,
  `is_edited` tinyint(3) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT NULL,
  `offer_date_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`offer_value_id`),
  KEY `FK_tbl_com_offer_value_1` (`offer_date_id`),
  KEY `FK_tbl_com_offer_value_2` (`product_id`),
  CONSTRAINT `FK_tbl_com_offer_value_1` FOREIGN KEY (`offer_date_id`) REFERENCES `tbl_com_offer_date` (`offer_date_id`),
  CONSTRAINT `FK_tbl_com_offer_value_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_com_offer_value`
--

/*!40000 ALTER TABLE `tbl_com_offer_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_com_offer_value` ENABLE KEYS */;


--
-- Definition of table `tbl_com_sales_date`
--

DROP TABLE IF EXISTS `tbl_com_sales_date`;
CREATE TABLE `tbl_com_sales_date` (
  `sales_date_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `total_before` double DEFAULT NULL,
  `total_after` double DEFAULT NULL,
  `discount_value` double DEFAULT NULL,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`sales_date_id`),
  KEY `FK_tbl_com_sales_date_1` (`by_user_id`),
  CONSTRAINT `FK_tbl_com_sales_date_1` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_com_sales_date`
--

/*!40000 ALTER TABLE `tbl_com_sales_date` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_com_sales_date` ENABLE KEYS */;


--
-- Definition of table `tbl_com_sales_value`
--

DROP TABLE IF EXISTS `tbl_com_sales_value`;
CREATE TABLE `tbl_com_sales_value` (
  `sales_value_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `total_mount` double DEFAULT NULL,
  `total_before` double DEFAULT NULL,
  `total_after` double DEFAULT NULL,
  `showen_mount` varchar(45) DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT NULL,
  `sales_date_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`sales_value_id`),
  KEY `FK_tbl_com_sales_value_1` (`sales_date_id`),
  KEY `FK_tbl_com_sales_value_2` (`product_id`),
  CONSTRAINT `FK_tbl_com_sales_value_1` FOREIGN KEY (`sales_date_id`) REFERENCES `tbl_com_sales_date` (`sales_date_id`),
  CONSTRAINT `FK_tbl_com_sales_value_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_com_sales_value`
--

/*!40000 ALTER TABLE `tbl_com_sales_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_com_sales_value` ENABLE KEYS */;


--
-- Definition of table `tbl_com_supply_value`
--

DROP TABLE IF EXISTS `tbl_com_supply_value`;
CREATE TABLE `tbl_com_supply_value` (
  `supply_value_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `calc_equ_id` bigint(20) unsigned DEFAULT NULL,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  `sender_name` varchar(255) DEFAULT NULL,
  `reciever_name` varchar(255) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `no_deposite` varchar(255) DEFAULT NULL,
  `pay_type_id` int(10) unsigned DEFAULT NULL,
  `supply_value` double DEFAULT NULL,
  `is_edited` tinyint(3) unsigned DEFAULT NULL,
  `supply_old_value` double DEFAULT NULL,
  PRIMARY KEY (`supply_value_id`),
  KEY `FK_supply_value_1` (`pay_type_id`),
  KEY `FK_supply_value_2` (`calc_equ_id`),
  KEY `FK_tbl_com_supply_value_3` (`by_user_id`),
  CONSTRAINT `FK_supply_value_1` FOREIGN KEY (`pay_type_id`) REFERENCES `pay_type` (`pay_type_id`),
  CONSTRAINT `FK_supply_value_2` FOREIGN KEY (`calc_equ_id`) REFERENCES `tbl_com_calculation_equation` (`calc_equ_id`),
  CONSTRAINT `FK_tbl_com_supply_value_3` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_com_supply_value`
--

/*!40000 ALTER TABLE `tbl_com_supply_value` DISABLE KEYS */;
INSERT INTO `tbl_com_supply_value` (`supply_value_id`,`calc_equ_id`,`by_user_id`,`sender_name`,`reciever_name`,`bank_name`,`no_deposite`,`pay_type_id`,`supply_value`,`is_edited`,`supply_old_value`) VALUES 
 (8,9,1,'محمود','أحمد',NULL,NULL,1,100,0,NULL),
 (9,9,1,'محمود',NULL,'بنك مصر','234',2,100,0,NULL),
 (10,9,1,'الشركة','أحمد سند',NULL,NULL,3,50,0,NULL),
 (11,10,1,'نتنمت','ىنمتنمت',NULL,NULL,1,100,0,NULL);
/*!40000 ALTER TABLE `tbl_com_supply_value` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_blending_date`
--

DROP TABLE IF EXISTS `tbl_rep_blending_date`;
CREATE TABLE `tbl_rep_blending_date` (
  `blending_date_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `direction_id` int(10) unsigned DEFAULT NULL,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  `date` date DEFAULT NULL,
  `total` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`blending_date_id`),
  KEY `FK_tbl_rep_blending_date_1` (`direction_id`),
  KEY `FK_tbl_rep_blending_date_2` (`by_user_id`),
  CONSTRAINT `FK_tbl_rep_blending_date_1` FOREIGN KEY (`direction_id`) REFERENCES `direction` (`id`),
  CONSTRAINT `FK_tbl_rep_blending_date_2` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_blending_date`
--

/*!40000 ALTER TABLE `tbl_rep_blending_date` DISABLE KEYS */;
INSERT INTO `tbl_rep_blending_date` (`blending_date_id`,`direction_id`,`by_user_id`,`date`,`total`,`is_edit`) VALUES 
 (8,4,1,'2015-05-02',70.85,0),
 (9,4,1,'2015-05-09',70.85,0);
/*!40000 ALTER TABLE `tbl_rep_blending_date` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_blending_value`
--

DROP TABLE IF EXISTS `tbl_rep_blending_value`;
CREATE TABLE `tbl_rep_blending_value` (
  `blending_value_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `blending_date_id` bigint(20) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT NULL,
  `max_mount` int(10) unsigned DEFAULT NULL,
  `max_mount_price` double DEFAULT NULL,
  `old_max_mount` int(10) unsigned DEFAULT NULL,
  `old_max_mount_price` double DEFAULT NULL,
  `min_mount` int(10) unsigned DEFAULT NULL,
  `min_mount_price` double DEFAULT NULL,
  `old_min_mount` int(10) unsigned DEFAULT NULL,
  `old_min_mount_price` double DEFAULT NULL,
  `showen_mount` varchar(50) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`blending_value_id`),
  KEY `FK_tbl_rep_blending_value_1` (`product_id`),
  KEY `FK_tbl_rep_blending_value_2` (`blending_date_id`),
  CONSTRAINT `FK_tbl_rep_blending_value_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_tbl_rep_blending_value_2` FOREIGN KEY (`blending_date_id`) REFERENCES `tbl_rep_blending_date` (`blending_date_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_blending_value`
--

/*!40000 ALTER TABLE `tbl_rep_blending_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_rep_blending_value` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_defect_date`
--

DROP TABLE IF EXISTS `tbl_rep_defect_date`;
CREATE TABLE `tbl_rep_defect_date` (
  `defect_date_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `direction_id` int(10) unsigned DEFAULT NULL,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  `date` date DEFAULT NULL,
  `total` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`defect_date_id`),
  KEY `FK_tbl_rep_defect_date_1` (`direction_id`),
  KEY `FK_tbl_rep_defect_date_2` (`by_user_id`),
  CONSTRAINT `FK_tbl_rep_defect_date_1` FOREIGN KEY (`direction_id`) REFERENCES `direction` (`id`),
  CONSTRAINT `FK_tbl_rep_defect_date_2` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_defect_date`
--

/*!40000 ALTER TABLE `tbl_rep_defect_date` DISABLE KEYS */;
INSERT INTO `tbl_rep_defect_date` (`defect_date_id`,`direction_id`,`by_user_id`,`date`,`total`,`is_edit`) VALUES 
 (8,4,1,'2015-05-02',70.85,0),
 (9,4,1,'2015-05-09',70.85,0);
/*!40000 ALTER TABLE `tbl_rep_defect_date` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_defect_value`
--

DROP TABLE IF EXISTS `tbl_rep_defect_value`;
CREATE TABLE `tbl_rep_defect_value` (
  `defect_value_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `defect_date_id` bigint(20) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT NULL,
  `max_mount` int(10) unsigned DEFAULT NULL,
  `max_mount_price` double DEFAULT NULL,
  `old_max_mount` int(10) unsigned DEFAULT NULL,
  `old_max_mount_price` double DEFAULT NULL,
  `min_mount` int(10) unsigned DEFAULT NULL,
  `min_mount_price` double DEFAULT NULL,
  `old_min_mount` int(10) unsigned DEFAULT NULL,
  `old_min_mount_price` double DEFAULT NULL,
  `showen_mount` varchar(50) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`defect_value_id`),
  KEY `FK_tbl_rep_defect_value_1` (`product_id`),
  KEY `FK_tbl_rep_defect_value_2` (`defect_date_id`),
  CONSTRAINT `FK_tbl_rep_defect_value_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_tbl_rep_defect_value_2` FOREIGN KEY (`defect_date_id`) REFERENCES `tbl_rep_defect_date` (`defect_date_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_defect_value`
--

/*!40000 ALTER TABLE `tbl_rep_defect_value` DISABLE KEYS */;
INSERT INTO `tbl_rep_defect_value` (`defect_value_id`,`defect_date_id`,`product_id`,`max_mount`,`max_mount_price`,`old_max_mount`,`old_max_mount_price`,`min_mount`,`min_mount_price`,`old_min_mount`,`old_min_mount_price`,`showen_mount`,`total_price`,`is_edit`) VALUES 
 (22,8,6,1,35,NULL,NULL,1,1.75,NULL,NULL,'1.1',36.75,0),
 (23,8,7,1,16,NULL,NULL,1,1.6,NULL,NULL,'1.1',17.6,0),
 (24,8,8,1,15,NULL,NULL,1,1.5,NULL,NULL,'1.1',16.5,0),
 (25,9,6,1,35,NULL,NULL,1,1.75,NULL,NULL,'1.1',36.75,0),
 (26,9,7,1,16,NULL,NULL,1,1.6,NULL,NULL,'1.1',17.6,0),
 (27,9,8,1,15,NULL,NULL,1,1.5,NULL,NULL,'1.1',16.5,0);
/*!40000 ALTER TABLE `tbl_rep_defect_value` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_first_time_date`
--

DROP TABLE IF EXISTS `tbl_rep_first_time_date`;
CREATE TABLE `tbl_rep_first_time_date` (
  `first_time_date_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `direction_id` int(10) unsigned DEFAULT NULL,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  `date` date DEFAULT NULL,
  `total` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`first_time_date_id`),
  KEY `FK_Tbl_rep_first_time_date_1` (`direction_id`),
  KEY `FK_Tbl_rep_first_time_date_2` (`by_user_id`),
  CONSTRAINT `FK_Tbl_rep_first_time_date_1` FOREIGN KEY (`direction_id`) REFERENCES `direction` (`id`),
  CONSTRAINT `FK_Tbl_rep_first_time_date_2` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_first_time_date`
--

/*!40000 ALTER TABLE `tbl_rep_first_time_date` DISABLE KEYS */;
INSERT INTO `tbl_rep_first_time_date` (`first_time_date_id`,`direction_id`,`by_user_id`,`date`,`total`,`is_edit`) VALUES 
 (25,4,1,'2015-05-02',70.85,0),
 (26,4,1,'2015-05-09',141.7,0);
/*!40000 ALTER TABLE `tbl_rep_first_time_date` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_first_time_value`
--

DROP TABLE IF EXISTS `tbl_rep_first_time_value`;
CREATE TABLE `tbl_rep_first_time_value` (
  `first_time_value_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `first_time_date_id` bigint(20) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT NULL,
  `max_mount` int(10) unsigned DEFAULT NULL,
  `max_mount_price` double DEFAULT NULL,
  `old_max_mount` int(10) unsigned DEFAULT NULL,
  `old_max_mount_price` double DEFAULT NULL,
  `min_mount` int(10) unsigned DEFAULT NULL,
  `min_mount_price` double DEFAULT NULL,
  `old_min_mount` int(10) unsigned DEFAULT NULL,
  `old_min_mount_price` double DEFAULT NULL,
  `showen_mount` varchar(50) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`first_time_value_id`),
  KEY `FK_tbl_first_time_value_1` (`product_id`),
  KEY `FK_tbl_first_time_value_2` (`first_time_date_id`),
  CONSTRAINT `FK_tbl_first_time_value_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_tbl_first_time_value_2` FOREIGN KEY (`first_time_date_id`) REFERENCES `tbl_rep_first_time_date` (`first_time_date_id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_first_time_value`
--

/*!40000 ALTER TABLE `tbl_rep_first_time_value` DISABLE KEYS */;
INSERT INTO `tbl_rep_first_time_value` (`first_time_value_id`,`first_time_date_id`,`product_id`,`max_mount`,`max_mount_price`,`old_max_mount`,`old_max_mount_price`,`min_mount`,`min_mount_price`,`old_min_mount`,`old_min_mount_price`,`showen_mount`,`total_price`,`is_edit`) VALUES 
 (73,25,6,1,35,NULL,NULL,1,1.75,NULL,NULL,'1.1',36.75,0),
 (74,25,7,1,16,NULL,NULL,1,1.6,NULL,NULL,'1.1',17.6,0),
 (75,25,8,1,15,NULL,NULL,1,1.5,NULL,NULL,'1.1',16.5,0),
 (76,26,6,2,70,NULL,NULL,2,3.5,NULL,NULL,'2.2',73.5,0),
 (77,26,7,2,32,NULL,NULL,2,3.2,NULL,NULL,'2.2',35.2,0),
 (78,26,8,2,30,NULL,NULL,2,3,NULL,NULL,'2.2',33,0);
/*!40000 ALTER TABLE `tbl_rep_first_time_value` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_invoice`
--

DROP TABLE IF EXISTS `tbl_rep_invoice`;
CREATE TABLE `tbl_rep_invoice` (
  `invoice_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `invoice_number` bigint(20) unsigned DEFAULT NULL,
  `customer_id` int(10) unsigned DEFAULT NULL,
  `invoice_date` date DEFAULT NULL,
  `total_price_before` double DEFAULT NULL,
  `discount_rate` double DEFAULT NULL,
  `additional_discount` double DEFAULT NULL,
  `discount_value` double DEFAULT NULL,
  `total_price_after` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `FK_tbl_rep_invoice_1` (`by_user_id`),
  KEY `FK_tbl_rep_invoice_2` (`customer_id`),
  CONSTRAINT `FK_tbl_rep_invoice_1` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_tbl_rep_invoice_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_invoice`
--

/*!40000 ALTER TABLE `tbl_rep_invoice` DISABLE KEYS */;
INSERT INTO `tbl_rep_invoice` (`invoice_id`,`invoice_number`,`customer_id`,`invoice_date`,`total_price_before`,`discount_rate`,`additional_discount`,`discount_value`,`total_price_after`,`is_edit`,`by_user_id`) VALUES 
 (1,1,9,'2015-04-24',70.85,10,0,7.085,63.76499999999999,NULL,1),
 (2,2,10,'2015-04-25',141.7,5,5,14.17,127.52999999999999,NULL,1),
 (3,12,9,'2015-05-09',70.85,5,1,4.2509999999999994,66.59899999999999,0,1),
 (4,11,9,'2015-05-08',141.7,10,10,28.34,113.35999999999999,0,1),
 (5,18,10,'2015-05-09',70.85,0,0,0,70.85,0,1),
 (6,19,9,'2015-05-08',230.85,0,0,0,230.85,0,1),
 (7,20,9,'2015-05-22',70.85,10,10,14.17,56.67999999999999,0,1),
 (8,21,9,'2015-05-08',86.85,0,0,0,86.85,0,1),
 (9,33,9,'2015-05-22',70.85,0,0,0,70.85,0,1),
 (10,30,9,'2015-05-09',70.85,10,0,7.085,63.76499999999999,0,1);
/*!40000 ALTER TABLE `tbl_rep_invoice` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_invoice_values`
--

DROP TABLE IF EXISTS `tbl_rep_invoice_values`;
CREATE TABLE `tbl_rep_invoice_values` (
  `invoice_value_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `product_id` int(10) unsigned DEFAULT NULL,
  `max_mount` int(10) unsigned DEFAULT NULL,
  `old_max_mount` int(10) unsigned DEFAULT NULL,
  `min_mount` int(10) unsigned DEFAULT NULL,
  `old_min_mount` int(10) unsigned DEFAULT NULL,
  `max_price` double DEFAULT NULL,
  `old_max_price` double DEFAULT NULL,
  `min_price` double DEFAULT NULL,
  `old_min_price` double DEFAULT NULL,
  `showen_mount` varchar(50) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  `invoice_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`invoice_value_id`),
  KEY `FK_tbl_rep_invoice_values_1` (`product_id`),
  KEY `FK_tbl_rep_invoice_values_2` (`invoice_id`),
  CONSTRAINT `FK_tbl_rep_invoice_values_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_tbl_rep_invoice_values_2` FOREIGN KEY (`invoice_id`) REFERENCES `tbl_rep_invoice` (`invoice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_invoice_values`
--

/*!40000 ALTER TABLE `tbl_rep_invoice_values` DISABLE KEYS */;
INSERT INTO `tbl_rep_invoice_values` (`invoice_value_id`,`product_id`,`max_mount`,`old_max_mount`,`min_mount`,`old_min_mount`,`max_price`,`old_max_price`,`min_price`,`old_min_price`,`showen_mount`,`total_price`,`is_edit`,`invoice_id`) VALUES 
 (1,6,1,NULL,1,NULL,35,NULL,1.75,NULL,'1.1',36.75,NULL,1),
 (2,7,1,NULL,1,NULL,16,NULL,1.6,NULL,'1.1',17.6,NULL,1),
 (3,8,1,NULL,1,NULL,15,NULL,1.5,NULL,'1.1',16.5,NULL,1),
 (4,6,2,NULL,2,NULL,70,NULL,3.5,NULL,'2.2',73.5,NULL,2),
 (5,7,2,NULL,2,NULL,32,NULL,3.2,NULL,'2.2',35.2,NULL,2),
 (6,8,2,NULL,2,NULL,30,NULL,3,NULL,'2.2',33,NULL,2),
 (7,6,1,NULL,1,NULL,35,NULL,1.75,NULL,'1.1',36.75,0,3),
 (8,7,1,NULL,1,NULL,16,NULL,1.6,NULL,'1.1',17.6,0,3),
 (9,8,1,NULL,1,NULL,15,NULL,1.5,NULL,'1.1',16.5,0,3),
 (10,6,2,NULL,2,NULL,70,NULL,3.5,NULL,'2.2',73.5,0,4),
 (11,7,2,NULL,2,NULL,32,NULL,3.2,NULL,'2.2',35.2,0,4),
 (12,8,2,NULL,2,NULL,30,NULL,3,NULL,'2.2',33,0,4),
 (13,6,1,NULL,1,NULL,35,NULL,1.75,NULL,'1.1',36.75,0,5),
 (14,7,1,NULL,1,NULL,16,NULL,1.6,NULL,'1.1',17.6,0,5),
 (15,8,1,NULL,1,NULL,15,NULL,1.5,NULL,'1.1',16.5,0,5),
 (16,6,1,NULL,1,NULL,35,NULL,1.75,NULL,'1.1',36.75,0,6),
 (17,7,11,NULL,1,NULL,176,NULL,1.6,NULL,'11.1',177.6,0,6),
 (18,8,1,NULL,1,NULL,15,NULL,1.5,NULL,'1.1',16.5,0,6),
 (19,6,1,NULL,1,NULL,35,NULL,1.75,NULL,'1.1',36.75,0,7),
 (20,7,1,NULL,1,NULL,16,NULL,1.6,NULL,'1.1',17.6,0,7),
 (21,8,1,NULL,1,NULL,15,NULL,1.5,NULL,'1.1',16.5,0,7),
 (22,6,1,NULL,1,NULL,35,NULL,1.75,NULL,'1.1',36.75,0,8),
 (23,7,1,NULL,11,NULL,16,NULL,17.6,NULL,'1.11',33.6,0,8),
 (24,8,1,NULL,1,NULL,15,NULL,1.5,NULL,'1.1',16.5,0,8),
 (25,6,1,NULL,1,NULL,35,NULL,1.75,NULL,'1.1',36.75,0,9),
 (26,7,1,NULL,1,NULL,16,NULL,1.6,NULL,'1.1',17.6,0,9),
 (27,8,1,NULL,1,NULL,15,NULL,1.5,NULL,'1.1',16.5,0,9),
 (28,6,1,NULL,1,NULL,35,NULL,1.75,NULL,'1.1',36.75,0,10),
 (29,7,1,NULL,1,NULL,16,NULL,1.6,NULL,'1.1',17.6,0,10),
 (30,8,1,NULL,1,NULL,15,NULL,1.5,NULL,'1.1',16.5,0,10);
/*!40000 ALTER TABLE `tbl_rep_invoice_values` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_last_time_date`
--

DROP TABLE IF EXISTS `tbl_rep_last_time_date`;
CREATE TABLE `tbl_rep_last_time_date` (
  `last_time_date_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `direction_id` int(10) unsigned DEFAULT NULL,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  `date` date DEFAULT NULL,
  `total` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`last_time_date_id`),
  KEY `FK_tbl_rep_last_time_date_1` (`direction_id`),
  KEY `FK_tbl_rep_last_time_date_2` (`by_user_id`),
  CONSTRAINT `FK_tbl_rep_last_time_date_1` FOREIGN KEY (`direction_id`) REFERENCES `direction` (`id`),
  CONSTRAINT `FK_tbl_rep_last_time_date_2` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_last_time_date`
--

/*!40000 ALTER TABLE `tbl_rep_last_time_date` DISABLE KEYS */;
INSERT INTO `tbl_rep_last_time_date` (`last_time_date_id`,`direction_id`,`by_user_id`,`date`,`total`,`is_edit`) VALUES 
 (16,4,1,'2015-05-01',70.85,0),
 (17,4,1,'2015-05-08',141.7,0);
/*!40000 ALTER TABLE `tbl_rep_last_time_date` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_last_time_value`
--

DROP TABLE IF EXISTS `tbl_rep_last_time_value`;
CREATE TABLE `tbl_rep_last_time_value` (
  `last_time_value_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `last_time_date_id` bigint(20) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT NULL,
  `max_mount` int(10) unsigned DEFAULT NULL,
  `max_mount_price` double DEFAULT NULL,
  `old_max_mount` int(10) unsigned DEFAULT NULL,
  `old_max_mount_price` double DEFAULT NULL,
  `min_mount` int(10) unsigned DEFAULT NULL,
  `min_mount_price` double DEFAULT NULL,
  `old_min_mount` int(10) unsigned DEFAULT NULL,
  `old_min_mount_price` double DEFAULT NULL,
  `showen_mount` varchar(50) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`last_time_value_id`),
  KEY `FK_tbl_rep_last_time_value_1` (`product_id`),
  KEY `FK_tbl_rep_last_time_value_2` (`last_time_date_id`),
  CONSTRAINT `FK_tbl_rep_last_time_value_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_tbl_rep_last_time_value_2` FOREIGN KEY (`last_time_date_id`) REFERENCES `tbl_rep_last_time_date` (`last_time_date_id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_last_time_value`
--

/*!40000 ALTER TABLE `tbl_rep_last_time_value` DISABLE KEYS */;
INSERT INTO `tbl_rep_last_time_value` (`last_time_value_id`,`last_time_date_id`,`product_id`,`max_mount`,`max_mount_price`,`old_max_mount`,`old_max_mount_price`,`min_mount`,`min_mount_price`,`old_min_mount`,`old_min_mount_price`,`showen_mount`,`total_price`,`is_edit`) VALUES 
 (46,16,6,1,35,NULL,NULL,1,1.75,NULL,NULL,'1.1',36.75,0),
 (47,16,7,1,16,NULL,NULL,1,1.6,NULL,NULL,'1.1',17.6,0),
 (48,16,8,1,15,NULL,NULL,1,1.5,NULL,NULL,'1.1',16.5,0),
 (49,17,6,2,70,NULL,NULL,2,3.5,NULL,NULL,'2.2',73.5,0),
 (50,17,7,2,32,NULL,NULL,2,3.2,NULL,NULL,'2.2',35.2,0),
 (51,17,8,2,30,NULL,NULL,2,3,NULL,NULL,'2.2',33,0);
/*!40000 ALTER TABLE `tbl_rep_last_time_value` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_new_loading_date`
--

DROP TABLE IF EXISTS `tbl_rep_new_loading_date`;
CREATE TABLE `tbl_rep_new_loading_date` (
  `new_loading_date_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `direction_id` int(10) unsigned DEFAULT NULL,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  `date` date DEFAULT NULL,
  `total` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`new_loading_date_id`),
  KEY `FK_tbl_rep_new_loading_date_1` (`direction_id`),
  KEY `FK_tbl_rep_new_loading_date_2` (`by_user_id`),
  CONSTRAINT `FK_tbl_rep_new_loading_date_1` FOREIGN KEY (`direction_id`) REFERENCES `direction` (`id`),
  CONSTRAINT `FK_tbl_rep_new_loading_date_2` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_new_loading_date`
--

/*!40000 ALTER TABLE `tbl_rep_new_loading_date` DISABLE KEYS */;
INSERT INTO `tbl_rep_new_loading_date` (`new_loading_date_id`,`direction_id`,`by_user_id`,`date`,`total`,`is_edit`) VALUES 
 (20,4,1,'2015-05-02',354.25,0),
 (21,4,1,'2015-05-09',354.25,0);
/*!40000 ALTER TABLE `tbl_rep_new_loading_date` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_new_loading_value`
--

DROP TABLE IF EXISTS `tbl_rep_new_loading_value`;
CREATE TABLE `tbl_rep_new_loading_value` (
  `new_loading_value_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `new_loading_date_id` bigint(20) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT NULL,
  `max_mount` int(11) DEFAULT NULL,
  `max_mount_price` double DEFAULT NULL,
  `old_max_mount` int(11) DEFAULT NULL,
  `old_max_mount_price` double DEFAULT NULL,
  `min_mount` int(11) DEFAULT NULL,
  `min_mount_price` double DEFAULT NULL,
  `old_min_mount` int(11) DEFAULT NULL,
  `old_min_mount_price` double DEFAULT NULL,
  `showen_mount` varchar(50) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`new_loading_value_id`),
  KEY `FK_tbl_new_loading_value_1` (`new_loading_date_id`),
  KEY `FK_tbl_new_loading_value_2` (`product_id`),
  CONSTRAINT `FK_tbl_new_loading_value_1` FOREIGN KEY (`new_loading_date_id`) REFERENCES `tbl_rep_new_loading_date` (`new_loading_date_id`),
  CONSTRAINT `FK_tbl_new_loading_value_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_new_loading_value`
--

/*!40000 ALTER TABLE `tbl_rep_new_loading_value` DISABLE KEYS */;
INSERT INTO `tbl_rep_new_loading_value` (`new_loading_value_id`,`new_loading_date_id`,`product_id`,`max_mount`,`max_mount_price`,`old_max_mount`,`old_max_mount_price`,`min_mount`,`min_mount_price`,`old_min_mount`,`old_min_mount_price`,`showen_mount`,`total_price`,`is_edit`) VALUES 
 (47,20,6,5,175,NULL,NULL,5,8.75,NULL,NULL,'5.5',183.75,0),
 (48,20,7,5,80,NULL,NULL,5,8,NULL,NULL,'5.5',88,0),
 (49,20,8,5,75,NULL,NULL,5,7.5,NULL,NULL,'5.5',82.5,0),
 (50,21,6,5,175,NULL,NULL,5,8.75,NULL,NULL,'5.5',183.75,0),
 (51,21,7,5,80,NULL,NULL,5,8,NULL,NULL,'5.5',88,0),
 (52,21,8,5,75,NULL,NULL,5,7.5,NULL,NULL,'5.5',82.5,0);
/*!40000 ALTER TABLE `tbl_rep_new_loading_value` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_offer_date`
--

DROP TABLE IF EXISTS `tbl_rep_offer_date`;
CREATE TABLE `tbl_rep_offer_date` (
  `offer_date_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `direction_id` int(10) unsigned DEFAULT NULL,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  `date` date DEFAULT NULL,
  `total` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`offer_date_id`),
  KEY `FK_tbl_rep_offer_date_1` (`direction_id`),
  KEY `FK_tbl_rep_offer_date_2` (`by_user_id`),
  CONSTRAINT `FK_tbl_rep_offer_date_1` FOREIGN KEY (`direction_id`) REFERENCES `direction` (`id`),
  CONSTRAINT `FK_tbl_rep_offer_date_2` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_offer_date`
--

/*!40000 ALTER TABLE `tbl_rep_offer_date` DISABLE KEYS */;
INSERT INTO `tbl_rep_offer_date` (`offer_date_id`,`direction_id`,`by_user_id`,`date`,`total`,`is_edit`) VALUES 
 (9,4,1,'2015-05-02',70.85,0),
 (10,4,1,'2015-05-09',70.85,0);
/*!40000 ALTER TABLE `tbl_rep_offer_date` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_offer_value`
--

DROP TABLE IF EXISTS `tbl_rep_offer_value`;
CREATE TABLE `tbl_rep_offer_value` (
  `offer_value_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `offer_date_id` bigint(20) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT NULL,
  `max_mount` int(10) unsigned DEFAULT NULL,
  `max_mount_price` double DEFAULT NULL,
  `old_max_mount` int(10) unsigned DEFAULT NULL,
  `old_max_mount_price` double DEFAULT NULL,
  `min_mount` int(10) unsigned DEFAULT NULL,
  `min_mount_price` double DEFAULT NULL,
  `old_min_mount` int(10) unsigned DEFAULT NULL,
  `old_min_mount_price` double DEFAULT NULL,
  `showen_mount` varchar(50) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`offer_value_id`),
  KEY `FK_tbl_rep_offer_value_1` (`product_id`),
  KEY `FK_tbl_rep_offer_value_2` (`offer_date_id`),
  CONSTRAINT `FK_tbl_rep_offer_value_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_tbl_rep_offer_value_2` FOREIGN KEY (`offer_date_id`) REFERENCES `tbl_rep_offer_date` (`offer_date_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_offer_value`
--

/*!40000 ALTER TABLE `tbl_rep_offer_value` DISABLE KEYS */;
INSERT INTO `tbl_rep_offer_value` (`offer_value_id`,`offer_date_id`,`product_id`,`max_mount`,`max_mount_price`,`old_max_mount`,`old_max_mount_price`,`min_mount`,`min_mount_price`,`old_min_mount`,`old_min_mount_price`,`showen_mount`,`total_price`,`is_edit`) VALUES 
 (16,9,6,1,35,NULL,NULL,1,1.75,NULL,NULL,'1.1',36.75,0),
 (17,9,7,1,16,NULL,NULL,1,1.6,NULL,NULL,'1.1',17.6,0),
 (18,9,8,1,15,NULL,NULL,1,1.5,NULL,NULL,'1.1',16.5,0),
 (19,10,6,1,35,NULL,NULL,1,1.75,NULL,NULL,'1.1',36.75,0),
 (20,10,7,1,16,NULL,NULL,1,1.6,NULL,NULL,'1.1',17.6,0),
 (21,10,8,1,15,NULL,NULL,1,1.5,NULL,NULL,'1.1',16.5,0);
/*!40000 ALTER TABLE `tbl_rep_offer_value` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_sales_date`
--

DROP TABLE IF EXISTS `tbl_rep_sales_date`;
CREATE TABLE `tbl_rep_sales_date` (
  `sales_date_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `direction_id` int(10) unsigned DEFAULT NULL,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  `date` date DEFAULT NULL,
  `total` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`sales_date_id`),
  KEY `FK_tbl_rep_sales_date_1` (`direction_id`),
  KEY `FK_tbl_rep_sales_date_2` (`by_user_id`),
  CONSTRAINT `FK_tbl_rep_sales_date_1` FOREIGN KEY (`direction_id`) REFERENCES `direction` (`id`),
  CONSTRAINT `FK_tbl_rep_sales_date_2` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_sales_date`
--

/*!40000 ALTER TABLE `tbl_rep_sales_date` DISABLE KEYS */;
INSERT INTO `tbl_rep_sales_date` (`sales_date_id`,`direction_id`,`by_user_id`,`date`,`total`,`is_edit`) VALUES 
 (9,4,1,'2015-05-02',141.7,0),
 (10,4,1,'2015-05-09',141.7,0);
/*!40000 ALTER TABLE `tbl_rep_sales_date` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_sales_value`
--

DROP TABLE IF EXISTS `tbl_rep_sales_value`;
CREATE TABLE `tbl_rep_sales_value` (
  `sales_value_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `sales_date_id` bigint(20) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT NULL,
  `max_mount` int(10) unsigned DEFAULT NULL,
  `max_mount_price` double unsigned DEFAULT NULL,
  `old_max_mount` int(10) unsigned DEFAULT NULL,
  `old_max_mount_price` double unsigned DEFAULT NULL,
  `min_mount` int(10) unsigned DEFAULT NULL,
  `min_mount_price` double DEFAULT NULL,
  `old_min_mount` int(10) unsigned DEFAULT NULL,
  `old_min_mount_price` double unsigned DEFAULT NULL,
  `showen_mount` varchar(50) DEFAULT NULL,
  `total_price` double unsigned DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`sales_value_id`),
  KEY `FK_tbl_rep_sales_value_1` (`product_id`),
  KEY `FK_tbl_rep_sales_value_2` (`sales_date_id`),
  CONSTRAINT `FK_tbl_rep_sales_value_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_tbl_rep_sales_value_2` FOREIGN KEY (`sales_date_id`) REFERENCES `tbl_rep_sales_date` (`sales_date_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_sales_value`
--

/*!40000 ALTER TABLE `tbl_rep_sales_value` DISABLE KEYS */;
INSERT INTO `tbl_rep_sales_value` (`sales_value_id`,`sales_date_id`,`product_id`,`max_mount`,`max_mount_price`,`old_max_mount`,`old_max_mount_price`,`min_mount`,`min_mount_price`,`old_min_mount`,`old_min_mount_price`,`showen_mount`,`total_price`,`is_edit`) VALUES 
 (13,9,6,2,70,NULL,NULL,2,3.5,NULL,NULL,'2.2',73.5,0),
 (14,9,7,2,32,NULL,NULL,2,3.1999999999999993,NULL,NULL,'2.2',35.19999999999999,0),
 (15,9,8,2,30,NULL,NULL,2,3,NULL,NULL,'2.2',33,0),
 (16,10,6,2,70,NULL,NULL,2,3.5,NULL,NULL,'2.2',73.5,0),
 (17,10,7,2,32,NULL,NULL,2,3.1999999999999993,NULL,NULL,'2.2',35.2,0),
 (18,10,8,2,30,NULL,NULL,2,3,NULL,NULL,'2.2',33,0);
/*!40000 ALTER TABLE `tbl_rep_sales_value` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_total_loading_date`
--

DROP TABLE IF EXISTS `tbl_rep_total_loading_date`;
CREATE TABLE `tbl_rep_total_loading_date` (
  `total_loading_date_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `by_user_id` int(10) unsigned DEFAULT NULL,
  `direction_id` int(10) unsigned DEFAULT NULL,
  `date` date DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  `total` double DEFAULT NULL,
  PRIMARY KEY (`total_loading_date_id`) USING BTREE,
  KEY `FK_tbl_rep_total_loading_date_1` (`direction_id`),
  KEY `FK_tbl_rep_total_loading_date_2` (`by_user_id`),
  CONSTRAINT `FK_tbl_rep_total_loading_date_1` FOREIGN KEY (`direction_id`) REFERENCES `direction` (`id`),
  CONSTRAINT `FK_tbl_rep_total_loading_date_2` FOREIGN KEY (`by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_total_loading_date`
--

/*!40000 ALTER TABLE `tbl_rep_total_loading_date` DISABLE KEYS */;
INSERT INTO `tbl_rep_total_loading_date` (`total_loading_date_id`,`by_user_id`,`direction_id`,`date`,`is_edit`,`total`) VALUES 
 (17,1,4,'2015-05-02',0,425.1),
 (18,1,4,'2015-05-09',0,495.95);
/*!40000 ALTER TABLE `tbl_rep_total_loading_date` ENABLE KEYS */;


--
-- Definition of table `tbl_rep_total_loading_value`
--

DROP TABLE IF EXISTS `tbl_rep_total_loading_value`;
CREATE TABLE `tbl_rep_total_loading_value` (
  `total_loading_value_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `total_loading_date_id` bigint(20) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned NOT NULL,
  `max_mount` int(10) unsigned NOT NULL,
  `max_mount_price` double NOT NULL,
  `old_max_mount` int(10) unsigned NOT NULL,
  `old_max_mount_price` double NOT NULL,
  `min_mount` int(10) unsigned DEFAULT NULL,
  `min_mount_price` double DEFAULT NULL,
  `old_min_mount` int(10) unsigned DEFAULT NULL,
  `old_min_mount_price` double DEFAULT NULL,
  `showen_mount` varchar(50) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `is_edit` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`total_loading_value_id`),
  KEY `FK_tbl_rep_total_loading_value_1` (`product_id`),
  KEY `FK_tbl_rep_total_loading_value_2` (`total_loading_date_id`),
  CONSTRAINT `FK_tbl_rep_total_loading_value_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_tbl_rep_total_loading_value_2` FOREIGN KEY (`total_loading_date_id`) REFERENCES `tbl_rep_total_loading_date` (`total_loading_date_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_rep_total_loading_value`
--

/*!40000 ALTER TABLE `tbl_rep_total_loading_value` DISABLE KEYS */;
INSERT INTO `tbl_rep_total_loading_value` (`total_loading_value_id`,`total_loading_date_id`,`product_id`,`max_mount`,`max_mount_price`,`old_max_mount`,`old_max_mount_price`,`min_mount`,`min_mount_price`,`old_min_mount`,`old_min_mount_price`,`showen_mount`,`total_price`,`is_edit`) VALUES 
 (40,17,6,6,210,0,0,6,10.5,NULL,NULL,'6.6',220.5,0),
 (41,17,7,6,96,0,0,6,9.6,NULL,NULL,'6.6',105.6,0),
 (42,17,8,6,90,0,0,6,9,NULL,NULL,'6.6',99,0),
 (43,18,6,7,245,0,0,7,12.25,NULL,NULL,'7.7',257.25,0),
 (44,18,7,7,112,0,0,7,11.2,NULL,NULL,'7.7',123.2,0),
 (45,18,8,7,105,0,0,7,10.5,NULL,NULL,'7.7',115.5,0);
/*!40000 ALTER TABLE `tbl_rep_total_loading_value` ENABLE KEYS */;


--
-- Definition of table `unit`
--

DROP TABLE IF EXISTS `unit`;
CREATE TABLE `unit` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `unit`
--

/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
INSERT INTO `unit` (`id`,`name`) VALUES 
 (1,'كرتونة'),
 (2,'عبوة');
/*!40000 ALTER TABLE `unit` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_type` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_1` (`user_type`),
  CONSTRAINT `FK_user_1` FOREIGN KEY (`user_type`) REFERENCES `user_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`,`user_name`,`password`,`name`,`user_type`) VALUES 
 (1,'admin',0x61646D696E,'أحمد سند',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


--
-- Definition of table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
CREATE TABLE `user_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `privilege` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_type`
--

/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` (`id`,`privilege`) VALUES 
 (1,'مدير'),
 (2,'محاسب');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
