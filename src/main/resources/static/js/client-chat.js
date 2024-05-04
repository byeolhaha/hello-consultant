let localStreamElement = document.querySelector('#localStream');
let pcListMap = new Map();
let otherKeyList = [];
let localStream = undefined;
const messages = [];
let chatClient = null;
let clientId = null;
let chatRoomId = null;
let clientLanguage = null;
let consultantId = 1;

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
    await getConsultantInfo();
};

async function findClientIdAndLanguage() {
    await findClientId();
    await findClientLanguage();
}

function findClientId() {
    return fetch(`/chat-rooms/${chatRoomId}`, {})
        .then(response => response.json())
        .then(data => {
            clientId = data.userId;
        });
}

function getConsultantInfo() {
    return fetch(`/users/${consultantId}/fc-info`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            }
        })
        .then(response => response.json())
        .then(data => {
              console.log("data:",data);
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
            isFC : false,
            targetLang : "KOREAN",
            sourceLang : clientLanguage
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
          originMessageElement.className = `message ${message.body.isFC == false ? 'sent' : 'received'}`;

          const translatedMessageElement = document.createElement('div');
          translatedMessageElement.className = `message ${message.body.isFC == false ? 'sent' : 'received'}`;

          const originContentElement = document.createElement('div');
          originContentElement.className = 'message-content';
          originContentElement.textContent = message.body.originContents; // 번역 전 내용 설정

          const translatedContentElement = document.createElement('div');
          translatedContentElement.className = 'message-content';
          translatedContentElement.textContent = message.body.translatedContents; // 번역 후 내용 설정

          const profileImageContainer = document.createElement('div');
          profileImageContainer.className = 'profile-image-container';

          const profileImage = document.createElement('img');
          profileImage.src = '/foreigner.jpg';
          profileImage.alt = 'Profile Image';
          profileImage.style.borderRadius = '50%';


          originMessageElement.appendChild(originContentElement);
          translatedMessageElement.appendChild(translatedContentElement);

          messageList.appendChild(originMessageElement);
          messageList.appendChild(translatedMessageElement);// 메시지 리스트에 메시지 추가
     });

     // 메시지가 추가될 때마다 스크롤을 맨 아래로 이동
     messageList.scrollTop = messageList.scrollHeight;
}

