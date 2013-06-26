// common js file

// Pal
var HOST_PREFIX = "http://localhost:8080";
var clientAppId = "645787b14ab25e9ec621d25f7ad78ba1";
var appSecret = "d8ec5bcf1e51cf34";

var docBase = "/flickr-oauth2";

var flickrCodeUrl = "https://www.flickr.com/dialog/oauth";
var flickrAccessTokenUrl = "https://graph.flickr.com/oauth/access_token";
var flickrLogoutUrl = "https://www.flickr.com/logout.php";

// friends_about_me,
var permissions = "user_about_me,user_birthday,user_education_history,user_location,user_relationships,user_work_history,email";

var state = "fbr123fbr";

function loginToFlickrServer() {
	var fbrform = document.forms['homeForm'];
	// fbrform.action = 'homeBean.loginToFlickr';
	fbrform.action = '#{homeBean.loginToFlickr}';
	fbrform.submit();
	return false;
}

function validateAccessToken() {
	var codeObj = document.getElementById('dashboardForm:code');

	var accessTokenObj = document.getElementById('dashboardForm:accessToken');

	if (codeObj == null || accessTokenObj == null || codeObj.value == "" || accessTokenObj.value == "") {
		// alert('codeObj.value:'+codeObj.value);
		// alert('accessTokenObj.value:'+accessTokenObj.value);
		alert("Invalid Access to Flickr Reader.\n\nPlease login with your Flickr Credentials.");
		// window.location.href = HOST_PREFIX + docBase + "/home.jsf";
	}

}

function loginToFlickrClient() {
	var redirect_uri = HOST_PREFIX + docBase + "/dashboard.jsf";

	
	var baseUrl = "GET http://www.flickr.com/services/oauth/request_token" 
		+ "?oauth_callback=" + redirect_uri 
		+ "&oauth_consumer_key=" + clientAppId
		+ "&oauth_nonce=95613465" 
		+ "&oauth_signature_method=HMAC-SHA1"
		+ "&oauth_timestamp=" + new Date().getTime()
		+ "&oauth_version=1.0";
	
	alert("baseUrl: "+baseUrl);
	var encodedBaseUrl = encodeURI(baseUrl);
	
	alert("encodedBaseUrl: "+encodedBaseUrl);
	
//	GET&http%3A%2F%2Fwww.flickr.com%2Fservices%2Foauth%2Frequest_token
//	&oauth_callback%3Dhttp%253A%252F%252Fwww.example.com
//	%26oauth_consumer_key%3D653e7a6ecc1d528c516cc8f92cf98611
//	%26oauth_nonce%3D95613465%26
//	oauth_signature_method%3DHMAC-SHA1
//	%26oauth_timestamp%3D1305586162
//	%26oauth_version%3D1.0
	

//	var hmac = Crypto.HMAC(Crypto.SHA1, "Message", "Secret Passphrase");
//	var hmacBytes = Crypto.HMAC(Crypto.SHA1, "Message", "Secret Passphrase", {asBytes : true});
	
	
	var passphrase = clientAppId + "&" + appSecret;
	var oauthSignature = Crypto.HMAC(Crypto.SHA1, encodedBaseUrl, passphrase, {	asString : true	});
	
	
	alert("passphrase: "+passphrase + ", oauthSignature: "+ oauthSignature);
	
	
	var requestUrl = "http://www.flickr.com/services/oauth/request_token" + "?oauth_nonce=95613465" + "&oauth_timestamp=" + new Date().getTime()
	+ "&oauth_consumer_key=" + clientAppId + "&oauth_signature_method=HMAC-SHA1" + "&oauth_version=1.0" + "&oauth_signature=" + oauthSignature
	+ "&oauth_callback=" + encodeURIComponent(redirect_uri);
	
	alert("requestUrl: "+requestUrl);
	
	window.location.href = requestUrl;
	

	return true;
}

function loginToFlickrClientOld() {
	var redirect_uri = HOST_PREFIX + docBase + "/dashboard.jsf";
	window.location.href = flickrCodeUrl + "?client_id=" + clientAppId + "&redirect_uri=" + redirect_uri + "&state=" + state + "&scope=" + permissions;
	return true;
}


function getRequestToken() {
	var redirect_uri = HOST_PREFIX + docBase + "/dashboard.jsf";
	window.location.href = flickrCodeUrl + "?client_id=" + clientAppId + "&redirect_uri=" + redirect_uri + "&state=" + state + "&scope=" + permissions;
	return true;
}


function logoutFromFlickrClient() {
	var logoutToken = document.getElementById('dashboardForm:accessToken').value;
	var fbr_logout_uri = HOST_PREFIX + docBase + "/logout.jsf";
	var logoutUri = flickrLogoutUrl + "?next=" + fbr_logout_uri + "&access_token=" + logoutToken;

	window.location.href = logoutUri;
	return false;
}

function getAccessToken() {
	var redirect_uri = HOST_PREFIX + docBase + "/dashboard.jsf&state=fbr123fbr";
	var code = getUrlVars()["code"];

	var url = flickrAccessTokenUrl + "?client_id=" + clientAppId + "&redirect_uri=" + redirect_uri + "&client_secret=" + appSecret + "&code=" + code;

	window.location.href = url;
	return false;
}

/**
 * Get URL parameters & values with jQuery. Ex: To get the value of code var code = getUrlVars()["code"];
 */
function getUrlVars() {
	var vars = [], hash;
	var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	for ( var i = 0; i < hashes.length; i++) {
		hash = hashes[i].split('=');
		vars.push(hash[0]);
		vars[hash[0]] = hash[1];
	}
	return vars;

}
