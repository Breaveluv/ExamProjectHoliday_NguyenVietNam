**TÀI LIỆU ĐẶC TẢ NGHIỆP VỤ**

**Quản lý đội bóng và cầu thủ**

**DỰ ÁN : FOOTBALL CLUB MANAGEMENT SYSTEM**

> **Người thực hiện : Nguyễn Việt Nam**
>
> **Ngày ban hành : 07/07/2026**
>
> **Mã tài liệu : FCMS-SRS-2026-01**
>
> **Phiên bản : 1.0**

  -----------------------------------------------------------------------
  **Trạng thái tài     **[DRAFT]{.smallcaps}**
  liệu**               
  -------------------- --------------------------------------------------
  **Người viết**       Nguyễn Việt Nam

  **Người review**     Nguyễn Việt Nam

  **Người phê duyệt**  Giảng viên hướng dẫn

  **QA**               Nguyễn Văn A

  **Phiên bản**        1.0

  **Ngày phát hành**   07/07/2026
  -----------------------------------------------------------------------

**KIỂM SOÁT TÀI LIỆU**

**Thông tin kiểm soát**

+-------------+-----------------+-----------------+-------------------+
| **Ngày**    | **Người lập**   | **Người kiểm    | **Người phê       |
|             |                 | tra**           | duyệt**           |
|             |                 |                 |                   |
|             |                 | **/ Kết quả**   | **/ Kết quả**     |
+-------------+-----------------+-----------------+-------------------+
| **0         | **Nguyễn Văn    | Đạt             | Đạt               |
| 7/07/2026** | A**             |                 |                   |
+-------------+-----------------+-----------------+-------------------+
|             |                 |                 |                   |
+-------------+-----------------+-----------------+-------------------+

**Thông tin lịch sử**

  ------------- ----------------- ---------- ----------------------------
  **Ngày**      **Người thực      **Phiên    **Nội dung**
                hiện**            bản**      

  07/07/2026    Nguyễn Văn A      1.0        Khởi tạo tài liệu đặc tả
                                             nghiệp vụ cho dự án Football
                                             Club Management System

                                             
  ------------- ----------------- ---------- ----------------------------

**Tài liệu liên quan, tham khảo**

  ------------- ---------------------------- ----------------------------
  **Ngày**      **Tên tài liệu**             **Nguồn**

  07/07/2026    Đề bài môn học Spring Boot - Giảng viên hướng dẫn
                Football Club Management     
                System                       

                                             
  ------------- ---------------------------- ----------------------------

MỤC LỤC

#  {#section .unnumbered}

