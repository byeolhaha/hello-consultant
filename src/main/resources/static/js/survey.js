 let userId = null;
 let fcId = null;
 let chatRoomId = null;

document.addEventListener("DOMContentLoaded", function() {

    var form = document.getElementById("clientSurveyForm");

    form.addEventListener("submit", async function(event) {
        event.preventDefault(); // 기본 제출 동작 방지

        // 설문조사 데이터 수집
        var formData = new FormData(form);
        var name = formData.get("name");
        var birthDate = formData.get("birthDate");
        var language = formData.get("language");
        var visaType = formData.get("visaType");
        var hasResidentCard = formData.get("hasResidentCard") == "Yes" ? true : false;

        await processSurveyData(name, birthDate, language, visaType, hasResidentCard);
    });

});

async function processSurveyData(name, birthDate, language, visaType, hasResidentCard) {
    try {
        const response = await fetch('/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                language: language,
                visaType: visaType,
                hasResidentCard: hasResidentCard,
                birthDate: birthDate,
                name: name
            })
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        userId = data.userId;

        alert('설문조사 완료');
        await matchConsultant(); // 이 부분을 수정하여 await 키워드를 사용하여 기다리도록 변경

    } catch (error) {
        console.error('Error:', error );
        alert('설문조사 실패');
    }
}

async function createChatRoom() {
    try {
        const response = await fetch('/chat-rooms', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                 fcId: fcId,
                 userId: userId
            })
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        chatRoomId = data.chatRoomId;

        openChatPasswordModal(chatRoomId);
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
        alert('채팅방 생성 실패');
        throw error;
    }
}

async function matchConsultant() {
    try {
        const response = await fetch('/consultants', {
            method: 'PATCH', // PATCH 메서드 사용
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            body: JSON.stringify({}) // 빈 JSON 객체 전송
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        fcId = data.fcId;

        console.log("fcId:", fcId);

        await createChatRoom();
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
        alert('상담 가능한 설계사가 없습니다.');
        throw error;
    }
}

function openChatPasswordModal(chatRoomId) {
    var modal = document.createElement("div");
    modal.classList.add("modal");

    // 모달 내용을 추가합니다.
    modal.innerHTML = `
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>채팅방 비밀번호 설정</h2>
            <p>비밀번호를 입력하세요:</p>
            <input type="password" id="password" name="password" required>
            <button id="submitPassword">비밀번호 설정</button>
        </div>
    `;

    // 모달을 body에 추가합니다.
    document.body.appendChild(modal);

    // 모달이 닫히도록 하는 close 버튼에 대한 이벤트 처리
    var closeButton = modal.querySelector(".close");
    closeButton.addEventListener("click", function() {
        modal.remove(); // 모달을 제거합니다.
    });

    // 비밀번호 설정 버튼에 대한 이벤트 처리
    var submitButton = modal.querySelector("#submitPassword");
    submitButton.addEventListener("click", function() {
        var passwordInput = modal.querySelector("#password");
        var password = passwordInput.value;

        // 비밀번호 유효성 검사
        if (!validatePassword(password)) {
            alert("비밀번호는 10자 이상이어야 하며, 특수문자, 영어, 숫자를 모두 포함해야 합니다.");
            return; // 유효하지 않은 비밀번호일 경우 함수 종료
        }

        // 유효한 비밀번호인 경우 서버로 전송
        fetch('/chat-rooms/password', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                chatRoomPassword: password,
                chatRoomId: chatRoomId
            })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            alert('비밀번호 설정이 완료되었습니다.');
            modal.remove();

            window.location.href = `/client.html?chatRoomId=${chatRoomId}`;
        })
        .catch(error => {
            console.error('Error:', error);
            alert('비밀번호 설정 중 오류가 발생했습니다.');
        });
    });
}

function validatePassword(password) {
    // 비밀번호가 10자 이상이고, 특수문자, 영어, 숫자를 모두 포함하는지 확인
    const regex = /^(?=.*[!@#$%^&*])(?=.*[a-zA-Z])(?=.*[0-9]).{10,}$/;
    return regex.test(password);
}