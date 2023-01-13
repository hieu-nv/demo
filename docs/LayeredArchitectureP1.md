Kiến trúc phân tầng(hay còn được gọi là kiến trúc `n-tier`) là kiến trúc phổ biến nhất. Kiến trúc này được xem là chuẩn không chính thức cho các ứng dụng `Java EE` và do được biết đến rộng rãi bởi hầu hết kiến trúc sư, nhà thiết kế và nhà phát triển. Kiến trúc này quen thuộc với các cơ cấu tổ chức và truyền thông CNTT truyền thống, được tìm thấy ở hầu hết các công ty khiến nó trở thành một lựa chọn tự nhiên cho các công ty phát triển ứng dụng doanh nghiệp.

### Giới thiệu kiến trúc phân tầng

Các thành phần bên trong kiến trúc phân tầng được tổ chức thành các tầng nằm ngang, mỗi tầng thực hiện một vai trò cụ thể trong ứng dụng, ví dụ như tầng trình diễn(presentation) hay tầng nghiệp vụ (business). Mặc dù kiến trúc phân tầng không qui định số lượng hay các loại tầng phải tồn tại, hầu hết các kiến trúc phân tầng bao gồm 4 tầng: tầng trình diễn, tầng nghiệp vụ, tầng lưu trữ(persistence), tầng cơ sở dữ liệu(database).

<p align="center">
  <img src="https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/01/04145016/4-tier.png">
</p>

Trong một vài trường hợp tầng nghiệp vụ và tầng lưu trữ có thể được kết hợp thành một tầng nghiệp vụ, đặc biệt khi logic lưu trữ (SQL hay HSQL) được nhúng bên trong các thành phần của tầng nghiệp vụ. Vì vậy với các ứng dụng nhỏ có thể chỉ có ba tầng, ngược lại với các ứng dụng lớn hoặc các ứng dụng có nghiệp vụ phức tạp có thể chứa năm tầng hoặc nhiều hơn.

Mỗi tầng trong kiến trúc phân tầng có một vai trò và trách nhiệm cụ thể trong ứng dụng. Ví dụ tầng trình diễn sẽ có trách nhiệm xử lí tất cả giao diện người dùng và logic giao tiếp trình duyệt, trái lại tầng nghiệp vụ sẽ chịu trách nhiệm thực thi các quy tắc nghiệp vụ cụ thể. Mỗi tầng trong kiến trúc phân tầng trừu tượng hoá các công việc cần phải hoàn thành để đáp ứng một yêu cầu nghiệp vụ cụ thể. Ví dụ tầng trình diễn không cần phải biết hay lo lắng về việc lấy dự liệu khách hàng như thế nào; nó chỉ cần hiển thị các thông tin đó lên màn hình theo định dạng cụ thể. Tương tự như vậy, tầng nghiệp vụ không cần bận tâm làm thế nào định dạng dữ liệu khách hàng để hiển khị lên màn hình hoặc ngay cả việc dự liệu khách hàng được lưu trữ ở đâu; nó chỉ cần lấy dữ liệu từ tầng lưu trữ, thực hiện logic nghiệp vụ dựa trên dữ liệu đó(tính toán các giá trị hay tổng hợp dữ liệu), và truyền các thông tin đó tới tầng trình diễn.

Một trong những tính năng mạnh mẹ của kiến trúc phân tầng sự tách biệt các mỗi bận tâm giữa các thành phần. Các thành phần bên trong một tầng cụ thể chỉ giải quyết các logic liên quan đến tầng đó. Ví dụ các thành phần ở tầng trình diễn chỉ giải quyết các logic hiển thị, ngược lại các thành phần cư trú ở tầng nghiệp vụ chỉ giải quyết các logic nghiệp vụ. Việc phân loại các loại thành phần như thế này giúp cho việc xây dựng mô hình trách nhiệm và vai trò có hiệu quả trở nên dễ dàng hơn, đồng thời giúp bạn dễ dàng phát triển, kiểm thử, quản lí, và bảo trì các ứng dụng nhờ vào các giao diện thành phần được mô tả rõ ràng và phạm vi thành phần được giới hạn.

### Tầng đóng(closed layer)

Chúng ta cùng xem hình dưới đây:

<p align="center">
  <img src="https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/01/04145012/closed_layer.png">
</p>


Mỗi tầng trong kiến trúc này được đánh dấu `CLOSED`. Đây là một khái niệm rất quan trọng trong kiến trúc phân tầng. Một tầng đóng có nghĩa là một yêu cầu(request) đi từ tầng này sang tầng khác phải đi qua tầng ngay bên dưới nó để đi tới tầng tiếp theo bên dưới tầng đó. Ví dụ một request bắt nguồn từ tầng trình diễn phải đi qua tầng trình diễn sau đó tới tầng lưu trữ trước cuối cùng chốt lại ở tầng cơ sở dữ liệu.

