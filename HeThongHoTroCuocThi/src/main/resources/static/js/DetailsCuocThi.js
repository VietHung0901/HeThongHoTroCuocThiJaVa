// $(document).ready(function () {
//     //Hiển thị từng quy định
//     var currentQuyDinhPage = 0;
//     var quyDinhPages = $(".listQuyDinh");
//     var totalQuyDinhPages = quyDinhPages.length;
//
//     function showQuyDinhPage(index) {
//         quyDinhPages.hide();
//         quyDinhPages.eq(index).show();
//     }
//
//     $("#prevBtnQuyDinh").click(function () {
//         currentQuyDinhPage = (currentQuyDinhPage > 0) ? currentQuyDinhPage - 1 : totalQuyDinhPages - 1;
//         showQuyDinhPage(currentQuyDinhPage);
//     });
//
//     $("#nextBtnQuyDinh").click(function () {
//         currentQuyDinhPage = (currentQuyDinhPage < totalQuyDinhPages - 1) ? currentQuyDinhPage + 1 : 0;
//         showQuyDinhPage(currentQuyDinhPage);
//     });
//
//     $("#showQuyDinhBtn").click(function () {
//         $("#quyDinhContainer").show();
//         $("#noiDungContainer").hide();
//         showQuyDinhPage(currentQuyDinhPage);
//     });
//
//     //Hiển thị từng nội dung
//     var currentNoiDungPage = 0;
//     var noiDungPages = $(".listNoiDung");
//     var totalNoiDungPages = noiDungPages.length;
//
//     function showNoiDungPage(index) {
//         noiDungPages.hide();
//         noiDungPages.eq(index).show();
//     }
//
//     $("#prevBtnNoiDung").click(function () {
//         currentNoiDungPage = (currentNoiDungPage > 0) ? currentNoiDungPage - 1 : totalNoiDungPages - 1;
//         showNoiDungPage(currentNoiDungPage);
//     });
//
//     $("#nextBtnNoiDung").click(function () {
//         currentNoiDungPage = (currentNoiDungPage < totalNoiDungPages - 1) ? currentNoiDungPage + 1 : 0;
//         showNoiDungPage(currentNoiDungPage);
//     });
//
//     $("#showNoiDungBtn").click(function () {
//         $("#noiDungContainer").show();
//         $("#quyDinhContainer").hide();
//         showNoiDungPage(currentNoiDungPage);
//     });
// });

