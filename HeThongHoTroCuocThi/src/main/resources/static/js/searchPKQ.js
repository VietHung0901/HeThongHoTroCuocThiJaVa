function searchPQK() {

    const cccd = document.getElementById('cccd').value;
    const pdkId = document.getElementById('pdkId').value;

    // Kiểm tra nếu cccd hoặc pdkId là trống
    if (!cccd || !pdkId) {
        alert('Vui lòng điền đủ thông tin.');
        return;
    }

    $.ajax({
        url: '/PhieuKetQuas/search/' + pdkId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            let trHTML = '';
            // Kiểm tra nếu giá trị của cccd khác data.cccd
            if (cccd !== data.cccd) {
                // Xóa các dữ liệu đang có trong bảng
                $('#pkq-table-body').html('');
                alert('Không tìm thấy thông tin!');
                return;
            }

            if (data) {
                trHTML +=
                    '<tr>' +
                    '<td>' + data.id + '</td>' +
                    '<td>' + data.tenCuocThi + '</td>' +
                    '<td>' + data.cccd + '</td>' +
                    '<td>' + data.hoten + '</td>' +
                    '<td>' + data.tenTruong + '</td>' +
                    '<td>' + data.diem + '</td>' +
                    '<td>' + data.phut + ':' + data.giay + '</td>' +
                    '<td>                     ' +
                    '<a class="btn btn-primary btn-sm" href="/PhieuKetQuas/cuocThiId/' + data.cuocThiId + '">kết quả cuộc thi</a>' +
                    '</td>'
                '</tr>';
            }
            $('#pkq-table-body').html(trHTML);
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
}