### Tại sao không cho phép tầng trình diễn truy cập trực tiếp đến tầng lưu trữ hay tầng cơ sở dữ liệu?

Truy cập trực tiếp cơ sở dữ liệu từ tầng trình diễn thì nhanh hơn là thông qua một loạt các tầng không cần thiết chỉ để lấy ra hay lưu lại thông tin cơ sở dữ liệu. Câu trả lời cho câu hỏi này nằm ở một khái niệm *tầng cô lập*(layers of isolation).

### Tầng cô lập

Khái niệm *tầng cô lập* có nghĩa là những thay đổi được tạo ra trong một tầng của kiến trúc nhìn chung không có tác động hay ảnh hướng đến các thành phần của tầng khác: thay đổi được cô lập trong các thành phần bên trong lớp đó và một lớp nào đó có có thể có liên quan(ví dụ như tầng lưu trữ có chứa SQL). Nếu chúng ta cho phép tầng trình diễn truy cập trực tiếp tới tầng lưu trữ thì những thay đổi được tạo ra với SQL trong tầng lưu trữ sẽ có tác động tới cả tầng nghiệp vụ và tầng trình diễn, do đó tạo ra một ứng dụng liên kết rất chặt chẽ với rất nhiều phụ thuộc chéo giữa các thành phần. Những ứng dụng như thế này quá cứng nhắc và tốn kém khi có thay đổi.

Khái niệm *tầng cô lập* cũng có nghĩa là mỗi một tầng độc lập với các tầng khác, do đó chúng biết rất ít hoặc không biết về hoạt động bên trong của các tầng khác trong kiến trúc. Để hiểu được sức mạnh và tầm quan trọng của khái niệm này, chúng ta cùng xem xét nỗ lực tái cấu trúc để chuyển đổi presentation framework từ JSP (Java Server Pages) to JSF (Java Server Faces). Giả sử rằng hợp đồng(model) được sử dụng giữa tầng trình diễn và tầng nghiệp vụ không thay đổi, tầng nghiệp vụ không bị ảnh hưởng bởi việc tái cấu trúc và giữ độc lập hoàn toàn với các loại giao diện người dùng được sử dụng bởi tầng trình diễn.

### Tầng mở

Tầng đóng tạo điều kiện thuận lợi cho tầng cô lập và do đó giúp cô lập thay đổi trong kiến trúc, đôi khi tầng mở có ý nghĩa đối với các tầng nhất định. Giả sử bạn muốn thêm vào tầng dịch vụ chia sẻ(shared services) chứa các thành phần dịch vụ dùng chung được truy cập bởi các thành phần trong tầng nghiệp vụ(các lớp tiện ích dữ liệu hay chuổi, các lớp kiểm tra và ghi nhật ký). Trong trường hợp này việc tạo ra tầng dịch vụ thường là ý tưởng tốt bởi lẽ về mặt kiến trúc thì nó hạn chế các truy cập vào các dịch vụ chia sẻ đổi với tầng nghiệp vụ(chứ không phải tầng trình diễn). Không có sự tách biệt về tầng, về mặt kiến trúc không có gì hạn chế tầng trình diễn truy cập vào các dịch vụ dùng chung này, gây khó khăn cho việc quản lý hạn chế truy cập.

Trong ví dụ này, tầng dịch vụ mới sẽ có thể sẽ nằm bên dưới tầng nghiệp vụ để chỉ rằng các thành phần trong tầng dịch vụ này không thể truy cập từ tầng trình diễn. Tuy nhiên, điều này có nghĩa rằng tầng nghiệp vụ lúc này bắt buộc phải đi qua tầng dịch vụ để tới tầng lưu trữ, điều này không có ý nghĩa gì cả. Đây là vấn đề lâu đời của kiến trúc phân tầng. Để giải quyết vấn đề này chúng ta tạo ra tầng mở trong kiến trúc.


<p align="center">
  <img src="https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/01/04145004/open_layer.png">
</p>

Các tầng dịch vụ trong trường hợp này được đánh dấu là `OPEN`, có nghĩa là các yêu cầu được cho phép đi qua tầng mở và đi trực tiếp tới tầng bên dưới.
Trong ví dụ trên vì tầng dịch vụ là tầng mở nên tầng nghiệp vụ bây giờ được phép bỏ qua nó và đi trực tiếp tới tầng lưu trữ, điều này hoàn toàn hợp lí.

Chúng ta có thể tận dụng khái niệm về tầng mở và đóng để xác định mối quan hệ giữa các tầng trong kiến trúc và luồng yêu cầu cũng như cung cấp cho nhà thiết kế, nhà phát triển các thông tin cần thiết để hiểu các hạn chế truy cập tới các tầng khác nhau trong kiến trúc. Việc không thể xác định chính xác các tầng nào trong kiến trúc là mở hay đóng và tại sao thường dẫn tới các cấu trúc liên kết chặt chẽ và dễ vỡ, chúng rất khó để kiểm thử, bảo trì và triển khai.