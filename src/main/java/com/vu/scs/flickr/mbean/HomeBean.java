package com.vu.scs.flickr.mbean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vu.scs.flickr.util.Constants;
import com.vu.scs.flickr.util.Signature;

@ManagedBean
@RequestScoped
public class HomeBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(HomeBean.class);

	@PostConstruct
	public void init() {

		logger.debug("entering init..");

		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String error_reason = req.getParameter("error_reason");
		if (error_reason != null) {
			try {
				// you may want to pass this error to UI
				String error_redirect_uri = Constants.FBR_ACCESS_DENIED_URI;
				String error_desc = req.getParameter("error_description");

				logger.debug("User denied access to the FBR app, error_reason: " + error_reason + ", error_desc: " + error_desc);
				logger.debug("redirecting to " + error_redirect_uri);
				((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect(error_redirect_uri);
			} catch (Exception e) {
				logger.error("Exception while redirecting to FBR access denied error page", e);
			}
		}

		String newCode = req.getParameter("oauth_token");
		String oauthCodeSecret = req.getParameter("oauth_token_secret");
		String oauthCallbackConfirmed = req.getParameter("oauth_callback_confirmed");

		logger.debug("newCode received oauth_token: " + newCode + ", oauth_token_secret:" + oauthCodeSecret + ", oauthCallbackConfirmed: "
				+ oauthCallbackConfirmed);

		if (newCode != null) {
			// int ret = retrieveToken(newCode, oauthCodeSecret);
			// this.code = newCode;
		}

	}

	private String getRequestTokenUrl() throws SignatureException, UnsupportedEncodingException {
		// var redirect_uri = HOST_PREFIX + docBase + "/dashboard.jsf";

		String redirect_uri = "http://localhost:8080/flickr-oauth2/dashboard.jsf";

		String baseUrl = "GET http://www.flickr.com/services/oauth/request_token" + "?oauth_callback=" + redirect_uri + "&oauth_consumer_key="
				+ Constants.CLIENT_APP_ID + "&oauth_nonce=95613465" + "&oauth_signature_method=HMAC-SHA1" + "&oauth_timestamp="
				+ Calendar.getInstance().getTimeInMillis() + "&oauth_version=1.0";

		// alert("baseUrl: "+baseUrl);
		logger.debug("baseUrl {}", baseUrl);
		// var encodedBaseUrl = encodeURI(baseUrl);

		String encodedBaseUrl = URLEncoder.encode(baseUrl, "UTF-8");

		logger.debug("encodedBaseUrl: " + encodedBaseUrl);

		// GET&http%3A%2F%2Fwww.flickr.com%2Fservices%2Foauth%2Frequest_token
		// &oauth_callback%3Dhttp%253A%252F%252Fwww.example.com
		// %26oauth_consumer_key%3D653e7a6ecc1d528c516cc8f92cf98611
		// %26oauth_nonce%3D95613465%26
		// oauth_signature_method%3DHMAC-SHA1
		// %26oauth_timestamp%3D1305586162
		// %26oauth_version%3D1.0

		// var hmac = Crypto.HMAC(Crypto.SHA1, "Message", "Secret Passphrase");
		// var hmacBytes = Crypto.HMAC(Crypto.SHA1, "Message", "Secret Passphrase", {asBytes : true});

		String passphrase = Constants.CLIENT_APP_ID + "&" + Constants.APP_SECRET;
		// String oauthSignature = Crypto.HMAC(Crypto.SHA1, encodedBaseUrl, passphrase, { asString : true });

		String oauthSignature = Signature.signHmacSHA1(encodedBaseUrl, passphrase);
		String oauthSignatureEncoded = URLEncoder.encode(oauthSignature, "UTF-8");

		// logger.debug("passphrase: "+passphrase + ", oauthSignature: "+ oauthSignature);

		String requestUrl = "http://www.flickr.com/services/oauth/request_token" + "?oauth_nonce=95613465" + "&oauth_timestamp=" + new Date().getTime()
				+ "&oauth_consumer_key=" + Constants.CLIENT_APP_ID + "&oauth_signature_method=HMAC-SHA1" + "&oauth_version=1.0" + "&oauth_signature="
				+ oauthSignatureEncoded + "&oauth_callback=" + URLEncoder.encode(redirect_uri, "UTF-8");

		logger.debug("requestUrl: " + requestUrl);

		return requestUrl;

	}

	// public String requestAccessToken() {
	// logger.debug("entering  userBasicProfile... with code " + code);
	// logger.debug("entering  userBasicProfile... with accessToken " + accessToken);
	//
	// if (StringUtils.isEmpty(code) || StringUtils.isEmpty(accessToken)) {
	// return "home";
	// }
	// BasicProfileService basicProfileService = new BasicProfileService();
	// try {
	// personDetail = basicProfileService.getUserBasicProfile(accessToken);
	// } catch (OAuthError e) {
	// this.setOauthError(e);
	// logger.error("OAuthError received: " + e.getErrorMessage());
	// return "error";
	// }
	//
	// logger.debug("end of userBasicProfile...");
	// return "basicProfile";
	//
	// }

	public static void main(String[] args) {
		HomeBean homeBean = new HomeBean();
		try {
			homeBean.getRequestTokenUrl();
		} catch (Exception e) {
			logger.error("execp in main", e);
		}
	}

}