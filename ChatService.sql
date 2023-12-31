-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: localhost
-- Thời gian đã tạo: Th10 07, 2023 lúc 01:58 PM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `ChatService`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `conversation`
--

USE ChatService;

CREATE TABLE `conversation` (
  `id` int(11) NOT NULL,
  `user_create_id` int(11) DEFAULT NULL,
  `another_user_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `conversation`
--

INSERT INTO `conversation` (`id`, `user_create_id`, `another_user_id`, `created_at`) VALUES
(2, 1, 2, NULL),
(3, 3, 1, NULL),
(4, 2, 3, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `message`
--


CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `conversation_id` int(11) DEFAULT NULL,
  `sender_id` int(11) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `timestamp` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `message`
--

INSERT INTO `message` (`id`, `conversation_id`, `sender_id`, `content`, `timestamp`) VALUES
(3, 2, 1, 'hello toan', '2023-11-02 13:47:36'),
(4, 2, 2, 'gi huy', '2023-11-02 13:47:36'),
(5, 3, 3, 'hello huy', '2023-11-02 13:48:55'),
(6, 3, 1, 'gi nam', '2023-11-02 13:48:55'),
(7, 4, 2, 'hello nam', '2023-11-02 13:49:17'),
(8, 3, 1, 'gi toan', '2023-11-02 13:49:17'),
(9, 3, 1, 'hello', '2023-11-07 19:21:21'),
(10, 3, 1, 'hello lan nua', '2023-11-07 19:22:12'),
(11, 3, 1, 'hello lan nua nua\r\n                                                                          ', '2023-11-07 19:22:25'),
(12, 3, 1, 'nam mat các', '2023-11-07 19:24:53'),
(13, 3, 1, 'nam oc cho', '2023-11-07 19:25:12'),
(14, 3, 1, 'nam oc cho', '2023-11-07 19:26:32'),
(15, 3, 1, 'nam oc cho', '2023-11-07 19:27:21'),
(16, 3, 1, 'nam oc cho 1', '2023-11-07 19:27:42');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `name`) VALUES
(1, 'huy'),
(2, 'toan'),
(3, 'nam');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `conversation`
--
ALTER TABLE `conversation`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `conversation_id` (`conversation_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `conversation`
--
ALTER TABLE `conversation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `message`
--
ALTER TABLE `message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