/*
$(window).on('beforeunload', function(){
    console.log("Page is being unloaded");
});

$(document).ready(function () {
    console.log("Page loaded");

    let currentQuyDinhPage = 0;
    let quyDinhPages = $(".listQuyDinh");
    let currentNoiDungPage = 0;
    let noiDungPages = $(".listNoiDung");

    // Ẩn tất cả các trang
    quyDinhPages.hide();
    noiDungPages.hide();

    // Hiển thị trang đầu tiên
    if (quyDinhPages.length > 0) {
        $(quyDinhPages[currentQuyDinhPage]).show();
    }
    if (noiDungPages.length > 0) {
        $(noiDungPages[currentNoiDungPage]).show();
    }

    function showQuyDinhPage(page) {
        console.log("Showing Quy Dinh page: " + page);
        quyDinhPages.hide();
        $(quyDinhPages[page]).show();
    }

    function showNoiDungPage(page) {
        console.log("Showing Noi Dung page: " + page);
        noiDungPages.hide();
        $(noiDungPages[page]).show();
    }

    $("#prevBtnQuyDinh").click(function () {
        console.log("Previous Quy Dinh button clicked");
        if (currentQuyDinhPage > 0) {
            currentQuyDinhPage--;
            showQuyDinhPage(currentQuyDinhPage);
        }
    });

    $("#nextBtnQuyDinh").click(function () {
        console.log("Next Quy Dinh button clicked");
        if (currentQuyDinhPage < quyDinhPages.length - 1) {
            currentQuyDinhPage++;
            showQuyDinhPage(currentQuyDinhPage);
        }
    });

    $("#prevBtnNoiDung").click(function () {
        console.log("Previous Noi Dung button clicked");
        if (currentNoiDungPage > 0) {
            currentNoiDungPage--;
            showNoiDungPage(currentNoiDungPage);
        }
    });

    $("#nextBtnNoiDung").click(function () {
        console.log("Next Noi Dung button clicked");
        if (currentNoiDungPage < noiDungPages.length - 1) {
            currentNoiDungPage++;
            showNoiDungPage(currentNoiDungPage);
        }
    });

    $("#showQuyDinhBtn").click(function () {
        console.log("Show Quy Dinh button clicked");
        $("#noiDungContainer").hide();
        $("#quyDinhContainer").toggle();
        if ($("#quyDinhContainer").is(":visible")) {
            showQuyDinhPage(currentQuyDinhPage);
        }
    });

    $("#showNoiDungBtn").click(function () {
        console.log("Show Noi Dung button clicked");
        $("#quyDinhContainer").hide();
        $("#noiDungContainer").toggle();
        if ($("#noiDungContainer").is(":visible")) {
            showNoiDungPage(currentNoiDungPage);
        }
    });
});
*/
$(document).ready(function () {
    let currentQuyDinhPage = 0;
    let quyDinhPages = $(".listQuyDinh");
    let currentNoiDungPage = 0;
    let noiDungPages = $(".listNoiDung");

    // Ẩn tất cả các trang
    quyDinhPages.hide();
    noiDungPages.hide();

    function showQuyDinhPage(page) {
        quyDinhPages.hide();
        if (page >= 0 && page < quyDinhPages.length) {
            $(quyDinhPages[page]).show();
        }
    }

    function showNoiDungPage(page) {
        noiDungPages.hide();
        if (page >= 0 && page < noiDungPages.length) {
            $(noiDungPages[page]).show();
        }
    }

    // Hiển thị trang đầu tiên nếu có
    if (quyDinhPages.length > 0) {
        showQuyDinhPage(currentQuyDinhPage);
    }
    if (noiDungPages.length > 0) {
        showNoiDungPage(currentNoiDungPage);
    }

    $("#prevBtnQuyDinh").click(function () {
        if (currentQuyDinhPage > 0) {
            currentQuyDinhPage--;
            showQuyDinhPage(currentQuyDinhPage);
        }
    });

    $("#nextBtnQuyDinh").click(function () {
        if (currentQuyDinhPage < quyDinhPages.length - 1) {
            currentQuyDinhPage++;
            showQuyDinhPage(currentQuyDinhPage);
        }
    });

    $("#prevBtnNoiDung").click(function () {
        if (currentNoiDungPage > 0) {
            currentNoiDungPage--;
            showNoiDungPage(currentNoiDungPage);
        }
    });

    $("#nextBtnNoiDung").click(function () {
        if (currentNoiDungPage < noiDungPages.length - 1) {
            currentNoiDungPage++;
            showNoiDungPage(currentNoiDungPage);
        }
    });

    $("#showQuyDinhBtn").click(function () {
        $("#noiDungContainer").hide();
        $("#quyDinhContainer").toggle();
        if ($("#quyDinhContainer").is(":visible")) {
            showQuyDinhPage(currentQuyDinhPage);
        }
    });

    $("#showNoiDungBtn").click(function () {
        $("#quyDinhContainer").hide();
        $("#noiDungContainer").toggle();
        if ($("#noiDungContainer").is(":visible")) {
            showNoiDungPage(currentNoiDungPage);
        }
    });

    // Đảm bảo rằng các phần tử được hiển thị khi trang được tải lại hoặc khi chuyển trang
    $(window).on('load', function () {
        if ($("#quyDinhContainer").is(":visible")) {
            showQuyDinhPage(currentQuyDinhPage);
        }
        if ($("#noiDungContainer").is(":visible")) {
            showNoiDungPage(currentNoiDungPage);
        }
    });
});
