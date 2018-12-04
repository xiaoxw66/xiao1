'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var password = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

/**
 * 连接socket
 * @param event
 */
function connect(event) {
    username = document.querySelector('#name').value.trim();
    if (username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);


        stompClient.connect({}, onConnected, onError);
    }
    // 中止事件
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic  连接webSocket
    stompClient.subscribe('/topic/public', onMessageReceived);
    console.log("我没有连接成onConnected");
    // 连接成功后向服务器发送一条消息,我连接了
    // Tell your username to the server
    stompClient.send("/app/chat.addUser", {}, JSON.stringify({sender: username, type: 'JOIN'}));

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
    alert("connection error")
    location.reload()
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();

    if (messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    console.log("getAvatarColor what is " + messageSender)
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

function login() {
    console.log("111111111111111111111");
    username = document.querySelector('#name').value.trim();
    password = document.querySelector('#password').value.trim();
    var URL = "http://localhost:8088/login/login?userAccount=" + username + "&password=" + password + "&validateCode=qw";
    console.log(URL)
    $.ajax({
        type: "POST", //提交方式
        url: URL,//路径
        data: {},//数据，这里使用的是Json格式进行传输
        success: function (result) {//返回数据根据结果进行相应的处理
            console.log("result")

        },
        error: function () {

        }
    });


}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
