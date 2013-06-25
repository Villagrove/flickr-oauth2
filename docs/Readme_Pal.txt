
config changes:

change the the attributes in the following files
src/main/java/com/vu/scs/fb/util/Constants.java
src/main/webapp/resources/javascript/common.js

attributes to be updated:
		 String HOST_PREFIX = "http://localhost:8080";
		 String CLIENT_APP_ID = "247266912049837";
		 String APP_SECRET = "5624e24f6f98a2835da033422ba93798";
		 
		  var HOST_PREFIX = "http://localhost:8080";
var clientAppId = "247266912049837";
var appSecret = "5624e24f6f98a2835da033422ba93798";


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
to modify / update the app registration in facebook:
https://developers.facebook.com/

login with facebook suer id and click on apps on the top to make changes for the app registration

--------------------------------------------------------------------------------------------------------------

jboss by default uses iw logfawork, to use ouro4j.xml inside the war, place the followign file and exclude the log4j and slf4 inside tha file
src/main/webapp/WEB-INF/jboss-deployment-structure.xml

--------------------------------------------------------------------------------------------------------------
Shutting down jboss 7.1.1 AS7 in domain mode
./domain.sh --connect command=/host=master:shutdown

--------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------