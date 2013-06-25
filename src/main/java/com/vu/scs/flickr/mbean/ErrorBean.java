package com.vu.scs.flickr.mbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vu.scs.flickr.util.OAuthError;

@ManagedBean
@RequestScoped
public class ErrorBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(BasicProfileBean.class);

	private static final long serialVersionUID = 1L;

	private OAuthError oauthError;

	public OAuthError getOauthError() {
		return oauthError;
	}

	public void setOauthError(OAuthError oauthError) {
		this.oauthError = oauthError;
	}

	@ManagedProperty(value = "#{dashboardBean}")
	// this is EL name of your bean
	private DashboardBean dashboardBean;

	public void setDashboardBean(DashboardBean dashboardBean) {
		this.dashboardBean = dashboardBean;
	}

	@PostConstruct
	private void init() {
		oauthError = dashboardBean.getOauthError();
		 logger.debug("oauthError:"+ oauthError);
	}

}