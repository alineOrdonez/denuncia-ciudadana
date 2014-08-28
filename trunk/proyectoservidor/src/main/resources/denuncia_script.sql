/*
Navicat MySQL Data Transfer

Source Server         : peycash
Source Server Version : 50620
Source Host           : localhost:3306
Source Database       : denuncia

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2014-08-28 17:50:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bitacora
-- ----------------------------
DROP TABLE IF EXISTS `bitacora`;
CREATE TABLE `bitacora` (
  `Id_Bitacora` int(11) NOT NULL AUTO_INCREMENT,
  `Peticion` longtext NOT NULL,
  `Respuesta` longtext,
  `Fecha_Hora_Peticion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Fecha_Hora_Respuesta` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`Id_Bitacora`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bitacora
-- ----------------------------

-- ----------------------------
-- Table structure for cat_categoria_denuncia
-- ----------------------------
DROP TABLE IF EXISTS `cat_categoria_denuncia`;
CREATE TABLE `cat_categoria_denuncia` (
  `Id_Cat_Denuncia` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(50) NOT NULL,
  `Envio_Email` smallint(6) NOT NULL,
  PRIMARY KEY (`Id_Cat_Denuncia`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cat_categoria_denuncia
-- ----------------------------
INSERT INTO `cat_categoria_denuncia` VALUES ('1', 'Accidente vehicular', '0');
INSERT INTO `cat_categoria_denuncia` VALUES ('2', 'Cables caidos', '0');
INSERT INTO `cat_categoria_denuncia` VALUES ('3', 'Derrumbe', '0');
INSERT INTO `cat_categoria_denuncia` VALUES ('4', 'Fuga de agua', '0');
INSERT INTO `cat_categoria_denuncia` VALUES ('5', 'Fuga de gas', '0');
INSERT INTO `cat_categoria_denuncia` VALUES ('6', 'Huelga', '0');
INSERT INTO `cat_categoria_denuncia` VALUES ('7', 'Incendio', '0');
INSERT INTO `cat_categoria_denuncia` VALUES ('8', 'Inundacion', '0');
INSERT INTO `cat_categoria_denuncia` VALUES ('9', 'Rescate de cadaver', '0');
INSERT INTO `cat_categoria_denuncia` VALUES ('10', 'Robo', '0');
INSERT INTO `cat_categoria_denuncia` VALUES ('11', 'Seccionar arbol', '0');

-- ----------------------------
-- Table structure for cat_contacto
-- ----------------------------
DROP TABLE IF EXISTS `cat_contacto`;
CREATE TABLE `cat_contacto` (
  `Id_Contacto` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) NOT NULL,
  `Email` varchar(50) NOT NULL,
  PRIMARY KEY (`Id_Contacto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cat_contacto
-- ----------------------------

-- ----------------------------
-- Table structure for cat_delegacion
-- ----------------------------
DROP TABLE IF EXISTS `cat_delegacion`;
CREATE TABLE `cat_delegacion` (
  `Id_Delegacion` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`Id_Delegacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cat_delegacion
-- ----------------------------

-- ----------------------------
-- Table structure for cat_estatus_email
-- ----------------------------
DROP TABLE IF EXISTS `cat_estatus_email`;
CREATE TABLE `cat_estatus_email` (
  `Id_Estatus_Email` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`Id_Estatus_Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cat_estatus_email
-- ----------------------------

-- ----------------------------
-- Table structure for cat_institucion
-- ----------------------------
DROP TABLE IF EXISTS `cat_institucion`;
CREATE TABLE `cat_institucion` (
  `Id_Institucion` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`Id_Institucion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cat_institucion
-- ----------------------------

-- ----------------------------
-- Table structure for configuracion
-- ----------------------------
DROP TABLE IF EXISTS `configuracion`;
CREATE TABLE `configuracion` (
  `Id_Configuracion` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(50) NOT NULL,
  `Valor` varchar(50) NOT NULL,
  PRIMARY KEY (`Id_Configuracion`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of configuracion
-- ----------------------------
INSERT INTO `configuracion` VALUES ('1', 'Hace 2 horas', '2');
INSERT INTO `configuracion` VALUES ('2', 'Hace 4 horas', '4');
INSERT INTO `configuracion` VALUES ('3', 'Hace 8 horas', '8');
INSERT INTO `configuracion` VALUES ('4', 'Hace 12 horas', '12');

-- ----------------------------
-- Table structure for denuncia
-- ----------------------------
DROP TABLE IF EXISTS `denuncia`;
CREATE TABLE `denuncia` (
  `Id_Denuncia` int(11) NOT NULL AUTO_INCREMENT,
  `Id_Cat_Denuncia` int(11) NOT NULL,
  `Direccion` varchar(255) DEFAULT NULL,
  `Email_Usuario` varchar(50) DEFAULT NULL,
  `Contador` int(11) DEFAULT NULL,
  `Latitud` decimal(9,6) DEFAULT NULL,
  `Longitud` decimal(9,6) DEFAULT NULL,
  `Fecha_Hora_Denuncia` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Fecha_Hora_ActCont` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Img_Path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id_Denuncia`),
  KEY `Id_Cat_Denuncia` (`Id_Cat_Denuncia`),
  CONSTRAINT `denuncia_ibfk_1` FOREIGN KEY (`Id_Cat_Denuncia`) REFERENCES `cat_categoria_denuncia` (`Id_Cat_Denuncia`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of denuncia
-- ----------------------------

-- ----------------------------
-- Table structure for denuncia_config
-- ----------------------------
DROP TABLE IF EXISTS `denuncia_config`;
CREATE TABLE `denuncia_config` (
  `img_path` varchar(255) DEFAULT NULL,
  `img_extension` varchar(5) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of denuncia_config
-- ----------------------------
INSERT INTO `denuncia_config` VALUES ('C:\\Users\\amaro\\Desktop', '.jpg', '1');

-- ----------------------------
-- Table structure for email_denuncia
-- ----------------------------
DROP TABLE IF EXISTS `email_denuncia`;
CREATE TABLE `email_denuncia` (
  `Id_Email_Denuncia` int(11) NOT NULL AUTO_INCREMENT,
  `Id_Denuncia` int(11) NOT NULL,
  `Estatus_Email` int(11) NOT NULL,
  `Id_Contacto` int(11) NOT NULL,
  PRIMARY KEY (`Id_Email_Denuncia`),
  KEY `Id_Denuncia` (`Id_Denuncia`),
  KEY `Estatus_Email` (`Estatus_Email`),
  KEY `Id_Contacto` (`Id_Contacto`),
  CONSTRAINT `email_denuncia_ibfk_1` FOREIGN KEY (`Id_Denuncia`) REFERENCES `denuncia` (`Id_Denuncia`),
  CONSTRAINT `email_denuncia_ibfk_2` FOREIGN KEY (`Estatus_Email`) REFERENCES `cat_estatus_email` (`Id_Estatus_Email`),
  CONSTRAINT `email_denuncia_ibfk_3` FOREIGN KEY (`Id_Contacto`) REFERENCES `cat_contacto` (`Id_Contacto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of email_denuncia
-- ----------------------------

-- ----------------------------
-- Table structure for inst_con_del
-- ----------------------------
DROP TABLE IF EXISTS `inst_con_del`;
CREATE TABLE `inst_con_del` (
  `Id_Inst_Con_Del` int(11) NOT NULL AUTO_INCREMENT,
  `Id_Delegacion` int(11) NOT NULL,
  `Id_Institucion` int(11) NOT NULL,
  `Id_Contacto` int(11) NOT NULL,
  `Id_Cat_Denuncia` int(11) NOT NULL,
  PRIMARY KEY (`Id_Inst_Con_Del`),
  KEY `Id_Institucion` (`Id_Institucion`),
  KEY `Id_Contacto` (`Id_Contacto`),
  KEY `Id_Delegacion` (`Id_Delegacion`),
  KEY `Id_Cat_Denuncia` (`Id_Cat_Denuncia`),
  CONSTRAINT `inst_con_del_ibfk_1` FOREIGN KEY (`Id_Institucion`) REFERENCES `cat_institucion` (`Id_Institucion`),
  CONSTRAINT `inst_con_del_ibfk_2` FOREIGN KEY (`Id_Contacto`) REFERENCES `cat_contacto` (`Id_Contacto`),
  CONSTRAINT `inst_con_del_ibfk_3` FOREIGN KEY (`Id_Delegacion`) REFERENCES `cat_delegacion` (`Id_Delegacion`),
  CONSTRAINT `inst_con_del_ibfk_4` FOREIGN KEY (`Id_Cat_Denuncia`) REFERENCES `cat_categoria_denuncia` (`Id_Cat_Denuncia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inst_con_del
-- ----------------------------
