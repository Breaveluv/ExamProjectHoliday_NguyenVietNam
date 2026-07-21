# TECHNICAL SPEC - Football Club Management System

## 1. Project Charter

- **A1**: PROJECT CHARTER
- **A2**: Tên dự án:
- **B2**: Football Club Management System (Hệ thống Quản lý Đội bóng và Cầu thủ)
- **A3**: Đơn vị yêu cầu:
- **B3**: Giảng viên hướng dẫn – Học phần Lập trình Web nâng cao (Spring Boot)
- **A4**: Ban chỉ đạo dự án:
- **B4**: Giảng viên hướng dẫn: ThS. Trần Quốc Bảo (giả định do SRS không nêu tên cụ thể)
- **A5**: Giám đốc dự án:
- **B5**: Nguyễn Việt Nam
- **A6**: Quản trị dự án:
- **B6**: Nguyễn Việt Nam (kiêm Business Analyst & Technical Leader – dự án 01 lập trình viên)
- **A7**: Đơn vị triển khai:
- **B7**: Sinh viên thực hiện – Nguyễn Việt Nam (01 lập trình viên)
- **A8**: Ngày bắt đầu:
- **B8**: 06/07/2026
- **D8**: Ngày kết thúc:
- **E8**: 18/07/2026
- **A9**: Mục tiêu dự án cần đạt được:
- **B9**: Xây dựng thành công hệ thống Football Club Management System phục vụ quản lý đội bóng và cầu thủ:
- Quản trị hệ thống: Xác thực JWT, phân quyền tài khoản ADMIN/USER, xử lý lỗi tập trung và bảo mật hệ thống (Spring Security, BCrypt).
- Quản lý chuyên môn: Quản lý đội bóng, hồ sơ cầu thủ, huấn luyện viên, lịch thi đấu - kết quả, thống kê thi đấu (bàn thắng, kiến tạo, thẻ phạt) và xử lý nghiệp vụ chuyển nhượng cầu thủ giữa các đội.
Mục tiêu nghiệp vụ: Hoàn thiện đồ án môn học Spring Boot đúng tiến độ 10 ngày, đáp ứng đầy đủ 09 chức năng (FN01-FN09) theo tài liệu SRS FCMS-SRS-2026-01.
- **A10**: Phạm vi dự án:
- **B10**: Triển khai tại: Sinh viên thực hiện (01 lập trình viên), giám sát bởi Giảng viên hướng dẫn.
Phạm vi trong dự án: Đăng ký/Đăng nhập (JWT), Quản lý đội bóng, Quản lý cầu thủ, Quản lý huấn luyện viên, Quản lý trận đấu (lịch, sân, kết quả), Thống kê thi đấu, Chuyển nhượng cầu thủ, Dashboard tổng quan, Phân quyền & Quản trị tài khoản.
- **D10**: Phạm vi ngoài dự án:
- **E10**: Không bao gồm: thanh toán trực tuyến, tích hợp bên thứ ba (email/SMS/notification/bản đồ), ứng dụng di động (mobile app) và các nghiệp vụ nâng cao khác ngoài SRS.
- **A11**: Kinh phí triển khai:
- **B11**: Không phát sinh chi phí (dự án môn học, không tính chi phí nhân sự/hạ tầng) (giả định)
- **D11**: Đơn vị triển khai:
- **E11**: Sinh viên thực hiện – Nguyễn Việt Nam
- **A13**: Kết quả/sản phẩm cần có:
- **B13**: Kết quả/sản phẩm cần có:
- Mã nguồn Backend (Java 17, Spring Boot 3.x, Gradle) và Frontend (ReactJS, Bootstrap 5) hoàn chỉnh, đóng gói trên GitHub.
- Cơ sở dữ liệu MySQL với ERD, DDL, ràng buộc dữ liệu đầy đủ.
- Bộ tài liệu: SRS, Technical Document, API Document, Database Document, User Guide.
Tiêu chí nghiệm thu: Hệ thống chạy ổn định, đầy đủ 09 chức năng FN01-FN09, không lỗi nghiêm trọng, bàn giao đúng hạn 18/07/2026.
Tiêu chí thành công: Giảng viên hướng dẫn phê duyệt nghiệm thu; hệ thống đáp ứng yêu cầu phi chức năng (thời gian phản hồi < 2 giây, bảo mật JWT/HTTPS).
Rủi ro: Thời gian triển khai ngắn (10 ngày) với 01 lập trình viên có thể ảnh hưởng tiến độ và chất lượng kiểm thử.
Biện pháp giảm thiểu: Ưu tiên các chức năng lõi (Authentication, Team/Player/Match Management) trước, giới hạn phạm vi theo đúng SRS, kiểm thử song song trong quá trình phát triển.

