let Msg = class {
    constructor(text) {
        this.type = "message"
        this.text = text ? text : document.getElementById("input_msg").value
        this.user = username
        this.time = Date.now()
    }
};

$(function () {
    //const username = [[${#authentication.principal.user.id}]];
    console.log("ready:" + username);
    if (!username || username == undefined) {
        alert("invalid username :" + username);
        return;
    }

    $("#input_msg").keyup(function (e) {
        if (e.keyCode === 13) {
            send();
        }
    });
    $("#button_send").on("click", (e) => {
        send();
    });

    function disconnect() { websocket.close(); }
    $("#button_disconnect").on("click", (e) => {
        disconnect();
    })
    

    const websocket = new WebSocket("ws://localhost:65530/ws/chat");
    websocket.onmessage = onMessage;
    websocket.onopen = onOpen;
    websocket.onclose = onClose;

    function send(str) {
        if ((!!str) == (!!document.getElementById("input_msg").value)) {
            console.error("empty str");
            return;
        }
        websocket.send(JSON.stringify(new Msg(str)));
        document.getElementById("input_msg").value = '';
    }
    function onClose(e) {
        send(" left the chatroom.");
    }

    function onOpen(e) {
        send(" has joined the chatroom.");
    }

    function onMessage(msg) {
        console.log(msg.data);
        var msg_data = JSON.parse(msg.data);
        var time = new Date(msg_data.time);
        console.log(time);

        //people those are logged in
        console.log("username : " + username + ", msg_data.user : " + msg_data.user);


        let str = "";
        switch (msg.type) {
            case "message":
                //separate a current user from others
                str = "<div class='alert ";
                if (username == msg_data.user) {
                    str += "alert-secondary' style='text-align:right'>";
                } else {
                    str += "alert-warning'>";
                }
                str += "<b><span class='msg_username'>" + msg_data.user + "</span></b> " + msg_data.text;
                str += "</div>";
                $("#msgArea").append(str).scrollTop($(msgArea).prop('scrollHeight')); //$("#msgArea").height());
                break;
            case "userlist":
                var ul = "";
                for (i = 0; i < msg.users.length; i++) {
                    ul += msg.users[i] + "<br>";
                }
                document.getElementById("userlist").innerHTML = ul;
                break;
            default:
                console.error(msg.type);
                break;
        }
    }
})