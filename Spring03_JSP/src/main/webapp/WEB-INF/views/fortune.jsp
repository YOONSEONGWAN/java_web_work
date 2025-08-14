<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/webapp/WEB-INF/views/fortune.jsp</title>
<style>
  :root{
    --bg:#f6f7fb; --card:#ffffff; --txt:#222; --muted:#667085;
    --brand:#3b82f6; --brand-hover:#2563eb; --good:#10b981; --bad:#ef4444; --neutral:#6b7280;
  }
  *{box-sizing:border-box}
  html,body{height:100%}
  body{
    margin:0; font-family:ui-sans-serif,system-ui,Segoe UI,Roboto,Apple SD Gothic Neo,Apple Color Emoji,Apple Gothic,Noto Sans KR,sans-serif;
    background:var(--bg); color:var(--txt); display:flex; align-items:center; justify-content:center; padding:24px;
  }
  .card{
    width:min(680px, 92vw); background:var(--card);
    border-radius:16px; box-shadow:0 10px 30px rgba(0,0,0,.06);
    padding:28px 24px 22px; transition:transform .2s ease;
  }
  .card:hover{ transform:translateY(-2px) }
  .card h1{ margin:0 0 16px; font-size:24px }
  .toolbar{ display:flex; gap:10px; align-items:center; margin-bottom:16px }
  .btn{
    appearance:none; border:0; border-radius:12px; padding:10px 16px;
    background:var(--brand); color:#fff; font-weight:700; cursor:pointer;
    transition:background .15s ease, transform .06s ease;
  }
  .btn:hover{ background:var(--brand-hover) }
  .btn:active{ transform:translateY(1px) }
  .result{
    display:flex; align-items:center; gap:8px;
    font-size:18px; font-weight:700; color:var(--brand-hover);
    background:#eef2ff; border:1px solid #dbe3ff; padding:12px 14px; border-radius:12px;
    margin-bottom:18px;
  }
  .result strong{ color:#111 }
  .list-wrap h4{ margin:8px 0 8px; color:var(--muted); font-weight:700 }
  ul.fortune-list{ margin:0; padding-left:18px }
  ul.fortune-list li{ padding:4px 0; color:#333 }
  /* 선택된 운세 강조색 (옵션) */
  .is-good strong{ color:var(--good) }
  .is-bad strong{ color:var(--bad) }
  .is-neutral strong{ color:#111 }
</style>
</head>
<body>
  <div class="card">
    <h1>오늘의 운세뽑기</h1>

    <div class="toolbar">
      <button id="btnFortune" class="btn">다시 뽑기</button>
    </div>

    <p id="fortuneResult" class="result">
      ✨ 오늘의 운세: <strong id="fortuneText">${fortune}</strong>
    </p>

    <div class="list-wrap">
      <h4>운세 리스트</h4>
      <ul class="fortune-list">
        <c:forEach var="tmp" items="${requestScope.luckyList}">
          <li>${tmp}</li>
        </c:forEach>
      </ul>
    </div>
  </div>

  <script>
    const resultEl = document.getElementById('fortuneResult');
    const textEl = document.getElementById('fortuneText');

    function paintByFortune(val){
      resultEl.classList.remove('is-good','is-bad','is-neutral');
      const good = ['대길','중길','소길','말길','반길','길'];
      const bad  = ['반흉','말흉','소흉','중흉','대흉','흉'];
      if (good.includes(val)) resultEl.classList.add('is-good');
      else if (bad.includes(val)) resultEl.classList.add('is-bad');
      else resultEl.classList.add('is-neutral');
    }
    // 초기 표시 색상
    paintByFortune(textEl.textContent.trim());

    document.getElementById('btnFortune').addEventListener('click', async function () {
      try {
        const res = await fetch('${pageContext.request.contextPath}/fortune/random', { method: 'GET' });
        if (!res.ok) throw new Error('HTTP ' + res.status);
        const text = (await res.text()).trim();
        textEl.textContent = text;
        paintByFortune(text);
      } catch (e) {
        console.error(e);
        textEl.textContent = '불러오기 실패 😢';
        resultEl.classList.remove('is-good','is-bad'); resultEl.classList.add('is-neutral');
      }
    });
  </script>
</body>
</html>
