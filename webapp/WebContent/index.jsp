<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Ultimate Tic-Tac-Toe</title>
	</head>
	<body>
		<form action="Identificar.action" method="post">
			<table border="1">
				<tr>
					<td>Correo electronico:</td>
					<td><input type="text" name="email" maxlength="30" size="20"></td>
				</tr>
				
				<tr>
					<td>Contraseña:</td>
					<td><input type="password" name="passwd" maxlength="30" size="20"></td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" value="Entrar">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>