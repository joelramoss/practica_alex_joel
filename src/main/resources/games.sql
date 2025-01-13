-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-01-2025 a las 17:50:43
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `games`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `desarrolladores`
--

CREATE TABLE `desarrolladores` (
  `ID` int(11) NOT NULL,
  `Nombre` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalles_juego`
--

CREATE TABLE `detalles_juego` (
  `juego_id` int(11) NOT NULL,
  `Reviews` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `generos`
--

CREATE TABLE `generos` (
  `ID` int(11) NOT NULL,
  `Genero` varchar(100) NOT NULL,
  `generos` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juego`
--

CREATE TABLE `juego` (
  `id` int(11) NOT NULL,
  `Title` varchar(255) NOT NULL,
  `Release_Date` date DEFAULT NULL,
  `Summary` text DEFAULT NULL,
  `Plays` int(11) DEFAULT NULL,
  `Playing` int(11) DEFAULT NULL,
  `Backlogs` int(11) DEFAULT NULL,
  `Wishlist` int(11) DEFAULT NULL,
  `timesListed` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juegos_generados`
--

CREATE TABLE `juegos_generados` (
  `juego_id` int(11) NOT NULL,
  `generos` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juego_equipo`
--

CREATE TABLE `juego_equipo` (
  `id` int(11) NOT NULL,
  `juego_id` int(11) DEFAULT NULL,
  `desarrollador_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rating`
--

CREATE TABLE `rating` (
  `Juego_ID` int(11) NOT NULL,
  `Rating` decimal(3,2) DEFAULT NULL,
  `Number_of_Reviews` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `desarrolladores`
--
ALTER TABLE `desarrolladores`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `detalles_juego`
--
ALTER TABLE `detalles_juego`
  ADD PRIMARY KEY (`juego_id`);

--
-- Indices de la tabla `generos`
--
ALTER TABLE `generos`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `juego`
--
ALTER TABLE `juego`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `juegos_generados`
--
ALTER TABLE `juegos_generados`
  ADD PRIMARY KEY (`juego_id`);

--
-- Indices de la tabla `juego_equipo`
--
ALTER TABLE `juego_equipo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `juego_id` (`juego_id`),
  ADD KEY `desarrollador_id` (`desarrollador_id`);

--
-- Indices de la tabla `rating`
--
ALTER TABLE `rating`
  ADD PRIMARY KEY (`Juego_ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `desarrolladores`
--
ALTER TABLE `desarrolladores`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `generos`
--
ALTER TABLE `generos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `juego`
--
ALTER TABLE `juego`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `juego_equipo`
--
ALTER TABLE `juego_equipo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalles_juego`
--
ALTER TABLE `detalles_juego`
  ADD CONSTRAINT `detalles_juego_ibfk_1` FOREIGN KEY (`juego_id`) REFERENCES `juego` (`ID`),
  ADD CONSTRAINT `fk_detalles_juego_juego` FOREIGN KEY (`juego_id`) REFERENCES `juego` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `juego_equipo`
--
ALTER TABLE `juego_equipo`
  ADD CONSTRAINT `juego_equipo_ibfk_1` FOREIGN KEY (`juego_id`) REFERENCES `juego` (`id`),
  ADD CONSTRAINT `juego_equipo_ibfk_2` FOREIGN KEY (`desarrollador_id`) REFERENCES `desarrolladores` (`ID`);

--
-- Filtros para la tabla `rating`
--
ALTER TABLE `rating`
  ADD CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`Juego_ID`) REFERENCES `juego` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
