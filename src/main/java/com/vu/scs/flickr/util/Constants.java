package com.vu.scs.flickr.util;

public interface Constants {
	// Pal
	String HOST_PREFIX = "http://localhost:8080";
	String CLIENT_APP_ID = "645787b14ab25e9ec621d25f7ad78ba1";
	String APP_SECRET = "d8ec5bcf1e51cf34";
	// http://localhost:8080/flickr-oauth2/home.jsf

	String STATE = "flickr123oauth2";

	String DOC_BASE = "/flickr-oauth2";

	String FB_ACCESS_TOKEN_URI = "https://graph.flickr.com/oauth/access_token";
	String FB_BASIC_INFO_URI = "https://graph.flickr.com/me";
	String FB_FRIENDS_LIST_URI = "https://graph.flickr.com/me/friends";

	String FBR_DASHBOARD_URI = HOST_PREFIX + DOC_BASE + "/dashboard.jsf";
	String FBR_ERROR_URI = HOST_PREFIX + DOC_BASE + "/error.jsf";
	String FBR_ACCESS_DENIED_URI = HOST_PREFIX + DOC_BASE + "/accessDenied.jsf";

}
