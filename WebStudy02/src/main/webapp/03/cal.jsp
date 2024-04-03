<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%
int year = 0;
int month = 0; // 0 ~ 11

// 년도, 월 중 하나라도 지정(넘겨져 오지)되지 않으면 오늘날짜 기준으로 월달력 출력
if (request.getParameter("year") == null || request.getParameter("month") == null) {
   Calendar today = Calendar.getInstance();
   year = today.get(Calendar.YEAR);
   month = today.get(Calendar.MONTH);
} else {
   // 출력하고자 하는 달력의 년도와 월
   year = Integer.parseInt(request.getParameter("year")); // 2022,..
   month = Integer.parseInt(request.getParameter("month")); // 0 ~ 11

   // 이전달 클릭 year, month-1 / 다음달 클릭 year, month+1
   // -1 -> 11,year--  12 -> 0,year++
   if (month == -1) {
      month = 11;
      year = year - 1;
   }
   if (month == 12) {
      month = 0;
      year = year + 1;
   }
}
// 출력하고자 달의 1일 객체 + 1일 요일 + 마지막 날짜
Calendar firstDate = Calendar.getInstance();
firstDate.set(Calendar.YEAR, year);
firstDate.set(Calendar.MONTH, month);
firstDate.set(Calendar.DATE, 1);
int firstDay = firstDate.get(Calendar.DAY_OF_WEEK); // 1일의 요일 정보(1일,2월,....,7토)
int lastDate = firstDate.getActualMaximum(Calendar.DATE);

// 출력 알고리즘(td의 개수 구하기)
int startBlankCnt = firstDay - 1;
int endBlankCnt = 0;
if ((startBlankCnt + lastDate) % 7 != 0) {
   endBlankCnt = 7 - (startBlankCnt + lastDate) % 7;
}
int tdCnt = startBlankCnt + lastDate + endBlankCnt;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>달력</title>
<style>
html, body {
	height: 100%;
	margin: 0px;
}

td {
	width: 100px;
	height: 100px;
	border: 1px solid #ddd;
	text-align: center;
}

th {
	height: 30px;
	font-weight: normal;
	background-color: #f2f2f2;
}

.text-danger {
	color: red;
}

.text-primary {
	color: blue;
}

.container {
	margin-top: 20px;
}

.btn {
	padding: 5px 10px;
	text-decoration: none;
}

.calendar-header {
	display: flex;
	justify-content: space-between;
	margin-bottom: 10px;
}
</style>
</head>

<body>
	<div class="container">
		<div class="calendar-header">
			<span> <a class="btn"
				href="?year=<%=year%>&month=<%=month - 1%>"> [이전달] </a>
			</span> <span class="fw-bold" style="font-size: 1.5em;"><%=year%>년 <%=month + 1%>월</span>
			<span> <a class="btn"
				href="?year=<%=year%>&month=<%=month + 1%>"> [다음달] </a>
			</span>
		</div>
		<table>
			<tr>
				<th class="text-danger">일</th>
				<th>월</th>
				<th>화</th>
				<th>수</th>
				<th>목</th>
				<th>금</th>
				<th class="text-primary">토</th>
			</tr>
			<tr>
				<%
            for (int i = 1; i <= tdCnt; i++) {
            %>
				<td>
					<%
               if (i > startBlankCnt && i <= startBlankCnt + lastDate) {
                  if (i % 7 == 0) {
               %> <span class="text-primary"><%=i - startBlankCnt%></span>
					<%
 } else if (i % 7 == 1) {
 %> <span class="text-danger"><%=i - startBlankCnt%></span> <%
 } else {
 %> <%=i - startBlankCnt%> <%
 }
 } else {
 %> &nbsp; <%
 }
 %>
				</td>
				<%
            if (i != tdCnt && i % 7 == 0) {
            %>
			</tr>
			<tr>
				<%
            }
            }
            %>
			</tr>
		</table>
	</div>
</body>
</html>