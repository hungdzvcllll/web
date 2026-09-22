# FLASHCARD SYSTEM - NỀN TẢNG HỌC TẬP THẺ GHI NHỚ VÀ QUẢN LÝ LỚP HỌC TRỰC TUYẾN

- **Dự án**: Nền tảng học tập trực tuyến Flashcard System
- **Kiến trúc**: Full-stack Monolithic / Client-Server tách biệt Frontend tĩnh và Backend RESTful API
- **Công nghệ chính**: Spring Boot 3, Java 17+, JPA / Hibernate, MySQL / PostgreSQL, Vanilla Web (HTML5/CSS3/JS), Docker

## GIỚI THIỆU

Flashcard System là một hệ thống web hỗ trợ học tập toàn diện dựa trên phương pháp thẻ ghi nhớ (spaced repetition / active recall). Ứng dụng cho phép người dùng tự tạo học phần (StudySet), tổ chức các thẻ từ vựng/thuật ngữ theo thư mục (Folder), tham gia các lớp học (Class), làm bài kiểm tra trắc nghiệm (Quiz/Test) và nâng cấp tài khoản VIP thông qua cổng thanh toán trực tuyến VNPAY.

Hệ thống được thiết kế theo kiến trúc chuẩn RESTful API với Backend viết bằng Spring Boot kết hợp bảo mật Spring Security & JWT, kết hợp với giao diện Frontend trực quan, thân thiện, dễ sử dụng.

### Tính năng chính:
- **Xác thực & Phân quyền**: Đăng ký, đăng nhập tài khoản bằng JWT (JSON Web Token), gửi mã kích hoạt/đổi mật khẩu qua Email (JavaMailSender).
- **Quản lý học phần (StudySet)**: Tạo, chỉnh sửa, xóa bộ thẻ ghi nhớ, hỗ trợ đính kèm hình ảnh minh họa cho từng thuật ngữ (`StudySetItem`).
- **Chế độ học tập & Kiểm tra**: Luyện tập thẻ lật (Flashcard flip), làm bài trắc nghiệm (Quiz), tự động chấm điểm và lưu vết lịch sử làm bài (`Quiz History`).
- **Tổ chức nội dung theo Thư mục**: Gom nhóm các học phần vào Thư mục (`Folder`), phân quyền truy cập thư mục (`AccessFolder`).
- **Quản lý Lớp học (Class)**: Tạo lớp học, mời/thêm học viên (`UserInClass`), chia sẻ tài liệu và bộ thẻ trong phạm vi lớp học.
- **Tài liệu tham khảo (TextBook) & Chương mục (Chapter)**: Quản lý và tra cứu tài liệu học tập điện tử đính kèm (PDF/Tài liệu số).
- **Thanh toán trực tuyến VNPAY & Tài khoản VIP**: Tích hợp cổng thanh toán VNPAY Sandbox để nâng cấp gói thành viên VIP, tự động gia hạn và ghi nhận lịch sử giao dịch.
- **Hệ thống thông báo (Notification)**: Cập nhật thông báo hệ thống, thông báo lớp học theo thời gian thực tới từng người dùng.
- **Đóng gói Docker**: Triển khai nhanh chóng cả ứng dụng và cơ sở dữ liệu thông qua Docker & Docker Compose.

## KIẾN TRÚC HỆ THỐNG

Hệ thống bao gồm 2 thành phần chính hoạt động độc lập và giao tiếp thông qua giao thức HTTP/JSON:

### 1. **Backend Server** (Java / Spring Boot)
- **Framework & Core**: Spring Boot 3.x, Spring MVC, Spring Data JPA, Spring Security.
- **Bảo mật**: Stateless Authentication với JWT Filter (`JwtFilter`, `JwtService`).
- **Cơ sở dữ liệu**: MySQL / PostgreSQL kết nối qua Hibernate ORM.
- **Tích hợp bên ngoài**: 
  - Gửi Email xác thực tự động qua SMTP (Java Mail Sender).
  - Cổng thanh toán trực tuyến VNPAY.
- **Chuyển đổi dữ liệu**: Áp dụng mô hình DTO (Data Transfer Object) và Mapper (`UserMapper`, `StudySetMapper`,...).

### 2. **Frontend Client** (HTML5 / CSS3 / JavaScript ES6+)
- Giao diện người dùng thuần (Vanilla Web), tối ưu tốc độ tải trang, không phụ thuộc nặng vào thư viện bên ngoài.
- Gọi REST API thông qua `fetch` API tập trung tại `js/api.js` và `js/config.js`.
- Hỗ trợ lưu trữ token tại LocalStorage, xử lý phiên đăng nhập và phân quyền trên giao diện người dùng.

