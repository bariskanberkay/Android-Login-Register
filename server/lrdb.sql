-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 26 Mar 2019, 01:57:27
-- Sunucu sürümü: 10.1.37-MariaDB
-- PHP Sürümü: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `lrdb`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_about_app`
--

CREATE TABLE `tbl_about_app` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(200) NOT NULL,
  `LOGO` varchar(200) NOT NULL,
  `EMAIL` varchar(200) NOT NULL,
  `VERSION` varchar(200) NOT NULL,
  `AUTHOR` varchar(200) NOT NULL,
  `CONTACT` varchar(200) NOT NULL,
  `WEBSITE` varchar(200) NOT NULL,
  `DESCRIPTION` text NOT NULL,
  `PRIVACY_POLICY` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `tbl_about_app`
--

INSERT INTO `tbl_about_app` (`ID`, `NAME`, `LOGO`, `EMAIL`, `VERSION`, `AUTHOR`, `CONTACT`, `WEBSITE`, `DESCRIPTION`, `PRIVACY_POLICY`) VALUES
(1, 'LR App', 'logo.png', 'lr@lr.com', '1.0.0.1', 'bariskanberkay', 'contact@lr.com', 'http://lr.com', '<p>E Book app is an android application.E Book App has user-friendly interface with easy to manage. The Quotes Pro are stored in Server Side for easy editing and better performance. You can create apps Different types of Category and Author.The application is specially optimized to be extremely easy to configure and detailed documentation is provided.</p>', '<p>E Book app is an android application.E Book App has user-friendly interface with easy to manage. The Quotes Pro are stored in Server Side for easy editing and better performance. You can create apps Different types of Category and Author.The application is specially optimized to be extremely easy to configure and detailed documentation is provided.</p>');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_api_settings`
--

CREATE TABLE `tbl_api_settings` (
  `ID` int(11) NOT NULL,
  `API_KEY` int(11) NOT NULL,
  `API_STATUS` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `tbl_api_settings`
--

INSERT INTO `tbl_api_settings` (`ID`, `API_KEY`, `API_STATUS`) VALUES
(1, 123456, 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_users`
--

CREATE TABLE `tbl_users` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(200) NOT NULL,
  `USERNAME` varchar(200) NOT NULL,
  `WEBSITE` varchar(200) NOT NULL,
  `BIO` text NOT NULL,
  `EMAIL` varchar(200) NOT NULL,
  `PHONE` varchar(200) NOT NULL,
  `PASSWORD` varchar(200) NOT NULL,
  `HASH_KEY` varchar(200) NOT NULL,
  `PROFILE_IMAGE` varchar(200) NOT NULL,
  `STATUS` int(11) NOT NULL,
  `LEVEL` int(11) NOT NULL,
  `VER_CODE` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tablo döküm verisi `tbl_users`
--

INSERT INTO `tbl_users` (`ID`, `NAME`, `USERNAME`, `WEBSITE`, `BIO`, `EMAIL`, `PHONE`, `PASSWORD`, `HASH_KEY`, `PROFILE_IMAGE`, `STATUS`, `LEVEL`, `VER_CODE`) VALUES
(1, 'Berkay Barışkan', 'bariskanberkay', 'http://berkaybariskan.com', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vitae aliquam odio, nec dignissim dui. Sed sollicitudin aliquet tincidunt. Curabitur lobortis augue vitae elementum facilisis. Ut commodo eros a hendrerit scelerisque. Maecenas id lectus justo. Fusce pulvinar eros ut hendrerit pretium. Etiam sapien ligula, tristique eu augue ac, suscipit tempor quam. Aenean facilisis fermentum luctus. Curabitur sodales tristique orci, id pharetra urna lacinia non. Nam sollicitudin, felis ut semper lacinia, mauris magna dignissim ante, id porttitor est neque eu justo. Nam tempus consequat dui, ac rhoncus orci volutpat nec. Duis vestibulum tortor id purus lacinia imperdiet.', 'berkay@berkay.com', '5309375950', 'berkay', '', 'image_1', 1, 0, 123456),
(3, '', 'test', '', '', 'test@test.com', '6515233', 'test', 'ccdbdd4a3d681d7e375c', '1.jpeg', 1, 2, 335019);

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `tbl_about_app`
--
ALTER TABLE `tbl_about_app`
  ADD PRIMARY KEY (`ID`);

--
-- Tablo için indeksler `tbl_api_settings`
--
ALTER TABLE `tbl_api_settings`
  ADD PRIMARY KEY (`ID`);

--
-- Tablo için indeksler `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`ID`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `tbl_about_app`
--
ALTER TABLE `tbl_about_app`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Tablo için AUTO_INCREMENT değeri `tbl_api_settings`
--
ALTER TABLE `tbl_api_settings`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Tablo için AUTO_INCREMENT değeri `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