## 2. WBS (Work Breakdown Structure) - sheet Price

| STT | Nội dung công việc | Ghi chú | Thời gian (ngày) | Effort (manday) | Comment |
|---|---|---|---|---|---|
| I | I. PHÂN TÍCH YÊU CẦU & THIẾT KẾ HỆ THỐNG |  |  |  |  |
| 1 | Lập kế hoạch dự án | Lập kế hoạch triển khai 10 ngày theo SDLC, phân chia công việc | 0.3 | 0.3 |  |
| =A12+1 | Phân tích yêu cầu nghiệp vụ (Requirement Analysis) | Xác định tác nhân ADMIN/USER, đặc tả 09 Use Case (UC-01..UC-09) theo SRS | 0.5 | 0.5 |  |
| =A13+1 | Thiết kế kiến trúc hệ thống (System Design) | Thiết kế Layer Architecture, luồng JWT, RESTful API giữa Backend - Frontend - MySQL | 0.4 | 0.4 |  |
| =A14+1 | Thiết kế cơ sở dữ liệu (Database Design) | Thiết kế ERD, DDL, quan hệ giữa User, Role, Team, Coach, Player, Match, Statistic, Transfer | 0.5 | 0.5 |  |
| =A15+1 | Thiết kế giao diện (UI/UX Design) | Wireframe cho Login, Dashboard, Team, Player, Coach, Match, Statistic, Transfer (dự kiến 12 màn hình) | 0.3 | 0.3 |  |
| II | II. PHÁT TRIỂN VÀ XÂY DỰNG (BACKEND & FRONTEND) |  |  |  |  |
| 1 | 1. Backend Development – Quản trị & Bảo mật hệ thống |  |  |  |  |
| 1.1 | Authentication |  | 0.5 | 0.5 |  |
|  |  + Đăng ký / Đăng nhập / Đăng xuất, sinh và xác thực JWT (Spring Security) |  |  |  |  |
| 1.2 | Dashboard |  | 0.5 | 0.5 |  |
|  |  + Thống kê tổng quan: tổng số đội, tổng số cầu thủ, trận sắp diễn ra |  |  |  |  |
|  |  + Vua phá lưới (top scorer), API cung cấp dữ liệu dashboard |  |  |  |  |
| 1.3 | Role Management & User Management |  | 0.5 | 0.5 |  |
|  |  + CRUD vai trò (Role: ADMIN/USER), gán quyền |  |  |  |  |
|  |  + CRUD tài khoản người dùng (User) |  |  |  |  |
| 1.4 | Security, Validation chung |  | 0.5 | 0.5 |  |
|  |  + Cấu hình Spring Security, mã hoá mật khẩu bằng BCrypt |  |  |  |  |
|  |  + Validation dữ liệu đầu vào (Bean Validation / Hibernate Validator) |  |  |  |  |
| 1.5 | Exception Handling & REST API chuẩn hoá |  | 0.4 | 0.4 |  |
|  |  + GlobalExceptionHandler, chuẩn hoá cấu trúc response REST API |  |  |  |  |
| 2 | 2. Backend Development – Nghiệp vụ Đội bóng, Cầu thủ, Trận đấu |  |  |  |  |
| 2.1 | Team Management |  | 0.5 | 0.5 |  |
|  |  + CRUD đội bóng (thêm/sửa/xoá/xem chi tiết) |  |  |  |  |
|  |  + Liên kết Coach phụ trách đội, danh sách Player thuộc đội |  |  |  |  |
| 2.2 | Player Management |  | 0.5 | 0.5 |  |
|  |  + CRUD cầu thủ | Xem toàn bộ thông tin của tài khoản đang đăng nhập |  |  |  |
|  |  + Quản lý trạng thái cầu thủ: New / Active / Transferred / Retired | Cập nhật một số thông tin tài khoản (trừ thông tin đặc biệt: mã, quyền, …) |  |  |  |
|  |  + Liên kết Team, vị trí thi đấu cầu thủ | Đổi mật khẩu |  |  |  |
| 2.3 | Coach Management & Match Management |  | 0.6 | 0.6 |  |
|  |  + CRUD huấn luyện viên (Coach) |  |  |  |  |
|  |  + CRUD trận đấu: lịch thi đấu, sân vận động |  |  |  |  |
|  |  + Cập nhật kết quả, tỷ số, trạng thái trận đấu (Scheduled/Ongoing/Finished/Postponed/Cancelled) |  |  |  |  |
| 2.4 | Statistics Management & Transfer Management |  | 0.6 | 0.6 |  |
|   |  + Thống kê bàn thắng, kiến tạo, thẻ vàng/đỏ, số phút thi đấu |  |  |  |  |
|  |  + Mua/Bán cầu thủ (Transfer), cập nhật Team mới cho cầu thủ |  |  |  |  |
|  |  + Lịch sử chuyển nhượng cầu thủ |  |  |  |  |
| 3 | 3. Frontend Development (ReactJS + Bootstrap 5) |  |  |  |  |
| 3.1 | Login/Register UI & Dashboard UI | Màn hình đăng nhập/đăng ký, dashboard tổng quan theo vai trò | 0.5 | 0.5 |  |
| 3.2 | Team UI & Player UI | Danh sách, thêm, sửa, xoá, xem chi tiết đội bóng và cầu thủ | 0.6 | 0.6 |  |
| 3.3 | Coach UI & Match UI | Quản lý huấn luyện viên, lịch thi đấu, cập nhật kết quả | 0.6 | 0.6 |  |
| 3.4 | Statistic UI & Transfer UI | Thống kê thi đấu, chuyển nhượng cầu thủ, giao diện Responsive (Bootstrap 5) | 0.5 | 0.5 |  |
| III | III. KIỂM THỬ HỆ THỐNG (TESTING) |  |  |  |  |
| 1 | Unit Test | Viết Unit Test cho Service/Repository (JUnit) | 0.4 | 0.4 |  |
| 2 | Integration Test | Kiểm thử tích hợp REST API bằng Postman | 0.3 | 0.3 |  |
| 3 | Manual Test | Kiểm thử thủ công toàn bộ luồng nghiệp vụ trên giao diện | 0.4 | 0.4 |  |
| 4 | Bug Fix & Regression Test | Sửa lỗi phát sinh và kiểm thử hồi quy | 0.4 | 0.4 |  |
| IV | IV. TRIỂN KHAI HỆ THỐNG (DEPLOYMENT) |  |  |  |  |
| 1 | Build & Cấu hình môi trường | Build ứng dụng Spring Boot (Gradle), cấu hình MySQL, biến môi trường | 0.3 | 0.3 |  |
| 2 | Golive & Bàn giao hệ thống | Triển khai hệ thống, bàn giao cho giảng viên hướng dẫn | 0.2 | 0.2 |  |
| V | V. TÀI LIỆU DỰ ÁN (DOCUMENTATION) |  |  |  |  |
| 1 | SRS & Technical Document | Hoàn thiện tài liệu đặc tả nghiệp vụ và tài liệu kỹ thuật | 0.3 | 0.3 |  |
| 2 | API Document & Database Document | Tài liệu mô tả REST API và thiết kế CSDL (ERD/DDL) | 0.3 | 0.3 |  |
| 3 | User Guide | Hướng dẫn sử dụng hệ thống cho ADMIN/USER | 0.2 | 0.2 |  |
|  | TỔNG  |  | =SUM(D12:D64) | =SUM(E12:E64) |  |

