-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: database-1.ckxezqztkeob.ap-south-1.rds.amazonaws.com    Database: pluggtoolmumbain
-- ------------------------------------------------------
-- Server version	5.7.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED='';

--
-- Table structure for table `bing_data`
--

DROP TABLE IF EXISTS `bing_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bing_data` (
  `bing_data_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `url_id` int(11) DEFAULT NULL,
  `page_urls` varchar(3000) DEFAULT NULL,
  `entity_title` varchar(1000) DEFAULT NULL,
  `entity_sub_title` varchar(1000) DEFAULT NULL,
  `innercards_lst` varchar(1000) DEFAULT NULL,
  `vlist_lst` varchar(1000) DEFAULT NULL,
  `official_site` varchar(1000) DEFAULT NULL,
  `wikipedia` varchar(1000) DEFAULT NULL,
  `twitter` varchar(1000) DEFAULT NULL,
  `facebook` varchar(1000) DEFAULT NULL,
  `linkedin` varchar(1000) DEFAULT NULL,
  `revenue` varchar(100) DEFAULT NULL,
  `founded` varchar(500) DEFAULT NULL,
  `headquarters` varchar(500) DEFAULT NULL,
  `ceo` varchar(500) DEFAULT NULL,
  `founders` varchar(500) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL,
  `ipaddress` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`bing_data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bing_page_data`
--

DROP TABLE IF EXISTS `bing_page_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bing_page_data` (
  `bing_page_data_id` int(11) NOT NULL AUTO_INCREMENT,
  `text_data` varchar(1000) DEFAULT NULL,
  `link` varchar(1000) DEFAULT NULL,
  `bing_data_id` int(11) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`bing_page_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `cat_path` varchar(255) DEFAULT '',
  `cat_desc` varchar(500) DEFAULT '',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company_affiliate`
--

DROP TABLE IF EXISTS `company_affiliate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_affiliate` (
  `company_affiliate_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `company_name` varchar(500) DEFAULT NULL,
  `company_link` varchar(1000) DEFAULT NULL,
  `company_description` varchar(3000) DEFAULT NULL,
  `li_co_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`company_affiliate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22993 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company_detail`
--

DROP TABLE IF EXISTS `company_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_detail` (
  `company_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(500) DEFAULT NULL,
  `company_location` varchar(500) DEFAULT NULL,
  `employee_count` varchar(500) DEFAULT NULL,
  `company_url` varchar(500) DEFAULT NULL,
  `company_headquater` varchar(500) DEFAULT NULL,
  `year_founded` varchar(500) DEFAULT NULL,
  `company_size` varchar(500) DEFAULT NULL,
  `company_speciality` varchar(5000) DEFAULT NULL,
  `company_confirmed_location` varchar(500) DEFAULT NULL,
  `affiliate_company` varchar(500) DEFAULT NULL,
  `showcase_page` varchar(500) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `url_id` varchar(500) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `ipaddress` varchar(255) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `li_co_id` int(11) DEFAULT '0',
  `company_type` varchar(500) DEFAULT NULL,
  `company_stock_name` varchar(100) DEFAULT NULL,
  `company_industry` varchar(200) DEFAULT NULL,
  `phone_number` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`company_id`),
  KEY `IX_user_id_company_detail` (`user_id`),
  KEY `IX_url_id_company_detail` (`url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=580579 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company_detail_log`
--

DROP TABLE IF EXISTS `company_detail_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_detail_log` (
  `company_id` int(11) NOT NULL DEFAULT '0',
  `company_name` varchar(500) DEFAULT NULL,
  `company_location` varchar(500) DEFAULT NULL,
  `employee_count` varchar(500) DEFAULT NULL,
  `company_url` varchar(500) DEFAULT NULL,
  `company_headquater` varchar(500) DEFAULT NULL,
  `year_founded` varchar(500) DEFAULT NULL,
  `company_size` varchar(500) DEFAULT NULL,
  `company_speciality` varchar(5000) DEFAULT NULL,
  `company_confirmed_location` varchar(500) DEFAULT NULL,
  `affiliate_company` varchar(500) DEFAULT NULL,
  `showcase_page` varchar(500) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `url_id` varchar(500) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `ipaddress` varchar(255) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `li_co_id` int(11) DEFAULT '0',
  `company_type` varchar(500) DEFAULT NULL,
  `company_stock_name` varchar(100) DEFAULT NULL,
  `company_industry` varchar(200) DEFAULT NULL,
  `phone_number` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company_detail_log1`
--

DROP TABLE IF EXISTS `company_detail_log1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_detail_log1` (
  `company_id` int(11) NOT NULL DEFAULT '0',
  `company_name` varchar(500) DEFAULT NULL,
  `company_location` varchar(500) DEFAULT NULL,
  `employee_count` varchar(500) DEFAULT NULL,
  `company_url` varchar(500) DEFAULT NULL,
  `company_headquater` varchar(500) DEFAULT NULL,
  `year_founded` varchar(500) DEFAULT NULL,
  `company_size` varchar(500) DEFAULT NULL,
  `company_speciality` varchar(2000) DEFAULT NULL,
  `company_confirmed_location` varchar(500) DEFAULT NULL,
  `affiliate_company` varchar(500) DEFAULT NULL,
  `showcase_page` varchar(500) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `url_id` varchar(500) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `ipaddress` varchar(255) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `li_co_id` int(11) DEFAULT '0',
  `company_type` varchar(500) DEFAULT NULL,
  `company_stock_name` varchar(100) DEFAULT NULL,
  `company_industry` varchar(200) DEFAULT NULL,
  `phone_number` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company_detail_log2`
--

DROP TABLE IF EXISTS `company_detail_log2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_detail_log2` (
  `company_id` int(11) NOT NULL DEFAULT '0',
  `company_name` varchar(500) DEFAULT NULL,
  `company_location` varchar(500) DEFAULT NULL,
  `employee_count` varchar(500) DEFAULT NULL,
  `company_url` varchar(500) DEFAULT NULL,
  `company_headquater` varchar(500) DEFAULT NULL,
  `year_founded` varchar(500) DEFAULT NULL,
  `company_size` varchar(500) DEFAULT NULL,
  `company_speciality` varchar(2000) DEFAULT NULL,
  `company_confirmed_location` varchar(500) DEFAULT NULL,
  `affiliate_company` varchar(500) DEFAULT NULL,
  `showcase_page` varchar(500) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `url_id` varchar(500) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `ipaddress` varchar(255) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `li_co_id` int(11) DEFAULT '0',
  `company_type` varchar(500) DEFAULT NULL,
  `company_stock_name` varchar(100) DEFAULT NULL,
  `company_industry` varchar(200) DEFAULT NULL,
  `phone_number` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company_detail_log_jul`
--

DROP TABLE IF EXISTS `company_detail_log_jul`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_detail_log_jul` (
  `company_id` int(11) NOT NULL DEFAULT '0',
  `company_name` varchar(500) DEFAULT NULL,
  `company_location` varchar(500) DEFAULT NULL,
  `employee_count` varchar(500) DEFAULT NULL,
  `company_url` varchar(500) DEFAULT NULL,
  `company_headquater` varchar(500) DEFAULT NULL,
  `year_founded` varchar(500) DEFAULT NULL,
  `company_size` varchar(500) DEFAULT NULL,
  `company_speciality` varchar(2000) DEFAULT NULL,
  `company_confirmed_location` varchar(500) DEFAULT NULL,
  `affiliate_company` varchar(500) DEFAULT NULL,
  `showcase_page` varchar(500) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `url_id` varchar(500) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `ipaddress` varchar(255) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `li_co_id` int(11) DEFAULT '0',
  `company_type` varchar(500) DEFAULT NULL,
  `company_stock_name` varchar(100) DEFAULT NULL,
  `company_industry` varchar(200) DEFAULT NULL,
  `phone_number` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company_location`
--

DROP TABLE IF EXISTS `company_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_location` (
  `company_location_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `country` varchar(500) DEFAULT '',
  `geographic_area` varchar(255) DEFAULT '',
  `city` varchar(255) DEFAULT NULL,
  `postal_code` varchar(50) DEFAULT NULL,
  `description` varchar(2000) DEFAULT '',
  `headquarter` varchar(500) DEFAULT NULL,
  `line1` varchar(2000) DEFAULT NULL,
  `line2` varchar(2000) DEFAULT NULL,
  `li_co_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`company_location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=749700 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event_tab`
--

DROP TABLE IF EXISTS `event_tab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_tab` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT '',
  `created_date` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT '',
  `published_date` datetime DEFAULT NULL,
  `event_desc` longblob,
  `event_img` varchar(255) DEFAULT '',
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `list_building_details`
--

DROP TABLE IF EXISTS `list_building_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `list_building_details` (
  `list_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `new_link` varchar(1000) DEFAULT NULL,
  `company_link` varchar(1000) DEFAULT NULL,
  `company_name` varchar(1000) DEFAULT NULL,
  `company_tenure` varchar(1000) DEFAULT NULL,
  `contact_location` varchar(1000) DEFAULT NULL,
  `contact_designation` varchar(255) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `url_id` int(11) NOT NULL DEFAULT '0',
  `ipaddress` varchar(100) NOT NULL DEFAULT '',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total_result_no` varchar(500) DEFAULT NULL,
  `total_changed_job_no` varchar(500) DEFAULT NULL,
  `page_number` int(11) DEFAULT NULL,
  `record_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`list_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39004 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `list_contacts`
--

DROP TABLE IF EXISTS `list_contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `list_contacts` (
  `list_contacts_id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) DEFAULT NULL,
  `head_title` varchar(255) DEFAULT NULL,
  `head_location` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `university` varchar(255) DEFAULT NULL,
  `university_url` varchar(1000) DEFAULT NULL,
  `current_title` varchar(255) DEFAULT NULL,
  `current_company` varchar(255) DEFAULT NULL,
  `current_company_link` varchar(255) DEFAULT NULL,
  `current_duration_start` varchar(255) DEFAULT NULL,
  `current_duration_end` varchar(255) DEFAULT NULL,
  `current_location` varchar(255) DEFAULT NULL,
  `past_title` varchar(255) DEFAULT NULL,
  `past_company` varchar(255) DEFAULT NULL,
  `past_company_link` varchar(500) DEFAULT NULL,
  `past_duration_start` varchar(500) DEFAULT NULL,
  `past_duration_end` varchar(500) DEFAULT NULL,
  `past_location` varchar(500) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `url_id` int(11) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ipaddress` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`list_contacts_id`)
) ENGINE=InnoDB AUTO_INCREMENT=678 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `maps_data`
--

DROP TABLE IF EXISTS `maps_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `maps_data` (
  `maps_data_id` int(11) NOT NULL AUTO_INCREMENT,
  `place_data` blob,
  `tile_data` blob,
  `raw_text` longblob,
  `root_url` varchar(1000) DEFAULT NULL,
  `url_id` int(11) DEFAULT '0',
  `user_id` int(11) DEFAULT '0',
  `ipaddress` varchar(100) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`maps_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=79068 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `maps_place_data`
--

DROP TABLE IF EXISTS `maps_place_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `maps_place_data` (
  `maps_place_data_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) DEFAULT NULL,
  `query` varchar(5000) DEFAULT NULL,
  `categories` varchar(2000) DEFAULT NULL,
  `domain` varchar(1000) DEFAULT NULL,
  `reviews` varchar(1000) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `maps_data_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`maps_place_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `maps_tile_data`
--

DROP TABLE IF EXISTS `maps_tile_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `maps_tile_data` (
  `maps_tile_data_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) DEFAULT NULL,
  `rating` varchar(200) DEFAULT NULL,
  `location` varchar(500) DEFAULT NULL,
  `detail` varchar(500) DEFAULT NULL,
  `opening_time` varchar(200) DEFAULT NULL,
  `maps_data_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`maps_tile_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35555 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_bing_url`
--

DROP TABLE IF EXISTS `master_bing_url`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_bing_url` (
  `master_bing_url_id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(1000) DEFAULT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'Active',
  `user_id` int(11) NOT NULL DEFAULT '0',
  `clear_address` varchar(1000) DEFAULT NULL,
  `exact_match1` varchar(1000) DEFAULT NULL,
  `exact_match2` varchar(1000) DEFAULT NULL,
  `fuzzy` varchar(500) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `state` varchar(500) DEFAULT NULL,
  `zip` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`master_bing_url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12646 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_company_url`
--

DROP TABLE IF EXISTS `master_company_url`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_company_url` (
  `company_url_id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(1000) DEFAULT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'Active',
  `user_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`company_url_id`),
  KEY `IX_user_id_master_company_url` (`user_id`),
  KEY `IX_status_master_company_url` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=677242 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_google_url`
--

DROP TABLE IF EXISTS `master_google_url`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_google_url` (
  `master_google_url_id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(500) DEFAULT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `status` varchar(10) NOT NULL DEFAULT 'Active',
  PRIMARY KEY (`master_google_url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=129183 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_list_building_url`
--

DROP TABLE IF EXISTS `master_list_building_url`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_list_building_url` (
  `master_list_url_id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(2000) DEFAULT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `status` varchar(10) NOT NULL DEFAULT 'Active',
  PRIMARY KEY (`master_list_url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1046 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_maps_url`
--

DROP TABLE IF EXISTS `master_maps_url`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_maps_url` (
  `master_maps_url_id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(2000) DEFAULT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `status` varchar(10) NOT NULL DEFAULT 'Active',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`master_maps_url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=79774 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_profile_email_data`
--

DROP TABLE IF EXISTS `master_profile_email_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_profile_email_data` (
  `master_profile_email_data_id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(1000) DEFAULT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `status` varchar(10) NOT NULL DEFAULT 'Active',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`master_profile_email_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_url`
--

DROP TABLE IF EXISTS `master_url`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_url` (
  `master_url_id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(2000) DEFAULT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `status` varchar(10) NOT NULL DEFAULT 'Active',
  PRIMARY KEY (`master_url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11798 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_url_1`
--

DROP TABLE IF EXISTS `master_url_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_url_1` (
  `master_url_id` int(11) NOT NULL DEFAULT '0',
  `url` varchar(2000) DEFAULT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `status` varchar(10) NOT NULL DEFAULT 'Active'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_url_log`
--

DROP TABLE IF EXISTS `master_url_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_url_log` (
  `master_url_id` int(11) NOT NULL DEFAULT '0',
  `url` varchar(1000) DEFAULT NULL,
  `user_id` varchar(11) DEFAULT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'Active'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_url_log1`
--

DROP TABLE IF EXISTS `master_url_log1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_url_log1` (
  `master_url_id` int(11) NOT NULL DEFAULT '0',
  `url` varchar(1000) DEFAULT NULL,
  `user_id` varchar(11) NOT NULL DEFAULT '0',
  `status` varchar(10) NOT NULL DEFAULT 'Active'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_url_log3`
--

DROP TABLE IF EXISTS `master_url_log3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_url_log3` (
  `master_url_id` int(11) NOT NULL DEFAULT '0',
  `url` varchar(1000) DEFAULT NULL,
  `user_id` varchar(11) NOT NULL DEFAULT '0',
  `status` varchar(10) NOT NULL DEFAULT 'Active'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_url_profile`
--

DROP TABLE IF EXISTS `master_url_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_url_profile` (
  `master_url_id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(1000) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'Active',
  PRIMARY KEY (`master_url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=823 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_yelp_url`
--

DROP TABLE IF EXISTS `master_yelp_url`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_yelp_url` (
  `master_yelp_url_id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(500) DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT '0',
  `status` varchar(10) NOT NULL DEFAULT 'Active',
  PRIMARY KEY (`master_yelp_url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47846 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_zillow_url`
--

DROP TABLE IF EXISTS `master_zillow_url`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_zillow_url` (
  `master_zillow_url_id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(1000) DEFAULT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'Active',
  `user_id` int(11) DEFAULT '0',
  PRIMARY KEY (`master_zillow_url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=113227 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profile_email_data`
--

DROP TABLE IF EXISTS `profile_email_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile_email_data` (
  `profile_email_data_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `designation` varchar(500) DEFAULT NULL,
  `company_name` varchar(1000) DEFAULT NULL,
  `company_url` varchar(1000) DEFAULT NULL,
  `location_name` varchar(1000) DEFAULT NULL,
  `links` varchar(1000) DEFAULT NULL,
  `root_url` varchar(1000) DEFAULT NULL,
  `url_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `ipaddress` varchar(50) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`profile_email_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property_data`
--

DROP TABLE IF EXISTS `property_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `property_data` (
  `property_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `rating` varchar(500) DEFAULT NULL,
  `industry` varchar(2000) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `phone_number` varchar(100) DEFAULT NULL,
  `timing` varchar(100) DEFAULT NULL,
  `direction` varchar(1000) DEFAULT NULL,
  `website` varchar(1000) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL,
  `ipaddress` varchar(50) DEFAULT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `record_start_number` varchar(500) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `url_id` int(11) NOT NULL DEFAULT '0',
  `current_page_no` varchar(50) DEFAULT NULL,
  `last_page_no` varchar(50) DEFAULT NULL,
  `is_success` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`property_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9696032 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scrap`
--

DROP TABLE IF EXISTS `scrap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scrap` (
  `scrap_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT '',
  `current_org` varchar(255) DEFAULT '',
  `current_position` varchar(255) DEFAULT '',
  `location` varchar(255) DEFAULT '',
  `url` varchar(255) DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT '1',
  `ipaddress` varchar(255) DEFAULT '',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `contact_url` varchar(1000) DEFAULT NULL,
  `url_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`scrap_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11116 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scrap1`
--

DROP TABLE IF EXISTS `scrap1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scrap1` (
  `scrap_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT '',
  `current_org` varchar(255) DEFAULT '',
  `current_position` varchar(255) DEFAULT '',
  `location` varchar(255) DEFAULT '',
  `url` varchar(255) DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT '1',
  `ipaddress` varchar(255) DEFAULT '',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `contact_url` varchar(1000) DEFAULT NULL,
  `url_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scrap1_jul`
--

DROP TABLE IF EXISTS `scrap1_jul`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scrap1_jul` (
  `scrap_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT '',
  `current_org` varchar(255) DEFAULT '',
  `current_position` varchar(255) DEFAULT '',
  `location` varchar(255) DEFAULT '',
  `url` varchar(255) DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT '1',
  `ipaddress` varchar(255) DEFAULT '',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `contact_url` varchar(1000) DEFAULT NULL,
  `url_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scrap1_old`
--

DROP TABLE IF EXISTS `scrap1_old`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scrap1_old` (
  `scrap_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT '',
  `current_org` varchar(255) DEFAULT '',
  `current_position` varchar(255) DEFAULT '',
  `location` varchar(255) DEFAULT '',
  `url` varchar(255) DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT '1',
  `ipaddress` varchar(255) DEFAULT '',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `contact_url` varchar(1000) DEFAULT NULL,
  `url_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scrap2`
--

DROP TABLE IF EXISTS `scrap2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scrap2` (
  `list_contacts_id` int(11) NOT NULL DEFAULT '0',
  `full_name` varchar(255) DEFAULT NULL,
  `head_title` varchar(255) DEFAULT NULL,
  `head_location` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `university` varchar(255) DEFAULT NULL,
  `university_url` varchar(1000) DEFAULT NULL,
  `current_title` varchar(255) DEFAULT NULL,
  `current_company` varchar(255) DEFAULT NULL,
  `current_company_link` varchar(255) DEFAULT NULL,
  `current_duration_start` varchar(255) DEFAULT NULL,
  `current_duration_end` varchar(255) DEFAULT NULL,
  `current_location` varchar(255) DEFAULT NULL,
  `past_title` varchar(255) DEFAULT NULL,
  `past_company` varchar(255) DEFAULT NULL,
  `past_company_link` varchar(500) DEFAULT NULL,
  `past_duration_start` varchar(500) DEFAULT NULL,
  `past_duration_end` varchar(500) DEFAULT NULL,
  `past_location` varchar(500) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `url_id` int(11) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ipaddress` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scrap2_old`
--

DROP TABLE IF EXISTS `scrap2_old`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scrap2_old` (
  `scrap_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT '',
  `current_org` varchar(255) DEFAULT '',
  `current_position` varchar(255) DEFAULT '',
  `location` varchar(255) DEFAULT '',
  `url` varchar(255) DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT '1',
  `ipaddress` varchar(255) DEFAULT '',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `contact_url` varchar(1000) DEFAULT NULL,
  `url_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scrap3_old`
--

DROP TABLE IF EXISTS `scrap3_old`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scrap3_old` (
  `scrap_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT '',
  `current_org` varchar(255) DEFAULT '',
  `current_position` varchar(255) DEFAULT '',
  `location` varchar(255) DEFAULT '',
  `url` varchar(255) DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT '1',
  `ipaddress` varchar(255) DEFAULT '',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `contact_url` varchar(1000) DEFAULT NULL,
  `url_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scrap4_old`
--

DROP TABLE IF EXISTS `scrap4_old`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scrap4_old` (
  `scrap_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT '',
  `current_org` varchar(255) DEFAULT '',
  `current_position` varchar(255) DEFAULT '',
  `location` varchar(255) DEFAULT '',
  `url` varchar(255) DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT '1',
  `ipaddress` varchar(255) DEFAULT '',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `contact_url` varchar(1000) DEFAULT NULL,
  `url_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(255) DEFAULT NULL,
  `lname` varchar(255) DEFAULT NULL,
  `useremail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_role` int(11) DEFAULT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'Active',
  `mobile_number` varchar(15) DEFAULT NULL,
  `approved_link` varchar(10) NOT NULL DEFAULT '0',
  `approved_link_scrap2` varchar(11) NOT NULL DEFAULT '0',
  `approved_link_scrap3` varchar(11) NOT NULL DEFAULT '0',
  `company_link` varchar(11) NOT NULL DEFAULT '0',
  `company_link_temp` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_january`
--

DROP TABLE IF EXISTS `user_january`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_january` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `fname` varchar(255) DEFAULT NULL,
  `lname` varchar(255) DEFAULT NULL,
  `useremail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_role` int(11) DEFAULT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'Active',
  `mobile_number` varchar(15) DEFAULT NULL,
  `approved_link` varchar(10) NOT NULL DEFAULT '0',
  `approved_link_scrap2` varchar(11) NOT NULL DEFAULT '0',
  `approved_link_scrap3` varchar(11) NOT NULL DEFAULT '0',
  `company_link` varchar(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `yelp_data`
--

DROP TABLE IF EXISTS `yelp_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yelp_data` (
  `yelp_data_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(1000) DEFAULT NULL,
  `review` varchar(1000) DEFAULT NULL,
  `star_rating` varchar(1000) DEFAULT NULL,
  `category` varchar(1000) DEFAULT NULL,
  `address` varchar(5000) DEFAULT NULL,
  `direction` varchar(5000) DEFAULT NULL,
  `phone` varchar(500) DEFAULT NULL,
  `website` varchar(500) DEFAULT NULL,
  `owner` varchar(1000) DEFAULT NULL,
  `root_url` varchar(1000) DEFAULT '',
  `url_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `ipaddress` varchar(50) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`yelp_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45341 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zillow_data`
--

DROP TABLE IF EXISTS `zillow_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zillow_data` (
  `zillow_id` int(11) NOT NULL AUTO_INCREMENT,
  `data_lst` blob,
  `sub_section_container0` blob,
  `sub_section_container1` blob,
  `sub_section_container2` blob,
  `sub_section_container3` mediumblob,
  `sub_section_container4` blob,
  `sub_section_container5` blob,
  `sub_section_container6` blob,
  `sub_section_container7` blob,
  `sub_section_container8` blob,
  `sub_section_container9` blob,
  `sub_section_container10` blob,
  `sub_section_container11` blob,
  `sub_section_container12` blob,
  `sub_section_container13` blob,
  `sub_section_container14` blob,
  `sub_section_container15` blob,
  `sub_section_container16` blob,
  `sub_section_container17` blob,
  `sub_section_container18` blob,
  `sub_section_container19` blob,
  `sub_section_container20` blob,
  `sub_section_container21` blob,
  `sub_section_container22` blob,
  `address_div` blob,
  `popup_div` blob,
  `agent_listing` blob,
  `fact_list` blob,
  `image0` blob,
  `image1` blob,
  `image2` blob,
  `image3` blob,
  `image4` blob,
  `home_values` blob,
  `monthly_cost` blob,
  `rental_home_values` blob,
  `nearby_school` blob,
  `premier_leader` blob,
  `url` varchar(1000) DEFAULT NULL,
  `url_id` int(11) NOT NULL DEFAULT '0',
  `user_id` int(11) NOT NULL DEFAULT '0',
  `todo` varchar(2000) DEFAULT NULL,
  `ipaddress` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`zillow_id`)
) ENGINE=InnoDB AUTO_INCREMENT=113217 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';
/*!40101 SET character_set_client = @saved_cs_client */;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-24 11:15:54
