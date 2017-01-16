define(function (require, exports, module) {

    var ysq = {};
    //通用类
    var Util = {
        submit: function (options) { // 提交
            var opts = {
                type: "post",
                url: undefined,
                data: undefined,
                dataType: "json", // 服务器端到客户端
                beforeSend: undefined
            };

            var optsExtend = {
                success: function (result, status, XMLHttpRequest) {
                    if (options.success) {
                        options.success(result, status, XMLHttpRequest);
                    }
                },
                error: function (response, error) {
                    if (options.error) {
                        options.error(response, error);
                        return;
                    }
                },
                beforeSend: function (request) {
                    if (options.beforeSend) {
                        options.beforeSend(request);
                    }
                },
            };
            $.extend(opts, options || {}, optsExtend);
            $.ajax(opts);
        },
        getContextPath: function(key, pageUrl) {
            var baseInfo = window.baseInfo;
            try {
                if (key && pageUrl) {
                    var path = "/" + baseInfo.type + "/" + baseInfo.version[key] + "/hospitals/" + baseInfo.hosId;
                    return window.ContextPath + path + pageUrl;
                } else if (key) {
                    var path = "/" + baseInfo.type + "/" + baseInfo.version[key] + "/hospitals/" + baseInfo.hosId;
                    return window.ContextPath + path;
                }
                return window.ContextPath;
            } catch (e) {
                return "";
            }
        },

    };
    ysq.Util = Util;
    module.exports = ysq;

});