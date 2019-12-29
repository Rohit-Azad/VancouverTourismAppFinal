-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Aug 05, 2019 at 04:13 AM
-- Server version: 5.5.62
-- PHP Version: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `FinalProjectDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `LoginInfo`
--

CREATE TABLE `LoginInfo` (
  `UserName` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(20) NOT NULL,
  `Role` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `LoginInfo`
--

INSERT INTO `LoginInfo` (`UserName`, `Password`, `FirstName`, `LastName`, `Role`) VALUES
('arsh007', 'CSIS4175', 'Arshdeep', 'Brar', 'Client'),
('gurman007', '123456', 'Gurmandeep', 'Singh', 'Client'),
('rajan122', 'qwerty123', 'Rajanpreet', 'Singh', 'Client'),
('rohit3049', 'passw0rd', 'Rohit', 'Azad', 'Administrator'),
('Stephen4175', 'CSIS4175', 'Stephen', 'Chiong', 'Administrator'),
('vasu4175', 'snackbar', 'Vasu', 'Joshi', 'Client');

-- --------------------------------------------------------

--
-- Table structure for table `TaxiDB`
--

CREATE TABLE `TaxiDB` (
  `TaxiID` int(11) NOT NULL,
  `VehicleType` varchar(20) NOT NULL,
  `VehicleName` varchar(30) NOT NULL,
  `SeatingCapacity` int(3) NOT NULL,
  `Quantity` int(3) NOT NULL,
  `Price` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TaxiDB`
--

INSERT INTO `TaxiDB` (`TaxiID`, `VehicleType`, `VehicleName`, `SeatingCapacity`, `Quantity`, `Price`) VALUES
(1, 'Van', 'Toyota Sienna', 8, 6, '4.99'),
(2, 'Sedan', 'Toyota Corolla', 5, 6, '3.99'),
(3, 'Hatchback', 'Mercedes Benz A Class', 4, 8, '3.49'),
(4, 'SUV', 'Acura MDX', 6, 8, '4.49'),
(5, 'Luxury', 'Mercedes Benz E Class', 4, 8, '7.99'),
(6, 'Van', 'Suzuki XL-7', 7, 6, '4.99'),
(7, 'Hatchback', 'Mazda Millenia', 4, 6, '3.99'),
(8, 'Sedan', 'Aud S4', 5, 6, '3.99'),
(9, 'Van', 'Toyota Camry', 5, 6, '3.99'),
(10, 'SUV', 'Toyota Fortuner', 7, 6, '4.99'),
(11, 'SUV', 'Mitsubushi Pajero', 6, 6, '4.49'),
(12, 'Sedan', 'Lexus ES', 5, 5, '3.99'),
(13, 'SUV', 'Mahindra Scorpio', 7, 6, '6.99'),
(14, 'Hatchback', 'Infiniti FX', 4, 6, '3.99'),
(15, 'Hatchback', 'Mini Mini', 4, 6, '3.49'),
(16, 'Sedan', 'Nissan Maxima', 5, 6, '3.99'),
(17, 'Luxury', 'Rolls Royce Phantom', 4, 6, '11.99');

-- --------------------------------------------------------

--
-- Table structure for table `TaxiReservationInfo`
--

CREATE TABLE `TaxiReservationInfo` (
  `ID` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `passengers` int(3) NOT NULL,
  `pickup` varchar(50) NOT NULL,
  `address1` varchar(50) NOT NULL,
  `address2` varchar(50) NOT NULL,
  `taxi` varchar(50) NOT NULL,
  `priceperhour` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TaxiReservationInfo`
--

INSERT INTO `TaxiReservationInfo` (`ID`, `name`, `passengers`, `pickup`, `address1`, `address2`, `taxi`, `priceperhour`) VALUES
('1', 'Rohit Azad', 5, '08/04/19 - 04:00', '110 28 Richmon d Street', 'New Westminster, BC', 'Van - Suzuki XL-7', '0.99'),
('JA_SE6', 'Jatin Kumar', 3, '08/04/19 - 05:00', '122 28 Richmond St', 'New Westminster, BC', 'Sedan - Aud S4', '0.99'),
('QU_SU40', 'Quincey', 5, '08/04/19 - 20:00', '200 420st', 'Surrey, BC', 'SUV - Mahindra Scorpio', '6.99'),
('RO_SU29', 'Rohit', 5, '08/04/19 - 21:00', '13- dsefse', 'dsgsdfsdfs', 'SUV - Acura MDX', '4.49'),
('VA_SE42', 'Vasu Joshi', 5, '08/04/19 - 05:00', '12 47th St', 'Vancouver, BC', 'Sedan - Aud S4', '3.99');

-- --------------------------------------------------------

--
-- Table structure for table `TouristAttractions`
--

CREATE TABLE `TouristAttractions` (
  `ID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Location` varchar(30) NOT NULL,
  `AvgReview` decimal(3,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TouristAttractions`
--

INSERT INTO `TouristAttractions` (`ID`, `Name`, `Location`, `AvgReview`) VALUES
(1, 'Samson V Museum', 'New Westminster', '3.90'),
(2, 'Pier Park', 'New Westminster', '4.10'),
(3, 'Starlight Casino', 'New Westminster', '9.99'),
(4, 'Hazelmere Golf & Tennis Club', 'Surrey', '4.90'),
(5, 'Holland Park', 'Surrey', '3.75'),
(6, 'Fraser Downs Racetrack & Casino', 'Surrey', '3.50'),
(7, 'Sushi Garden Japanese Restaurant', 'Burnaby', '4.90'),
(8, 'Robert Burnaby Park', 'Burnaby', '3.50'),
(9, 'Patterson Park', 'Burnaby', '4.50'),
(10, 'Burnaby Mountain Park', 'Burnaby', '4.25'),
(11, 'Jericho Beach', 'North Vancouver', '4.90'),
(12, 'Deep Cove', 'North Vancouver', '5.00'),
(13, 'Grouse Mountain', 'North Vancouver', '4.90'),
(14, 'English Bay Beach', 'Downtown Vancouver', '4.50'),
(15, 'Stanley Park', 'Downtown Vancouver', '4.10'),
(16, 'FlyOver Canada', 'Downtown Vancouver', '4.75'),
(17, 'Iona Beach Regional Park', 'Richmond', '3.90'),
(18, 'Minoru Park', 'Richmond', '3.75'),
(19, 'Garry Point Park', 'Richmond', '4.00'),
(20, 'Steveston Heritage Fishing Village', 'Richmond', '4.90');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `LoginInfo`
--
ALTER TABLE `LoginInfo`
  ADD PRIMARY KEY (`UserName`);

--
-- Indexes for table `TaxiReservationInfo`
--
ALTER TABLE `TaxiReservationInfo`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `TouristAttractions`
--
ALTER TABLE `TouristAttractions`
  ADD PRIMARY KEY (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
