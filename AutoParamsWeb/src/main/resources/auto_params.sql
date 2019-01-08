-- MySQL dump 10.13  Distrib 8.0.13, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: auto_params
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `algorithm`
--

DROP TABLE IF EXISTS `algorithm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `algorithm` (
  `algo_id` int(11) NOT NULL AUTO_INCREMENT,
  `algo_name` varchar(50) DEFAULT NULL,
  `algo_lib` varchar(20) DEFAULT NULL,
  `algo_type` varchar(20) DEFAULT NULL,
  `default_params` text,
  PRIMARY KEY (`algo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `algorithm`
--

INSERT INTO `algorithm` VALUES (1,'DecisionTreeClassifier','sklearn','Classification','[{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"criterion\",\"stringValues\":[\"gini\",\"entropy\"]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"splitter\",\"stringValues\":[\"best\",\"random\"]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":10,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":18,\"intUpValue\":100,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_depth\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0.5,\"floatMax\":1,\"floatMaxInclusive\":true,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":20,\"floatUpValue\":1,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_features\",\"stringValues\":[\"auto\",\"sqrt\"]}]');
INSERT INTO `algorithm` VALUES (2,'RandomForestClassifier','sklearn','Classification','[{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":1,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":100,\"intUpValue\":100,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"n_estimators\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0.5,\"floatMax\":1,\"floatMaxInclusive\":true,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":5,\"floatUpValue\":1,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_features\",\"stringValues\":[\"auto\",\"sqrt\"]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":1,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":100,\"intUpValue\":100,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_depth\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":2,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":9,\"intUpValue\":10,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"min_samples_split\",\"stringValues\":[]}]');
INSERT INTO `algorithm` VALUES (3,'LogisticRegressionCV','sklearn','Classification','[{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":5,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":-2147483648,\"intMinInclusive\":false,\"intNum\":6,\"intUpValue\":10,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"Cs\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":5,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":6,\"intUpValue\":10,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"cv\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"penalty\",\"stringValues\":[\"l1\",\"l2\"]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"solver\",\"stringValues\":[\"newton-cg\",\"lbfgs\",\"liblinear\",\"sag\",\"saga\"]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":100,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":10,\"intUpValue\":200,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_iter\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"multi_class\",\"stringValues\":[\"multinomial\"]}]');
INSERT INTO `algorithm` VALUES (4,'XGBClassifier','XGBoost','Classification','[{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":50,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":10,\"intUpValue\":200,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"n_estimators\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"booster\",\"stringValues\":[\"gbtree\",\"gblinear\",\"dart\"]},{\"boolValues\":[],\"floatDownValue\":0.01,\"floatMax\":3.4028235E38,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":5,\"floatUpValue\":0.2,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"learning_rate\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":10,\"intUpValue\":20,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"min_split_loss\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":1,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":5,\"intUpValue\":25,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_depth\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0.5,\"floatMax\":1.0000001,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":10,\"floatUpValue\":1,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"subsample\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":-2147483648,\"intMaxInclusive\":false,\"intMin\":-2147483648,\"intMinInclusive\":false,\"intNum\":5,\"intUpValue\":10,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"reg_lambda\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"sample_type\",\"stringValues\":[\"uniform\",\"weighted\"]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"normalize_type\",\"stringValues\":[\"tree\",\"forest\"]}]');
INSERT INTO `algorithm` VALUES (5,'MLPClassifier','sklearn','Classification','[{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":3,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":100,\"intUpValue\":103,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"n_layers\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":2,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":99,\"intUpValue\":100,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"hidden_layer_size\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"activation\",\"stringValues\":[\"identity\",\"logistic\",\"tanh\",\"relu\"]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"solver\",\"stringValues\":[\"lbfgs\",\"sgd\",\"adam\"]},{\"boolValues\":[],\"floatDownValue\":1.0E-4,\"floatMax\":3.4028235E38,\"floatMaxInclusive\":false,\"floatMin\":1.4E-45,\"floatMinInclusive\":false,\"floatNum\":100,\"floatUpValue\":0.1,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"alpha\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":1.0E-4,\"floatMax\":3.4028235E38,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":100,\"floatUpValue\":0.1,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"learning_rate\",\"stringValues\":[]}]');
INSERT INTO `algorithm` VALUES (6,'LogisticRegression','sklearn','Classification','[{\"boolValues\":[],\"floatDownValue\":1,\"floatMax\":3.4028235E38,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":100,\"floatUpValue\":10,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"C\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"penalty\",\"stringValues\":[\"l1\",\"l2\"]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"solver\",\"stringValues\":[\"newton-cg\",\"lbfgs\",\"liblinear\",\"sag\",\"saga\"]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":100,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":100,\"intUpValue\":2000,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_iter\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"multi_class\",\"stringValues\":[\"multinomial\"]}]\n');
INSERT INTO `algorithm` VALUES (7,'LinearRegression','sklearn','Regression','[{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"fit_intercept\",\"stringValues\":[]},{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"normalize\",\"stringValues\":[]}]');
INSERT INTO `algorithm` VALUES (8,'Ridge','sklearn','Regression','[{\"boolValues\":[],\"floatDownValue\":1.0E-10,\"floatMax\":3.4028235E38,\"floatMaxInclusive\":false,\"floatMin\":1.4E-45,\"floatMinInclusive\":false,\"floatNum\":100,\"floatUpValue\":0.01,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"alpha\",\"stringValues\":[]},{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"fit_intercept\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":100,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":10,\"intUpValue\":5000,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_iter\",\"stringValues\":[]},{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"normalize\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"solver\",\"stringValues\":[\"auto\",\"svd\",\"cholesky\",\"lsqr\",\"sparse_cg\",\"sag\"]}]');
INSERT INTO `algorithm` VALUES (9,'RidgeCV','sklearn','Regression','[{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"fit_intercept\",\"stringValues\":[]},{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"normalize\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"scoring\",\"stringValues\":[\"mean_squared_error\",\"r2\",\"median_absolute_error\"]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":5,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":6,\"intUpValue\":10,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":true,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"cv\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"gcv_mode\",\"stringValues\":[\"auto\",\"svd\",\"eigen\"]},{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"store_cv_values\",\"stringValues\":[]}]');
INSERT INTO `algorithm` VALUES (10,'Lasso','sklearn','Regression','[{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":1,\"floatMaxInclusive\":false,\"floatMin\":1.4E-45,\"floatMinInclusive\":false,\"floatNum\":100,\"floatUpValue\":1,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"alpha\",\"stringValues\":[]},{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"fit_intercept\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":100,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":10,\"intUpValue\":5000,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_iter\",\"stringValues\":[]},{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"normalize\",\"stringValues\":[]},{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"warm_start\",\"stringValues\":[]},{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"positive\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"selection\",\"stringValues\":[\"cyclic\",\"random\"]}]');
INSERT INTO `algorithm` VALUES (11,'LassoCV','sklearn','Regression','[{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":100,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":10,\"intUpValue\":5000,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_iter\",\"stringValues\":[]},{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"fit_intercept\",\"stringValues\":[]},{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"normalize\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":5,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":6,\"intUpValue\":10,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":true,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"cv\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"selection\",\"stringValues\":[\"cyclic\",\"random\"]}]');
INSERT INTO `algorithm` VALUES (12,'LassoLarsCV','sklearn','Regression','[{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":100,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":10,\"intUpValue\":5000,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_iter\",\"stringValues\":[]},{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"fit_intercept\",\"stringValues\":[]},{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"normalize\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":5,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":6,\"intUpValue\":10,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":true,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"cv\",\"stringValues\":[]},{\"boolValues\":[true,false],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":true,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"positive\",\"stringValues\":[]}]');
INSERT INTO `algorithm` VALUES (13,'XGBRegressor','XGBoost','Regression','[{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":50,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":10,\"intUpValue\":200,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"n_estimators\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"booster\",\"stringValues\":[\"gbtree\",\"gblinear\",\"dart\"]},{\"boolValues\":[],\"floatDownValue\":0.01,\"floatMax\":3.4028235E38,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":5,\"floatUpValue\":0.2,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"learning_rate\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":10,\"intUpValue\":20,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"min_split_loss\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":1,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":5,\"intUpValue\":25,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_depth\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0.5,\"floatMax\":3.4028235E38,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":10,\"floatUpValue\":1,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"subsample\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":3.4028235E38,\"floatMaxInclusive\":false,\"floatMin\":1.4E-45,\"floatMinInclusive\":false,\"floatNum\":5,\"floatUpValue\":10,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"reg_lambda\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"sample_type\",\"stringValues\":[\"uniform\",\"weighted\"]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"normalize_type\",\"stringValues\":[\"tree\",\"forest\"]}]');
INSERT INTO `algorithm` VALUES (14,'RandomForestRegressor','sklearn','Regression','[{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":1,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":100,\"intUpValue\":100,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"n_estimators\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0.5,\"floatMax\":1,\"floatMaxInclusive\":true,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":5,\"floatUpValue\":1,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_features\",\"stringValues\":[\"auto\",\"sqrt\",\"log2\"]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":1,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":100,\"intUpValue\":100,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_depth\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":2,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":9,\"intUpValue\":10,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"min_samples_split\",\"stringValues\":[]}]');
INSERT INTO `algorithm` VALUES (15,'GradientBoostingRegressor','sklearn','Regression','[{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":51,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":100,\"intUpValue\":150,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"n_estimators\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0.1,\"floatMax\":3.4028235E38,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":10,\"floatUpValue\":1,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"learning_rate\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"loss\",\"stringValues\":[\"ls\",\"lad\",\"huber\",\"quantile\"]},{\"boolValues\":[],\"floatDownValue\":0.6,\"floatMax\":3.4028235E38,\"floatMaxInclusive\":false,\"floatMin\":1.4E-45,\"floatMinInclusive\":false,\"floatNum\":4,\"floatUpValue\":0.9,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"alpha\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":21,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":30,\"intUpValue\":50,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_depth\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":2,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":4,\"intUpValue\":5,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"min_samples_split\",\"stringValues\":[]}]');
INSERT INTO `algorithm` VALUES (16,'SVR','sklearn','Regression','[{\"boolValues\":[],\"floatDownValue\":0.001,\"floatMax\":3.4028235E38,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":100,\"floatUpValue\":0.01,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"gamma\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0.5,\"floatMax\":3.4028235E38,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":10,\"floatUpValue\":1,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"C\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":3.4028235E38,\"floatMaxInclusive\":false,\"floatMin\":1.4E-45,\"floatMinInclusive\":false,\"floatNum\":10,\"floatUpValue\":5,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"coef0\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"kernel\",\"stringValues\":[\"rbf\",\"sigmoid\"]}]');
INSERT INTO `algorithm` VALUES (17,'LinearSVR','sklearn','Regression','[{\"boolValues\":[],\"floatDownValue\":0.5,\"floatMax\":3.4028235E38,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":100,\"floatUpValue\":1,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":true,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"C\",\"stringValues\":[]}]');
INSERT INTO `algorithm` VALUES (18,'MLPRegressor','sklearn','Regression','[{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":3,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":100,\"intUpValue\":103,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"n_layers\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":2,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":10,\"intUpValue\":11,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"hidden_layer_size\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"activation\",\"stringValues\":[\"identity\",\"logistic\",\"tanh\",\"relu\"]}]');
INSERT INTO `algorithm` VALUES (19,'KMeans','sklearn','Regression','[{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":2,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":14,\"intUpValue\":15,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"n_clusters\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":1,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":10,\"intUpValue\":10,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"n_init\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"init\",\"stringValues\":[\"k-means++\",\"random\"]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":200,\"intMax\":2147483647,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":20,\"intUpValue\":400,\"isBool\":false,\"isFloat\":false,\"isInt\":true,\"isList\":false,\"isNull\":false,\"isString\":false,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"max_iter\",\"stringValues\":[]},{\"boolValues\":[],\"floatDownValue\":0,\"floatMax\":0,\"floatMaxInclusive\":false,\"floatMin\":0,\"floatMinInclusive\":false,\"floatNum\":0,\"floatUpValue\":0,\"intDownValue\":0,\"intMax\":0,\"intMaxInclusive\":false,\"intMin\":0,\"intMinInclusive\":false,\"intNum\":0,\"intUpValue\":0,\"isBool\":false,\"isFloat\":false,\"isInt\":false,\"isList\":false,\"isNull\":false,\"isString\":true,\"listValues\":[],\"paramDesc\":\"\",\"paramName\":\"algorithm\",\"stringValues\":[\"auto\",\"full\",\"elkan\"]}]');

