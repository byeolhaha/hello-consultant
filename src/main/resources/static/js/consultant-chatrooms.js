let userId = 1;

document.addEventListener("DOMContentLoaded", function() {
    getChatRooms();
    getConsultantInfo();
});

async function getChatRooms() {
    try {
        const response = await fetch(`/chat-rooms/consultants?userId=${userId}`);
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

function createChatRoomElement(chatRoom) {
    const chatRoomElement = document.createElement('div');
    chatRoomElement.classList.add('chat-room');

    chatRoomElement.addEventListener('click', function() {
        window.location.href = `/templates/consultant.html?chatRoomId=${chatRoom.chatRoomId}`;
    });

    //chat-room-detail 안에 외국인 이름과 최신 메세지 넣기
    const chatRoomDetailElement = document.createElement('div');
    chatRoomDetailElement.classList.add('chat-room-details');

    const foreignerName = document.createElement('p');
    foreignerName.textContent = chatRoom.foreignerName + '🖐️';
    foreignerName.classList.add('name');
    chatRoomDetailElement.appendChild(foreignerName);

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

async function getConsultantInfo() {
    return fetch(`/users/${userId}/fc-info`, {
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
