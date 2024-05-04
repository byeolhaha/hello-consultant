let localStreamElement = document.querySelector('#localStream');
let pcListMap = new Map();
let otherKeyList = [];
let localStream = undefined;
const messages = [];
let chatClient = null;
let clientId = null;
let consultantId = null;
let chatRoomId = null;
let clientLanguage = null;

function getRoomIdFromUrl() {
    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);
    return urlParams.get('chatRoomId');
}

document.addEventListener("DOMContentLoaded", function() {
    chatRoomId = getRoomIdFromUrl();

    const messageInput = document.getElementById('message-input');

    messageInput.addEventListener('keydown', function(event) {
       if (event.key === 'Enter' && !event.shiftKey) {
              event.preventDefault();
              sendMessage();
           }
    });
});

// 채팅용 소켓 연결
const connectChat = async () => {
    const chatSocket = new SockJS('/ws/connect');
    chatClient = Stomp.over(chatSocket);

    chatClient.connect({}, function(frame) {
        console.log('Connected as client');
        chatClient.subscribe(`/queue/chats/${chatRoomId}`, function(message) {
            const messageData = JSON.parse(message.body);
             messages.push(messageData);
             displayMessages();

        });
    });
}

// 방 번호받고 입장, 웹소켓 실행
window.onload = async function() {
    await connectChat();
    await findClientIdAndLanguage();
};

async function findClientIdAndLanguage() {
    await findClientId();
    await findClientInfo();
}

function findClientId() {
    return fetch(`/chat-rooms/${chatRoomId}`, {})
        .then(response => response.json())
        .then(data => {
            clientId = data.userId;
            consultantId = data.fcId;
            console.log("dddclientId:", clientId);
        });
}

async function findClientInfo() {
    console.log("clientId:", clientId);
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

    // 가져온 데이터를 각 요소에 채워 넣기
    profileNameElement.textContent = data.name;
    birthDateElement.textContent = '생년월일: ' + data.birthDate;
    visaTypeElement.textContent = '비자타입: ' + data.visaType;
    languageElement.textContent = '언어: ' + data.sourceLanguage;
    residencePermitElement.textContent = '외국인 거주증 여부: ' + data.hasResidentCard;
}

function sendMessage() {
    const newMessage = document.getElementById('message-input').value;
    if (newMessage && chatClient && chatClient.connected) {
        const chatMessage = {
            contents: newMessage,
            userId: consultantId,
            isFC : true,
            targetLang : clientLanguage,
            sourceLang : "KOREAN"
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
         originMessageElement.className = `message ${message.body.isFC == true ? 'sent' : 'received'}`;

         const translatedMessageElement = document.createElement('div');
         translatedMessageElement.className = `message ${message.body.isFC == true ? 'sent' : 'received'}`;

         const originContentElement = document.createElement('div');
         originContentElement.className = 'message-content';
         originContentElement.textContent = message.body.originContents; // 번역 전 내용 설정

         const translatedContentElement = document.createElement('div');
         translatedContentElement.className = 'message-content';
         translatedContentElement.textContent = message.body.translatedContents; // 번역 후 내용 설정

         originMessageElement.appendChild(originContentElement); // 번역 전 내용을 메시지에 추가
         translatedMessageElement.appendChild(translatedContentElement); // 번역 후 내용을 메시지에 추가

         messageList.appendChild(originMessageElement);
         messageList.appendChild(translatedMessageElement);// 메시지 리스트에 메시지 추가
     });

     // 메시지가 추가될 때마다 스크롤을 맨 아래로 이동
     messageList.scrollTop = messageList.scrollHeight;
}
