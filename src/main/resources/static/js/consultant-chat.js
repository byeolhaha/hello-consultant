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
let nextChatMessageId = '';
let hasNext = true;

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

async function fetchChatMessages() {
    const requestUrl = `/chat-rooms/${chatRoomId}/messages?myId=${clientId}&nextChatMessageId=${nextChatMessageId}&isFC=false`;
    console.log('Request URL:', requestUrl); // 요청 URL 출력

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
                memberId: consultantId,
                isFC : true
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

// 방 번호받고 입장, 웹소켓 실행
window.onload = async function() {
    await enterChatRoom();
    await findClientIdAndLanguage();
    await connectChat();
};

async function findClientIdAndLanguage() {
    await findClientId();
    await findClientInfo();

    const messageList = document.getElementById('message-list');

    await fetchChatMessages();
    messageList.scrollTop = messageList.scrollHeight;
    messageList.addEventListener('scroll', handleScroll);
}

function findClientId() {
    return fetch(`/chat-rooms/${chatRoomId}`, {})
        .then(response => response.json())
        .then(data => {
            clientId = data.userId;
            consultantId = data.fcId;
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
          originMessageElement.className = `message ${message.isFC == true ? 'sent' : 'received'}`;

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
               isFC: true
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
