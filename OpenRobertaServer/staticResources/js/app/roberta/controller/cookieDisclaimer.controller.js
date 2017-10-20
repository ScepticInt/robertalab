define(["exports", "jquery", "guiState.controller"], function(exports, $, GUISTATE_C) {
    var $disclaimer = $("#cookieDisclaimer"),
        $okButton = $disclaimer.children("button"),
        cookieName = "OpenRoberta_Cookie",
        cookieValue = "accepted",
        cookieSettings = {
            expires: 30,
            path: "/",
            domain: "",
            secure: true
        };
    
    function init() {
        cookieSettings.secure = GUISTATE_C.isPublicServerVersion();
        
        $(window).on('unload', function(){
            if (!cookiesAllowed()) {
                var cookies = $.cookie();
                for (var cookie in cookies) {
                    if (cookies.hasOwnProperty(cookie)) {
                        $.removeCookie(cookie);
                    }
                }
            }
        });
        
        if (cookiesAllowed() || !GUISTATE_C.isPublicServerVersion()) {
            refreshCookie();
        } else {
            addHandler(refreshCookie);
            showDisclaimer();
        }
    }
    
    exports.init = init;

    /**
     * @private
     */
    function refreshCookie() {
        $.cookie(cookieName, cookieValue, cookieSettings);
        hideDisclaimer();
    }

    /**
     * @private
     */
    function hideDisclaimer() {
        $disclaimer.addClass("accepted");
    }
    
    /**
     * @private
     */
    function showDisclaimer() {
        $disclaimer.removeClass("accepted");
        $disclaimer.removeClass("hidden");
    }
    
    function cookieExists() {
        return typeof $.cookie(cookieName) !== "undefined";
    }
    
    function cookiesAllowed() {
        return cookieExists();
    }
    exports.cookiesAllowed = cookiesAllowed;

    /**
     * @private
     */
    function addHandler(func) {
        if (typeof func === "function") {
            $okButton.one('click', func);
        }
    }
    exports.addHandler = addHandler;
    
    function maxExpirationTime(maximum) {
        if (typeof maximum !== "number") {
            maximum = 364;
        }
        return cookieExists() ? Math.min(maximum, 364) : null;
    }
    exports.maxExpirationTime = maxExpirationTime;
});