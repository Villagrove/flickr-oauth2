
config changes:

change the the attributes in the following files
src/main/java/com/vu/scs/fb/util/Constants.java
src/main/webapp/resources/javascript/common.js

attributes to be updated: in java file
		 String HOST_PREFIX = "http://localhost:8080";
		 String CLIENT_APP_ID = "645787b14ab25e9ec621d25f7ad78ba1";
		 String APP_SECRET = "d8ec5bcf1e51cf34";
		 

in javascript file:
var HOST_PREFIX = "http://localhost:8080";
var clientAppId = "645787b14ab25e9ec621d25f7ad78ba1";
var appSecret = "d8ec5bcf1e51cf34";


--------------------------------------------------------------------------------------------------------------
home page url: 
http://localhost:8080/flickr-oauth2/home.jsf

http://localhost:8080/flickr-oauth2/home.jsf
http://localhost:8080/flickr-oauth2/home.faces
http://localhost:8080/flickr-oauth2/home.xhtml
--------------------------------------------------------------------------------------------------------------
jboss console:
http://127.0.0.1:9990
or
http://127.0.0.1:9990/console

--------------------------------------------------------------------------------------------------------------
to modify / update the app registration in flickr:
https://developers.flickr.com/

login with flickr suer id and click on apps on the top to make changes for the app registration

--------------------------------------------------------------------------------------------------------------

jboss by default uses iw logfawork, to use ouro4j.xml inside the war, place the followign file and exclude the log4j and slf4 inside tha file
src/main/webapp/WEB-INF/jboss-deployment-structure.xml

--------------------------------------------------------------------------------------------------------------
Shutting down jboss 7.1.1 AS7 in domain mode
./domain.sh --connect command=/host=master:shutdown

--------------------------------------------------------------------------------------------------------------

flickr developer guides:
http://www.flickr.com/services/api/misc.overview.html

get dev started:
http://www.flickr.com/services/api/auth.oauth.html#request_token


--------------------------------------------------------------------------------------------------------------
how to sign in javascript:
http://stackoverflow.com/questions/4337959/need-hmac-sha1-encryption-library-for-javascript
--------------------------------------------------------------------------------------------------------------
