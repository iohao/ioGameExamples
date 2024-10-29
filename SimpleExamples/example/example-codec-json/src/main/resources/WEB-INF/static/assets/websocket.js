function formatDate(now) {
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var date = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();
    return year + "-" + (month = month < 10 ? ("0" + month) : month) + "-" + (date = date < 10 ? ("0" + date) : date) + " " + (hour = hour < 10 ? ("0" + hour) : hour) + ":" + (minute = minute < 10 ? ("0" + minute) : minute) + ":" + (second = second < 10 ? ("0" + second) : second);
}

var websocket;

function addsocket() {

    var wsaddr = "ws://127.0.0.1:10100/websocket";
    StartWebSocket(wsaddr);
}


function StartWebSocket(wsUri) {
    websocket = new WebSocket(wsUri);
    websocket.binaryType = 'arraybuffer';

    websocket.onopen = function (evt) {
        onOpen(evt, wsUri)
    };
    websocket.onclose = function (evt) {
        onClose(evt)
    };
    websocket.onmessage = function (evt) {
        onMessage(evt)
    };
    websocket.onerror = function (evt) {
        onError(evt)
    };
}

function onOpen(evt, wsUri) {
    writeToScreen("<span style='color:red'>连接成功，现在你可以发送信息进行测试了！</span>");
    writeToScreen(wsUri);

    SendMessage();
}

function onClose(evt) {
    writeToScreen("<span style='color:red'>Websocket连接已断开！</span>");
    websocket.close();
}

function binaryData(data) {
    return JSON.parse(new TextDecoder("utf-8").decode(new Uint8Array(data)))
}

function onMessage(evt) {
    let externalMessage = binaryData(evt.data);
    let bizData = externalMessage.data;
    let bizDataJson = binaryData(bizData);
    externalMessage.data = bizDataJson;

    console.log(bizDataJson);

    let json = JSON.stringify(externalMessage);

    writeToScreen('<span style="color:blue">服务端回应&nbsp;' + formatDate(new Date()) + '</span><br/><span>' + json + '</span>');
}

function onError(evt) {
    writeToScreen('<span style="color: red;">发生错误:</span> ' + evt.data);
}

function SendMessage() {
    var data = {
        name: "英雄无敌-3"
        // value : "英雄无敌"
    }

    var externalMessageBytes = createExternalMessage(data);

    websocket.send(externalMessageBytes);
}

function createExternalMessage(data) {

    var message = {
        cmdCode: 1,
        cmdMerge: merge(19, 1),
        data: data
    }

    var json = JSON.stringify(message);
    writeToScreen('<span style="color:green">你发送的信息&nbsp;' + formatDate(new Date()) + '</span><br/>' + json);

    var textEncoder = new TextEncoder();
    var dataArray = textEncoder.encode(JSON.stringify(data));

    message.data = Array.from(dataArray);

    json = JSON.stringify(message);

    return textEncoder.encode(json);
}

function merge(cmd, subCmd) {
    return (cmd << 16) + subCmd;
}

function writeToScreen(message) {
    var div = "<div>" + message + "</div>";
    var d = $("#output");
    var d = d[0];
    var doScroll = d.scrollTop == d.scrollHeight - d.clientHeight;
    $("#output").append(div);
    if (doScroll) {
        d.scrollTop = d.scrollHeight - d.clientHeight;
    }
}



