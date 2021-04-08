<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <title>Игра</title>
 </head>
 <body>
  <form action="" th:action="@{/newGame}" method="post">
    <input type="submit" value="Начать играть">
  </form>

 </body>
</html>