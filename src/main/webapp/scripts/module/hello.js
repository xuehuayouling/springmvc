/**
 * 个人信息
 * @author 管淑婷
 */
define(function (require, exports, module) {
    var dateGenerator = require("dateGenerator");
    require("iSelect");
    require("iScroll");
    var Modal = {};

    //页面初始化
    Modal.Initialize = function () {
        this._enableEvents();
    };

    Modal._enableEvents = function () {
        $('#choice-date').on('click', function () {
            module.startDateClicked();
        });
    };

    Modal._load = function () {

    }

    //日期渲染
    module.startDateClicked = function () {
        var sDate = new Date();
        var eDate = new Date();
        eDate.setDate(eDate.getDate() + 110);
        var dateData = dateGenerator.createa(sDate, eDate);
        var years = [];
        var months = [];
        var days = [];
        for (var y in dateData) {
            var year = {};
            year.id = y;
            year.value = y;
            year.parentId = 0;
            years.push(year);
            for (var m in dateData[y]) {
                var month = {}
                month.id = y + m;
                month.value = m;
                month.parentId = year.id;
                months.push(month);
                dateData[y][m].forEach(function (d) {
                    var day = {}
                    day.id = y + m + d;
                    day.value = d;
                    day.parentId = month.id;
                    days.push(day);
                });
            }
        }
        var iosSelect = new IosSelect(3,
            [years, months, days],
            {
                title: '日期选择',
                itemHeight: 35,
                oneTwoRelation: 1,
                twoThreeRelation: 1,
                selectcallback: function (selectOneObj, selectTwoObj, selectThreeObj) {
                    var year = selectOneObj.value.substring(0, selectOneObj.value.length - 1);
                    var month = selectTwoObj.value.substring(0, selectTwoObj.value.length - 1);
                    if (month.length == 1) {
                        month = "0" + month;
                    }
                    var day = selectThreeObj.value.substring(0, selectThreeObj.value.length - 1);
                    if (day.length == 1) {
                        day = "0" + day;
                    }
                    $('#choice-date').text(year + '-' + month + '-' + day).data('code', year + '/' + month + '/' + day);
                },
                cancelcallback: function () {
                }
            });
    };

    module.exports = Modal;
});