// Các biến lưu trữ index hiện tại của các danh sách
let currentQuyDinhIndex = 0;
let currentNoiDungIndex = 0;

// Hàm để chuyển đến quy định tiếp theo
function nextQuyDinh() {
    const quyDinhElements = document.querySelectorAll('.listQuyDinh');
    if (currentQuyDinhIndex < quyDinhElements.length - 1) {
        quyDinhElements[currentQuyDinhIndex].style.display = 'none';
        currentQuyDinhIndex++;
        quyDinhElements[currentQuyDinhIndex].style.display = 'block';
    }
}

// Hàm để chuyển đến quy định trước đó
function prevQuyDinh() {
    const quyDinhElements = document.querySelectorAll('.listQuyDinh');
    if (currentQuyDinhIndex > 0) {
        quyDinhElements[currentQuyDinhIndex].style.display = 'none';
        currentQuyDinhIndex--;
        quyDinhElements[currentQuyDinhIndex].style.display = 'block';
    }
}

// Hàm để chuyển đến nội dung tiếp theo
function nextNoiDung() {
    const noiDungElements = document.querySelectorAll('.listNoiDung');
    if (currentNoiDungIndex < noiDungElements.length - 1) {
        noiDungElements[currentNoiDungIndex].style.display = 'none';
        currentNoiDungIndex++;
        noiDungElements[currentNoiDungIndex].style.display = 'block';
    }
}

// Hàm để chuyển đến nội dung trước đó
function prevNoiDung() {
    const noiDungElements = document.querySelectorAll('.listNoiDung');
    if (currentNoiDungIndex > 0) {
        noiDungElements[currentNoiDungIndex].style.display = 'none';
        currentNoiDungIndex--;
        noiDungElements[currentNoiDungIndex].style.display = 'block';
    }
}

// Gắn sự kiện click vào các nút
document.getElementById('prevBtnQuyDinh').addEventListener('click', prevQuyDinh);
document.getElementById('nextBtnQuyDinh').addEventListener('click', nextQuyDinh);
document.getElementById('prevBtnNoiDung').addEventListener('click', prevNoiDung);
document.getElementById('nextBtnNoiDung').addEventListener('click', nextNoiDung);
