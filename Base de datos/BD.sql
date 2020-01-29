-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-01-2020 a las 12:36:08
-- Versión del servidor: 10.1.38-MariaDB
-- Versión de PHP: 7.1.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gestiontrabajo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mantenimientos`
--

CREATE TABLE `mantenimientos` (
  `CODIGO` varchar(255) NOT NULL,
  `DESCRIPCION` varchar(255) DEFAULT NULL,
  `CODIGOMAQUINA` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `maquinas`
--

CREATE TABLE `maquinas` (
  `CODIGO` varchar(255) NOT NULL,
  `DESCRIPCION` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registros`
--

CREATE TABLE `registros` (
  `CODIGO` int(11) NOT NULL,
  `HORA` varchar(255) NOT NULL,
  `ACCION` varchar(255) NOT NULL,
  `USUARIO` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tareas`
--

CREATE TABLE `tareas` (
  `CODIGO` varchar(255) NOT NULL,
  `DESCRIPCION` varchar(255) DEFAULT NULL,
  `MAQUINA` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajadores`
--

CREATE TABLE `trabajadores` (
  `DNI` varchar(9) NOT NULL,
  `NOMBRE` varchar(255) NOT NULL,
  `APELLIDO1` varchar(255) NOT NULL,
  `APELLIDO2` varchar(255) DEFAULT NULL,
  `FOTO` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajomantenimientos`
--

CREATE TABLE `trabajomantenimientos` (
  `CODIGO` int(11) NOT NULL,
  `DURACION` int(11) NOT NULL,
  `FECHAREALIZACION` int(11) NOT NULL,
  `DNITRABAJADOR` varchar(9) NOT NULL,
  `CODIGOMANTENIMIENTO` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajotareas`
--

CREATE TABLE `trabajotareas` (
  `CODIGO` int(11) NOT NULL,
  `DURACION` int(11) NOT NULL,
  `FECHAREALIZACION` int(11) NOT NULL,
  `DNITRABAJADOR` varchar(9) NOT NULL,
  `CODIGOTAREA` varchar(255) NOT NULL,
  `CODIGOMAQUINA` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `USERNAME` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `TIPOCUENTA` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`USERNAME`, `PASSWORD`, `TIPOCUENTA`) VALUES
('admin', 'admin', 3);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `mantenimientos`
--
ALTER TABLE `mantenimientos`
  ADD PRIMARY KEY (`CODIGO`),
  ADD KEY `CODIGOMAQUINA` (`CODIGOMAQUINA`);

--
-- Indices de la tabla `maquinas`
--
ALTER TABLE `maquinas`
  ADD PRIMARY KEY (`CODIGO`);

--
-- Indices de la tabla `registros`
--
ALTER TABLE `registros`
  ADD PRIMARY KEY (`CODIGO`),
  ADD KEY `USUARIO` (`USUARIO`);

--
-- Indices de la tabla `tareas`
--
ALTER TABLE `tareas`
  ADD PRIMARY KEY (`CODIGO`);

--
-- Indices de la tabla `trabajadores`
--
ALTER TABLE `trabajadores`
  ADD PRIMARY KEY (`DNI`);

--
-- Indices de la tabla `trabajomantenimientos`
--
ALTER TABLE `trabajomantenimientos`
  ADD PRIMARY KEY (`CODIGO`),
  ADD KEY `DNITRABAJADOR` (`DNITRABAJADOR`),
  ADD KEY `CODIGOMANTENIMIENTO` (`CODIGOMANTENIMIENTO`);

--
-- Indices de la tabla `trabajotareas`
--
ALTER TABLE `trabajotareas`
  ADD PRIMARY KEY (`CODIGO`),
  ADD KEY `CODIGOMAQUINA` (`CODIGOMAQUINA`),
  ADD KEY `DNITRABAJADOR` (`DNITRABAJADOR`),
  ADD KEY `CODIGOTAREA` (`CODIGOTAREA`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`USERNAME`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `registros`
--
ALTER TABLE `registros`
  MODIFY `CODIGO` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `trabajomantenimientos`
--
ALTER TABLE `trabajomantenimientos`
  MODIFY `CODIGO` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `trabajotareas`
--
ALTER TABLE `trabajotareas`
  MODIFY `CODIGO` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `mantenimientos`
--
ALTER TABLE `mantenimientos`
  ADD CONSTRAINT `mantenimientos_ibfk_1` FOREIGN KEY (`CODIGOMAQUINA`) REFERENCES `maquinas` (`CODIGO`) ON DELETE CASCADE;

--
-- Filtros para la tabla `registros`
--
ALTER TABLE `registros`
  ADD CONSTRAINT `registros_ibfk_1` FOREIGN KEY (`USUARIO`) REFERENCES `usuarios` (`USERNAME`) ON DELETE NO ACTION;

--
-- Filtros para la tabla `trabajomantenimientos`
--
ALTER TABLE `trabajomantenimientos`
  ADD CONSTRAINT `trabajomantenimientos_ibfk_1` FOREIGN KEY (`DNITRABAJADOR`) REFERENCES `trabajadores` (`DNI`) ON DELETE CASCADE,
  ADD CONSTRAINT `trabajomantenimientos_ibfk_2` FOREIGN KEY (`CODIGOMANTENIMIENTO`) REFERENCES `mantenimientos` (`CODIGO`) ON DELETE CASCADE;

--
-- Filtros para la tabla `trabajotareas`
--
ALTER TABLE `trabajotareas`
  ADD CONSTRAINT `trabajotareas_ibfk_1` FOREIGN KEY (`CODIGOMAQUINA`) REFERENCES `maquinas` (`CODIGO`) ON DELETE CASCADE,
  ADD CONSTRAINT `trabajotareas_ibfk_2` FOREIGN KEY (`DNITRABAJADOR`) REFERENCES `trabajadores` (`DNI`) ON DELETE CASCADE,
  ADD CONSTRAINT `trabajotareas_ibfk_3` FOREIGN KEY (`CODIGOTAREA`) REFERENCES `tareas` (`CODIGO`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