### Cấu trúc thư mục dự án:
```
flashcard-system/
├── pom.xml                               # Quản lý thư viện Maven (Spring Boot, JWT, MySQL, Mail,...)
├── Dockerfile                            # Dockerfile đóng gói backend JAR
├── docker-compose.yml                    # Khởi tạo Database và Backend Service
├── docker_application.properties         # Cấu hình Spring Boot cho môi trường Docker
├── src/
│   ├── main/
│   │   ├── java/com/web/web/
│   │   │   ├── WebApplication.java       # Main entry point của ứng dụng Spring Boot
│   │   │   ├── config/                   # Cấu hình hệ thống & tích hợp VNPAY
│   │   │   ├── controller/               # Lớp Controller điều hướng REST API endpoints
│   │   │   ├── dto/                      # Đối tượng Request / Response DTO
│   │   │   ├── entity/                   # Các Entity ánh xạ Database (JPA)
│   │   │   ├── mapper/                   # Chuyển đổi qua lại giữa Entity và DTO
│   │   │   ├── repository/               # Lớp Repository truy vấn Database (Spring Data JPA)
│   │   │   ├── security/                 # Cấu hình Spring Security, JWT Filter & UserDetails
│   │   │   └── service/                  # Lớp Service xử lý nghiệp vụ chính
│   │   └── resources/
│   │       ├── application.properties    # Cấu hình kết nối DB, Mail, VNPAY cục bộ
│   │       └── static/                   # Thư mục lưu trữ media tĩnh (hình ảnh, tài liệu PDF)
│   ├── flashcard-frontend/               # Mã nguồn giao diện người dùng (Client)
│   │   ├── index.html                    # Trang đích chính
│   │   ├── Dockerfile                    # Đóng gói static web (Nginx)
│   │   ├── css/style.css                 # File định dạng giao diện chung
│   │   ├── js/                           # Thư viện JavaScript (app.js, api.js, config.js, utils.js)
│   │   └── pages/                        # Các trang chức năng (dashboard, study, quiz, classes,...)
│   └── test/                             # Unit Test & Integration Test (JUnit, Mockito)
└── docs/                                 # Tài liệu kiến trúc và hình ảnh minh họa
```

## CƠ SỞ DỮ LIỆU (DATABASE SCHEMA)

Cơ sở dữ liệu của hệ thống được chuẩn hóa bao gồm các bảng thực thể và bảng quan hệ liên kết:

| Bảng (Entity) | Vai trò | Trường tiêu biểu |
|---|---|---|
| `User` | Thông tin tài khoản người dùng | `id`, `email`, `password`, `fullName`, `role`, `status` |
| `VipUser` | Thông tin gia hạn và cấp độ VIP | `id`, `userId`, `startDate`, `endDate`, `status` |
| `StudySet` | Bộ học phần thẻ ghi nhớ | `id`, `title`, `description`, `isPublic`, `userId` |
| `StudySetItem` | Từng thẻ học gồm thuật ngữ/định nghĩa | `id`, `term`, `definition`, `imageUrl`, `studySetId` |
| `Folder` | Thư mục nhóm các bộ Flashcard | `id`, `name`, `description`, `userId` |
| `AccessFolder` | Quan hệ liên kết giữa Thư mục và StudySet | `folderId`, `studySetId` |
| `Class` | Lớp học trực tuyến | `id`, `name`, `description`, `code`, `ownerId` |
| `UserInClass` | Danh sách thành viên trong lớp học | `classId`, `userId`, `roleInClass`, `joinedAt` |
| `TextBook` | Sách điện tử / Giáo trình học tập | `id`, `title`, `author`, `fileUrl` |
| `Chapter` | Các chương mục trong sách giáo trình | `id`, `title`, `orderIndex`, `textBookId` |
| `Test` | Đề kiểm tra và câu hỏi trắc nghiệm | `id`, `title`, `duration`, `studySetId` |
| `Notification` | Thông báo gửi tới tài khoản người dùng | `id`, `userId`, `content`, `isRead`, `createdAt` |

## HƯỚNG DẪN CÀI ĐẶT VÀ CHẠY THỬ

### Bước 1: Chuẩn bị môi trường

