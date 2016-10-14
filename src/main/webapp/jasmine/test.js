define(function (require, exports, module) {
    var dateGenerator = require("dateGenerator");
    module.exports = Modal;
    var Modal = {};

    //页面初始化
    Modal.Initialize = function () {
        describe("A suite of basic functions", function() {
            var sDate = new Date('2016/10/15');
            var eDate = new Date('2016/10/15');
            eDate.setDate(eDate.getDate() + 110);
            var dateData = dateGenerator.createForISelect(sDate, eDate);
            it("test date-generator createForISelect", function() {
                var exp = 2;
                expect(exp).toEqual(dateData.years.length);
            });
        });
    };
    return Modal;
});

