const nb = document.querySelector('.name-block');
const ob = document.querySelector('.output-block');
const ib = document.querySelector('.input-block');

let ws = null, openedSession = false;

nb.addEventListener('blur', handleNameChange);

nb.addEventListener('keydown', (e) =>
{
    if (e.key === 'Enter')
        handleNameChange();
});

nb.addEventListener('focus', (e) =>
{
    nb.style.color = 'black';

    if (ws !== null)
    {
        openedSession = false;

        ws.close();
    }

    nb.value = '';
});


function handleNameChange ()
{
    if(openedSession === true)
        return;

    if (nb.value.length < 2)
    {
        nb.style.color = 'red';

        return;
    }

    openedSession = true;
    openSocket(nb.value)
}

function openSocket (username) {

    ws = new WebSocket(`ws://localhost:8080/chat/${username}`);

    ws.onopen = (wb, event) => {
        console.log(event);
    };

    ws.onerror = (event) => {
        console.log(event)
    }

    ws.onmessage = (e) => {
       writeMessage(JSON.parse(e.data));
    }
}

ib.addEventListener('blur', handleNewMsg);
ib.addEventListener('keydown', (e) =>
{
    if(e.key === 'Enter')
        handleNewMsg();
});

function handleNewMsg ()
{
    ws.send(JSON.stringify(
        {
            from: nb.value,
            content: ib.value
        }
    ));
}

function writeMessage (msgObj)
{
    let tmpl = '<p></p>';

    if (msgObj.from === '@special')
    {
        tmpl = `<span class="user-connected">${msgObj.content}<span/>`

    }
    else
    {
        console.log(new Date(msgObj.timestring).getHours());
        tmpl = `<div class="msg-block">
                    <span class="username-span">${msgObj.from}</span>
                    <p class="users-msg">${msgObj.content}</p>
                </div>`;
    }

    ob.insertAdjacentHTML('afterend', tmpl);
}