**Yêu cầu:**
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) >= 17
- [Apache Maven](https://maven.apache.org/) >= 3.8 (hoặc sử dụng wrapper `mvnw` có sẵn)
- [MySQL Server](https://dev.mysql.com/downloads/installer/) >= 8.0 hoặc Docker Desktop
- Trình duyệt web hiện đại (Google Chrome, Firefox, Microsoft Edge)

### Bước 2: Cấu hình biến môi trường và Cơ sở dữ liệu

Tạo cơ sở dữ liệu rỗng trong MySQL:
```sql
CREATE DATABASE flashcard_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Chỉnh sửa thông số kết nối trong tệp `src/main/resources/application.properties`:
```properties
# Cấu hình kết nối MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/flashcard_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Cấu hình Secret Key JWT
jwt.secret=9a0211345f2c204161fde45f1d8efc7ee33f009e4f51e069fc25a3d72b9a7620
jwt.expiration=86400000

# Cấu hình Mail Service (Gmail App Password)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Cấu hình cổng thanh toán VNPAY Sandbox
vnpay.tmnCode=YOUR_TMN_CODE
vnpay.hashSecret=YOUR_HASH_SECRET
vnpay.payUrl=https://sandbox.vnpayment.vn/paymentv2/vpcpay.html
vnpay.returnUrl=http://localhost:8080/api/v1/payment/vnpay-return
```

### Bước 3: Khởi chạy Backend Server

Sử dụng Maven Wrapper để biên dịch và khởi chạy máy chủ:

```powershell
# Trên Windows
./mvnw.cmd clean spring-boot:run

# Trên Linux / macOS
./mvnw clean spring-boot:run
```

Khi ứng dụng khởi động thành công, dịch vụ REST API sẽ phục vụ tại địa chỉ: `http://localhost:8080`

### Bước 4: Khởi chạy Frontend Client

Bạn có thể chạy trực tiếp Frontend bằng tiện ích **Live Server** trên VS Code hoặc một máy chủ web tĩnh (Nginx / Python HTTP Server):

```powershell
# Chạy nhanh qua Python HTTP Server
cd src/flashcard-frontend
python -m http.server 3000
```

Mở trình duyệt và truy cập: `http://localhost:3000`

---

### Cách khác: Khởi chạy nhanh bằng Docker Compose

Nếu bạn đã cài đặt sẵn **Docker** và **Docker Compose**, chỉ cần thực hiện một lệnh duy nhất:

```bash
docker-compose up -d --build
```
Hệ thống sẽ tự động khởi tạo dịch vụ Database, Backend API và máy chủ Nginx tĩnh phục vụ toàn bộ ứng dụng.

## CÁC API ENDPOINTS CHÍNH

Dưới đây là tổng hợp một số endpoints quan trọng được cung cấp bởi hệ thống:

| Nhóm chức năng | Phương thức | Endpoint | Mô tả |
|---|---|---|---|
| **Auth** | `POST` | `/api/v1/auth/register` | Đăng ký tài khoản người dùng mới |
| | `POST` | `/api/v1/auth/login` | Xác thực đăng nhập, trả về Access Token (JWT) |
| | `POST` | `/api/v1/auth/forgot-password` | Gửi mã OTP xác nhận về Email |
| **StudySet** | `GET` | `/api/v1/studysets` | Lấy danh sách các bộ học phần công khai |
| | `POST` | `/api/v1/studysets` | Tạo mới bộ học phần kèm danh sách thẻ |
| | `GET` | `/api/v1/studysets/{id}` | Lấy chi tiết học phần và danh sách Flashcards |
| **Folder** | `GET` | `/api/v1/folders` | Lấy danh sách thư mục của người dùng |
| | `POST` | `/api/v1/folders/{id}/add-studyset` | Gán bộ học phần vào thư mục lưu trữ |
| **Class** | `POST` | `/api/v1/classes` | Tạo lớp học mới |
| | `POST` | `/api/v1/classes/join` | Tham gia lớp học thông qua mã mời (Code) |
| **Quiz & Test** | `GET` | `/api/v1/tests/by-studyset/{id}` | Lấy bộ câu hỏi kiểm tra cho học phần |
| | `POST` | `/api/v1/tests/submit` | Nộp bài làm, tính toán điểm và trả về kết quả |
| **VNPAY** | `POST` | `/api/v1/payment/create-vnpay` | Tạo đường dẫn thanh toán gói VIP qua VNPAY |
| | `GET` | `/api/v1/payment/vnpay-return` | Xử lý phản hồi thanh toán (IPN & URL return) |

## KẾT QUẢ VÀ HƯỚNG PHÁT TRIỂN

### 1. Kết quả đạt được
- Xây dựng thành công hệ thống học tập Flashcard hoàn chỉnh với đầy đủ các nghiệp vụ quản lý thẻ nhớ, lớp học và thư mục.
- Kiểm soát bảo mật tốt nhờ mô hình Stateless JWT kết hợp kiểm tra quyền truy cập tài nguyên.
- Tích hợp thành công chức năng thanh toán tự động qua VNPAY và cơ chế gửi email xác nhận.

### 2. Hướng phát triển tương lai
- **Thuật toán lặp lại ngắt quãng (Spaced Repetition)**: Ứng dụng thuật toán SuperMemo (SM-2) để tối ưu lịch ôn tập từng từ theo năng lực ghi nhớ của người học.
- **Ứng dụng di động**: Xây dựng phiên bản Mobile App (React Native / Flutter) đồng bộ dữ liệu với Backend.
- **Trí tuệ nhân tạo (AI Assistant)**: Tự động trích xuất từ vựng, tóm tắt và sinh flashcards tự động từ tài liệu PDF bằng LLM API.