--
-- Table structure for table `evaluate`
--

DROP TABLE IF EXISTS `evaluate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `evaluate` (
  `lib` varchar(20) DEFAULT NULL,
  `algo_type` varchar(20) DEFAULT NULL,
  `eva_type` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluate`
--

INSERT INTO `evaluate` VALUES ('sklearn','classification','accuracy_score:precision_score:recall_score:f1_score:roc_auc_score');
INSERT INTO `evaluate` VALUES ('sklearn','clustering','adjusted_rand_score:adjusted_mutual_info_score:homogeneity_completeness_v_measure:fowlkes_mallows_score:calinski_harabaz_score:silhouette_score');
INSERT INTO `evaluate` VALUES ('sklearn','regression','mean_squared_error:mean_absolute_error:explained_variance_score:median_absolute_error:r2_score');

--
-- Table structure for table `opt_algorithm`
--

DROP TABLE IF EXISTS `opt_algorithm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `opt_algorithm` (
  `opt_algo_id` int(11) NOT NULL AUTO_INCREMENT,
  `opt_algo_name` varchar(50) DEFAULT NULL,
  `default_params` text,
  PRIMARY KEY (`opt_algo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opt_algorithm`
--
INSERT INTO auto_params.opt_algorithm (opt_algo_id, opt_algo_name, default_params) VALUES (1, 'Bayesian_Optimization', '[{"name":"bo_loop", "val":50, "placeholder":"迭代次数", "type": "int", "range": "[0,+∞)"}, {"name":"bo_random_choice", "val":5, "placeholder":"先验个数", "type": "int", "range": "[0,+∞)"}, {"name":"bo_count", "val":1, "placeholder":"迭代选择个数", "type": "int", "range": "[0,+∞)"}, {"name":"bo_gp_kenel_func", "val":"ARD_Matern_5/2", "placeholder":"高斯核", "type": "string", "range": ["ARD_Matern_5/2"]}, {"name":"bo_ac_func", "val":"expected_improvement", "placeholder":"获取函数", "type": "string", "range": ["expected_improvement", "upper_confidence_bound"]}]');

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `permission` (
  `P_ID` int(11) NOT NULL AUTO_INCREMENT,
  `P_NAME` varchar(30) DEFAULT NULL,
  `P_DESC` varchar(200) DEFAULT NULL,
  `P_DISPLAYNAME` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`P_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

