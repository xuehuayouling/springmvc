/**
 * sea.config
 * 
 * @author 管淑婷 2016.8.23
 * 
 */

$.ajaxSettings.async = false;

var contextPath = "";
if (window.rootPath) {
	contextPath = window.rootPath
}
seajs && seajs.config({
	base: contextPath,
	alias: {
		"iScroll": "third/iscroll/iscroll.js",
		"iScroll": "third/iscroll/iscroll.js",
		"iSelect": "scripts/iSelect.js",
		"dateGenerator": "scripts/date-generator.js"
	},
	// 路径配置
	paths: {
		'modulePath': contextPath + "scripts/module/"
	},
	//preload: ['seajs-text'],
	charset: "utf-8",
	timeout: 20000,
	debug: true
});

$.ajaxSettings.async = true;