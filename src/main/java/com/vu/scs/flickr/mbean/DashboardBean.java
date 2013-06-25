package com.vu.scs.flickr.mbean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vu.scs.flickr.bean.Person;
import com.vu.scs.flickr.bean.PersonDetail;
import com.vu.scs.flickr.service.BasicProfileService;
import com.vu.scs.flickr.service.FriendsListService;
import com.vu.scs.flickr.util.Constants;
import com.vu.scs.flickr.util.OAuthError;

@ManagedBean
@RequestScoped
public class DashboardBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(DashboardBean.class);

	private OAuthError oauthError;

	private String accessToken;

	private static final long serialVersionUID = 1L;

	private String code;

	private String state;

	private List<Person> personList;

	private PersonDetail personDetail;

	public List<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}

	public PersonDetail getPersonDetail() {
		return personDetail;
	}

	public void setPersonDetail(PersonDetail personDetail) {
		this.personDetail = personDetail;
	}

	public String getCode() {
		return code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public OAuthError getOauthError() {
		return oauthError;
	}

	public void setOauthError(OAuthError oauthError) {
		this.oauthError = oauthError;
	}

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

		String newCode = req.getParameter("code");

		logger.debug("newCode received: " + newCode);

		if (newCode != null) {
			int ret = retrieveToken(newCode);
			this.code = newCode;
		}

	}

	private int retrieveToken(String code) {
		logger.debug("trying to retrieve token with the code: " + code);
		
		String redirect_uri = Constants.FBR_DASHBOARD_URI;

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(Constants.FB_ACCESS_TOKEN_URI);

		try {

			String[][] parameters = { { "client_id", Constants.CLIENT_APP_ID }, { "client_secret", Constants.APP_SECRET },
					{ "redirect_uri", redirect_uri }, { "code", code } };

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);

			for (int i = 0; i < parameters.length; i++) {
				nameValuePairs.add(new BasicNameValuePair(parameters[i][0], parameters[i][1]));
			}

			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse resp = client.execute(post);

			logger.debug("resp received: " + resp);

			BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

			String message = "";
			String lineData;
			while ((lineData = rd.readLine()) != null) {
				message += lineData;
			}

			logger.debug("message received: " + message);

			String token = null;

			// Add more safety traps
			String[] params = message.split("&");
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					if (params[i].contains("access_token")) {
						String[] B = params[i].split("=");
						if (B != null) {
							token = B[1];
						}
						break;
					}
				}
			} else {
				// Let the user know about the error.
				return 0;
			}

			logger.debug("token received: " + token);

			accessToken = token;

			return 1;

		} catch (Exception e) {
			return 0;
		}

	}

	public String userBasicProfile() {
		logger.debug("entering  userBasicProfile... with code " + code);
		logger.debug("entering  userBasicProfile... with accessToken " + accessToken);

		if (StringUtils.isEmpty(code) || StringUtils.isEmpty(accessToken)) {
			return "home";
		}
		BasicProfileService basicProfileService = new BasicProfileService();
		try {
			personDetail = basicProfileService.getUserBasicProfile(accessToken);
		} catch (OAuthError e) {
			this.setOauthError(e);
			logger.error("OAuthError received: "+ e.getErrorMessage());
			return "error";
		}

		logger.debug("end of userBasicProfile...");
		return "basicProfile";

	}

	public String friendsList() {
		logger.debug("entering  userBasicProfile... with code " + code);
		logger.debug("entering  friendsList... with accessToken " + accessToken);

		if (StringUtils.isEmpty(code) || StringUtils.isEmpty(accessToken)) {
			return "home";
		}

		FriendsListService friendsListService = new FriendsListService();
		try {
			personList = friendsListService.getFriendsList(accessToken);
		} catch (OAuthError e) {
			this.setOauthError(e);
			logger.error("OAuthError received: "+ e.getErrorMessage());
			return "error";
		}
		logger.debug("end of friendsList.");
		return "friendsList";

	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}