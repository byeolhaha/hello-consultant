let localStreamElement = document.querySelector('#localStream');
let pcListMap = new Map();
let otherKeyList = [];
let localStream = undefined;
const messages = [];
let chatClient = null;
let clientId = null;
let chatRoomId = null;
let clientLanguage = null;
let consultantId = null;
let nextChatMessageId = '';
let hasNext = true;

function getRoomIdFromUrl() {
    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);
    return urlParams.get('chatRoomId');
}

document.addEventListener("DOMContentLoaded", async function() {
    chatRoomId = getRoomIdFromUrl();
    openChatPasswordModal();

    const messageInput = document.getElementById('message-input');

        messageInput.addEventListener('keydown', function(event) {
            if (event.key === 'Enter' && !event.shiftKey) {
                event.preventDefault();
                sendMessage();
            }
        });
});

async function fetchChatMessages() {
    const requestUrl = `/chat-rooms/${chatRoomId}/messages?myId=${clientId}&nextChatMessageId=${nextChatMessageId}&isFC=false`;

    const response = await fetch(requestUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    });

    if (response.ok) {
        const data = await response.json();
        const chatMessages = data.chatMessages;

        // 채팅 메시지를 처리합니다.
        for (let i = chatMessages.length - 1; i >= 0; i--) {
              messages.unshift(chatMessages[i]);
              displayMessages();
        }

        // 다음 메시지의 ID를 업데이트합니다.
        nextChatMessageId = data.nextChatMessageId;
        hasNext = data.hasNext;
    } else {
        console.error('Failed to fetch chat messages');
    }
}

function handleScroll(event) {
    const element = event.target;
    if (element.scrollTop === 0 && hasNext) {
        fetchChatMessages();
    }
}


// 채팅용 소켓 연결
const connectChat = async () => {
    const chatSocket = new SockJS('/ws/connect');
    chatClient = Stomp.over(chatSocket);

    const headers = {
            memberId: clientId,
            isFC : false
    };

    chatClient.connect(headers, function(frame) {
        chatClient.subscribe(`/queue/chats/${chatRoomId}`, function(message) {
            const messageData = JSON.parse(message.body);
            messageData.body.chatMessageTranslateResponses.forEach(
               message => {
                  messages.push(message);
               }
             );
             displayMessages();
             scrollDown();
        });

    });
}

window.onload = async function() {
    await enterChatRoom();
    await findRoomInfo();
    await connectChat();
    await findClientAndConsultantInfo();

    const messageList = document.getElementById('message-list');

    await fetchChatMessages();
    messageList.scrollTop = messageList.scrollHeight;
    messageList.addEventListener('scroll', handleScroll);
};

async function findClientAndConsultantInfo() {
    await getConsultantInfo();
    await findClientLanguage();
}

async function findRoomInfo() {
    return fetch(`/chat-rooms/${chatRoomId}`, {})
        .then(response => response.json())
        .then(data => {
            clientId = data.userId;
            consultantId = data.fcId;
        });
}

async function getConsultantInfo() {
    return fetch(`/users/${consultantId}/fc-info`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            }
        })
        .then(response => response.json())
        .then(data => {
            console.log("data:", data);
            const profileImage = document.querySelector('.profile-image');
            profileImage.innerHTML = `<img src="${data.profileUrl}" alt="프로필 이미지">`;

            const profileName = document.querySelector('.profile-name');
            profileName.textContent = data.name + ' 상담사';

            const introduce = document.querySelector('.introduce');
            introduce.textContent = data.introduceMessage + '🖐️';

            const telNumber = document.querySelector('.telNumber');
            telNumber.textContent = 'Tel ☎️ : ' + data.phoneNumber;
        });
}

async function findClientLanguage() {
    return fetch(`/users/${clientId}/foreigner-info`, {
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
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function sendMessage() {
     const newMessage = document.getElementById('message-input').value;
     if (newMessage && chatClient && chatClient.connected) {
         const chatMessage = {
             contents: newMessage,
             userId: clientId,
             isFC: false,
             targetLang: "KOREAN",
             sourceLang: clientLanguage
         };

         chatClient.send(`/app/chats/${chatRoomId}`, {}, JSON.stringify(chatMessage));
         document.getElementById('message-input').value = ''; // 메시지 입력란 초기화
     }
}

function displayMessages() {
 const messageList = document.getElementById('message-list');
     messageList.innerHTML = '';

     messages.forEach(message => {
          const originMessageElement = document.createElement('div');
          originMessageElement.className = `message ${message.isFC == false ? 'sent' : 'received'}`;

          const originContentElement = document.createElement('div');
          originContentElement.className = 'message-content';
          originContentElement.textContent = message.contents;

          originMessageElement.appendChild(originContentElement);

          messageList.appendChild(originMessageElement);

     });
}

function scrollDown() {
    messageList = document.getElementById('message-list');
    messageList.scrollTop = messageList.scrollHeight;
}

async function enterChatRoom() {
    try {
        const response = await fetch(`/chat-rooms/${chatRoomId}/messages`, {
            method: 'PATCH', // PATCH 메서드 사용
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
               isFC: false
            })
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
        throw error;
    }
}

function openChatPasswordModal() {
    var modal = document.createElement("div");
    modal.classList.add("modal");

    // 모달 내용을 추가합니다.
    modal.innerHTML = `
        <div class="modal-content">
            <h2>Enter the chat room password</h2>
            <input type="password" id="password" name="password" required>
            <button id="submitPassword">Enter</button>
        </div>
    `;

    document.body.appendChild(modal);

    var submitButton = modal.querySelector("#submitPassword");
    submitButton.addEventListener("click", function() {
        var passwordInput = modal.querySelector("#password");
        var password = passwordInput.value;

        fetch(`/chat-rooms/${chatRoomId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                chatRoomPassword: password
            })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
             return response.json();
        })
        .then(data => {
             if(!data.isMyUser) {
                  throw new Error('Invalid password');
             }

             alert('Successfully entered password 👍');
             modal.remove();
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Invalid password');
        });
    });
}