## 3. Master Plan (Gantt) - sheet Master Plan

| STT | Công việc | Phụ trách | Phối hợp | Tiến độ | Kế hoạch bắt đầu | Kế hoạch kết thúc |
|---|---|---|---|---|---|---|
| 9 | I. Khởi tạo & Phân tích yêu cầu (Requirement Analysis) |  |  | 0 |  |  |
| 10 | Khởi động dự án, xác nhận phạm vi với giảng viên hướng dẫn | Nguyễn Việt Nam | Giảng viên hướng dẫn | 0 | 06/07/2026 | 06/07/2026 |
| 11 | Khảo sát, tổng hợp yêu cầu nghiệp vụ (Use Case, tác nhân ADMIN/USER) | Nguyễn Việt Nam | Giảng viên hướng dẫn | 0 | 06/07/2026 | 06/07/2026 |
| 12 | Thống nhất phạm vi & phương án triển khai (Spring Boot, ReactJS, MySQL) | Nguyễn Việt Nam | Giảng viên hướng dẫn | 0 | 06/07/2026 | 06/07/2026 |
| 13 | Hoàn thiện tài liệu SRS (FCMS-SRS-2026-01) & trình phê duyệt | Nguyễn Việt Nam | Giảng viên hướng dẫn | 0 | 06/07/2026 | 06/07/2026 |
| 14 | Kick-off phát triển hệ thống | Nguyễn Việt Nam | Giảng viên hướng dẫn | 0 | 06/07/2026 | 06/07/2026 |
| 15 | II. Thiết kế hệ thống (System & Database Design) |  |  |  |  |  |
| 16 | Thiết kế kiến trúc hệ thống (Layer Architecture, RESTful API) | Nguyễn Việt Nam |  | 0 | 07/07/2026 | 07/07/2026 |
| 17 | Thiết kế luồng xử lý JWT, phân quyền ADMIN/USER | Nguyễn Việt Nam |  | 0 | 07/07/2026 | 07/07/2026 |
| 18 | Thiết kế cơ sở dữ liệu: ERD, quan hệ User-Role-Team-Coach-Player-Match-Statistic-Transfer | Nguyễn Việt Nam |  | 0 | 08/07/2026 | 08/07/2026 |
| 19 | Thiết kế DDL, ràng buộc dữ liệu (PK/FK/Constraint) cho MySQL | Nguyễn Việt Nam |  | 0 | 08/07/2026 | 08/07/2026 |
| 20 | Thiết kế giao diện (UI/UX wireframe) các màn hình chính | Nguyễn Việt Nam |  | 0 | 08/07/2026 | 08/07/2026 |
| 21 | III. Phát triển, kiểm thử hệ thống (Backend, Frontend & Testing) |  |  |  |  |  |
| 22 | Backend Development – Quản trị & Bảo mật hệ thống | Nguyễn Việt Nam |  | 0 | 09/07/2026 | 10/07/2026 |
| 23 | Authentication (JWT, Spring Security) | Nguyễn Việt Nam |  | 0 | 09/07/2026 | 09/07/2026 |
| 24 | Đăng ký / Đăng nhập / Đăng xuất, sinh & xác thực JWT | Nguyễn Việt Nam |  | 0 | 09/07/2026 | 09/07/2026 |
| 25 | Dashboard tổng quan | Nguyễn Việt Nam |  | 0 | 09/07/2026 | 09/07/2026 |
| 26 | Thống kê tổng số đội, tổng số cầu thủ, trận sắp diễn ra, vua phá lưới | Nguyễn Việt Nam |  | 0 | 09/07/2026 | 09/07/2026 |
| 27 | Đổi mật khẩu, xem thông tin tài khoản | Nguyễn Việt Nam |  | 0 | 09/07/2026 | 09/07/2026 |
| 28 | Role Management & User Management | Nguyễn Việt Nam |  | 0 | 10/07/2026 | 10/07/2026 |
| 29 | CRUD vai trò (Role) và tài khoản người dùng (User) | Nguyễn Việt Nam |  | 0 | 10/07/2026 | 10/07/2026 |
| 30 | Đổi mật khẩu | Nguyễn Việt Nam |  | 0 | 10/07/2026 | 10/07/2026 |
| 31 | Security & Validation & Exception Handling | Nguyễn Việt Nam |  | 0 | 10/07/2026 | 10/07/2026 |
| 32 | Backend Development – Nghiệp vụ Đội bóng, Cầu thủ, Huấn luyện viên | Nguyễn Việt Nam |  | 0 | 10/07/2026 | 13/07/2026 |
| 33 | Team Management: CRUD đội bóng | Nguyễn Việt Nam |  | 0 | 10/07/2026 | 10/07/2026 |
| 34 | Đăng nhập/đăng xuất module đội bóng (kiểm thử phân quyền) | Nguyễn Việt Nam |  | 0 | 10/07/2026 | 10/07/2026 |
| 35 | Liên kết Coach phụ trách, danh sách Player thuộc đội | Nguyễn Việt Nam |  | 0 | 10/07/2026 | 10/07/2026 |
| 36 | Player Management: CRUD cầu thủ | Nguyễn Việt Nam |  | 0 | 13/07/2026 | 13/07/2026 |
| 37 | Quản lý trạng thái cầu thủ (New/Active/Transferred/Retired) | Nguyễn Việt Nam |  | 0 | 13/07/2026 | 13/07/2026 |
| 38 | Liên kết Team, vị trí thi đấu cầu thủ | Nguyễn Việt Nam |  | 0 | 13/07/2026 | 13/07/2026 |
| 39 | Coach Management: CRUD huấn luyện viên | Nguyễn Việt Nam |  | 0 | 13/07/2026 | 13/07/2026 |
| 40 | Match Management: CRUD trận đấu, lịch thi đấu, sân vận động | Nguyễn Việt Nam |  | 0 | 13/07/2026 | 13/07/2026 |
| 41 | Cập nhật kết quả, tỷ số, trạng thái trận đấu | Nguyễn Việt Nam |  | 0 | 13/07/2026 | 13/07/2026 |
| 42 | Statistics Management: thống kê bàn thắng, kiến tạo, thẻ vàng/đỏ | Nguyễn Việt Nam |  | 0 | 13/07/2026 | 13/07/2026 |
| 43 | Transfer Management: Mua/Bán cầu thủ, lịch sử chuyển nhượng | Nguyễn Việt Nam |  | 0 | 13/07/2026 | 13/07/2026 |
| 44 | Hoàn thiện REST API & Exception Handling toàn hệ thống | Nguyễn Việt Nam |  | 0 | 13/07/2026 | 13/07/2026 |
| 45 | Chuẩn hoá response API, GlobalExceptionHandler | Nguyễn Việt Nam |  | 0 | 13/07/2026 | 13/07/2026 |
| 46 | Kiểm tra CRUD Team/Player qua Postman | Nguyễn Việt Nam |  | 0 | 13/07/2026 | 13/07/2026 |
| 47 | Rà soát validation dữ liệu toàn hệ thống | Nguyễn Việt Nam |  | 0 | 13/07/2026 | 13/07/2026 |
| 48 | Frontend Development (ReactJS + Bootstrap 5) | Nguyễn Việt Nam |  | 0 | 14/07/2026 | 15/07/2026 |
| 49 | Login/Register UI & Dashboard UI | Nguyễn Việt Nam |  | 0 | 14/07/2026 | 14/07/2026 |
| 50 | Team UI & Player UI (danh sách, thêm, sửa, xoá, chi tiết) | Nguyễn Việt Nam |  | 0 | 14/07/2026 | 14/07/2026 |
| 51 | Coach UI & Match UI (lịch thi đấu, kết quả) | Nguyễn Việt Nam |  | 0 | 15/07/2026 | 15/07/2026 |
| 52 | Statistic UI & Transfer UI, Responsive UI | Nguyễn Việt Nam |  | 0 | 15/07/2026 | 15/07/2026 |
| 53 | Kiểm thử hệ thống (Testing) | Nguyễn Việt Nam |  | 0 | 16/07/2026 | 16/07/2026 |
| 54 | Unit Test & Integration Test (JUnit, Postman) | Nguyễn Việt Nam |  | 0 | 16/07/2026 | 16/07/2026 |
| 55 | Manual Test toàn bộ luồng nghiệp vụ (UAT nội bộ) | Nguyễn Việt Nam |  | 0 | 16/07/2026 | 16/07/2026 |
| 56 | IV. Triển khai hệ thống (Deployment) |  |  |  |  |  |
| 57 | Chuẩn bị dữ liệu mẫu (master data): Role, User, đội bóng, cầu thủ mẫu | Nguyễn Việt Nam |  | 0 | 17/07/2026 | 17/07/2026 |
| 58 | Triển khai & cấu hình hệ thống Backend/Frontend/MySQL | Nguyễn Việt Nam |  | 0 | 17/07/2026 | 17/07/2026 |
| 59 | Cài đặt hệ thống (build Gradle, cấu hình môi trường) | Nguyễn Việt Nam |  | 0 | 17/07/2026 | 17/07/2026 |
| 60 | Khai báo dữ liệu (master data) | Nguyễn Việt Nam |  | 0 | 17/07/2026 | 17/07/2026 |
| 61 | Bug Fix sau kiểm thử | Nguyễn Việt Nam |  | 0 | 17/07/2026 | 17/07/2026 |
| 62 | Kiểm thử UAT trước khi bàn giao | Nguyễn Việt Nam | Giảng viên hướng dẫn | 0 | 17/07/2026 | 17/07/2026 |
| 63 | Hoàn thiện tài liệu dự án (Documentation) | Nguyễn Việt Nam |  | 0 | 17/07/2026 | 17/07/2026 |
| 64 | Soạn Technical Document | Nguyễn Việt Nam |  | 0 | 17/07/2026 | 17/07/2026 |
| 65 | Soạn API Document & Database Document | Nguyễn Việt Nam |  | 0 | 17/07/2026 | 17/07/2026 |
| 66 | Soạn User Guide | Nguyễn Việt Nam |  | 0 | 17/07/2026 | 17/07/2026 |
| 67 | Golive (môi trường local) | Nguyễn Việt Nam | Giảng viên hướng dẫn | 0 | 17/07/2026 | 17/07/2026 |
| 68 | Bug Fix & hỗ trợ sau bàn giao | Nguyễn Việt Nam | Giảng viên hướng dẫn | 0 | 17/07/2026 | 18/07/2026 |
| 69 | V. Nghiệm thu dự án (Acceptance) |  |  |  |  |  |
| 70 | Hoàn thành bàn giao tài liệu vận hành (SRS, Technical, API, DB, User Guide) | Nguyễn Việt Nam | Giảng viên hướng dẫn | 0 | 18/07/2026 | 18/07/2026 |
| 71 | Nghiệm thu & hoàn tất đóng dự án | Nguyễn Việt Nam | Giảng viên hướng dẫn | 0 | 18/07/2026 | 18/07/2026 |