INSERT INTO `permission` VALUES (1,'submitTask','提交新任务','提交新任务');
INSERT INTO `permission` VALUES (2,'taskManage','查看历史任务','查看历史任务');
INSERT INTO `permission` VALUES (3,'fileManage','数据管理','数据管理');
INSERT INTO `permission` VALUES (4,'userManage','用户管理','用户管理');
INSERT INTO `permission` VALUES (5,'roleList','角色管理','角色管理');

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_blob_triggers`
--


--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_calendars`
--


--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_cron_triggers`
--

INSERT INTO `qrtz_cron_triggers` VALUES ('mapScheduler','syncTriggerBean','DEFAULT','0/2 * * * * ?','Asia/Shanghai');

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role_perm_rela`
--

DROP TABLE IF EXISTS `role_perm_rela`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role_perm_rela` (
  `R_ID` int(11) DEFAULT NULL,
  `P_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_perm_rela`
--

INSERT INTO `role_perm_rela` VALUES (1,1);
INSERT INTO `role_perm_rela` VALUES (1,2);
INSERT INTO `role_perm_rela` VALUES (1,3);
INSERT INTO `role_perm_rela` VALUES (1,4);
INSERT INTO `role_perm_rela` VALUES (1,5);

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roles` (
  `R_ID` int(11) NOT NULL AUTO_INCREMENT,
  `R_NAME` varchar(50) DEFAULT NULL,
  `R_DESC` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`R_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` VALUES (1,'admin','管理员');

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `task` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) DEFAULT NULL,
  `f_id` int(11) DEFAULT NULL,
  `t_status` tinyint(1) DEFAULT NULL,
  `algo_id` int(11) DEFAULT NULL,
  `algo_params` text,
  `opt_algo_id` int(11) DEFAULT NULL,
  `opt_algo_params` text,
  `submit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `done_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `task_result` text,
  `eva_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task_detail`
