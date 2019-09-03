package com.cloud.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DCResponse {
	 	@JsonProperty("Status")
	    private String status;
	    @JsonProperty("Authentication")
	    private Authentication authentication;
	    @JsonProperty("Responseinfo")
	    private Responseinfo responseinfo;
	    @JsonProperty("ContextData")
	    private ContextData contextdata;
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Authentication getAuthentication() {
			return authentication;
		}
		public void setAuthentication(Authentication authentication) {
			this.authentication = authentication;
		}
		public Responseinfo getResponseinfo() {
			return responseinfo;
		}
		public void setResponseinfo(Responseinfo responseinfo) {
			this.responseinfo = responseinfo;
		}
		public ContextData getContextdata() {
			return contextdata;
		}
		public void setContextdata(ContextData contextdata) {
			this.contextdata = contextdata;
		}
	    
}