[MỤC LỤC 4](#_heading=h.ay6puas5t18e)

[PHẦN 1: GIỚI THIỆU 5](#phần-1-giới-thiệu)

> [1.1 MỤC ĐÍCH TÀI LIỆU 5](#mục-đích-tài-liệu)
>
> [1.2 PHẠM VI TÀI LIỆU 5](#phạm-vi-tài-liệu)
>
> [1.3 TỔNG QUAN ỨNG DỤNG 5](#tổng-quan-ứng-dụng)
>
> [1.4 THUẬT NGỮ VIẾT TẮT 5](#thuật-ngữ-viết-tắt)

[PHẦN 2: YÊU CẦU TỔNG THỂ 6](#phần-2-yêu-cầu-tổng-thể)

> [2.1 SƠ ĐỒ QUAN HỆ ĐỐI TƯỢNG 6](#sơ-đồ-quan-hệ-đối-tượng)
>
> [2.2 SƠ ĐỒ USE CASE 6](#sơ-đồ-use-case)
>
> [2.3 SƠ ĐỒ LUỒNG 6](#sơ-đồ-luồng)
>
> [2.4. SƠ ĐỒ CHUYỂN TRẠNG THÁI 6](#sơ-đồ-chuyển-trạng-thái)
>
> [2.5. PHÂN QUYỀN 6](#phân-quyền)
>
> [2.5.1. PHÂN QUYỀN CHỨC NĂNG 6](#phân-quyền-chức-năng)
>
> [2.5.2. PHÂN QUYỀN DỮ LIỆU 6](#phân-quyền-dữ-liệu)
>
> [2.6. SITE MAP 6](#site-map)

[PHẦN 3: CHỨC NĂNG 7](#phần-3-chức-năng)

> [3.1. \<TÊN CHỨC NĂNG\> 7](#đăng-nhập)
>
> [3.1.1. ĐẶC TẢ USE CASE 7](#đặc-tả-use-case)
>
> [3.1.2. SƠ ĐỒ LUỒNG CHI TIẾT 8](#sơ-đồ-luồng-chi-tiết)
>
> [3.1.3. GIAO DIỆN 8](#giao-diện)
>
> [3.1.4. MÔ TẢ CHI TIẾT 8](#mô-tả-chi-tiết)

[PHẦN 4: CÁC COMPONENT, THÔNG BÁO, CẢNH BÁO
9](#phần-4-các-component-thông-báo-cảnh-báo)

[PHẦN 5: LINK ISSUE 10](#phần-5-link-issue)

#  {#section-1 .unnumbered}

# PHẦN 1: GIỚI THIỆU {#phần-1-giới-thiệu .unnumbered}

##  {#section-2 .unnumbered}

## 1.1 Mục đích tài liệu {#mục-đích-tài-liệu .unnumbered}

> Tài liệu này mô tả chi tiết các yêu cầu nghiệp vụ và chức năng của hệ
> thống Football Club Management System (Hệ thống quản lý đội bóng và
> cầu thủ). Tài liệu nhằm thống nhất cách hiểu về phạm vi, nghiệp vụ và
> các chức năng cần xây dựng giữa lập trình viên, giảng viên hướng dẫn
> và các bên liên quan, làm cơ sở để thiết kế, phát triển và kiểm thử hệ
> thống.

## 1.2 Phạm vi tài liệu {#phạm-vi-tài-liệu .unnumbered}

Tài liệu áp dụng cho toàn bộ phạm vi của dự án môn học Football Club
Management System, bao gồm các nghiệp vụ: đăng ký/đăng nhập tài khoản,
quản lý đội bóng, quản lý cầu thủ, quản lý huấn luyện viên, quản lý trận
đấu, thống kê thi đấu và chuyển nhượng cầu thủ. Do dự án được thực hiện
trong 10 ngày bởi 01 lập trình viên, phạm vi được giới hạn ở mức vừa đủ
để hoàn thành đúng tiến độ, không bao gồm các nghiệp vụ nâng cao như
thanh toán trực tuyến, tích hợp bên thứ ba hay ứng dụng di động.

## 1.3 Tổng quan ứng dụng

Football Club Management System là ứng dụng web hỗ trợ quản lý đội bóng
và cầu thủ, cho phép người quản trị (ADMIN) tạo lập và theo dõi thông
tin đội bóng, cầu thủ, huấn luyện viên, lịch thi đấu, kết quả và số liệu
thống kê; người dùng (USER) có thể tra cứu các thông tin trên. Hệ thống
được xây dựng theo kiến trúc client-server: Backend sử dụng Spring Boot
3, Spring Security, Spring Data JPA, xác thực bằng JWT và cung cấp REST
API; Frontend sử dụng ReactJS (hoặc phương án thay thế Thymeleaf +
Bootstrap 5); dữ liệu được lưu trữ trên MySQL.

## 1.4 Thuật ngữ viết tắt {#thuật-ngữ-viết-tắt .unnumbered}

  --------- ----------------------- ----------------------------------------
  **STT**   **Từ viết tắt**         **Diễn giải**

  1         API                     Application Programming Interface

  2         JWT                     JSON Web Token

  3         ID                      Mã định danh (Identifier)

  4         CRUD                    Create, Read, Update, Delete

  5         ERD                     Entity Relationship Diagram

  6         STT                     Số thứ tự
  --------- ----------------------- ----------------------------------------

#  {#section-3 .unnumbered}

# PHẦN 2: YÊU CẦU TỔNG THỂ {#phần-2-yêu-cầu-tổng-thể .unnumbered}

## 2.1 Sơ đồ quan hệ đối tượng {#sơ-đồ-quan-hệ-đối-tượng .unnumbered}

Sơ đồ quan hệ đối tượng (ERD) của hệ thống gồm 7 thực thể chính: User,
Team, Coach, Player, Match, Statistic, Transfer. Quan hệ chính: một Team
có một Coach phụ trách và nhiều Player; một Player có nhiều bản ghi
Statistic (theo từng Match) và nhiều bản ghi Transfer; một Match diễn ra
giữa hai Team (đội nhà và đội khách).\
![](media/image1.png){width="6.0in" height="3.6314807524059494in"}

## 2.2 Sơ đồ Use Case {#sơ-đồ-use-case .unnumbered}

Sơ đồ Use Case mô tả tương tác giữa 2 tác nhân (ADMIN, USER) với các
nhóm chức năng chính: Xác thực, Quản lý đội bóng, Quản lý cầu thủ, Quản
lý huấn luyện viên, Quản lý trận đấu, Thống kê, Chuyển nhượng và
Dashboard. ADMIN có toàn quyền thực hiện mọi Use Case; USER chỉ được
thực hiện các Use Case xem/tra cứu thông tin.\
![](media/image2.png){width="6.0in" height="3.96876968503937in"}

## 2.3 Sơ đồ luồng {#sơ-đồ-luồng .unnumbered}

## Luồng xử lý tổng quát của hệ thống: Người dùng truy cập hệ thống → Đăng nhập → Hệ thống xác thực bằng JWT và phân quyền theo vai trò (ADMIN/USER) → Điều hướng đến Dashboard tương ứng → Người dùng chọn chức năng nghiệp vụ (Quản lý đội bóng/cầu thủ/HLV/trận đấu/thống kê/chuyển nhượng) → Hệ thống gọi REST API xử lý và trả kết quả → Hiển thị dữ liệu trên giao diện. {#luồng-xử-lý-tổng-quát-của-hệ-thống-người-dùng-truy-cập-hệ-thống-đăng-nhập-hệ-thống-xác-thực-bằng-jwt-và-phân-quyền-theo-vai-trò-adminuser-điều-hướng-đến-dashboard-tương-ứng-người-dùng-chọn-chức-năng-nghiệp-vụ-quản-lý-đội-bóngcầu-thủhlvtrận-đấuthống-kêchuyển-nhượng-hệ-thống-gọi-rest-api-xử-lý-và-trả-kết-quả-hiển-thị-dữ-liệu-trên-giao-diện. .unnumbered}

## 2.4. Sơ đồ chuyển trạng thái {#sơ-đồ-chuyển-trạng-thái .unnumbered}

## Trạng thái cầu thủ:  {#trạng-thái-cầu-thủ .unnumbered}

## Chưa ký hợp đồng (New) → Đang thi đấu (Active, thuộc 1 Team) → \[hết hạn hợp đồng / bị bán\] → Chuyển nhượng (Transferred, cập nhật Team mới) → Giải nghệ (Retired). Trạng thái trận đấu: Đã lên lịch (Scheduled) → Đang diễn ra (Ongoing) → Đã kết thúc (Finished); trước khi diễn ra có thể chuyển sang Hoãn (Postponed) hoặc Hủy (Cancelled). {#chưa-ký-hợp-đồng-new-đang-thi-đấu-active-thuộc-1-team-hết-hạn-hợp-đồng-bị-bán-chuyển-nhượng-transferred-cập-nhật-team-mới-giải-nghệ-retired.-trạng-thái-trận-đấu-đã-lên-lịch-scheduled-đang-diễn-ra-ongoing-đã-kết-thúc-finished-trước-khi-diễn-ra-có-thể-chuyển-sang-hoãn-postponed-hoặc-hủy-cancelled. .unnumbered}

## 2.5. Phân quyền {#phân-quyền .unnumbered}

### 2.5.1. Phân quyền chức năng {#phân-quyền-chức-năng .unnumbered}

(Xem chi tiết bảng phân quyền chức năng bên dưới, mục 2.5 --- cột ✔ thể
hiện quyền được phép thao tác.)

  -----------------------------------------------------------------------
  **Chức năng**           **ADMIN**               **USER**
  ----------------------- ----------------------- -----------------------
  Đăng ký / Đăng nhập     ✔                       ✔

  Quản lý đội bóng        ✔                       ---
  (Thêm/Sửa/Xóa)                                  

  Xem danh sách đội bóng  ✔                       ✔

  Quản lý cầu thủ         ✔                       ---
  (Thêm/Sửa/Xóa)                                  

  Xem thông tin cầu thủ   ✔                       ✔

  Quản lý huấn luyện viên ✔                       ---

  Quản lý trận đấu / Cập  ✔                       ---
  nhật kết quả                                    

  Xem lịch thi đấu & kết  ✔                       ✔
  quả                                             

  Xem thống kê thi đấu    ✔                       ✔

  Quản lý chuyển nhượng   ✔                       ---
  cầu thủ                                         
  -----------------------------------------------------------------------

### 2.5.2. Phân quyền dữ liệu {#phân-quyền-dữ-liệu .unnumbered}

## ADMIN được truy cập và thao tác trên toàn bộ dữ liệu của hệ thống (tất cả đội bóng, cầu thủ, huấn luyện viên, trận đấu, thống kê, giao dịch chuyển nhượng). USER chỉ được xem dữ liệu công khai (danh sách đội bóng, cầu thủ, lịch thi đấu, kết quả, thống kê) và không có quyền tạo mới, chỉnh sửa hay xóa dữ liệu. {#admin-được-truy-cập-và-thao-tác-trên-toàn-bộ-dữ-liệu-của-hệ-thống-tất-cả-đội-bóng-cầu-thủ-huấn-luyện-viên-trận-đấu-thống-kê-giao-dịch-chuyển-nhượng.-user-chỉ-được-xem-dữ-liệu-công-khai-danh-sách-đội-bóng-cầu-thủ-lịch-thi-đấu-kết-quả-thống-kê-và-không-có-quyền-tạo-mới-chỉnh-sửa-hay-xóa-dữ-liệu. .unnumbered}

## 2.6. Site Map {#site-map .unnumbered}

\- Trang đăng nhập / Đăng ký / Quên mật khẩu- Dashboard (Tổng số đội,
tổng số cầu thủ, trận sắp diễn ra, vua phá lưới)- Quản lý đội bóng (Danh
sách, Thêm, Sửa, Xóa, Xem chi tiết)- Quản lý cầu thủ (Danh sách, Thêm,
Sửa, Xóa, Xem chi tiết)- Quản lý huấn luyện viên (CRUD)- Quản lý trận
đấu (Tạo trận, Lịch thi đấu, Sân vận động, Kết quả, Tỷ số)- Thống kê
(Bàn thắng, Kiến tạo, Thẻ vàng, Thẻ đỏ, Số phút thi đấu)- Chuyển nhượng
(Mua cầu thủ, Bán cầu thủ, Lịch sử chuyển nhượng)

# PHẦN 3: CHỨC NĂNG {#phần-3-chức-năng .unnumbered}

*Danh sách chức năng và đặc tả chi tiết của hệ thống Football Club
Management System được trình bày trong bảng dưới đây:*

  ----------------------------------------------------------------------------
      **Mã**      **Tên chức năng**   **Phân hệ**     **Tác nhân (Actor)**
  --- ----------- ------------------- --------------- ------------------------
  1   FN01 - Đăng Authentication      Guest, ADMIN,   
      ký / Đăng                       USER            
      nhập / Quên                                     
      mật khẩu                                        

  2   FN02 - Quản Team Management     ADMIN           
      lý đội bóng                                     
      (CRUD)                                          

  3   FN03 - Quản Player Management   ADMIN           
      lý cầu thủ                                      
      (CRUD)                                          

  4   FN04 - Quản Coach Management    ADMIN           
      lý huấn                                         
      luyện viên                                      
      (CRUD)                                          

  5   FN05 - Quản Match Management    ADMIN           
      lý trận đấu                                     
      (Lịch, Sân,                                     
      Kết quả)                                        

  6   FN06 -      Statistics          ADMIN, USER     
      Thống kê                                        
      thi đấu                                         

  7   FN07 -      Transfer            ADMIN           
      Chuyển                                          
      nhượng cầu                                      
      thủ                                             

  8   FN08 -      Dashboard           ADMIN, USER     
      Dashboard                                       
      tổng quan                                       

  9   FN09 - Phân Administration      ADMIN           
      quyền &                                         
      Quản trị                                        
      tài khoản                                       
  ----------------------------------------------------------------------------

## 3.1. Đăng nhập {#đăng-nhập .unnumbered}

### 3.1.1. Đặc tả Use Case {#đặc-tả-use-case .unnumbered}

+---------------------+------------------------------------------------+
| Use Case ID         | *UC-01*                                        |
+---------------------+------------------------------------------------+
| Mô tả               | *Cho phép người dùng (ADMIN hoặc USER) đăng    |
|                     | nhập vào hệ thống Football Club Management     |
|                     | System bằng tài khoản đã đăng ký để sử dụng    |
|                     | các chức năng theo phân quyền.*                |
+---------------------+------------------------------------------------+
| Tác nhân (Actor(s)) | *ADMIN, USER*                                  |
+---------------------+------------------------------------------------+
| Sự ưu tiên          | *Cao*                                          |
| (Priority)          |                                                |
+---------------------+------------------------------------------------+
| Trigger             | *Người dùng truy cập trang Đăng nhập và nhấn   |
|                     | nút \'Đăng nhập\'*                             |
+---------------------+------------------------------------------------+
| Điều kiện cần       | *Người dùng đã có tài khoản hợp lệ (username,  |
|                     | password) trong hệ thống và tài khoản đang ở   |
| (Pre - Condition)   | trạng thái Active*                             |
+---------------------+------------------------------------------------+
| Điều kiện sau       | *Hệ thống cấp JWT access token và chuyển hướng |
|                     | người dùng vào Dashboard tương ứng với vai     |
| (Post -             | trò*                                           |
| Condition(s))       |                                                |
+---------------------+------------------------------------------------+
| Luồng cơ bản        | *1. Người dùng nhập Username/Email và          |
|                     | Password.\                                     |
| (Basic Flow)        | 2. Người dùng nhấn nút \'Đăng nhập\'.\         |
|                     | 3. Hệ thống kiểm tra thông tin đăng nhập với   |
|                     | dữ liệu trong bảng User.\                      |
|                     | 4. Nếu hợp lệ, hệ thống sinh JWT token và trả  |
|                     | về client.\                                    |
|                     | 5. Hệ thống điều hướng người dùng đến          |
|                     | Dashboard theo vai trò (ADMIN/USER).*          |
+---------------------+------------------------------------------------+
| Luồng thay thế      | *Người dùng chọn \'Quên mật khẩu\' → hệ thống  |
|                     | gửi email khôi phục kèm liên kết đặt lại mật   |
| (Alternative Flow ) | khẩu.*                                         |
+---------------------+------------------------------------------------+
| Luồng ngoại lệ      | *Nếu Username/Password không đúng, hệ thống    |
| (Exception Flow)    | hiển thị thông báo \'Sai tên đăng nhập hoặc    |
|                     | mật khẩu\'. Nếu tài khoản bị khóa, hệ thống    |
|                     | hiển thị thông báo \'Tài khoản đã bị khóa\'.*  |
+---------------------+------------------------------------------------+
| Ràng buộc           | *Mật khẩu phải được mã hóa (BCrypt) trước khi  |
|                     | lưu và so sánh. Token JWT có thời hạn hiệu lực |
| (Business Rules)    | (ví dụ 24 giờ).*                               |
+---------------------+------------------------------------------------+
| Yêu cầu phi chức    | *Thời gian phản hồi đăng nhập dưới 2 giây; dữ  |
| năng                | liệu đăng nhập truyền qua HTTPS; giới hạn số   |
|                     | lần đăng nhập sai để chống brute-force.*       |
| (Non-Functional     |                                                |
| Requirement)        |                                                |
+---------------------+------------------------------------------------+

### 3.1.2. Sơ đồ luồng chi tiết {#sơ-đồ-luồng-chi-tiết .unnumbered}

*Người dùng mở trang Đăng nhập → nhập Username/Email và Password → nhấn
nút \'Đăng nhập\' → hệ thống gọi API POST /auth/login → kiểm tra thông
tin trong bảng User → nếu hợp lệ, sinh JWT token và trả về client →
client lưu token và điều hướng vào Dashboard theo vai trò (ADMIN/USER);
nếu không hợp lệ, hiển thị thông báo lỗi trên cùng màn hình.*

### 3.1.3. Giao diện {#giao-diện .unnumbered}

*Màn hình Đăng nhập gồm: logo hệ thống, ô nhập Username/Email, ô nhập
Password (ẩn ký tự), nút \'Đăng nhập\', liên kết \'Quên mật khẩu?\' và
liên kết \'Đăng ký tài khoản mới\'. Khi đăng nhập thất bại, hệ thống
hiển thị thông báo lỗi màu đỏ ngay phía trên nút Đăng nhập.*

### 3.1.4. Mô tả chi tiết {#mô-tả-chi-tiết .unnumbered}

*Bảng dưới đây mô tả các trường thông tin trên màn hình Đăng nhập:*

  ---------------------------------------------------------------------------
  **Tên tiếng      **Tên tiếng      **Loại**   **Bắt buộc?** **Mô tả**
  Việt**           Anh**                                     
  ---------------- ---------------- ---------- ------------- ----------------
  Tên đăng nhập    username         String     Có            Tên đăng nhập
                                                             hoặc email của
                                                             người dùng, tối
                                                             đa 50 ký tự

  Mật khẩu         password         String     Có            Mật khẩu đăng
                                                             nhập, tối thiểu
                                                             6 ký tự, được mã
                                                             hóa bằng BCrypt
                                                             trước khi lưu
  ---------------------------------------------------------------------------

# PHẦN 4: CÁC COMPONENT, THÔNG BÁO, CẢNH BÁO {#phần-4-các-component-thông-báo-cảnh-báo .unnumbered}

*- Thông báo thành công (màu xanh lá): ví dụ \'Đăng nhập thành công\',
\'Lưu thông tin cầu thủ thành công\'*- Thông báo lỗi (màu đỏ): ví dụ
\'Sai tên đăng nhập hoặc mật khẩu\', \'Không thể xóa đội bóng đang có
cầu thủ\'- Hộp thoại xác nhận (Confirm dialog): ví dụ \'Bạn có chắc chắn
muốn xóa cầu thủ này?\'- Component phân trang (Pagination) cho các danh
sách: đội bóng, cầu thủ, trận đấu- Component tìm kiếm (Search box) và bộ
lọc (Filter) theo đội bóng, vị trí thi đấu- Loading spinner khi hệ thống
đang gọi API

# PHẦN 5: LINK ISSUE  {#phần-5-link-issue .unnumbered}

*Các yêu cầu và lỗi phát sinh trong quá trình phát triển được theo dõi
qua công cụ quản lý issue nội bộ của nhóm (ví dụ Jira/Trello của lớp
học); do phạm vi dự án môn học thực hiện trong 01 ngày, các issue phát
sinh (nếu có) sẽ được ghi nhận trực tiếp trong mục Ghi chú của báo cáo
nộp kèm.*