--

DROP TABLE IF EXISTS `task_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `task_detail` (
  `detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) DEFAULT NULL,
  `detail` text,
  `detail_type` tinyint(1) DEFAULT NULL,
  `detail_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`detail_id`),
  KEY `task_detail_task_id_index` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `train_file`
--

DROP TABLE IF EXISTS `train_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `train_file` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) DEFAULT NULL,
  `f_path` varchar(100) NOT NULL,
  `f_type` varchar(10) DEFAULT NULL,
  `col_name` tinyint(1) DEFAULT NULL,
  `clu_label` tinyint(1) DEFAULT NULL,
  `origin_name` varchar(100) DEFAULT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `f_size` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role_rela`
--

DROP TABLE IF EXISTS `user_role_rela`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_role_rela` (
  `U_ID` int(11) DEFAULT NULL,
  `R_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_rela`
--

INSERT INTO `user_role_rela` VALUES (1,1);

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `U_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(50) DEFAULT NULL,
  `PASSWORD` varchar(100) DEFAULT NULL,
  `NICKNAME` varchar(50) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `TELEPHONE` varchar(50) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `DELETE_TIME` datetime DEFAULT NULL,
  `LAST_TIME` datetime DEFAULT NULL,
  `ENABLED` tinyint(4) DEFAULT NULL COMMENT '0',
  PRIMARY KEY (`U_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

INSERT INTO `users` VALUES (1,'admin','$2a$10$cHo9vYyTF7567AlpnRaM9OBLLZfvovRTlFoCodM0Dy3HVjw28tii2','恩博','akbbxbxnmn@qq.com',NULL,NULL,NULL,NULL,'2018-11-18 20:41:22',1);
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-26 23:49:32
