let userId = null;

document.addEventListener("DOMContentLoaded", function() {
    userId = getUserIdFromCookie();
    getChatRooms();
    findClientInfo();
});

async function getChatRooms() {
    try {
        const response = await fetch(`/chat-rooms/foreigners?userId=${userId}`);
        const data = await response.json();

        const chatRoomList = document.getElementById('chatRoomList');
        data.responses.forEach(chatRoom => {
            const chatRoomElement = createChatRoomElement(chatRoom);
            chatRoomList.appendChild(chatRoomElement);
        });
    } catch (error) {
        console.error('Error fetching chat rooms:', error);
    }
}

function getUserIdFromCookie() {
    const cookieString = document.cookie;
    const cookies = cookieString.split(';');

    for (let cookie of cookies) {
        const [cookieName, cookieValue] = cookie.split('=');
        if (cookieName.trim() === 'userId') {
            return cookieValue;
        }
    }

    return null;
}

function createChatRoomElement(chatRoom) {
    const chatRoomElement = document.createElement('div');
    chatRoomElement.classList.add('chat-room');

    chatRoomElement.addEventListener('click', function() {
        window.location.href = `/templates/client.html?chatRoomId=${chatRoom.chatRoomId}`;
    });

    const profileImg = document.createElement('img');
    profileImg.src = chatRoom.profileUrl;
    profileImg.alt = "Profile Picture";
    profileImg.classList.add('profile-img');
    chatRoomElement.appendChild(profileImg);

    //chat-room-detail 안에 상담 이름과 최신 메세지 넣기
    const chatRoomDetailElement = document.createElement('div');
    chatRoomDetailElement.classList.add('chat-room-details');

    const consultantName = document.createElement('p');
    consultantName.textContent = chatRoom.consultantName + '🖐️';
    consultantName.classList.add('name');
    chatRoomDetailElement.appendChild(consultantName);

    const contents = document.createElement('p');
    contents.textContent = chatRoom.contents;
    contents.classList.add('contents');
    chatRoomDetailElement.appendChild(contents);

    chatRoomElement.appendChild(chatRoomDetailElement);

    //chat room info에 최신 메세지 생성 날짜랑 안읽은 메세지 수 넣기
    const chatRoomInfoElement = document.createElement('div');
    chatRoomInfoElement.classList.add('chat-room-info');

    const chatMessageCreatedElement = document.createElement('div');
    chatMessageCreatedElement.textContent = `${chatRoom.messageCreatedAt}`;
    chatMessageCreatedElement.classList.add('chat-message-created');

    // 원 안에 안 읽음 숫자 넣기
    const centerCircleElement = document.createElement('div');
    centerCircleElement.classList.add('center-circle');
    const notReadCount = document.createElement('span');
    notReadCount.textContent = `${chatRoom.notReadCount}`;
    notReadCount.classList.add('not-read');
    centerCircleElement.appendChild(notReadCount);

    chatRoomInfoElement.appendChild(chatMessageCreatedElement);
    chatRoomInfoElement.appendChild(centerCircleElement);

    chatRoomElement.appendChild(chatRoomInfoElement);

    return chatRoomElement;
}

async function findClientInfo() {
    console.log("clientId:", userId);
    return fetch(`/users/${userId}/foreigner-info`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        clientLanguage = data.sourceLanguage;
        populateOpponentInfo(data)
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function populateOpponentInfo(data) {
    const profileNameElement = document.querySelector('.profile-name');
    const birthDateElement = document.querySelector('.profile-details span:nth-child(1)');
    const visaTypeElement = document.querySelector('.profile-details span:nth-child(2)');
    const languageElement = document.querySelector('.profile-details span:nth-child(3)');
    const residencePermitElement = document.querySelector('.profile-details span:nth-child(4)');

    profileNameElement.textContent = data.name;
    birthDateElement.textContent = 'Birth Day : ' + data.birthDate +'🎂';
    visaTypeElement.textContent = 'Visa Type : ' + data.visaType;
    languageElement.textContent = 'Language : ' + data.sourceLanguage;
    residencePermitElement.textContent = 'Resident Card Have : ' + data.hasResidentCard;
}

document.getElementById("plusChatRoom").addEventListener("click", function() {
    matchConsultant();
});

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
            <h2>Set chat room password</h2>
            <input type="password" id="password" name="password" required>
            <button id="submitPassword">Enter</button>
        </div>
    `;

    document.body.appendChild(modal);

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