## 4. Project Members

- Row1: DANH SÁCH NHÂN SỰ THAM GIA DỰ ÁN |  |  |  |  |  |  | 
- Row2: STT | Họ Tên | Đơn vị/Ban | Chức Vụ | Vai trò trong dự án | Điện thoại | Email | Ghi chú
- Row3: Nhóm thực hiện dự án (Sinh viên) |  |  |  |  |  |  | 
- Row4: 1 | Nguyễn Việt Nam | Sinh viên thực hiện | Project Manager | Lập kế hoạch, quản lý tiến độ dự án | 0901234567 | nguyenvietnam@student.hnue.edu.vn | Kiêm nhiệm PM
- Row5: 2 | Nguyễn Việt Nam | Sinh viên thực hiện | Business Analyst | Phân tích yêu cầu, viết SRS, đặc tả Use Case | 0901234567 | nguyenvietnam@student.hnue.edu.vn | Kiêm nhiệm BA
- Row6: 3 | Nguyễn Việt Nam | Sinh viên thực hiện | Technical Leader / Backend Developer | Thiết kế kiến trúc, lập trình Backend (Spring Boot, MySQL) | 0901234567 | nguyenvietnam@student.hnue.edu.vn | Kiêm nhiệm Backend
- Row7: 4 | Nguyễn Việt Nam | Sinh viên thực hiện | Frontend Developer | Lập trình giao diện (ReactJS, Bootstrap 5) | 0901234567 | nguyenvietnam@student.hnue.edu.vn | Kiêm nhiệm Frontend
- Row8: 5 | Nguyễn Việt Nam | Sinh viên thực hiện | Tester | Kiểm thử hệ thống (Unit Test, Integration Test, Manual Test) | 0901234567 | nguyenvietnam@student.hnue.edu.vn | Kiêm nhiệm Tester
- Row11: Đơn vị liên quan (Giám sát / Phê duyệt) |  |  |  |  |  |  | 
- Row12: 1 | ThS. Trần Quốc Bảo (giả định) | Giảng viên hướng dẫn | Giảng viên | Phê duyệt SRS, nghiệm thu dự án |  | giangvien@hnue.edu.vn | Người phê duyệt (theo SRS)
- Row13: 2 | Nguyễn Văn A | Giảng viên hướng dẫn / Bạn học | QA / Reviewer | Review tài liệu SRS, kiểm tra chất lượng |  | nguyenvana@student.hnue.edu.vn | QA theo tài liệu SRS