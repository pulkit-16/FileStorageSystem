For h2 data base use = http://localhost:8080/h2-console/login.do?jsessionid=01bcf02fe254e4f6acefb4d80410e53d

used 4 Api end points
1.for new upload and update
POST:
 http://localhost:8080/newupload/{fileName}/{username}

 Add a file:
 BOdy->form-date->  key = file , value = "upload filefrom local system"

2.to download file of latest version of same name
GET
http://localhost:8080/download/{fileName}/{username}

3.to get all versions of file for particular user
GET:
http://localhost:8080/versions/{username}?fileName={fileName}

4.to choose from older version
select 'uuid' from all version

GET:
http://localhost:8080/olderversion/{uuid}


used H2 data base and all the information is in application.properties

