<%@page import="com.adrop.dropbox.client.Account"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Account</title>
</head>
<body>
  <div>After Account Login Page</div>
  <div>
    <ul>
      <li>${account.country}</li>
      <li>${account.displayName}</li>
      <li>${account.quotaNormal}</li>
      <li>${account.quotaQuota}</li>
      <li>${account.quotaShared}</li>
      <li>${account.uid}</li>
      <li>${account.referralLink}</li>
    </ul>
  </div>
</body>
</html>