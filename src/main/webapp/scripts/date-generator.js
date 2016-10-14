/**
 * Created by io_sys@126.com on 2016/3/2. 有改动
 */

/**
 *
 * 生成日期数据模块
 *
 * 为了解决 jquery-smart-roller 组件在日期选择中的应用而定义的模块，
 * 所生成的日期数据格式完全匹配 jquery-smart-roller 数据要求。具体格式可参见 jquery-smart-roller
 *
 */
define(function(requrie ,exports ,module) {
    "use strict";

    var nMonth = ['1' ,'2' ,'3' ,'4' ,'5' ,'6' ,'7' ,'8' ,'9' ,'10' ,'11' ,'12'],
        wMonth = ['一' ,'二' ,'三' ,'四' ,'五' ,'六' ,'七' ,'八' ,'九' ,'十' ,'十一' ,'十二'];

    var unit = ['年' ,'月' ,'日'];

    var DAY_OF_MONTH = {
        1: 31,
        2: 28,
        3: 31,
        4: 30,
        5: 31,
        6: 30,
        7: 31,
        8: 31,
        9: 30,
        10: 31,
        11: 30,
        12: 31
    };
    var UNIT_INDEX_OF_YEAR = 0,
        UNIT_INDEX_OF_MONTH = 1,
        UNIT_INDEX_OF_DAY = 2;

    /**
     *
     * 是否是闰年
     *
     * 计算过的年份将用闭包缓存，不再重复计算
     *
     */
    var _isLeapYear = function() {
        var cache = {};

        return function(year) {

            return cache[year] || (cache[year] = ((year%400 === 0 || year%4 === 0) && year%100 !== 0));
        };
    }.call();

    /**
     *
     * 根据月份生成日期数据
     *
     * @param {Number} month 月份
     * @param {Boolean} isLeapYear 是否闰年
     * @returns {Array}
     * @private
     */
    function _createDayByMonth(year, month ,isLeapYear) {
        var d,ret = [];
        var now = new Date();

        if(isLeapYear) {

            d = 2 === +month ? 29 : DAY_OF_MONTH[month];
        }else {

            d = DAY_OF_MONTH[month];
        }

        if(year === now.getFullYear() && month === now.getMonth() + 1) {
            for(var day=1 ;day<=now.getDate() ;day++) {
                ret.push(day + unit[UNIT_INDEX_OF_DAY]);
            }
        }else {
            for(var day=1 ;day<=d ;day++) {
                ret.push(day + unit[UNIT_INDEX_OF_DAY]);
            }
        }

        return ret;
    }

    /**
     *
     * 根据月份生成日期数据
     *** @param {Date} sDate 开始日期
     *  @param {Date} eDate 结束日期
     * @param {Number} month 月份
     * @param {Boolean} isLeapYear 是否闰年
     * @returns {Array}
     * @private
     */
    function _createDayByMontha(sDate, eDate, year, month ,isLeapYear) {
        var d,ret = [];
        var nowY = sDate.getFullYear(),
            nowM = sDate.getMonth() + 1;
        var eY = eDate.getFullYear();
        var eM = eDate.getMonth() + 1;
        if(isLeapYear) {

            d = 2 === +month ? 29 : DAY_OF_MONTH[month];
        }else {

            d = DAY_OF_MONTH[month];
        }

        if (nowY == eY && nowM == eM) {
            for (var day = sDate.getDate(); day <= eDate.getDate(); day++) {
                ret.push(day + unit[UNIT_INDEX_OF_DAY]);
            }
        } else {
            if(year === nowY && month === nowM) {
                for (var day = sDate.getDate(); day <= d; day++) {
                    ret.push(day + unit[UNIT_INDEX_OF_DAY]);
                }
            } else if (year === eY && month === eM) {
                for (var day = 1; day <= eDate.getDate(); day++) {
                    ret.push(day + unit[UNIT_INDEX_OF_DAY]);
                }
            }else {
                for(var day=1 ;day<=d ;day++) {
                    ret.push(day + unit[UNIT_INDEX_OF_DAY]);
                }
            }
        }
        return ret;
    }

    /**
     *
     * 整年日期数据生成器
     *
     */
    var _yearGenerator = function() {
        var leapYearTemplate = {},
            commonYearTemplate = {},
            hasCacheOfLeap = false,
            hasCacheOfCommon = false;

        return function(year) {
            var isLeapYear = _isLeapYear(year);

            if(isLeapYear && hasCacheOfLeap) {

                return leapYearTemplate;
            }else if(!isLeapYear && hasCacheOfCommon) {

                return commonYearTemplate;
            }

            for(var i= 0,m ;m=nMonth[i++] ;) {
                if(isLeapYear) {

                    leapYearTemplate[m + unit[UNIT_INDEX_OF_MONTH]] = _createDayByMonth(year, m, isLeapYear);
                    hasCacheOfLeap = true;
                }else {

                    commonYearTemplate[m + unit[UNIT_INDEX_OF_MONTH]] = _createDayByMonth(year, m, isLeapYear);
                    hasCacheOfCommon = true;
                }
            }

            return isLeapYear ? leapYearTemplate : commonYearTemplate;
        }
    }.call();

    /**
     *
     * 年份数据生成器，此方法只完成 完善通过 _yearGenerator 生成的数据的格式
     *
     * @param year 年份
     * @returns {{}}
     * @private
     */
    function _generator(year) {

        var k = year + unit[UNIT_INDEX_OF_YEAR],
            ret = {};

        ret[k] = _yearGenerator(year);

        return ret;
    }

    /**
     *
     * 创建当前年的日期信息
     ** @param {Date} sDate 开始日期
     *  @param {Date} eDate 结束日期
     * @private
     */
    function _crSYearDaya(sDate, eDate) {
        var now = sDate;
        var nowY = now.getFullYear(),
            nowM = now.getMonth() + 1;
        var eY = eDate.getFullYear();
        var eM = eDate.getMonth() + 1;
        var ret = {};
        ret[nowY + unit[UNIT_INDEX_OF_YEAR]] = {};

        if (eY > nowY) {
            for(var month=nowM; month<=12; month++) {

                ret[nowY + unit[UNIT_INDEX_OF_YEAR]][month + unit[UNIT_INDEX_OF_MONTH]] = _createDayByMontha(sDate, eDate, nowY, +month, _isLeapYear(nowY));
            }
        } else {
            for(var month=nowM; month<=eM; month++) {

                ret[nowY + unit[UNIT_INDEX_OF_YEAR]][month + unit[UNIT_INDEX_OF_MONTH]] = _createDayByMontha(sDate, eDate, nowY, +month, _isLeapYear(nowY));
            }
        }


        return ret;
    }

    /**
     *
     * 创建当前年的日期信息
     ** @param {Date} sDate 开始日期
     *  @param {Date} eDate 结束日期
     * @private
     */
    function _crEYearDaya(sDate, eDate) {
        var now = sDate;
        var nowY = now.getFullYear(),
            nowM = now.getMonth() + 1;
        var eY = eDate.getFullYear();
        var eM = eDate.getMonth() + 1;
        var ret = {};
        ret[eY + unit[UNIT_INDEX_OF_YEAR]] = {};

        if (eY > nowY) {
            for(var month=1; month<=eM; month++) {

                ret[eY + unit[UNIT_INDEX_OF_YEAR]][month + unit[UNIT_INDEX_OF_MONTH]] = _createDayByMontha(sDate, eDate, eY, +month, _isLeapYear(eY));
            }
        } else {
            for(var month=nowM; month<=eM; month++) {

                ret[eY + unit[UNIT_INDEX_OF_YEAR]][month + unit[UNIT_INDEX_OF_MONTH]] = _createDayByMontha(sDate, eDate, eY, +month, _isLeapYear(eY));
            }
        }

        return ret;
    }

    /**
     *
     * 创建年份数据
     *
     * @param {Date} sDate 开始日期
     * @param {Date} eDate 结束日期
     * @returns {{}}
     */
    exports.create = function(sDate, eDate) {
        var nowYear = sDate.getFullYear();
        var eYear = eDate.getFullYear();

        if(nowYear < 1900) {
            throw '起始年份不能小于1900年';
        }

        var ret = {},
            l = eYear - nowYear,
            y = nowYear;

        for(var i=1 ;i<=l ;i++) {
            var o = _generator(y);

            for(var k in o) {
                ret[k] = o[k];
            }

            y++;
        }

        ret[nowYear + unit[UNIT_INDEX_OF_YEAR]] =  _crSYearDaya(sDate, eDate)[nowYear + unit[UNIT_INDEX_OF_YEAR]];

        if (eYear > nowYear) {
            ret[eYear + unit[UNIT_INDEX_OF_YEAR]] =  _crEYearDaya(sDate, eDate)[eYear + unit[UNIT_INDEX_OF_YEAR]]
        }
        return ret;
    }

    exports.createForISelect = function(sDate, eDate) {
        var dateData = this.create(sDate, eDate);
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
        var data = {};
        data.years = years;
        data.months = months;
        data.days = days;
        return data;
    }
});