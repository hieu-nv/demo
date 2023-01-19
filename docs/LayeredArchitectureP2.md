Trong phần 1 chúng ta đã tìm hiều về [Kiến trúc phân tầng](https://magz.techover.io/2023/01/04/kien-truc-phan-tang-layered-architecture-phan-1/) và các khái niệm quan trọng nhất của nó. Trong phần 2 này chúng ta sẽ xem xét cách thức hoạt động của kiến trúc phân tầng và những điểm cần lưu ý khi sử dụng kiến trúc này.


### Ví dụ

Để minh họa cách thức hoạt động của kiến trúc phân lớp, chúng ta cùng xem xét một yêu cầu từ người dùng doanh nghiệp muốn truy xuất thông tin khách hàng của một cá nhân cụ thể.

![](https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/01/12110524/MicrosoftTeams-image-1.png)

Các mũi tên màu đen thể hiện luồng yêu cầu xuống cơ sở dữ liệu để lấy thông tin khách hàng. Các mũi tên màu đỏ thể hiện luồng phản hồi ngược trở lại màn hình để hiển thị dữ liệu. Trong ví dụ này, thông tin khách hàng bao gồm cả dữ liệu khách hàng và dữ liệu đơn hàng (đơn hàng do khách hàng đặt).
*Customer Screen* có nhiệm vụ tiếp nhận yêu cầu và hiển thị thông tin khách hàng. Nó hoàn toàn không biết nơi dữ liệu được lư trữ, làm thế nào để lấy nó hoặc có bao nhiêu bảng cơ sở dữ liệu phải được truy vấn để lấy dữ liệu. Khi *Customer Screen* nhận được yêu cấu lấy thông tin khác hàng của một cá nhân cụ thể, nó sẽ chuyển tiếp yêu cầu đó tới mô đun *Customer Delegate*. Mô đun này có nhiệm vụ biết các mô đun ở tầng nghiệp vụ có thể xử lí yêu cầu đó cũng như làm thế nào để lấy các mô đun đó và dữ liệu nào mà nó cần (hợp đồng). *Customer Object* ở tầng nghiệp vụ có nhiệm vụ tổng hợp toàn bộ thông tin cần thiết bởi yêu cầu nghiệp vụ(trong trường hợp này là thông tin khách hàng). Mô đun này gọi ra *Customer DAO* (đối tượng truy cập dữ liệu) ở tầng lưu trữ để lấy dữ liệu khách hàng cùng với *Order DAO* để lấy thông tin đơn hàng. Các mô đun này lần lượt thực thi các câu lệnh SQL để lấy dữ liệu tương ứng và trả lại cho *Customer Object* ở tầng nghiệp vụ. Khi *Customer Object* nhận được dữ liệu, nó sẽ tổng hợp dữ liệu và trả lại các thông tin đó cho *Customer Delegate*, sau đó *Customer Delegate* trả lại các dữ liệu đó cho *Customer Screen* để hiển thị cho người dùng. Từ khía cạnh công nghệ, có hàng tá cách để cài đặt các mô đun này. Ví dụ với nên tàng *Java*, *Customer Screen* có thể là một màn hình JSF(Java Server Faces) cùng với *Customer Delete* là thành phần bean được quản lí. *Customer Object* ở tầng nghiệp vụ có thể là một Spring Bean cục bộ hoặc EJB3 bean từ xa. Các đối tượng truy cập cơ sở dữ liệu được minh hoạ trong ví dụ trước có thể được triển khai dưới dạng POJO (Plain Old Java Objects) đơn giản, MyBatis XML Mapper, hoặc ngay cả cá đối tượng đóng gọi lời gọi JDBC thuần hoặc các truy vấn Hibernate. Trên nền tảng Microsoft, *Customer Screen* có thể là một mô đun ASP(Active Server Pages) sử dụng framework .NET để truy cập các mô đun C# ở tầng nghiệp vụ với các mô đun truy cập dữ liệu khác hàng và đơn hàng được triển khai dưới dàng ADO(ActiveX Data Objects).

### Những điểm cần cân nhắc khi sử dụng kiến trúc phân tầng

Kiến trúc phân tầng là một mẫu kiến trúc về cơ bản là vững chắc, hấu hết các ứng dụng có thể bắt đầu bằng kiến trúc này, đặc biệt khi chúng ta không chắc chắn kiến trúc nào là phù hợp nhất cho ứng dụng của chúng ta. Tuy nhiên về mặt kiến trúc thì có một vài điều cần cân nhắc trước khi chọn mẫu kiến trúc này.

Điểm đầu tiên cần chú ý là các *anti-pattern*(phản mẫu - các mẫu thiết kế cần tránh sử dụng). Một trong số đó là *kiến trúc hố sụt*. Kiến trúc này mô tả tình huống luồng yêu cầu đi qua nhiều tầng của kiến trúc mà đơn giản xử lí chuyển tiếp với rất ít hoặc không có logic được thực hiện bên trong mỗi tầng. Ví dụ, tầng trình diễn phản hồi một yêu cầu từ người dùng muốn lấy dữ liệu khách hàng. Tầng trình diễn chuyển yêu cầu tới tầng nghiệp vụ, nó đơn giản chuyển tiếp yêu cầu tới tầng lưu trữ, sau đó tạo một lời gọi SQL đơn giản tới tầng cơ sở dữ liệu để lấy dữ liệu khách hàng. Dữ liệu sau đó được truyền theo đường ngược lại mà không có xử lí thêm hay logic tổng hợp, tính toán hoặc biến đổi dữ liệu.

Mỗi kiến trúc phân tầng sẽ có ít nhất một số tình huống rơi vào phản mẫu kiến trúc hố sụt. Tuy nhiên điều quan trọng là phân tích tỉ lệ phần trăm yêu cầu thuộc loại này. Quy tắc 80-20 thường là một phương pháp hay, nên tuân theo để xác định liệu có hay không chúng ta đang rơi vào phản mẫu kiến trúc hố sụt. Thông thường có khoảng 20% yêu cầu được xử lí đơn giản và 80% yêu cầu có một số logic nghiệp vụ liên quan đến yêu cầu đó. Tuy nhiên nếu chúng ta thấy rằng tỉ lệ này bị đảo ngược và phần lớn các yêu cầu của chúng ta là quá trình xử lí đơn giản, chúng ta có thể cân nhắc một số tầng trong kiến trúc là mở.

Một điểm khác cần cân nhắc với mẫu kiến trúc phân lớp là nó có xu hướng thích ứng với các ứng dụng nguyên khối, ngay cả khi chúng ta tách tầng trình diễn và tầng nghiệp vụ thành các đơn có thể triển khai riêng biệt. Mặc dù điều này có thể không phải là vấn đề đáng lo ngại đối với một số ứng dụng nhưng nó đặt ra một số vấn đề tiềm ẩn về triển khai, độ bền và độ tin cậy chung, hiệu suất và khả năng mở rộng.

### Phân tích kiến trúc phân tầng

Dưới đây là bảng đánh giá và phân tích về các đặc điểm kiến trúc phổ biến của kiến trúc phân tầng. Các đánh giá cho từng đặc điểm dựa trên xu hướng tự nhiên của các đặc điểm đó, điển hình như là khả năng triển khai cũng như là mức độ phổ biến của mẫu kiến trúc này.


| Đặc điểm                |                                                           Đánh giá                                                           |
| ----------------------- | :--------------------------------------------------------------------------------------------------------------------------: |
| Tính linh hoạt tổng thể | ![](https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/01/12150010/MicrosoftTeams-image-3.png) |
| Dễ triển khai           | ![](https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/01/12150010/MicrosoftTeams-image-3.png) |
| Khả năng kiểm thử       | ![](https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/01/12153818/MicrosoftTeams-image-4.png) |
| Hiệu năng               | ![](https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/01/12150010/MicrosoftTeams-image-3.png) |
| Khả năng mở rộng        | ![](https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/01/12150010/MicrosoftTeams-image-3.png) |
| Dễ phát triển           | ![](https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/01/12153818/MicrosoftTeams-image-4.png) |

#### Tính linh hoạt tổng thể

Tính linh hoạt tổng thể là khả năng đáp ứng nhanh chóng với một môi trường thay đổi liên tục. Trong khi thay đổi được cô lập thông qua *tầng cô lập*, nó vẫn cồng kềnh và tốn thời gian để thực hiện các thay đổi trong kiến trúc này bởi vì bản chất nguyên khối của hầu hết các triển khai cũng như sự liên kết chặt chẽ của các thành phần thường được tìm thấy với mẫu kiến trúc này.

#### Dễ triển khai

Tùy thuộc vào cách chúng ta triển khai mẫu này, khả năng triển khai có thể trở thành một vấn đề, đặc biệt đối với các ứng dụng lớn. Một thay đổi nhỏ đối với một thành phần có thể yêu cầu triển khai lại toàn bộ ứng dụng (hoặc một phần lớn của ứng dụng), dẫn đến việc triển khai cần được lập kế hoạch, được lên lịch và thực hiện ngoài giờ hoặc vào cuối tuần. Như vậy, mô hình này không dễ thích ứng với việc triển khai liên tục, tiếp tục giảm xếp hạng tổng thể cho triển khai.

#### Khả năng kiểm thử

Trong kiến thúc này, các thành phần trong thuộc vào một tầng cụ thể, các tầng khác có thể được mô phỏng hoặc khai thác, giúp cho kiến trúc này tương đối dễ kiểm thử. Một nhà phát triển có thể giả lập một thành phần trình bày hoặc màn hình để cô lập thử nghiệm trong một thành phần nghiệp vụ, cũng như mô phỏng tầng nghiệp vụ để kiểm tra chức năng màn hình nhất định.

#### Hiệu năng

Mặc dù đúng là một số kiến trúc phân tầng có thể hoạt động tốt, nhưng kiến trúc này không phù hợp với các ứng dụng hiệu năng cao do tính không hiệu quả của việc phải đi qua nhiều tầng của kiến trúc để đáp ứng yêu cầu nghiệp vụ.

#### Khả năng mở rộng

Do xu hướng triển khai nguyên khối và liên kết chặt chẽ của kiến trúc này, các ứng dụng được xây dựng bằng cách sử dụng mẫu kiến trúc này thường khó mở rộng quy mô. Chúng ta có thể mở rộng quy mô kiến trúc phân tầng bằng cách tách các tầng thành các triển khai vật lý riêng biệt hoặc sao chép toàn bộ ứng dụng thành nhiều nút, nhưng nhìn chung mức độ chi tiết quá rộng, khiến việc mở rộng quy mô trở nên tốn kém.

#### Dễ phát triển

Tính dễ phát triển nhận được điểm tương đối cao, chủ yếu là do mô hình này quá nổi tiếng và không quá phức tạp để thực hiện. Bởi vì hầu hết các công ty phát triển ứng dụng bằng cách tách các bộ kỹ năng theo tầng (trình diễn, nghiệp vụ, cơ sở dữ liệu), kiến trúc này trở thành lựa chọn tự nhiên cho hầu hết việc phát triển ứng dụng kinh doanh. Mối liên hệ giữa cơ cấu tổ chức và truyền thông của công ty với cách thức phát triển phần mềm được vạch ra là cái được gọi là định luật Conway. Bạn có thể Google "Conway’s law" để có thêm thông tin về mối tương quan hấp dẫn này.
![](https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/01/12161333/conways-law.jpeg)

### Tài liệu tham khảo

- Software Architecture Patterns
- https://excalidraw.com/#json=hrBEjMVolrAZuYRhB7w3q,2S9KbEZEOSt2aY2emcNYuA