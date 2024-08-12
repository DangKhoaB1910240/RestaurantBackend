-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th8 09, 2024 lúc 04:25 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `event`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `activity`
--

CREATE TABLE `activity` (
  `id` int(11) NOT NULL,
  `activity_name` varchar(255) DEFAULT NULL,
  `date_time` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `event_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `activity`
--

INSERT INTO `activity` (`id`, `activity_name`, `date_time`, `description`, `img`, `event_id`) VALUES
(15, 'chơi game', '2024-03-01 09:00:00.000000', 'chơi game', 'https://www.atebits.com/wp-content/uploads/2019/03/shutterstock_1053076886-1440x960.jpg', 6),
(20, 'ăn trái cây', '2024-05-09 10:00:00.000000', 'ăn ngon', 'https://www.nhahangquangon.com/wp-content/uploads/2017/10/gia-%C4%91%C3%ACnh-bu%E1%BB%95i-t%E1%BB%91i.jpeg', 17);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `event`
--

CREATE TABLE `event` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `event_name` varchar(255) DEFAULT NULL,
  `max_quantity` int(11) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `total_attended` int(11) NOT NULL,
  `total_registered` int(11) NOT NULL,
  `organizer_id` int(11) NOT NULL,
  `end_date_time` datetime(6) DEFAULT NULL,
  `start_date_time` datetime(6) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `event`
--

INSERT INTO `event` (`id`, `description`, `event_name`, `max_quantity`, `status`, `total_attended`, `total_registered`, `organizer_id`, `end_date_time`, `start_date_time`, `img`) VALUES
(3, 'Tạo ra một không gian thú vị và sôi động, kích thích sự sáng tạo, và cung cấp cơ hội cho cộng đồng người chơi Boom Online để tương tác, chia sẻ kinh nghiệm, và thăng tiến trong thế giới game độc đáo này.\r\n\r\n\r\n\r\n\r\n\r\n', 'Hội nghị game Boom Online cho người trẻ', 20, b'1', 0, 0, 1, '2024-03-25 20:00:00.000000', '2024-03-21 07:00:00.000000', 'https://www.sellanycode.com/system/assets/uploads/products/Boomonlinemultiplayerroom46_sellanycode_icon_1606819015.png'),
(4, 'Tạo ra một không gian tương tác và giáo dục, nơi người chơi FIFA có thể hiểu rõ hơn về tiến triển đồ họa trong trò chơi và cảm nhận được sự chân thực và sống động hơn từ trải nghiệm chơi game của mình.', 'Trận đấu đồ họa của FIFA', 30, b'1', 0, 0, 1, '2024-04-29 09:30:00.000000', '2024-04-28 07:00:00.000000', 'https://cdn.tgdd.vn/2020/04/GameApp/unnamed-200x200-55.png'),
(5, 'Đưa người chơi vào một thế giới bắn súng đỉnh cao với đồ họa đỉnh cao và công nghệ tiên tiến, đồng thời tạo nên không khí hứng khởi và sáng tạo trong cộng đồng game thủ.', 'Bắn Nát 2024 - Chinh Phục Vũ Trụ', 40, b'1', 0, 0, 1, '2024-03-29 15:00:00.000000', '2024-03-17 07:00:00.000000', 'https://st.gamevui.com/images/image/2020/09/30/dot-kich-6-200.jpg'),
(6, 'Tạo ra một sự kiện vui nhộn, hứng khởi và đầy tính cộng đồng để tôn vinh và kỷ niệm thương hiệu Gunny, cũng như cung cấp cho người chơi cơ hội gặp gỡ, kết nối, và trải nghiệm những tính năng mới độc đáo của trò chơi.', 'Gunn Fest 2024 - Hành Trình Chiến Thắng a', 6, b'1', 0, 0, 1, '2024-03-03 07:00:00.000000', '2024-03-01 08:00:00.000000', 'https://st.quantrimang.com/photos/image/2022/04/05/Gunny-Origin-PC-200.jpg'),
(8, 'Phần tham gia trực tuyến cho phép người chơi ở xa tham gia vào các buổi thảo luận, hội thảo và trò chơi mạng.', 'Hội Nghị Đổi Mới và Sáng Tạo', 20, b'1', 0, 0, 2, '2024-03-30 18:08:22.000000', '2024-03-24 18:08:22.000000', 'https://defarosystems.com/wp-content/uploads/2017/06/IT-Information-Technology-200x200.jpeg'),
(9, 'Các buổi thảo luận sâu rộng với những diễn giả nổi tiếng từ cộng đồng Java về các chủ đề như Java 17, Java Virtual Machine, Microservices, và DevOps.', 'Hội nghị thượng đỉnh đổi mới Java 2024', 20, b'1', 0, 0, 2, '2024-04-30 23:19:12.000000', '2024-04-28 22:19:12.000000', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQts_miPWFGDtOtChxr3zUPA9w00WlZh8QFFoDNr-l7kvab4mAcYrsMnqGDhg9b6kMo70Q&usqp=CAU'),
(17, 'ăn uống lành mạnh', 'Ăn uống điều độ', 40, b'1', 1, 0, 16, '2024-05-10 09:00:00.000000', '2024-05-09 08:00:00.000000', 'https://careplusvn.com/files/Thap-dinh-duong-1.jpg'),
(21, 'ăn trái cây tốt cho sức khỏe', 'Bổ sung vitamin', 40, b'1', 0, 0, 16, '2024-05-10 12:00:00.000000', '2024-05-09 10:00:00.000000', 'https://vtv1.mediacdn.vn/thumb_w/650/2019/12/25/012-1577263068963589657236-crop-1577263077348902404394.jpg'),
(22, 'ăn trái cây tốt cho sức khỏe', 'ăn trái cây', 40, b'1', 0, 2, 16, '2024-05-05 08:00:00.000000', '2024-05-04 08:00:00.000000', 'https://www.huggies.com.vn/-/media/Project/HuggiesVN/Images/Articles/Cham-soc-be/thuc-pham-va-thuc-an-cho-be/dinh-duong-cho-be/be-6-thang-tuoi-an-duoc-trai-cay-gi.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `logger`
--

CREATE TABLE `logger` (
  `id` int(11) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `date_time` datetime(6) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `logger`
--

INSERT INTO `logger` (`id`, `content`, `date_time`, `role_name`, `user_id`) VALUES
(87, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-04-30 20:51:42.000000', 'ADMIN', 1),
(88, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-04-30 20:52:08.000000', 'ADMIN', 1),
(89, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Không tham gia đúng hạn\"', '2024-04-30 20:52:09.000000', 'ADMIN', 1),
(90, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-04-30 20:52:50.000000', 'ADMIN', 1),
(91, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-04-30 21:13:13.000000', 'ADMIN', 1),
(92, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-04-30 21:20:43.000000', 'ADMIN', 1),
(93, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-04-30 21:20:55.000000', 'ADMIN', 1),
(94, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Không tham gia đúng hạn\"', '2024-04-30 21:21:03.000000', 'ADMIN', 1),
(95, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-04-30 21:21:23.000000', 'ADMIN', 1),
(96, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-04-30 21:28:46.000000', 'ADMIN', 1),
(97, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-04-30 21:28:47.000000', 'ADMIN', 1),
(98, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Không tham gia đúng hạn\"', '2024-04-30 21:28:54.000000', 'ADMIN', 1),
(99, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-04-30 21:28:55.000000', 'ADMIN', 1),
(100, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-04-30 21:29:44.000000', 'ADMIN', 1),
(101, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-04-30 21:29:49.000000', 'ADMIN', 1),
(102, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Không tham gia đúng hạn\"', '2024-04-30 21:29:54.000000', 'ADMIN', 1),
(103, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-04-30 21:30:03.000000', 'ADMIN', 1),
(104, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-04-30 21:30:04.000000', 'ADMIN', 1),
(105, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-04-30 21:30:11.000000', 'ADMIN', 1),
(106, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-04-30 21:30:12.000000', 'ADMIN', 1),
(107, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-04-30 21:30:13.000000', 'ADMIN', 1),
(108, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-04-30 21:30:13.000000', 'ADMIN', 1),
(109, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-04-30 21:30:14.000000', 'ADMIN', 1),
(110, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-04-30 21:30:14.000000', 'ADMIN', 1),
(111, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-04-30 21:30:20.000000', 'ADMIN', 1),
(112, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Không tham gia đúng hạn\"', '2024-04-30 21:30:27.000000', 'ADMIN', 1),
(113, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-04-30 21:30:32.000000', 'ADMIN', 1),
(114, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-04-30 21:30:37.000000', 'ADMIN', 1),
(115, '- Thêm sự kiện aaaaa thuộc nhà tổ chức DINH DƯỠNG', '2024-04-30 22:47:34.000000', 'ADMIN', 1),
(116, '- Xóa sự kiện: aaaaa', '2024-04-30 22:58:49.000000', 'ADMIN', 1),
(117, '- Thêm hoạt động \"ăn bánh\" cho sự kiện \"Ăn uống điều độ\"', '2024-05-01 21:31:08.000000', 'ADMIN', 1),
(118, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-05-01 21:38:05.000000', 'ADMIN', 1),
(119, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-05-02 19:58:16.000000', 'ADMIN', 1),
(120, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-05-02 19:58:19.000000', 'ADMIN', 1),
(121, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Không tham gia đúng hạn\"', '2024-05-02 19:58:21.000000', 'ADMIN', 1),
(122, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-05-02 19:58:24.000000', 'ADMIN', 1),
(123, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"ronaldo\" thành \"Không tham gia đúng hạn\"', '2024-05-02 19:58:28.000000', 'ADMIN', 1),
(124, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-05-02 19:58:32.000000', 'ADMIN', 1),
(125, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"ronaldo\" thành \"Chưa xử lý\"', '2024-05-02 19:58:33.000000', 'ADMIN', 1),
(126, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"ronaldo\" thành \"Đã xác nhận tham gia\"', '2024-05-02 19:58:34.000000', 'ADMIN', 1),
(127, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"ronaldo\" thành \"Chưa xử lý\"', '2024-05-02 19:59:32.000000', 'ADMIN', 1),
(128, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"ronaldo\" thành \"Không tham gia đúng hạn\"', '2024-05-02 19:59:34.000000', 'ADMIN', 1),
(129, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-05-02 19:59:47.000000', 'ADMIN', 1),
(130, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"ronaldo\" thành \"Chưa xử lý\"', '2024-05-02 19:59:47.000000', 'ADMIN', 1),
(131, '- Thêm hoạt động \"ăn trái cây\" cho sự kiện \"Ăn uống điều độ\"', '2024-05-02 20:03:47.000000', 'ADMIN', 1),
(132, '- Xóa hoạt động \"ăn trái cây\" của sự kiện \"Ăn uống điều độ\"', '2024-05-02 20:03:55.000000', 'ADMIN', 1),
(133, '- Cập nhật sự kiện \"Ăn uống điều độ\" thành \"Ăn uống điều độ\"', '2024-05-02 20:04:59.000000', 'ADMIN', 1),
(134, '- Cập nhật sự kiện \"Ăn uống điều độ\" thành \"Ăn uống điều độ\"', '2024-05-02 20:05:44.000000', 'ADMIN', 1),
(135, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"ronaldo\" thành \"Đã xác nhận tham gia\"', '2024-05-02 20:06:52.000000', 'ADMIN', 1),
(136, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-05-02 20:07:27.000000', 'ADMIN', 1),
(137, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-05-02 20:07:31.000000', 'ADMIN', 1),
(138, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Không tham gia đúng hạn\"', '2024-05-02 20:07:34.000000', 'ADMIN', 1),
(139, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-05-02 20:07:44.000000', 'ADMIN', 1),
(140, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"ronaldo\" thành \"Chưa xử lý\"', '2024-05-02 20:07:46.000000', 'ADMIN', 1),
(141, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"ronaldo\" thành \"Không tham gia đúng hạn\"', '2024-05-02 20:07:51.000000', 'ADMIN', 1),
(142, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-05-02 20:08:01.000000', 'ADMIN', 1),
(143, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"ronaldo\" thành \"Chưa xử lý\"', '2024-05-02 20:08:02.000000', 'ADMIN', 1),
(144, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"ronaldo\" thành \"Đã xác nhận tham gia\"', '2024-05-02 20:08:02.000000', 'ADMIN', 1),
(145, '- Cập nhật trạng thái tài khoản \"ronaldo\" thành Khóa', '2024-05-02 20:09:09.000000', 'ADMIN', 1),
(146, '- Cập nhật trạng thái tài khoản \"ronaldo\" thành Hoạt động', '2024-05-02 20:09:17.000000', 'ADMIN', 1),
(147, '- Thêm vai trò: Giám đốc', '2024-05-02 20:10:01.000000', 'ADMIN', 1),
(148, '- Xóa vai trò: Giám đốc', '2024-05-02 20:10:10.000000', 'ADMIN', 1),
(149, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-05-02 20:10:37.000000', 'ADMIN', 1),
(150, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"ronaldo\" thành \"Chưa xử lý\"', '2024-05-02 20:10:37.000000', 'ADMIN', 1),
(151, '- Cập nhật sự kiện \"Ăn uống điều độ\" thành \"Ăn uống điều độ\"', '2024-05-02 20:11:23.000000', 'ADMIN', 1),
(152, '- Thêm hoạt động \"aaa\" cho sự kiện \"Ăn uống điều độ\"', '2024-05-02 20:42:57.000000', 'ADMIN', 1),
(153, '- Xóa hoạt động \"aaa\" của sự kiện \"Ăn uống điều độ\"', '2024-05-02 20:46:00.000000', 'ADMIN', 1),
(154, '- Xóa hoạt động \"ăn bánh\" của sự kiện \"Ăn uống điều độ\"', '2024-05-02 20:46:23.000000', 'ADMIN', 1),
(155, '- Xóa hoạt động \"ăn trái cây\" của sự kiện \"Ăn uống điều độ\"', '2024-05-02 20:46:27.000000', 'ADMIN', 1),
(156, '- Thêm hoạt động \"ăn trái cây\" cho sự kiện \"Ăn uống điều độ\"', '2024-05-02 20:46:54.000000', 'ADMIN', 1),
(157, '- Cập nhật sự kiện \"Gunn Fest 2024 - Hành Trình Chiến Thắng a\" thành \"Gunn Fest 2024 - Hành Trình Chiến Thắng a\"', '2024-05-02 21:03:47.000000', 'ADMIN', 1),
(158, '- Cập nhật sự kiện \"Gunn Fest 2024 - Hành Trình Chiến Thắng a\" thành \"Gunn Fest 2024 - Hành Trình Chiến Thắng a\"', '2024-05-02 21:05:18.000000', 'ADMIN', 1),
(159, '- Thêm sự kiện Ăn uống điều độ thuộc nhà tổ chức DINH DƯỠNG', '2024-05-02 22:49:36.000000', 'ADMIN', 1),
(160, '- Xóa sự kiện: Ăn uống điều độ', '2024-05-02 22:50:18.000000', 'ADMIN', 1),
(161, '- Thêm sự kiện Ăn uống điều độ thuộc nhà tổ chức Sức Khỏe Vẻ Đẹp', '2024-05-02 22:51:30.000000', 'ADMIN', 1),
(162, '- Cập nhật sự kiện \"Ăn uống điều độ\" thành \"Ăn uống điều độ\"', '2024-05-02 22:54:14.000000', 'ADMIN', 1),
(163, '- Cập nhật sự kiện \"Ăn uống điều độ\" thành \"Ăn uống điều độ\"', '2024-05-02 22:54:49.000000', 'ADMIN', 1),
(164, '- Xóa sự kiện: Ăn uống điều độ', '2024-05-02 22:54:53.000000', 'ADMIN', 1),
(165, '- Thêm sự kiện ăn trái cây thuộc nhà tổ chức DINH DƯỠNG', '2024-05-03 07:31:53.000000', 'ADMIN', 1),
(166, '- Cập nhật sự kiện \"ăn trái cây\" thành \"Bổ sung vitamin\"', '2024-05-03 07:43:19.000000', 'ADMIN', 1),
(167, '- Cập nhật trạng thái đăng ký sự kiện \"Ăn uống điều độ\" của tài khoản \"quyết chiến\" thành \"Đã xác nhận tham gia\"', '2024-05-03 08:28:08.000000', 'ADMIN', 1),
(168, '- Thêm sự kiện ăn trái cây thuộc nhà tổ chức DINH DƯỠNG', '2024-05-03 08:31:59.000000', 'ADMIN', 1),
(169, '- Cập nhật trạng thái đăng ký sự kiện \"ăn trái cây\" của tài khoản \"quyết chiến\" thành \"Không tham gia đúng hạn\"', '2024-08-05 19:45:01.000000', 'ADMIN', 1),
(170, '- Cập nhật trạng thái đăng ký sự kiện \"ăn trái cây\" của tài khoản \"quyết chiến\" thành \"Chưa xử lý\"', '2024-08-05 19:45:03.000000', 'ADMIN', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `organizer`
--

CREATE TABLE `organizer` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `organizer_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `organizer`
--

INSERT INTO `organizer` (`id`, `email`, `organizer_name`, `phone`, `address`, `img`) VALUES
(1, 'gameonline@gmail.com', 'Đấu Trí Gamesaaa', '0763955356', 'đường 3/2, phường Xuân Khánh, quận Ninh Kiều, thành phố Cần Thơ', 'https://www.ddmagency.com/wp-content/uploads/2021/11/everspace-2-350x260.png'),
(2, 'dieuky@gmail.com', 'Công Nghệ Kỳ Diệu', '01242882856', 'quận 3 và quận 5, thành phố Hồ Chí Minh', 'https://braincancercanada.ca/wp-content/uploads/2023/06/AdobeStock_396750640-scaled-350x260.jpeg'),
(3, 'vedep@gmail.com', 'Sức Khỏe Vẻ Đẹp', '0917349905', 'Hồ Gươm, thủ đô Hà Nội', 'https://m.media-amazon.com/images/I/61KVZCHI+8L._AC_UF350,350_QL80_.jpg'),
(16, 'food@gmail.com', 'DINH DƯỠNG', '012345678', '12 đề thám', 'https://vuanem.com/blog/wp-content/uploads/2022/05/the-nao-la-thap-dinh-duong.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `registration`
--

CREATE TABLE `registration` (
  `id` int(11) NOT NULL,
  `registration_date` date DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `registration`
--

INSERT INTO `registration` (`id`, `registration_date`, `status`, `event_id`, `user_id`) VALUES
(15, '2024-05-03', 0, 22, 6);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'USER'),
(2, 'ADMIN');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `address`, `email`, `fullname`, `password`, `phone_number`, `status`, `username`) VALUES
(1, 'Cần Thơ', 'admin@gmail.com', 'Nguyễn Khánh An', '$2a$10$rRhsxFIXSfEFHhSAQoxufOzN7k0Vp9M7Taiw7Xe1cAb9DmlEVUEOO', '0843152505', b'1', 'admin'),
(2, 'Sóc Trăng', 'khoab1910240@gmail.com', 'Nguyễn Văn A', '$2a$10$ZJXbaqVIWKE/MM1Pbomh6ujaXm8sfi2MMRyWiMo29rk8rm/a3WaZG', '0843152505', b'1', 'dark'),
(3, 'An Giang', 'template@gmail.com', 'Angular', '$2a$10$8qtBcXdqXeNUGxbfGp884uuxiWqg7km/6kXCB.d0S3arQ.nnvgTVy', '0917349907', b'1', 'admin2'),
(4, 'cần thơ', 'ankhanhnguyen2308@gmail.com', 'an', '$2a$10$9WlUBT2qEuGlt0xdytOBXeBrgOdYeOd0LB1F0UQLe3gq9O8fwL6lW', '0102012012121', b'1', 'an'),
(5, '123', 'an@gmail.com', 'messi', '$2a$10$hjwMkSgMwWzU5BJQvMn5qezBZ1UNU8I0658Yyhy8/BZwfNrGmmwNy', '0120102012012', b'1', 'messi'),
(6, 'cần thơ', 'ankhanhnguyen2308@gmail.com', 'quyết chiến', '$2a$10$LAjjNbBzUU/3ITaHAIZCAukH0310d9f16b8r./KXXKemBUJfrNCpa', '1312313123123123', b'1', 'quyết chiến'),
(7, 'Cần Thơ', 'ankhanhnguyen2308@gmail.com', 'ronaldo', '$2a$10$Rat1Ob6eu0A6/6.eqPAKLeEgYXGmvjFlV/yv7l0piZYI9A8D/YSQe', '0949123', b'1', 'ronaldo');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_role`
--

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 2),
(2, 1),
(3, 2),
(4, 1),
(5, 1),
(6, 1),
(7, 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `activity`
--
ALTER TABLE `activity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8vaj54yaxyeog07uce4q17paj` (`event_id`);

--
-- Chỉ mục cho bảng `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKik6obgrxb8c6aakhf8fj7gs9h` (`organizer_id`);

--
-- Chỉ mục cho bảng `logger`
--
ALTER TABLE `logger`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8pc9cxnnahndarikcnqaqqtd1` (`user_id`);

--
-- Chỉ mục cho bảng `organizer`
--
ALTER TABLE `organizer`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `registration`
--
ALTER TABLE `registration`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKs4x1uat6i8fx26qpdrfwfg3ya` (`event_id`),
  ADD KEY `FKkyuphiynxwt1mtlfsptc991sc` (`user_id`);

--
-- Chỉ mục cho bảng `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `user_role`
--
ALTER TABLE `user_role`
  ADD KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  ADD KEY `FKj345gk1bovqvfame88rcx7yyx` (`user_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `activity`
--
ALTER TABLE `activity`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT cho bảng `event`
--
ALTER TABLE `event`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT cho bảng `logger`
--
ALTER TABLE `logger`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=171;

--
-- AUTO_INCREMENT cho bảng `organizer`
--
ALTER TABLE `organizer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT cho bảng `registration`
--
ALTER TABLE `registration`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `activity`
--
ALTER TABLE `activity`
  ADD CONSTRAINT `FK8vaj54yaxyeog07uce4q17paj` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`);

--
-- Các ràng buộc cho bảng `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `FKik6obgrxb8c6aakhf8fj7gs9h` FOREIGN KEY (`organizer_id`) REFERENCES `organizer` (`id`);

--
-- Các ràng buộc cho bảng `logger`
--
ALTER TABLE `logger`
  ADD CONSTRAINT `FK8pc9cxnnahndarikcnqaqqtd1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `registration`
--
ALTER TABLE `registration`
  ADD CONSTRAINT `FKkyuphiynxwt1mtlfsptc991sc` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKs4x1uat6i8fx26qpdrfwfg3ya` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`);

--
-- Các ràng buộc cho bảng `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
