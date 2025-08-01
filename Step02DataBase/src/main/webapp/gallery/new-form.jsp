<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/gallery/new-form.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
<style>
    .drop-zone {
        border: 2px dashed #0d6efd;
        cursor: pointer;
        border-radius: 10px;
        background-color: #f8f9fa;
        display:flex;
        align-items: center;
        justify-content: center;
        height: 200px;
        
    }
    /* drop zone 에 파일을 드래그해서 위에 올렸을 때 적용할 css */
    .drop-zone.dragover {
        background-color: #e9ecef;
    }
    .preview img {
        max-width: 120px;
        margin: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }
    .preview-item {
        position: relative;
        display: inline-block;
    }
	.remove-btn {
	    position: absolute;
	    top: 11px;
	    right: 11px;
	    background-color: #ffffffcc; /* 반투명 흰색 */
	    color: #333;
	    border: none;
	    border-radius: 4px;
	    width: 20px;
	    height: 20px;
	    font-size: 16px;
	    cursor: pointer;
	    text-align: center;
	    transition: background-color 0.2s, color 0.2s;
	    box-shadow: 0 1px 4px rgba(0,0,0,0.2);
	    display: flex;
		align-items: center;
		justify-content: center;
	}
	
	.remove-btn:hover {
	    background-color: #dc3545;
	    color: white;
	}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/include/navbar.jsp">
    <jsp:param name="thisPage" value="gallery"/>
</jsp:include>
<div class="container mt-4">
    <h2><i class="bi bi-image"></i> 새 갤러리 작성</h2>
    <form action="${pageContext.request.contextPath }/gallery/save" method="post" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="title" class="form-label">제목</label>
            <input type="text" class="form-control" id="title" name="title" required>
        </div>
        <div class="mb-3">
            <label for="content" class="form-label">내용</label>
            <textarea class="form-control" id="content" name="content" rows="6" required></textarea>
        </div>

        <div class="mb-3">
            <label class="form-label">이미지 업로드 (Drag & Drop 또는 클릭)</label>
            <div class="drop-zone" id="dropZone">
                이곳에 이미지를 끌어다 놓거나 클릭하여 선택하세요.
                <input type="file" name="images" id="fileInput" multiple hidden>
            </div>
            <div class="preview d-flex flex-wrap mt-3" id="preview"></div>
        </div>

        <button type="submit" class="btn btn-primary">등록</button>
    </form>
</div>

<script>
    const dropZone = document.getElementById("dropZone");
    const fileInput = document.getElementById("fileInput");
    const preview = document.getElementById("preview");

    let selectedFiles = [];

    // drop zone 을 클릭했을 때 input type="file" 을 강제 클릭해서 파일 선택창이 열리도록 한다. 
    dropZone.addEventListener("click", () => fileInput.click());

    // drop zone 에 마우스가 드래그해서 들어갔을때 
    dropZone.addEventListener("dragover", (e) => {
        e.preventDefault();
        // add dragover class -> 그러면 .dropzone.dragover 클래스가 됨 -> 색상 변경  
        dropZone.classList.add("dragover");
    });
 // drop zone 에 마우스가 드래그해서 나갔을때
    dropZone.addEventListener("dragleave", () => {
    	// add dragover class -> 그러면 .dropzone.dragover 클래스가 안 됨 -> 색상 원위치   
        dropZone.classList.remove("dragover");
    });
	// 파일을 끌어다가 dorp 했을 때,
    dropZone.addEventListener("drop", (e) => {
        e.preventDefault();
        dropZone.classList.remove("dragover"); // 색상 원위치
        // 선택된 파일객체가 들어있는 배열을 기능을 쓸 수 있는 files 배열로 바꿔 넣는다.
        const files = Array.from(e.dataTransfer.files);
        // 기존에 선택된 파일이 있는 배열에 합친다.
        selectedFiles = [...selectedFiles, ...files]; // = selectedFiles.concat(files)
        // 프뷰(미리보기) 업데이트
        updatePreview();
    });
	// drag drop 이 아니고 파일을 직접 선택했을 때, input type="file" 에 change 이벤트가 발생한다.
    fileInput.addEventListener("change", () => {
    	// 위와 같은 동작을 한다.
        const files = Array.from(fileInput.files);
    	// selectedFiles 는 빈 배열객체
        selectedFiles = selectedFiles.concat(files);
        updatePreview();
    });
	
	// previe 를 업데이트 하는 함수(내부에서 await 이 있는 비동기 함수이기 때문에 async 키워드를 붙여서 만들었다.)
    async function updatePreview() {
		// 이미 출력된 이미지를 모두 없애고
        preview.innerHTML = "";

		// input type="file" 에 value 를 넣어 줄 정보를 구하기 위한 객체
        const dataTransfer = new DataTransfer();

		// 반복문 돌면서 선택된 파일 객체를 순서대로 참조하면서
        for (let i = 0; i < selectedFiles.length; i++) {
        	// i 번째 파일 객체를 불러와서
            const file = selectedFiles[i];
        	// 만일 이미지 파일이 아니라면 continue (continue 아래의 코드를 실행하지 않고 for 문을 계속 실행)
            if (!file.type.startsWith("image/")) continue; // return 하면 함수가 끝나니까 continue로 다시 for문에 넘김

            try {
            	// 선택한 이미지 파일을 읽어서 결과를 얻어냄
            	// await 이 있으면 promise 객체가 resolve 될 때까지 기다린다. 
                const imageUrl = await readFileAsDataURL(file);  

                const container = document.createElement("div");
                container.classList.add("preview-item")

                const img = document.createElement("img");
                // img.src = imageUrl;
                img.setAttribute("src", imageUrl);

                const btn = document.createElement("button");
                // btn.className = "remove-btn";
                btn.classList.add("remove-btn")
                btn.innerText = "×";
                btn.addEventListener("cilck", () => {
                	// selectedFiles 배열에서 i 번째 방으로부터 1개의 item 삭제 
                    selectedFiles.splice(i, 1);
                	// 삭제 후 preview 가 다시 update 되도록 함수를 다시 호출해준다.
                    updatePreview();
                });

                container.insertAdjacentElement("beforeend",img);
                container.insertAdjacentElement("beforeend", btn);
                preview.insertAdjacentElement("beforeend", container);
				// DateTransfer 객체에 파일객체를 추가
                dataTransfer.items.add(file);
            } catch (err) {
                console.error("이미지 로딩 실패:", err);
            }
        }
        // 선택한 파일(이미지 파일만 선별)객체가 들어있는 배열을 input type="file" 의 value 로 넣어준다. 
        fileInput.files = dataTransfer.files;
    }

    // 전달된 파일을 읽어들이는 Promise 객체를 리턴해주는 함수 
    function readFileAsDataURL(file) {
        return new Promise((resolve, reject) => {
        	// FileReader 객체를 이용해서 파일을 읽어들이고
            const reader = new FileReader();
        	// 모두가 읽었을 때 실행할 함수 
            reader.onload = () => {
            	// 읽은 date url 문자열을 resolve() 함수를 호출하면서 전달(Promise 가 해결된다.)
            	resolve(reader.result)
            };
            reader.readAsDataURL(file);
        });
    }
</script>

</body>
</html>