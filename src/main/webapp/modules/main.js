var contextPath = "/springmvc/";

seajs && seajs.config({
    base: contextPath,
    alias: {
        //"jquery": "modules/jquery/jquery-3.1.1.js",
    },
    paths: {
        'modulePath': contextPath + "static"
    },
    charset: "utf-8",
    timeout: 20000,
    debug: true
});