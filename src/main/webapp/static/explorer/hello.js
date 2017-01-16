define(function(require, exports, module) {

    // 通过 require 引入依赖
    //var $ = require('jquery');
    var Modal = {};

    Modal.Initialize = function() {
        $('#test').text(window.result);
    };

    // 或者通过 module.exports 提供整个接口
    module.exports = Modal;

});