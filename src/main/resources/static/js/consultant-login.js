document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var employeeNumber = document.getElementById('employeeNumber').value;
    var password = document.getElementById('password').value;

    var url = '/consultants/passwords';
    var requestBody = {
        employeeNumber: employeeNumber,
        consultantPassword : password
    };

    fetch(url, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })
    .then(response => response.json())
    .then(data => {
         if (data.isMyConsultant === true) {
                    window.location.href = `/templates/consultant-chatrooms.html?fcId=${data.fcId}`;
         } else {
              // 로그인 실패 알림창 띄우기
              alert('로그인에 실패했습니다. 사용자 이름과 비밀번호를 확인해주세요.');
          }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('로그인에 실패했습니다. 사용자 이름과 비밀번호를 확인해주세요.');
    });
});