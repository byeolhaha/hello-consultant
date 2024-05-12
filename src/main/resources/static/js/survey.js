 let userId = null;
 let fcId = null;
 let chatRoomId = null;

document.addEventListener("DOMContentLoaded", function() {
    var form = document.getElementById("clientSurveyForm");

    form.addEventListener("submit", async function(event) {
        event.preventDefault();

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

        setCookie('userId', userId, 30);

        alert('설문조사 완료');
        await matchConsultant();

    } catch (error) {
        console.error('Error:', error );
        alert('설문조사 실패');
    }
}

function setCookie(name, value, days) {
    const date = new Date();
    date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
    const expires = "expires=" + date.toUTCString();
    document.cookie = name + "=" + value + ";" + expires + ";path=/";
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
        alert('There is no consultant available. Please wait.');
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
            <h2>Set chat room password</h2>
            <input type="password" id="password" name="password" required>
            <button id="submitPassword">Enter</button>
        </div>
    `;

    document.body.appendChild(modal);

    var closeButton = modal.querySelector(".close");
    closeButton.addEventListener("click", function() {
        modal.remove();
    });

    var submitButton = modal.querySelector("#submitPassword");
    submitButton.addEventListener("click", function() {
        var passwordInput = modal.querySelector("#password");
        var password = passwordInput.value;

        if (!validatePassword(password)) {
            alert("Password must be at least 10 characters long and must contain all special characters, English, and numbers.");
            return;
        }

        fetch(`/chat-rooms/${chatRoomId}/passwords`, {
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
            alert('Password setting is complete.');
            modal.remove();

            window.location.href = `/templates/client.html?chatRoomId=${chatRoomId}`;
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while setting the password.');
        });
    });
}

function validatePassword(password) {
    const regex = /^(?=.*[!@#$%^&*])(?=.*[a-zA-Z])(?=.*[0-9]).{10,}$/;
    return regex.test(password);
}