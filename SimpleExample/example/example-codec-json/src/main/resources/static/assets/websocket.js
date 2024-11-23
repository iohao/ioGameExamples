function formatDate(now) {
    const year = now.getFullYear();
    let month = now.getMonth() + 1;
    let date = now.getDate();
    let hour = now.getHours();
    let minute = now.getMinutes();
    let second = now.getSeconds();
    return year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (date < 10 ? ("0" + date) : date) + " " + (hour < 10 ? ("0" + hour) : hour) + ":" + (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
}

let websocket;

function addSocket() {
    let wsUri = "ws://127.0.0.1:10100/websocket";
    StartWebSocket(wsUri);
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
    writeToScreen("<span style='color:red'>onOpen Success！</span>");
    writeToScreen(wsUri);

    SendMessage();
}

function onClose(evt) {
    writeToScreen("<span style='color:red'>onClose！</span>");
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

    writeToScreen('<span style="color:blue">ServerResponse&nbsp;' + formatDate(new Date()) + '</span><br/><span>' + json + '</span>');
}

function onError(evt) {
    writeToScreen('<span style="color: red;">onError:</span> ' + evt.data);
}

function SendMessage() {
    let data = {
        name: "英雄无敌-3"
        // value : "英雄无敌"
    }

    let externalMessageBytes = createExternalMessage(data);

    websocket.send(externalMessageBytes);
}

function createExternalMessage(data) {

    let message = {
        cmdCode: 1,
        cmdMerge: merge(19, 1),
        data: data
    }

    let json = JSON.stringify(message);
    writeToScreen('<span style="color:green">YourMessage(你发送的信息)&nbsp;' + formatDate(new Date()) + '</span><br/>' + json);

    let textEncoder = new TextEncoder();
    let dataArray = textEncoder.encode(JSON.stringify(data));

    message.data = Array.from(dataArray);

    json = JSON.stringify(message);

    return textEncoder.encode(json);
}

function merge(cmd, subCmd) {
    return (cmd << 16) + subCmd;
}

function writeToScreen(message) {
    let div = "<div>" + message + "</div>";
    let d = $("#output")[0];
    let doScroll = d.scrollTop == d.scrollHeight - d.clientHeight;
    $("#output").append(div);
    if (doScroll) {
        d.scrollTop = d.scrollHeight - d.clientHeight;
    }
}



